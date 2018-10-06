import java.net.*;
import java.io.*;
 
public class A1035531_4 {
  public final static int PORT = 5504; // 13

  public static void main(String[] args) {   
   

   int thePort;
    if(args.length !=1 || (thePort = Integer.parseInt(args[0])) < 0)thePort = PORT;

	
    try (ServerSocket server = new ServerSocket(thePort)) {
      while (true) {  
        try {
          Socket connection = server.accept();
          Thread task = new ProxyThread(connection);
          task.start();
        } catch (IOException ex) {}
      } 
    } catch (IOException ex) {
      System.err.println("Couldn't start server");
    }
  }

  
  private static class ProxyThread extends Thread {
    private Socket connection;
    
    ProxyThread(Socket connection) {
      this.connection = connection;
    }
  
  
    public synchronized String readLine(InputStream in) throws IOException {
        StringBuffer sb = new StringBuffer(80); 
		
        int c;

        while((c = in.read()) != '\n') {
           if(c != '\r') sb.append((char) c);
        }
        return sb.toString();
    }

    public static final int TIMEOUT = 15000;
    public void CopyTo(InputStream in, OutputStream out) {
      int b;

      try {
        while((b = in.read()) >= 0) {
  	out.write(b);
        }
      } catch(Exception ex) {
        System.err.println(ex.getMessage());
      }
    }

	
    public void CopyLine(InputStream in, OutputStream out) {
      try {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        for (String line; (line = reader.readLine()) != null;) {
	  writer.write(line + "\r\n");
          writer.flush();
        }
      } catch(Exception ex) {
        System.err.println(ex.getMessage());
        return;
      }
    }

	
    public class RemoteThread extends Thread {
      private Socket socket = null;
      private InputStream childIn;
      private OutputStream childOut;

      public RemoteThread(InputStream in, OutputStream out) {
        childIn = in;
        childOut = out;
      }

      @Override
      public void run() {
        CopyTo(childIn, childOut);
      }
    }

    public void printConnections(Socket local, Socket remote) {
      String	p =     local.getInetAddress().getHostAddress()
      		+ ":" + local.getPort()
		+ "-" + local.getLocalAddress().getHostAddress()
		+ ":" + local.getLocalPort()
		+ "-" + remote.getLocalAddress().getHostAddress()
		+ ":" + remote.getLocalPort()
		+ "-" + remote.getInetAddress().getHostAddress()
		+ ":" + remote.getPort();
      System.out.println(p);
    }

    @Override
    public void run() {
      try {
        InputStream in = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        String line = readLine(in);
	String[] pieces = line.split(" ");
	String remoteHost = pieces[0];
        int remotePort;

        if((remotePort = Integer.parseInt(pieces[1])) <= 0) remotePort = -1;

	Socket socket = null;
        try {
          socket = new Socket(remoteHost, remotePort);
          socket.setSoTimeout(TIMEOUT);
	  printConnections(connection, socket);
          OutputStream out2 = socket.getOutputStream();
          InputStream in2 = socket.getInputStream();
          Thread t = new RemoteThread(in, out2);
          t.start();
          CopyTo(in2, out);
        } catch (IOException ex) {
          System.err.println(ex);
        } finally {
          try {
	    socket.close();
          } catch (IOException ex) {
          }
          // System.exit(0);
	}
      } catch (IOException ex) {
        System.err.println(ex);
      } finally {
        try {
          connection.close();
        } catch (IOException e) {
			}
      }
    }
  }
}
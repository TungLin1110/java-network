import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class A1035531EX09 {

  private static final Logger logger = Logger.getLogger("Redirector");
  
  public static final String SERVER = "dict.org";
  public static final int PORT = 2628;
  public static final int TIMEOUT = 15000;
  private final int port;
  public A1035531EX09(int port) {
    this.port = port;
  }

  public void start() {
	try (ServerSocket server = new ServerSocket(port)) {
      logger.info("Redirecting connections on port " 
          + server.getLocalPort());
    

	Socket socket = null;
      socket = new Socket(SERVER, PORT);
      socket.setSoTimeout(TIMEOUT);
      OutputStream out = socket.getOutputStream();
      Writer writer = new OutputStreamWriter(out, "UTF-8");
      writer = new BufferedWriter(writer);
      InputStream in = socket.getInputStream();
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(in, "UTF-8"));
	     
		 
		 
      while (true) {
        try {
          Socket s = server.accept();
          Thread t = new RedirectThread(s,writer,reader);
          t.start();
        } catch (IOException ex) {
          logger.warning("Exception accepting connection");
        } catch (RuntimeException ex) {
          logger.log(Level.SEVERE, "Unexpected error", ex);
        }
      } 
    } catch (BindException ex) {
      logger.log(Level.SEVERE, "Could not start server.", ex);
    } catch (IOException ex) {
      logger.log(Level.SEVERE, "Error opening server socket", ex);
    }         
  }
  
  private class RedirectThread extends Thread {
    private final Socket connection;
    private final Writer writer;
    private final BufferedReader reader;    
    RedirectThread(Socket s, Writer writer, BufferedReader reader) {
      this.connection = s;    
	  this.writer=writer;
	  this.reader=reader;
    }
        
    public void run() {
      try {   
        Writer out = new BufferedWriter(
                      new OutputStreamWriter(
                       connection.getOutputStream(), "US-ASCII"
                      )
                     );
        Reader in = new InputStreamReader(
                     new BufferedInputStream( 
                      connection.getInputStream()
                     )
                    );
                    
        // read the first line only; that's all we need
        StringBuilder request = new StringBuilder(80);
        while (true) {
          int c = in.read();
          if (c == '\r' || c == '\n' || c == -1) break;
          request.append((char) c);
        }
        
        String get = request.toString();
        String[] pieces = get.split(" ");	// Bug: "\\w*"
        String theFile = pieces[1].substring(1);
       
        if (get.indexOf("HTTP") != -1) {
    writer.write("DEFINE foldoc " + theFile + "\r\n");
    writer.flush();
        
		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      if (line.startsWith("250 ")) { // OK
        return;
      } else if (line.startsWith("552 ")) { // no match
        out.write("No definition found for " + theFile);
        out.flush();
        return;
      }
      else if (line.matches("\\d\\d\\d .*")) continue;
      else if (line.trim().equals(".")) continue;
      else  out.write(line+"\r\n");
			out.flush();
			}
		} 
		
        logger.log(Level.INFO, 
            "Redirected " + connection.getRemoteSocketAddress());
      } catch(IOException ex) {
        logger.log(Level.WARNING, 
            "Error talking to " + connection.getRemoteSocketAddress(), ex);
      } finally {
        try {
          connection.close();
        } catch (IOException ex) {}  
      }  
    

	}
           }

  public static void main(String[] args) {
	  
	
	
	
	int thePort;
    try {
      thePort = Integer.parseInt(args[1]);
    } catch (RuntimeException ex) {
      thePort = 5531;	// 80
    }   
    
	
	
	A1035531EX09 redirector = new A1035531EX09(thePort);
    redirector.start();
  }
 
 
}

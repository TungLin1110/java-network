import java.net.*;
import java.io.*;
import java.util.concurrent.*;

public class A1035531_3 {
  public final static int PORT = 5503; // 7

  public static void main(String[] args) {
    ExecutorService pool = Executors.newFixedThreadPool(10);
    
	int	thePort;
    if((thePort = Integer.parseInt(args[0])) <= 0) thePort = PORT;

    try (ServerSocket server = new ServerSocket(thePort)) {
      while (true) {
        try {
          Socket connection = server.accept();
          Callable<Void> task = new EchoTask(connection);
          pool.submit(task);
        } catch (IOException ex) {}
      }
    } catch (IOException ex) {
      System.err.println("Couldn't start server");
    }
  }

  private static class EchoTask implements Callable<Void> {
    private Socket connection;

    EchoTask(Socket connection) {
      this.connection = connection;
    }

    @Override
    public Void call() throws IOException {
      try {
        InputStreamReader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        int c;
        while ((c = in.read()) != -1) {
	  
	  if(c == 27) break; 
      
		  out.write(c);
          out.write(c);
          out.flush();
        }
        } catch (IOException ex) {
          System.err.println(ex);
        } finally {
          connection.close();
      }
      return null;
    }
  }
}
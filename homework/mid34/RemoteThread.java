import java.io.*;
import java.net.*;

public class RemoteThread extends Thread {
  public static final int TIMEOUT = 15000;
  private static Socket socket = null;
  private InputStream childIn;
  private OutputStream childOut;

  public static void CopyTo(InputStream in, OutputStream out) {
    int b;

    try {	// Lab2:p4file2scr()
      while((b = in.read()) >= 0) {
        out.write(b);
      }
    } catch(Exception ex) {
      System.err.println(ex.getMessage());
    }
  }

  public static void CopyLine(InputStream in, OutputStream out) {
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

  public RemoteThread(InputStream in, OutputStream out) {
    childIn = in;
    childOut = out;
  }

  @Override
  public void run() {
    CopyTo(childIn, childOut);
    try {
      socket.close();
    } catch (IOException ex) {
      // ignore
    }
    System.exit(0);
  }
  
  public static void main(String[] args) {
    String theSite;
    int thePort;

    theSite = args[0];
    thePort = Integer.parseInt(args[1]);
    try {
      socket = new Socket(theSite, thePort);
      socket.setSoTimeout(TIMEOUT);
      OutputStream out = socket.getOutputStream();
      InputStream in = socket.getInputStream();
      // Create Thread
      Thread t = new RemoteThread(in, System.out);
      t.start();
      CopyLine(System.in, out);
    } catch (IOException ex) {
      System.err.println(ex);
    }
  }
}
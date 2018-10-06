import java.io.*;
import java.util.zip.*;

public class GZipRunnable implements Runnable {

  private final File input;
  
  public GZipRunnable(File input) {
    this.input = input;
  }
  
  @Override
  public void run() {
    // don't compress an already compressed file
    if (!input.getName().endsWith(".b5")) { 
      File output = new File(input.getParent(), input.getName() + ".b5");
      if (!output.exists()) { // Don't overwrite an existing file
        try ( // with resources; requires Java 7
          BufferedReader in = new BufferedReader(
				      new InputStreamReader(
				      new FileInputStream(input), "unicode"));
        BufferedWriter out = new BufferedWriter(
		     		      new OutputStreamWriter(
					new FileOutputStream(output), "big5"));
         ) {
            int b;
            while ((b = in.read()) != -1) out.write(b);
            out.flush();
        } catch (IOException ex) {
          System.err.println(ex);
        }
      }  
    } 
  } 
}
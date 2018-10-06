import java.io.*;

class A1035531_1 {

  public static long CopyTo(InputStream in, OutputStream out) { 
    int b;
    long n = 0;

    try {	
      while((b = in.read()) >= 0) {
        out.write(b);
        n++;
      }
    } catch(Exception ex) {
      System.err.println(ex.getMessage());
      return -1;
    }
    return n;
  }
  public static void main(String[] args) {
    try {
      FileInputStream in = new FileInputStream(args[0]);

      try {
        FileOutputStream out = new FileOutputStream(args[1]);
	long n;
        if((n = CopyTo(in, out)) >= 0) {
	  System.out.println(n);
        }
        out.close();
      } catch(Exception ex) {
        System.err.println(ex.getMessage());
      }
      in.close();
    } catch(Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}
import java.net.*;

public class AllByName {
  public static void main (String[] args) {
    try {
      for (String name : args) {
	InetAddress[] addresses = InetAddress.getAllByName(name);
	for (InetAddress addr : addresses) {
	  System.out.println(addr);
	}
      }
    } catch(Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}

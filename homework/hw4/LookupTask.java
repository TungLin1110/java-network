
import java.net.*; 
import java.util.concurrent.Callable;

public class LookupTask implements Callable<String> {

  private String line;
  
  public LookupTask(String line) {
    this.line = line;
  }
  
  @Override
  public String call() {
    try {
      // separate out the IP address
      //int index = line.indexOf(' ');
      //String address = line.substring(0, index);
	  
	  String address=line;
	  String addresses = InetAddress.getByName(line).getHostAddress();
	  
     // String theRest = line.substring(index);
     // String hostname = InetAddress.getByName(address).getHostName();
      return address+" "+addresses ;
    } catch (Exception ex) {
      return line;
    } 
   } 
  }
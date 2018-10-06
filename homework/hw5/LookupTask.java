import java.net.*; 
import java.util.concurrent.Callable;
import java.util.*;
import java.io.*;

public class LookupTask implements Runnable{

   private int i ;
   private String host;
  
  public LookupTask(String host,int i) {
    this.i = i;
	this.host=host;
  }
  
  @Override
  public void run() {
     try {
        Socket s = new Socket(host,i);
		s.close();
         System.out.println( "There is a server on port " + i + " of "+host );
        
      } catch (UnknownHostException ex) {
        System.err.println(ex);
       
      } catch (IOException ex) {
        
		// must not be a server on this port
      }
	  
	  
  }
}
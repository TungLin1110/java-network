import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*; 

public class A1035531EX08{
	
	
	
public static void main(String[] args) {
       ExecutorService exec = Executors.newFixedThreadPool(4);
	   
	    String host = args.length > 0 ? args[0] : "localhost";
     long startTime = System.currentTimeMillis(); // or System.nanoTime()
	 
	 
       for (int i = 1; i <= 200; i++)
           exec.execute(new LookupTask(host,i));
      
	  exec.shutdown(); 
	  
	 
	 try {boolean finshed = exec.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
	   long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Spent time (ms): " + estimatedTime);
    }

}
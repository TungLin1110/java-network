import java.io.*;

class Lab02 {
    public static void p3kbd2file() {
        byte[]	linebuf = new byte[1024];
	int	n;

	try(FileOutputStream outfile = new FileOutputStream("lab02out.txt")) {
	    try {
                while((n = System.in.read(linebuf)) >= 0) {
                    outfile.write(linebuf, 0, n);
	        }
	    } catch(Exception ex) {
	        System.err.println(ex.getMessage());
	    }
	    try {
	        outfile.close();
	    } catch(Exception ex) {
	        System.err.println(ex.getMessage());
	    }
        } catch(Exception ex) {
	    System.err.println(ex.getMessage());
	}
    }

    public static void p4file2scr(String filename) {
        try(FileInputStream infile = new FileInputStream(filename)) {
	    try {
	        int b, n = 0;

                while((b = infile.read()) >= 0) {
		    System.out.write(b);
		    n++;
		}
		System.out.println("Total: " + n + " Bytes");
	    } catch(Exception ex) {
	        System.err.println(ex.getMessage());
	    }
	    try {
	        infile.close();
	    } catch(Exception ex) {
	        System.err.println(ex.getMessage());
	    }
        } catch(Exception ex) {
	    System.err.println(ex.getMessage());
	} 
    }
    public static void p5file2writer(String filename,String outfilename) 
	{
	String	line;

        try (BufferedReader infile = new BufferedReader(
				      new InputStreamReader(
				      new FileInputStream(filename), "unicode"));
	     BufferedWriter outfile = new BufferedWriter(
		     		      new OutputStreamWriter(
					      new FileOutputStream(outfilename), "big5")))
	{
	  	int b;
		int n=0;

		while((b = infile.read()) >=0) {
		    outfile.write(b);
			n++;
		}
		System.out.println("Total: " + n + " Bytes");
	} catch (Exception ex) {
	    System.err.println(ex.getMessage());
	}
    }

    public static void main(String[] args) {
   
	// p3kbd2file();
	// p4file2scr(args[0]);
	   p5file2writer(args[0],args[1]);
    }
}

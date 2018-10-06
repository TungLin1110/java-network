import java.io.*;

class Lab02 {

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
    // p1kbd2scr();
	// p2bufkbd2scr();
	// p3kbd2file();
	// p4file2scr(args[0]);
	   p5file2writer(args[0],args[1]);
    }
}

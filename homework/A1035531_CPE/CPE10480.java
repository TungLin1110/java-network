import java.util.*;
public class CPE10480 {
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		
		while (in.hasNext())
		{
			int a = in.nextInt();
			int b = in.nextInt();
			int count=0;
		if(a==0&&b==0)
			break;
		for(int i=a;i<=b;i++)	
		{
		if(Math.sqrt(i)%2==0||Math.sqrt(i)%2==1)
			count=count+1;
		}
			System.out.println(count);
		}	
	}
}


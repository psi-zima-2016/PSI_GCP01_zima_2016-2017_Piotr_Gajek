import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Scanner;

public class Hebb 
{
	public static int wymiar = 300;
	public static double[][] p;//= new double[3][2];
	public static double[][] p2;
	public static double[] c = new double[2];
	public static double[] a;//= new double[3];
	public static double[] w = new double[2];
	public static double n = 0.1;
	public static Scanner odczyt;
	public static int count = 350;
	double globalError = 0;
 	double localError = 0;
 	double MSE = 0;
 	double MAPE = 0; 
 	double pom = 0;
 	int iter = 0; 
	
	public Hebb()
	{
		try 
		{
			odczyt = new Scanner(new File("dane.txt"));
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		wymiar = odczyt.nextInt();
 		p = new double[wymiar][2];    
 		a = new double[wymiar];  
		w[0]=1.0;
		w[1]=0.2;
		c[0]=1.0;
		c[1]=1.0;
		int apt=0;
		while(odczyt.hasNextDouble())
		{	
				p[apt][0]=odczyt.nextDouble();
				p[apt][1]=odczyt.nextDouble();	
				a[apt]=1;
				apt++;
		}
	}

	public static double  count(double x1, double x2)
	{
	    double suma = 0;
		suma = x1 * w[0] + x2 * w[1]; 
		if(suma >= 0)
		{
			suma = 1;
		}
		else
		{
			suma = -1;
		}
		return suma;
	}
	
	public static void learn() throws FileNotFoundException 
 	{	
			PrintWriter zapis = new PrintWriter("wagi.txt");
			PrintWriter zapis2 = new PrintWriter("blad.txt");
	 	double globalError = 0;
	 	double localError = 0;
	 	double MSE = 0;
	 	double MAPE = 0; 
	 	double pom = 0;
	 	int iter = 0; 
 		long start=System.currentTimeMillis();
 		do{
 			globalError=0;
 			iter++;
 			for(int i=0; i<wymiar; i++)
 			{
 				pom = a[i];
 				
 				a[i]=count(p[i][0],p[i][1]);
 				//System.out.println("["+w[0]+" "+w[1]+"]"+ " + " + "["+c[0]+ " "+c[1] +"] * "+ a[k] + " * ["+ p[k][0]+" "+p[k][1] + "] " ); 
 				//System.out.println("["+w[0]+" "+w[1]+"]"+  " ["+ p[i][0]+" "+p[i][1] + "] " + " -> "+ a[i] ); 
 				
 				//Bez wspó³czynnika zapominania
 				w[0] = w[0] + (c[0]*a[i]*p[i][0]);
 			    w[1] = w[1] + (c[1]*a[i]*p[i][1]);
 				
 				//Ze wspó³czynnikiem zapominania
 				//w[0] = w[0]*(0.93) + (c[0]*a[i]*p[i][0]);
 			    //w[1] = w[1]*(0.93) + (c[1]*a[i]*p[i][1]);
 				
 				//Regu³a Oji
 				//w[0] = w[0]+(c[0]*a[i]*(p[i][0]-a[i]*w[0]));
 				//w[1] = w[1]+(c[1]*a[i]*(p[i][1]-a[i]*w[1]));
 				
 				w[0]=new BigDecimal(w[0]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 				w[1]=new BigDecimal(w[1]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
 				
 				localError = Math.abs(pom - a[i]);
 				globalError = globalError + (localError*localError);
 				zapis.println(w[0] +" "+ w[1]);
 			}	
				MSE = Math.pow(globalError, 2)/wymiar;
				MAPE =  (globalError*10) / wymiar; 
				zapis2.println(MSE);
	 			System.out.println("MSE: " + MSE + " MAPE: " + MAPE + " %");
 			} while( globalError!=0 && iter < 10000);
 			long stop=System.currentTimeMillis();
 			double time = stop - start;
 			
 			System.out.println("Czas wykonania:"+time+" ms");	
 			
	}
	
	public static void testuj()
	{
		double globalError = 0;
	 	double localError = 0;
		double MSE = 0;
	 	double MAPE = 0; 
	 	double pom = 0;
	 	int iter = 0; 
		try 
		{
			odczyt = new Scanner(new File("walidacja.txt"));
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		wymiar = odczyt.nextInt();
		p2 = new double[wymiar][2];    
		int apt=0;
		while(odczyt.hasNextDouble())
		{	
				p2[apt][0]=odczyt.nextDouble();
				p2[apt][1]=odczyt.nextDouble();				
				apt++;
		}
		System.out.println("--- Walidacja ---");
		do{
			globalError=0;
 			iter++;
		for(int i=0;i<wymiar;i++)
		{
			pom = a[i];
			a[i]=count(p[i][0],p[i][1]);
			System.out.println("["+p2[i][0]+" "+p2[i][1]+"] " + " -> " + pom);
			localError = Math.abs(pom - a[i]);
			globalError = globalError + (localError*localError);
		}
		MSE = Math.pow(globalError, 2)/wymiar;
		MAPE =  (globalError*10) / wymiar; 
		System.out.println("MSE: " + MSE + " MAPE: " + MAPE + " %");
		}while(globalError!=0 && iter < 10);
	}
	
	public static void main(String[] args) throws FileNotFoundException
		{
			Hebb h = new Hebb();
			h.learn();
			h.testuj();
		}
}

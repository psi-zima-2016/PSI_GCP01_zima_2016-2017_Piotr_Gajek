import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Neuron {
	
		private static final Scanner NULL = null;

		public static Random los = new Random();

	 	public double suma = 0;
	 	public double[] w;                  //tablica wag
	 	public double[][] x;				//tablica wartosci
	 	public double[] outputs;
	 	public int wymiar = 0; 				 
	 	public int kroki = 50;               //ilosc powtorzen
	 	public double eta = 0.1;            //współczynik uczenia                 
	 	public double y = 0;     
	 	public int theta = 1;//wyjscie neuronu 
	 	public double localError = 0;

	 	public Neuron()
	 	{
	 		wymiar = 20;  
	 		x = new double[2][wymiar];          //20 przykładów
	 		w = new double[3];      		    //wag tyle co wejść+1   
	 	    outputs = new double[wymiar];       //20 wyjść
	 	    losujWagi();	 	  
	 	}		


	 	public void losujWartosci()
	 	{
	 		for(int i=0;i<wymiar;i++)
	 		{
	 			x[0][i] = los.nextInt(20)-10;
	 			x[1][i] = los.nextInt(20)-10;
	 		}
	 	}

	 	public void losujWagi()
	 	{
	 		for(int i=0;i<3;i++)
	 		{
	 			w[i] = (los.nextDouble() * 2) - 1;
	 		}
	 	}


	 	public void ucz() throws FileNotFoundException

	 	{
					Scanner odczyt = new Scanner(new File("plik.txt"));
	 				int apt=0;
					while(odczyt.hasNextDouble())
						{
	 						x[0][apt]=odczyt.nextDouble();
	 						x[1][apt]=odczyt.nextDouble();
	 						outputs[apt]=odczyt.nextDouble();
	 						apt++;
						}						
		 	int iter = 0;	
		 	int globalError = 0;
	 		do
	 		{
	 			globalError = 0;
	 			iter++;
	 			for(int p=0; p<wymiar; p++)
	 			{
		 			y = licz(x[0][p],x[1][p]); 
		 			localError = outputs[p]-y;
		 			w[0] += eta*localError*x[0][p];
		 			w[1] += eta*localError*x[1][p];
		 			w[2] += eta*localError;
		 			globalError += (localError*localError);
		 			System.out.println(x[0][p] +" "+x[1][p] +" "+ outputs[p]+" => "+y );
	 			}
	 			System.out.println("Iteracja: "+iter);
	 			System.out.println("Błąd = "+Math.sqrt(globalError/wymiar));
	 		} while(globalError!=0 && iter < 1000);
	 		
	 		
	 		System.out.println("Nauczony! "+ w[0]+" " + w[1] + " " + w[2] );	
	 		losujWartosci();
	 		for(int i=0;i<wymiar;i++)
	 		{
	 			double pom = testuj(x[0][i],x[1][i]);
		 		System.out.println( x[0][i]+" "+x[1][i]+" => "+pom);
	 		}
	 		
		}

		public double licz(double x1, double x2)
		{
			suma = 0;
			suma = x1 * w[0] + x2 * w[1] + w[2]; //sumator
			if(suma >= theta)
			{
				suma = 1;
			}
			else
			{
				suma = 0;
			}
			return suma;
		}
		
		
		public double testuj(double a, double b)
		{
			double apt = 0;
			apt = w[0]*a + w[1]*b + w[2];
			if(apt >= 1)
			{
				apt = 1;
			}
			else
			{
				apt = 0;
			}
			return apt;
		}

		 public static void main(String[] args) throws FileNotFoundException

		{
			 Neuron neuron = new Neuron();// tworzymy neuron o n wejsciach i takim wymiarze
			 neuron.ucz();
		}
}

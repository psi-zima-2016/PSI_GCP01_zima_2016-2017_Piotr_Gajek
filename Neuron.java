import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
	 	public double eta = 0.10;            //wsp�czynik uczenia                 
	 	public double y = 0;     
	 	public double theta = 1;//wyjscie neuronu 
	 	public double localError = 0;
	 	public Scanner odczyt;
	 	public int in;
	 	
	 	public Neuron()
	 	{
	 		try {
				odczyt = new Scanner(new File("plik.txt"));
				//odczyt = new Scanner(new File("walidacja.txt"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 		in = 2;
	 		wymiar = odczyt.nextInt();
	 		x = new double[in][wymiar];          //20 przyk�ad�w
	 		w = new double[in+1];      		    //wag tyle co wej��+1   
	 	    outputs = new double[wymiar];       //20 wyj��
	 	    losujWagi();
	 	    //setWagi(0.592,0.8014, 0.9516);
	 	 // setWagi(0.8225330269184498 ,0.8408765257064726, 0.6887539253404061);
	 	    setWagi(-1.5842831515218556,0.30288259749225244,-21.980074659541888);

	 	}		

	 	public void losujWartosci()
	 	{
	 		for(int i=0;i<wymiar;i++)
	 		{
	 			x[0][i] = los.nextInt(20)-10;
	 			x[1][i] = los.nextInt(20)-10;
	 			//x[2][i] = los.nextInt(20)-10;
	 		}
	 	}


	 	public void losujWagi()
	 	{
	 		for(int i=0;i<in+1;i++)
	 		{
	 			w[i] = (los.nextDouble() * 2) - 1;
	 		}
	 	}

	 	public void setWagi(double a, double b, double c)
	 	{
	 			w[0] =  a;
	 			w[1] =  b;
	 			w[2] =  c;
	 	}

	 	public void ucz() throws FileNotFoundException
	 	{		
			PrintWriter zapis = new PrintWriter("blad.txt");
	 				int apt=0;
					while(odczyt.hasNextDouble())
						{	
	 						x[0][apt]=odczyt.nextDouble();
	 						x[1][apt]=odczyt.nextDouble();
	 						outputs[apt]=odczyt.nextDouble();
	 						apt++;
						}						
		 	int iter = 0;	
		 	double globalError;
		 	double MSE =0;
	 		long start=System.currentTimeMillis();

	 		do
	 		{	
	 			globalError=0; //suma
	 			iter++;
	 			for(int p=0; p<wymiar; p++)
	 			{
	 				y = licz(x[0][p],x[1][p]); 
		 			localError = outputs[p]-(int)y;//1 +1
		 			w[0] += eta*localError*x[0][p];
		 			w[1] += eta*localError*x[1][p];
		 			w[2] += eta*localError; //bias
		 			globalError = globalError + (localError*localError); //suma bledow
		 			System.out.println(x[0][p] +" "+x[1][p] +" "+ outputs[p]+" => "+y );
	 			}
	 			MSE = Math.pow(globalError, 2)/wymiar;
	 			System.out.println("Iteracja: "+iter+ " MSE = " + MSE );
		 		zapis.println(MSE);
	 		} while(globalError!=0 && iter < 100000);
 			long stop=System.currentTimeMillis();

	 		zapis.close();
	 		System.out.println("Nauczony! ");
	 		double time = stop - start;
 			
 			System.out.println("Czas wykonania:"+time+" ms");	
	 		System.out.println(w[0]);
	 		System.out.println(w[1]);
	 		System.out.println(w[2]);
	 		//sprawdzamy czy dzia�a
//	 		losujWartosci();
//	 		for(int i=0;i<wymiar;i++)
//	 		{
//	 			double pom = testuj(x[0][i],x[1][i]);
//		 		System.out.println( x[0][i]+" "+x[1][i]+" "+"=> "+ pom);
//	 		}
	 		
		}

		public double licz(double x1, double x2)
		{
			suma = 0;
			suma = x1 * w[0] + x2 * w[1] +w[2]; //sumator
			
			//Funkcja progowa unipolarna
    		if(suma >= theta) suma = 1;
			else suma = 0;
			
			//Funkcja progowa bipolarna
//			if(suma >= theta) suma = 1;
//			else suma = -1;
			
			//Funkcja lniowa
//			double a = 0.5;
//			double b = 0;   
//			suma = a*suma+b; Y = AX+B
//    		if(suma >= 0) suma = 1;
//			else suma = -1;
			
			//Obci�ta funkcja linowa
//			if(suma<-1) suma = -1;
//			else if(suma>=-1 && suma<=1) suma = suma;
//			else suma = 1;
			
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
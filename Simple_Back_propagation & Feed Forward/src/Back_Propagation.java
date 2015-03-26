import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Back_Propagation {
	static int n,m,l;
	static ArrayList<float []>X = new ArrayList<float[]>();
	static ArrayList<float []>Y = new ArrayList<float[]>();
	static Matrix WHI ;
	static Matrix WOH ;
	
	

	public static void readFile() throws FileNotFoundException
	{
		FileReader train = new FileReader("train.txt");
		BufferedReader trainbuf = new BufferedReader(train);
		Scanner sc = new Scanner(train);
		int k;
		
		m = sc.nextInt();
		l = sc.nextInt();
		n = sc.nextInt();
		k = sc.nextInt();

		WHI = new Matrix(l, m);
		WOH = new Matrix(n, l);
		setWeights();
		
		for (int i = 0; i < k; i++) {
			float []x = new float[m];
			for (int j = 0; j < x.length; j++) {
				x[j] = sc.nextFloat();
			}
			
			float []y = new float[n];
			for (int j = 0; j < y.length; j++) {
				y[j] = sc.nextFloat();
			}
			
			X.add(x);
			Y.add(y);
			
		}
		
		
	}
	
	private static void setWeights()
	{
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < m; j++) {
				WHI.array[i][j] = -5 + (float)Math.random()*10;
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < l; j++) {
				WOH.array[i][j] = -5 + (float)Math.random()*10;
			}
		}
		
		
	}
	//////////////////////////////////////////////////////////////////////
	public static void back_probagate_train()
	{
		try {
			readFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float learningRate = 0.2f;
		float totalMSE = 1;
		// train and update for 500 trial or MES < 0.01
		
		for(int i=0;i<1000 && totalMSE > 0.01f;i++)
		{
			totalMSE = 0;
			float MSE = 0;
			for (int j = 0; j < X.size(); j++) {
				float []I = null;
				I = new float[l];
				
				float []x = X.get(j);
				float []O = calculateOutput(x, I);
				float []y = Y.get(j);
				float []segmaK = new float [O.length];
				float []segmaOK ;
		            
				for (int k = 0; k < segmaK.length; k++) {
					segmaK [k] = y[k] - O[k];	
				}
				
				segmaOK = calculateComulativeErrors(O, segmaK);
				
				/////////////// Update
				// update hidden weights first
				//System.out.println("I");
//				for (int k = 0; k < I.length; k++) {
//					System.out.println(I[k] + " ");
//				}
				float []segmaHJ = new float[I.length];
				
				for (int jj = 0; jj < segmaHJ.length; jj++) {
					float summesion = 0;
					
					for (int k = 0; k < segmaK.length; k++) {
						summesion += (segmaK[k] * WOH.array[k][jj]);
					}
					segmaHJ[jj] = I[jj] * (1 - I[jj]) * summesion;
				}
				
				for (int jj = 0; jj < WHI.rows; jj++) {
					
					for (int ii = 0; ii < WHI.columns; ii++) {
						WHI.array[jj][ii]+= (learningRate * segmaHJ[jj] * x[ii]);
						
					}
				}
				
				/////////////////////////////////////////////////////////
				// Update output weights
				for (int kk = 0; kk < WOH.rows; kk++) {
					
					for (int jj = 0; jj < WOH.columns; jj++) {
						WOH.array[kk][jj]+= (learningRate * segmaOK[kk] * I[jj]);
						
					}
				}
				
				// calc mse
				MSE = 0;
				
				for (int k = 0; k < segmaK.length; k++) {
					MSE += (segmaK[k] * segmaK[k]);
				}
				MSE /= 2;

//				System.out.println("totalMSE = " + MSE);
				totalMSE += MSE;
//				System.out.println("totalMSE = " + totalMSE);
			}
			
			totalMSE /= X.size();
			
		}
		
		
		// store m l n and WHI then WOH on file weights.txt
		StringBuilder output = new StringBuilder("");
		for (int i = 0; i < WHI.rows; i++) {
			for (int j = 0; j < WHI.columns; j++) {
				output.append(Float.toString(WHI.array[i][j]) + " ");	
			}
			output.append("\n");
		}
		
		for (int i = 0; i < WOH.rows; i++) {
			for (int j = 0; j < WOH.columns; j++) {
				output.append(Float.toString(WOH.array[i][j]) + " ");	
			}
			output.append("\n");
		}
		File f = new File("weights.txt");
		DealWithFiles.printOnFile(f, output.toString());
		System.out.println("Total MSE for training = " + totalMSE);
	}
	///////////////////////////////////////
	// uses weights and calc the error 
	public static float[] calculateOutput(float []x, float []I)
	{
		float []output = new float[n];
		
		float []netH = Matrix.Multiplication(WHI, x);
		
		
		for (int j = 0; j < I.length; j++) {
			I[j] = Calculate_segmoid(netH[j]);
		}
		
		float []netO = Matrix.Multiplication(WOH, I);
		
		for (int j = 0; j < output.length; j++) {
			output[j] = Calculate_segmoid(netO[j]);
		}
		
		return output;
	}
	
	
	
	
	private static float Calculate_segmoid(float x){
		float res =(float) (1/(1+Math.exp(-(x))));
		return res;
	}
	
	private static float[] calculateComulativeErrors(float []o,float []s){
		float []res = new float[s.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = o[i]*(1-o[i])*s[i];
		}
		return res;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public static float feedForward ()
	{
		try {
			readFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileReader train = null;
		try {
			train = new FileReader("weights.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader trainbuf = new BufferedReader(train);
		Scanner sc = new Scanner(train);
		WHI = new Matrix(l, m);
		WOH = new Matrix(n, l);
		
		for (int i = 0; i < WHI.rows; i++) {
			for (int j = 0; j < WHI.columns; j++) {
				WHI.array[i][j] = sc.nextFloat();
			}
			
		}
		
		for (int i = 0; i < WOH.rows; i++) {
			for (int j = 0; j < WOH.columns; j++) {
				WOH.array[i][j] = sc.nextFloat();	
			}
			
		}
		
		float totalMSE = 0;
		float MSE = 0;
		for (int j = 0; j < X.size(); j++) {
			float []I = null;
			I = new float[l];
			float []x = X.get(j);
			float []O = calculateOutput(x, I);
			float []y = Y.get(j);
			float []segmaK = new float [O.length];
		       
			for (int k = 0; k < segmaK.length; k++) {
				segmaK [k] = y[k] - O[k];	
			}
			
			MSE = 0;
			
			for (int k = 0; k < segmaK.length; k++) {
				MSE += (segmaK[k] * segmaK[k]);
			}
			MSE /= 2;
			
			totalMSE += MSE;
			
		
		}
		
		return totalMSE;
		
	}
	
}

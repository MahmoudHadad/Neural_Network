import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;




public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//train();
		test();
	}
	
	public static void train () throws IOException
	{
		try {
			Back_Propagation.readFile();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int k =  5;
		int partitionSize = Back_Propagation.XX.size() / k;
		float totalTestError = 0;
		float totalTrainError = 0;
		
		
		for(int i=0;i<k;i++)
		{
			
			ArrayList<float[]>YTest = new ArrayList<float[]>( Back_Propagation.YY.subList( partitionSize*i, (i+1)*partitionSize ) );
			ArrayList<float[]>XTest = new ArrayList<float[]>( Back_Propagation.XX.subList( partitionSize*i, (i+1)*partitionSize ) );
		
			ArrayList<float[]>YTrain = (ArrayList<float[]>) Back_Propagation.YY.clone() ;
			ArrayList<float[]>XTrain = (ArrayList<float[]>) Back_Propagation.XX.clone();
			
			YTrain.subList( partitionSize*i, (i+1)*partitionSize ).clear();
			XTrain.subList( partitionSize*i, (i+1)*partitionSize ).clear();
			
			totalTrainError += Back_Propagation.back_probagate_train(XTrain, YTrain);
			totalTestError += Back_Propagation.feedForward(XTest, YTest);
			
			System.out.println("total train error = " + totalTrainError);
			System.out.println("total test error = " + totalTestError);
			
		}
		
		totalTestError /=k;
		totalTrainError /=k;
		
		System.out.println("Final total train error = " + totalTrainError);
		System.out.println("Final test error = " + totalTestError);
		//////////////////////////////////
		
		File file = new File("model.txt");
		FileWriter fileWriter = null;
		BufferedWriter writer = null;
		try {
			
			fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.write(Back_Propagation.l+" ");
		writer.write(Back_Propagation.learningRate+" ");
		writer.write(Back_Propagation.gamma+" ");
		writer.newLine();
		
		writer.write(Back_Propagation.m+" ");
		writer.write(Back_Propagation.n+" ");
		writer.newLine();
		
		for (int i = 0; i < Back_Propagation.l; i++) {
			for (int j = 0; j < Back_Propagation.m; j++) {
				writer.write(Back_Propagation.WHI.array[i][j]+" ");
			}
			writer.newLine();
		}
		writer.newLine();
		
		for (int i = 0; i < Back_Propagation.n; i++) {
			for (int j = 0; j < Back_Propagation.l; j++) {
				writer.write(Back_Propagation.WOH.array[i][j]+" ");
			}
			writer.newLine();
		}
		writer.close();
		fileWriter.close();
	}
	////////////////////////////////////////////////////////////////////
	public static void test () throws FileNotFoundException
	{
		// read model.txt
		FileReader train = new FileReader("model.txt");
		BufferedReader trainbuf = new BufferedReader(train);
		Scanner sc = new Scanner(train);
		
		Back_Propagation.l = sc.nextInt();
		Back_Propagation.learningRate = sc.nextFloat();
		Back_Propagation.gamma = sc.nextFloat();
		
		
		Back_Propagation.m = sc.nextInt();
		Back_Propagation.n = sc.nextInt();
		
		Back_Propagation.WHI = new Matrix(Back_Propagation.l, Back_Propagation.m);
		Back_Propagation.WOH = new Matrix(Back_Propagation.n, Back_Propagation.l);
		
		
		for (int i = 0; i < Back_Propagation.l; i++) {
			for (int j = 0; j < Back_Propagation.m; j++) {
				Back_Propagation.WHI.array[i][j] = sc.nextFloat();
			}
		}
		
		for (int i = 0; i < Back_Propagation.n; i++) {
			for (int j = 0; j < Back_Propagation.l; j++) {
				Back_Propagation.WOH.array[i][j] = sc.nextFloat();
			}
		}
		
		////////
		// read test.text
		
		FileReader test = new FileReader("test.txt");
		BufferedReader testbuf = new BufferedReader(test);
		Scanner sctest = new Scanner(test);
		
		
		int m = sctest.nextInt();
		int n = sctest.nextInt();
		int k = sctest.nextInt();
		
//		System.out.println(m);
//		System.out.println(n);
//		
//		System.out.println(Back_Propagation.m);
//		System.out.println(Back_Propagation.l);
//		System.out.println(Back_Propagation.n);
		System.out.println("____________________________________");
		
		
		Back_Propagation.XX = new ArrayList<float[]>();
		Back_Propagation.YY = new ArrayList<float[]>();
		
		for (int i = 0; i < k; i++) {
			float []x = new float[m];
			for (int j = 0; j < x.length; j++) {
				x[j] = sctest.nextFloat();
			}
			
			float []y = new float[n];
			for (int j = 0; j < y.length; j++) {
				y[j] = sctest.nextFloat();
			}
			
			Back_Propagation.XX.add(x);
			Back_Propagation.YY.add(y);
			
		}
		
		//////////////////////////////////////////////////////////////////
		// call feedforward
		System.out.println(Back_Propagation.feedForward(Back_Propagation.XX, Back_Propagation.YY));
		
	}
}

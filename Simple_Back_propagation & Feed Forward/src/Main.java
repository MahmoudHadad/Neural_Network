
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Back_Propagation.back_probagate_train();
		//System.out.println(Back_Propagation.feedForward());
		
		/////////////////////////////////

		float[][] m = new float[2][2];
		m[0][0] = 1;
		m[0][1] = 1;
		m[1][0] = 2;
		m[1][1] = 2;
		float[][] m2 = new float[2][1];
		m2[0][0] = 1;
		m2[1][0] = 1;
		
		float [][]res = Matrix.Multiplication(m, m2);
		
		for (int i = 0; i < res.length; i++) {
			for (int j = 0; j < res[0].length; j++) 
				System.out.println(res[i][j]);
		
		}
	}
}

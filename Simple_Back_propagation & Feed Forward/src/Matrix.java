
public class Matrix {
	public int rows;
	public int columns;
	public float [][]array;
	public Matrix(int length, int width) {
		super();
		this.rows = length;
		this.columns = width;
		
		array = new float[rows][columns];
	}
	
	public static  Matrix Multiplication(Matrix m1,Matrix m2 ){
		
			Matrix result = new Matrix(m1.rows,m2.columns);    
	  
			float mult;
			  
		    for(int i=0;i<m1.rows;i++)
		    {
		    	
		      for(int j=0;j<m2.columns;j++)
		      {
		      	mult=0;
		      	for(int k=0;k<m1.columns;k++) 
		          {
		          	mult+=m1.array[i][k]* m2.array[k][j];
		          }
		          result.array[i][j]=mult;
		                 
		      }
		    }
		    return result;
	}
	
	public static  float[] Multiplication(Matrix m1, float[] arr){
		
		Matrix m2 = new Matrix(arr.length, 1);
		
		for (int i = 0; i < arr.length; i++) {
			m2.array[i][0] = arr[i];
			
		}
		Matrix result = Multiplication(m1, m2);

		float[] ret = new float[m1.rows];
		for (int i = 0; i < m1.rows; i++) {
			ret[i] =  result.array[i][0] ;
			
		}
		//System.out.println("/////////////////////////////////////////////////");
		
		return ret;
	}
	
	//////////////////////////////////////////
public static  float[] Multiplication(float[][] m, float[] arr){
		
		Matrix m2 = new Matrix(arr.length, 1);
		
		for (int i = 0; i < arr.length; i++) {
			m2.array[i][0] = arr[i];
			
		}
		Matrix m1 = new Matrix(m.length, m[0].length);
		Matrix result = Multiplication(m1, m2);

		float[] ret = new float[m1.rows];
		for (int i = 0; i < m1.rows; i++) {
			ret[i] =  result.array[i][0] ;
			
		}
		//System.out.println("/////////////////////////////////////////////////");
		
		return ret;
	}
	//////////////////////////////////////////
	public static  float[][] Multiplication(float[][]m1, float[][] m2){
	
		float[][] result = new float [m1.length][m2[0].length];
		
		float mult;
		  
	    for(int i=0;i<m1.length;i++)
	    {
	    	
	      for(int j=0;j<m2[0].length;j++)
	      {
	      	mult=0;
	      	for(int k=0;k<m1[0].length;k++) 
	          {
	          	mult+=m1[i][k]* m2[k][j];
	          }
	          result[i][j]=mult;
	                 
	      }
	    }
			
		return result;
	}

}
	
	




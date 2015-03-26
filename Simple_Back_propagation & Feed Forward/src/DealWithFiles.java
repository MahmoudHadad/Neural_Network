

import java.io.*;

import org.omg.CORBA_2_3.portable.OutputStream;

public class DealWithFiles {
	
	public static StringBuilder readFile(File file)
	{
		StringBuilder input = new StringBuilder("");
		
		try 
		{
			InputStream in = new FileInputStream(file);
			BufferedInputStream buf = new 	BufferedInputStream(in);
			
			while(true)
			{
				int data = buf.read();
				
				if(data ==-1 )
					break;
				
				input.append((char)data);
				
			}
		}
		
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return input;
	}
	
	
	public static void printOnFile(File file, String text)
	{
		
		try {
			java.io.OutputStream out = new FileOutputStream(file);
			out.write(text.getBytes());
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}

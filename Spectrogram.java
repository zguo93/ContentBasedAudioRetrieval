import java.util.*;
public class Spectrogram {
	final int ChunkSize=2048;// Every 2048 points is regarded as a chunk of data, which will be inputed to FFT
	int numofchunk;
	Complex[][] array;
	double[][] array_double;
	
	int[][] points;//maximum points in each chunk
	
	
	public final int[] freq_range=new int[] {40,80,120,180,300}; 
	//Frequency range which the maximum search is used by.
	
	public Spectrogram(int length,int[] music){
		//input the total length of music(samples)
		numofchunk=length/ChunkSize-1;
		array=new Complex[numofchunk][ChunkSize];
		for(int i=0;i<numofchunk;i++)
		{
			Complex[] temp=new Complex[ChunkSize];
			for(int j=0;j<ChunkSize;j++)
			{
				temp[j]=new Complex((double)music[i*ChunkSize+j]/32767,0);
			}
			array[i]=FFT.fft(temp);
		}
	}
	public double[][] getArray()
	{
		array_double=new double[numofchunk][ChunkSize];
		for(int i=0;i<numofchunk;i++)
		{
			
			for(int j=0;j<ChunkSize;j++)
			{
				array_double[i][j]=array[i][j].abs();
			}
			
		}
		return array_double;
	}
	public int getNumOfChunk()
	{
		return numofchunk;
	}
	
	public int[][] findMax()
	{
		//use after getArray function
		//Find max point in frequency domain(using array_double)
		//Freq_range will be used here.
		points=new int[numofchunk][freq_range.length-1];
		for(int num=0;num<numofchunk;num++)
		{
			//each chunk has its frequency response
			for(int i=0;i<freq_range.length-1;i++)
			{
				double max=-2;
				for(int j=freq_range[i];j<freq_range[i+1];j++)
				{
					if(array_double[num][j]>max)
					{
						max=array_double[num][j];
						points[num][i]=j;
						
					}
				}
			}
		}
		
		return points;
	}
	
	
	
	
	public static void main(String[] args)
	{
		ReadAudio audio=new ReadAudio();
		int[] s=new int[audio.getLength()];
		audio.getData(s);
		
		Spectrogram test=new Spectrogram(s.length,s);
		double[][] d=test.getArray();
		System.out.println("test for double array");
		
		int[][] max=test.findMax();
		System.out.println("test for max points");
		
		
		HashMap<Long, String> map=new HashMap<Long, String>(); 
		map.put((long) 1, "haha");
		map.get((long)1);
		System.out.println(""+map.containsKey((long)1)+" get value "+map.get((long)1));
		
		

	}
	
}

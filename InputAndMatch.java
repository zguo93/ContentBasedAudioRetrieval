//Produced by Zichang Guo
//lessons: 1. Use double hashmap to match multiple keys with values
//		   2. Use integer to control the decimal of double number

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class InputAndMatch {
	HashMap<Long, ArrayList<Data>> table;
	long[] hash;
	ArrayList<Data> list;
	int DecimalFactor;// this factor will round the time every (0.5/DecimalFactor) sec to control the matching
	
	long startTime;// matching starts time
	long endTime;//matching ends time
	
	
	
	public InputAndMatch(int Decimal)
	{
		//Decimal means, points within 2*0.5/Decimal seconds will  be regarded as 1 single time.
		table =new HashMap<Long,ArrayList<Data> >();
		DecimalFactor=Decimal;
	}
	
	

	public void points2hash(int length, int[][] points,String s)
	{
		//transfer maximum points in each chunk into hash.
		//length is the number of chunks
		//points stores maximum value of each chunk
		//s is the name of song
		hash=new long[length];

		for(int i=0;i<length;i++)
		{
			long p1 = points[i][0];
		    long p2 = points[i][1];
		    long p3 = points[i][2];
		    long p4 = points[i][3];
		    hash[i]= (p4) * 100000000 + (p3) * 100000 + (p2) * 100 + p1;
		    Data data=new Data((int)(i*(double)(2048*DecimalFactor)/(double)(44100)),s);

		    if(!table.containsKey(hash[i]))
		    {
		    	list=new ArrayList<Data>();
		    	list.add(data);
		    	table.put(hash[i], list);
		    }
		    else{
		    	ArrayList<Data> temp;
		    	temp=table.get(hash[i]);
		    	temp.add(data);
		    	table.put(hash[i], temp);
		    }
		   
		}
		
	    
		
	}
	public String match(int length, int[][] points)
	{
		//transfer maximum points in each chunk into hash.
		//length is the number of chunks
		//points stores maximum value of each chunk
		//s is the name of song
		startTime=System.currentTimeMillis();
		
		hash=new long[length];
		HashMap<String,Map<Integer,Integer>> match_data=new HashMap<String, Map<Integer,Integer>> ();
		//first integer is time, which is 100 times than original time.
		
		int max=0;
		String best_match = null;
		
		for(int i=0;i<length;i++)
		{
			
			long p1 = points[i][0];
		    long p2 = points[i][1];
		    long p3 = points[i][2];
		    long p4 = points[i][3];
		    
		    long[] RangedData=DataTolerance(p1,p2,p3,p4,2);
		    
		    
		    
		    for(int temp=0;temp<RangedData.length;temp++)
		    {
		    	hash[i]= RangedData[temp];
		    	
		    	int time_diff=(int)(i*(double)(2048*DecimalFactor)/(double)(44100));
			    ArrayList<Data> returnedList; 
			    //to store the obtained ArrayList if table contains same key.
			    if(table.containsKey(hash[i]))
			    {
			    	returnedList=table.get(hash[i]);

			    	for(int j=0;j<returnedList.size();j++)
			    	{
			    		int t=time_diff-returnedList.get(j).time;
			    		String name=returnedList.get(j).name;
			    		//find the song name, then use 
			    		Map<Integer,Integer> CountofMatch=match_data.get(name);
			    		if(CountofMatch==null)
			    		{
			    			//if song name cannot be found, then put in a new one
			    			CountofMatch=new HashMap<Integer,Integer>();
			    			match_data.put(name,CountofMatch);
			    		}
			    		else{
			    			//if found the hash table with name, then looking for time.
			    			Integer count=CountofMatch.get(t);
			    			if(count==null)
			    			{
			    				CountofMatch.put(t, 1);
			    			}
			    			else{
			    				//update the count of (name, time)
			    				CountofMatch.put(t,count+1);
			    				if(count+1>max)
			    				{
			    					max=count+1;
			    					best_match=name;
			    				}
			    					
			    			}
			    		}
			    		
			    		
			    	}
			    }

		    }
		    
		}
		endTime=System.currentTimeMillis();
		System.out.println("the best match has "+max+" matches"+" searching time is "+(endTime-startTime));
		
		return best_match;
	}

	private long[] DataTolerance(long p1,long p2,long p3, long p4,int tolerance)
	{
		//translate the 4 points to a long hash
		//each point will have this (point-tolerence, point+tolence) many points to match.
		long [] hash=new long [(2*tolerance+1)*4];
		for(int i=-tolerance;i<tolerance;i++)
		{
			hash[i+tolerance]= (p4) * 100000000 + (p3) * 100000 + (p2) * 100 + p1+tolerance;
		}
		for(int i=-tolerance;i<tolerance;i++)
		{
			hash[i+3*tolerance+1]= (p4) * 100000000 + (p3) * 100000 + (p2+tolerance) * 100 + p1;
		}
		for(int i=-tolerance;i<tolerance;i++)
		{
			hash[i+5*tolerance+2]= (p4) * 100000000 + (p3+tolerance) * 100000 + (p2) * 100 + p1;
		}
		for(int i=-tolerance;i<tolerance;i++)
		{
			hash[i+7*tolerance+3]= (p4+tolerance) * 100000000 + (p3) * 100000 + (p2) * 100 + p1;
		}
		return hash;
		
	}
	
	
	public class Data{
		int time;
		String name;
		@Override
		public boolean equals(Object d)
		{
			Data temp=(Data)d;
			if(time==temp.time&&name==temp.name)
				return true;
			else
				return false;
			
		}
		public Data(int t,String s)
		{
			time=t;
			name=s;
		}
		public double getTime()
		{
			return time;
		}
		public String getName()
		{
			return name;
		}
	}
	
	public static void main(String[] args)
	{
		
		ReadAudio audio=new ReadAudio();
		int[] s=new int[audio.getLength()];
		audio.getData(s);
		String name=audio.name;
		
		Spectrogram test=new Spectrogram(s.length,s);
		double[][] d=test.getArray();
		int[][] max=test.findMax();
		
		InputAndMatch main_function=new InputAndMatch(10);
		main_function.points2hash(d.length, max, name);
		System.out.println("First song: insert hashes into hash table");
		
		
		//read second music and push into hashtable
		ReadAudio audio_2=new ReadAudio();
		int[] s_2=new int[audio_2.getLength()];
		audio_2.getData(s_2);
		String name_2=audio_2.name;
		
		test=new Spectrogram(s_2.length,s_2);
		double[][] d_2=test.getArray();
		int[][] max_2=test.findMax();
		
		main_function.points2hash(max_2.length, max_2, name_2);
		System.out.println("Second song: insert hashes into hash table");
		
		//matching
		ReadAudio audio_3=new ReadAudio();
		int[] s_3=new int[audio_3.getLength()];
		audio_3.getData(s_3);
		
		test=new Spectrogram(s_3.length,s_3);
		double[][] d_3=test.getArray();
		int[][] max_3=test.findMax();
		
		String bestmatch=main_function.match(max_3.length, max_3);
		System.out.println("best match is "+bestmatch);
		
		
	}
}

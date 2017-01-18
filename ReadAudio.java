import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;

import sun.audio.*;
public class ReadAudio {
	
	InputStream in;
	AudioStream au;
	String st;
	String name;
	
	public ReadAudio()
	{
		//constructor to obtain the path of audio file
		try
		{
			JFileChooser openf=new JFileChooser();
			Component j=null;
			openf.showOpenDialog(j);
			File fl=openf.getSelectedFile();
			
			st=fl.getAbsolutePath();
			//obtain the file name
			int LengthofName=0;
			for(int i =0;i<st.length();i++)
			{
				if(st.charAt(i)=='\\')
					LengthofName=i;
			}
			name=st.substring(LengthofName+1);
			
			in=new FileInputStream(st);
			au=new AudioStream(in);
			

		}
		catch(Exception e){
			
		}
	}
	
	public void getData(byte[] b){
		//read the data and write to byte array b
		
		b= new byte[au.getLength()];
		try {
			au.read(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int getLength()
	{
		return au.getLength()/4;
	}
	public void getData(int[] s){
		//s should be initialized first.
		byte[] b=new byte[au.getLength()];
		try{
			au.read(b);
		}catch(IOException e){
			
		}
		//byte2short(b,s);
		
		for(int i=0;i<au.getLength()/4;i++)
		{
			byte[] temp=new byte[4];
			temp[0]=b[i*4];
			temp[1]=b[i*4+1];
			temp[2]=b[i*4+2];
			temp[3]=b[i*4+3];
			s[i]=byteArrayToInt(temp);
			
		}
	}
	
	public static int byteArrayToInt(byte[] b) {
		//averaging 2 channels and get mono music
        final int start = 0;
        int left=(int) (((b[start+1])<<8)| (0xFF& b[start]));
        int right=(int) (((b[start+3])<<8)| (0xFF& b[start+2]));

        return (left+right)/2;
    }
	
	public void PlayAudio(){
		//playing the audio clip
		
		try {
			FileInputStream temp_in=new FileInputStream(st);
			AudioStream temp_au=new AudioStream(temp_in);
			AudioPlayer.player.start(temp_au);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public static void main(String args[])
	{
		//for testing
		ReadAudio audio=new ReadAudio();
		int[] s=new int[audio.getLength()];
		audio.getData(s);
		audio.PlayAudio();
		System.out.println("sound is start, with length = "+audio.getLength()+" array size is "+s.length);
	}

}

// Sheriyar Shakeel m3u8 converter v1
package classes;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;
public class Downloader {

	public static	ArrayList<String> linkOfFile = new ArrayList<String>();//link dei file da scaricare
	public static	ArrayList<String> FileNellaCartella = new ArrayList<String>();//tutti i file contenuti nella cartella
	public static 	String folderPath;//path da dove prendere i file
	public Component frame = null;
	
	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		Downloader.folderPath = folderPath;
	}

	public static ArrayList<String> getPath() {
		return linkOfFile;
	}

	public static void setPath(ArrayList<String> path) {
		Downloader.linkOfFile = path;
	}

	public static ArrayList<String> getFileNellaCartella() {
		return FileNellaCartella;
	}

	public static void setFileNellaCartella(ArrayList<String> fileNellaCartella) {
		FileNellaCartella = fileNellaCartella;
	}



	public static String LetturaFile(String file) // read the file and extract the link for the download
	{
		String data = null;
		ArrayList<String> temp = new ArrayList<String>();
		String percorso = folderPath;
		
		
    	 try {
    	      File myObj = new File(percorso+file);
    	      Scanner myReader = new Scanner(myObj);
    	      
    	      
    	      while (myReader.hasNext()) 
    	      {
    	        data = myReader.next();
    	        temp.add(data);

    	      }
    	      myReader.close();
    	      
    	      Iterator iterator = temp.iterator();
  	    	  String sup;
  	    	  
  	          while(iterator.hasNext()) 
  	          {
  	        	  
  	        	sup = (String) iterator.next();

  	        	if(!sup.contains("#"))
  	        	{
  	        		
  	        		return sup;
  	        	}
  	        }
    	      
    	    } catch (FileNotFoundException e) {
    	      System.out.println("An error occurred.");
    	      e.printStackTrace();
    	    }
    	 return null;
	}
	
	public int LetturaCartella()
	{
		
		File folder = new File(folderPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
		  if (listOfFiles[i].isFile()) 
		  {
		    
		    FileNellaCartella.add(listOfFiles[i].getName());
		  } 
		  else if (listOfFiles[i].isDirectory()) 
		  {
		    System.out.println("Directory " + listOfFiles[i].getName());
		    
		    
		  }
		}
		return FileNellaCartella.size();
		
		
	}
	
	public static String CreazionePath(String path,String name)
	{
		String PI ="ffmpeg -i \"";
    	String PF ="\" -bsf:a aac_adtstoasc -vcodec copy -c copy -crf 50 \"";
    	String address = path;
    	String OF = name + " \".mp4";
    	
    	String comando = PI + address + PF + OF ;
    	//System.out.println(comando);
		return comando;
		
	}
	
	public void addLinks() throws IOException
	{
		Iterator fileInDir = FileNellaCartella.iterator();
    	
    	while(fileInDir.hasNext()) {

    		linkOfFile.add(LetturaFile((String)fileInDir.next()));
    		
    	}
	
	}
	
	public void download(String file,int i )
	{
			
		
		
		
		    String command = "cmd /c start cmd.exe";
    
    	    String Filename2 = FilenameUtils.getBaseName(FileNellaCartella.get(i));
    	   
            String comando= CreazionePath(file, Filename2);
     
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando);
   	        builder.redirectErrorStream(true);
   	        Process p;
			try {
				p = builder.start();
				 BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		   	        String line;
		   	        while (true) {
		   	            line = r.readLine();
		   	            if (line == null) { break; }
		   	            System.out.println(line);
		           
		   	        }
		   	    
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				
				JOptionPane.showMessageDialog(frame, "error!");
			}
   	       
    	}
	}

	


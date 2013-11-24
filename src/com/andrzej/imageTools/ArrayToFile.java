package com.andrzej.imageTools;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static java.nio.file.StandardCopyOption.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.andrzej.imageTools.ImageData;

public class ArrayToFile {

	String mainPath ="Z:\\Pictures\\";
	ImageData imageData;
	List <String> listImg = new ArrayList<String>();
    public static void main (String[] args) throws IOException{
    	 
        ArrayToFile listFilesUtil = new ArrayToFile();
        final String directoryWindows ="D://Pictures";
        listFilesUtil.listFilesAndFilesSubDirectories(directoryWindows);
        listFilesUtil.listAmount();
        listFilesUtil.getData();
    
    }
    /**
     * List all files from a directory and its subdirectories
     * @param directoryName to be listed
     */
    private void listFilesAndFilesSubDirectories(String directoryName){
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList){
            if (file.isFile()){
                listImg.add(file.getAbsolutePath().toString());
            } else if (file.isDirectory()){
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }
    
    
    private void listAmount() {
    	System.out.println(listImg.size());
    }
    
    
    
    private void getData () throws IOException{
    int i = 0;	
    for(String object: listImg){
    	
    	String msg = ""; 
    	
    	if (object.toLowerCase().contains(".jpg") || object.toLowerCase().contains(".jpeg")){
    	//System.out.println(object);
    	Path source = Paths.get(object);
    	
    	msg += "OldName: "+ object;
    	String newName = ImageData.getImageInfo(object);
    	//System.out.println(newName);
    	
    	msg += ";newName: "+ newName;
    	System.out.println(msg);
    	
    	File theDir;
    	Path target;
    	if (newName.length()<7) {
    	    newName = object;
    	    theDir = new File(mainPath+"Strange");
    	    target = Paths.get(newName);
    	}	else {
    		theDir = new File(mainPath+newName.substring(0, 4));
    	   	target = Paths.get(theDir+"\\"+newName+".jpg");   		
    	}
    	
    	// if the directory does not exist, create it
    	  if (!theDir.exists()) {
    	    System.out.println("creating directory: " + theDir);
    	    boolean result = theDir.mkdir();  
    	     if(result) {    
    	       System.out.println("DIR created");  
    	     }
    	  }
    	
    	msg += ";targetDir: "+ theDir; 
    	
    	//Files.copy(source, target, REPLACE_EXISTING);
    	i++;
    	//if (i > 2000) break;
    	System.out.println (msg);
    	System.out.println (i);
    	logToFile(msg);
    	
    	}
    
    }
    
    }

    
    public void logToFile(String content) {
		try {
			File file = new File(mainPath + "log.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
 			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.newLine();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
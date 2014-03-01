package com.andrzej.imageTools;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

public class ImageData {
	 
	static String filename;
    
    /**
     * List all files from a directory and its subdirectories
     * @param directoryName to be listed
     */
    
	public static void main (String[] args) throws IOException{
				
		String newfilename = getImageInfo("D:\\Pictures\\16042005NowiWGosciach\\2005-04-16 031.jpg");
		System.out.println (newfilename);
		
    
    }

	public static String getImageInfo (String path) { 
		
		String dateOrg ="",dateMod = "";
		try
        {
            File jpgFile = new File( path );
            
            Metadata metadata = ImageMetadataReader.readMetadata( jpgFile );
            
            Directory directory = metadata.getDirectory( ExifDirectory.class );
            if( directory != null )
            {

                for(Iterator i = directory.getTagIterator(); i.hasNext(); )
                {
                	
                	Tag tag = ( Tag )i.next();
                    if (tag.getTagName().equals("Date/Time Original")) { 
                    	dateOrg = tag.getDescription().replace(":", "-").replace(" ", "-");
                    } 
                    if (tag.getTagName().equals("Date/Time")) {
                    	dateMod = tag.getDescription().replace(":", "-").replace(" ", "-");
                    }
                    
                    if (!dateOrg.equals(dateMod) && dateOrg.length()>1){
                    filename = dateOrg.trim()+"_"+dateMod.trim();
                    } else {
                    	filename = dateOrg.trim();	
                    } 
                }
            }
            else
            {
            	filename = "EXIF is null";
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
            System.out.println (path);
        }
		return filename;
	}
}
    
 
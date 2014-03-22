package com.andrzej.imageTools;
import java.io.File;
import java.util.Iterator;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

public class ImageData {
	 
	static String filename;

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
    
 
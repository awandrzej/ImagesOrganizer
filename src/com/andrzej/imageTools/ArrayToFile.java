
package com.andrzej.imageTools;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

	public class ArrayToFile {
	List<String> listImg = new ArrayList<>();
	List<String> listImgNew = new ArrayList<>();
	String finalPath;
	String mainPathVideos;
	String mainPathPictures;


	public static void main(String[] args) throws IOException {

		//-Dmyvar String context = System.getProperty("myvar");

		ArrayToFile listFilesUtil = new ArrayToFile();
		String srcDirectory;// = "/media/andrzej/89E4-2578/";
		String dstDirectory;// = "/home/andrzej";

		try {
			srcDirectory = args[0];
			File file = new File(srcDirectory);
			String path = file.getAbsolutePath();
            listFilesUtil.listFilesAndFilesSubDirectories(path);

		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Source directory must be provided!");
			System.out.println("for example: java -jar ImagesOrganizer.jar D:\\MyImagesToOrganizeFolder C,D,E - provide only drive letter");
			System.exit(1);
		}

		try {
			dstDirectory = args[1];
			listFilesUtil.setupDirectories(dstDirectory);
		}catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Destination directory must be provided eg. C:\\");
			System.out.println("for example: java -jar ImagesOrganizer.jar D:\\MyImagesToOrganizeFolder C,D,E - provide only drive letter");
			System.exit(1);
		}

		//listFilesUtil.setupDirectories(dstDirectory);

        listFilesUtil.listSort();
		listFilesUtil.listAmount();
		listFilesUtil.getData_old();

	}

	public void setupDirectories(String dstDir) {

		File file = new File(dstDir);
		dstDir = file.getAbsolutePath();

		mainPathVideos = dstDir + "/VideoMigration/";
		mainPathPictures = dstDir + "/PhotoMigration/";


		File destDir;
		destDir = new File(mainPathPictures);
		if (!destDir.exists()) {
			System.out.println("creating directory: " + destDir);
			boolean result = destDir.mkdir();
		}
		destDir = new File(mainPathVideos);
		if (!destDir.exists()) {
			System.out.println("creating directory: " + destDir);
			boolean result = destDir.mkdir();
		}
	}



	/**
	 * List all files from a directory and its subdirectories
	 * 
	 * @param directoryName
	 *            to be listed
	 */

	public void listFilesAndFilesSubDirectories(String directoryName) {
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();

		for (File file : fList) {
			if (file.isFile()
					&& (
					file.toString().toLowerCase().contains(".jpg")
					||file.toString().toLowerCase().contains(".jpeg")
					||file.toString().toLowerCase().contains(".mpg")
                    ||file.toString().toLowerCase().contains(".mpeg")
					||file.toString().toLowerCase().contains(".avi")
					||file.toString().toLowerCase().contains(".mp4")
					||file.toString().toLowerCase().contains(".wmv")
					||file.toString().toLowerCase().contains(".mov")
			)) {
				listImg.add(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}

		}
	}

	private void listAmount() {
		System.out.println("files to process: " + listImg.size());
		System.out.println("start convert names... " );
		for (int i = 0; i < listImg.size() ; i++) {

			String oldName = listImg.get(i);
			Path path = Paths.get(oldName);

			Utils.percentCounter(i,listImg.size());

			System.out.println("oldName: "+path.getFileName().toString().toLowerCase());

			String s = (path.getFileName()).toString().toLowerCase();

			String newName;
			if(		  oldName.toLowerCase().contains(".mp4")
					||oldName.toLowerCase().contains(".avi")
					||oldName.toLowerCase().contains(".wmv")
					||oldName.toLowerCase().contains(".mov")
					||oldName.toLowerCase().contains(".mpg")
					||oldName.toLowerCase().contains(".mpeg")){
			newName = (ImageData.getMovieInfo(oldName)).replace("-", "")
					+ "_" + s;
			}else {
			newName = (ImageData.getImageInfo(oldName)).replace("-", "")
					+ "_" + s;
			}

            newName = newName.replace(" ", "_");
			System.out.println("newName: " +newName);

			if (Collections.frequency(listImgNew, newName)>0) {
				newName = newName.replace(".jpg","_"+Integer.toString(Collections.frequency(listImgNew, newName))+".jpg");
			}

			listImg.set(i, newName
							+ ";"
							+ oldName);
			listImgNew.add(newName);

		}
	}

	private void listSort() {
		System.out.println("sortowanie");
		Collections.sort(listImg);

	}

	private void getData_old() throws IOException {
		int i = 0;
		for (String object : listImg) {
			String msg;

			List<String> nameList = Arrays.asList(object.split(";"));
			String oldName = nameList.get(1);
			String newName = nameList.get(0);

			if (	   oldName.toLowerCase().contains(".jpg")
					|| oldName.toLowerCase().contains(".jpeg")
					|| oldName.toLowerCase().contains(".mpg")
					|| oldName.toLowerCase().contains(".mpeg")
					|| oldName.toLowerCase().contains(".avi")
					|| oldName.toLowerCase().contains(".mp4")
					|| oldName.toLowerCase().contains(".mov")
					|| oldName.toLowerCase().contains(".wmv")
					) {
				
				Path source = Paths.get(oldName);

				File theDir;
				Path target;

				if (newName.contains(".jpg")||newName.contains(".jpeg")) {
					finalPath = mainPathPictures;
				}else{
					finalPath = mainPathVideos;
				}

				if (finalPath == null)finalPath="";

				System.out.println("newName"+newName);
				System.out.println("finalPath"+finalPath);
				theDir = new File(finalPath + newName.substring(0, 4));
				target = Paths.get(theDir + "/" + newName);

				if (!theDir.exists()) {
					System.out.println("creating directory: " + theDir);
					boolean result = theDir.mkdir();
					if (result) {
						System.out.println("DIR created");
					}
				}

				System.out.println("copying...");
				System.out.println(target);
				System.out.println(source);
				if (!source.toString().toLowerCase().contains("FlipShare Data\\Previews")){
					Files.copy(source, target ,REPLACE_EXISTING);	
				}
				i++;
				msg = ("no-" + Integer.toString(i) + ": " + source + "-->" + target);
				logToFile(msg);
				System.out.println(i + " files of " + listImg.size()+" " +msg);
			}
		}
	}

	public void logToFile(String content) {
		try {
			File file = new File(finalPath + "log.txt");
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
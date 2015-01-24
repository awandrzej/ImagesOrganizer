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
	String mainPath = "F://PhotoMigration//";

	public static void main(String[] args) throws IOException {
		
		ArrayToFile listFilesUtil = new ArrayToFile();
		//final String directoryWindows = "E://Pictures//2008-10-24 Natalka 20081020//";
        final String directoryWindows = "E://Pictures//";
		listFilesUtil.listFilesAndFilesSubDirectories(directoryWindows);
        listFilesUtil.listSort();
		listFilesUtil.listAmount();
		listFilesUtil.getData_old();


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
							|| file.toString().toLowerCase().contains(".jpeg")
					//		|| file.toString().toLowerCase().contains(".mpg")
                    //        || file.toString().toLowerCase().contains(".mpeg")
					//		|| file.toString().toLowerCase().contains(".avi")
					//		|| file.toString().toLowerCase().contains(".mp4")
									)) {
			listImg.add(file.getAbsolutePath().toString());
			} else if (file.isDirectory()) {
				listFilesAndFilesSubDirectories(file.getAbsolutePath());
			}

		}
	}

	private void listAmount() {
		System.out.println("files to process: " + listImg.size());

		for (int i = 0; i < listImg.size() ; i++) {

			String oldName = listImg.get(i);

            String s = (oldName.replaceAll("E:\\\\P.*\\\\", "").toLowerCase());

			String newName;
			if(oldName.toLowerCase().contains(".mp4")){
			newName = (ImageData.getMovieInfo(oldName)).replace("-", "")
					+ "_" + s;
			}else {
			newName = (ImageData.getImageInfo(oldName)).replace("-", "")
					+ "_" + s;
			}

            newName = newName.replace(" ", "_");
			//System.out.println(newName);

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

			String msg = "";

			List<String> nameList = Arrays.asList(object.split(";"));
			String oldName = nameList.get(1);
			String newName = nameList.get(0);

			
			if (	   oldName.toLowerCase().contains(".jpg")
					|| oldName.toLowerCase().contains(".jpeg")
			//		|| oldName.toLowerCase().contains(".mpg")
			//		|| oldName.toLowerCase().contains(".mpeg")
			//		|| oldName.toLowerCase().contains(".avi")
			//		|| oldName.toLowerCase().contains(".mp4")
					) {
				
				Path source = Paths.get(oldName);

				File theDir;
				Path target;
				theDir = new File(mainPath + newName.substring(0, 4));
				
				target = Paths.get(theDir + "\\" + newName);
				if (!theDir.exists()) {
					System.out.println("creating directory: " + theDir);
					boolean result = theDir.mkdir();
					if (result) {
						System.out.println("DIR created");
					}
				}

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
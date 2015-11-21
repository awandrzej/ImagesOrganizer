package com.andrzej.imageTools;

import java.io.File;
import java.text.SimpleDateFormat;

public class Utils {

	public static void main(String[] args) {
		percentCounter(2700,23000);
	}

	public static void percentCounter(int splitter, int arraySize){

	int myNumber = (splitter*100/arraySize);
	String percentCurrent = Integer.toString(myNumber);
		if (percentCurrent.substring(1).contains("0")) {
		System.out.println("percent ..." + percentCurrent);

		}



	}

}

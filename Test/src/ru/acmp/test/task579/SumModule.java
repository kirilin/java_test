package ru.acmp.test.task579;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SumModule {
	private final static String INPUT_FILE_URL = "579/input.txt"; 
	private final static String OUTPUT_FILE_URL = "579/output.txt";
	private final static int MIN_N = 1;
	private final static int MAX_N = 10000;
	
	public static void main(String[] args) throws IOException {
		int[] arrayList;

		arrayList = getDataFromFile();
		if(arrayList == null) {
			setDataToFile("ERROR. Wrong input data");
			return;
		}
		
		int amount = 0;									//сумма чисел
		int countPos = 0;								//количество положительных чисел
		int countNeg = 0;								//количество отрицательных чисел
		StringBuilder arrayPos = new StringBuilder(); 	//строка индексов положительных чисел
		StringBuilder arrayNeg = new StringBuilder(); 	//строка индексов отрицательных чисел
		
		for(int i = 0; i < arrayList.length; i++) {
			amount += arrayList[i];
			if(arrayList[i] < 0) {
				arrayNeg.append((i+1) + " " );
				countNeg++;
			} else {
				arrayPos.append((i+1) + " " );
				countPos++;
			}
		}
		if (amount < 0) {
			//передаем для записи в файл количество и строку с позициями (за вычетом последнего пробела)
			setDataToFile(countNeg + "\n" + arrayNeg.substring(0, arrayNeg.length() - 1)); 
		} else {
			setDataToFile(countPos + "\n" + arrayPos.substring(0, arrayPos.length() - 1));
		}
		
	}

	public static int[] getDataFromFile() throws IOException {
		
		String lineFromFile;
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(INPUT_FILE_URL))){
			lineFromFile = fileReader.readLine();
			
			int sizeArray = Integer.valueOf(lineFromFile);
			if(sizeArray < MIN_N || sizeArray > MAX_N) {
				return null;
			}
			
			int[] arrayList = new int[sizeArray];
			
			lineFromFile = fileReader.readLine();
			
			String[] splitLine = lineFromFile.trim().split(" ");
			
			for(int i = 0; i < sizeArray; i++) {
				arrayList[i] = Integer.parseInt(splitLine[i]);
			}
			
			return arrayList;
					
		} catch (FileNotFoundException e) {
			System.err.println("File not found!!");
			e.printStackTrace();
		} 
		return null;
	}
	
	public static void setDataToFile(String result) {
			
		try (FileWriter userWriter = new FileWriter(OUTPUT_FILE_URL, false)){
			userWriter.write(result);	
		} catch (IOException e) {
			System.err.println("ERROR while writing data to file");
			e.printStackTrace();
		}
	}
	
}

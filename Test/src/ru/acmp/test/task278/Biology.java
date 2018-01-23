package ru.acmp.test.task278;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Biology {
	
	private final static String INPUT_FILE_URL = "278/input.txt"; 
	private final static String OUTPUT_FILE_URL = "278/output.txt"; 	
	
	public static void main(String[] args) throws IOException {
		ArrayList<String> inputData;
		String chainS; 	//Последовательность ДНК
		int chainSPos;	//Счетчик позиции в chainS
		String chainT; 	//Последовательность ДНК в результате эволюции
		
		StringBuilder resultData = new StringBuilder();
		
		inputData = getDataFromFile();
		
		for (int i = 0; i < inputData.size(); i += 2) {
			chainS = inputData.get(i);
			chainSPos = 0;
			chainT = inputData.get(i + 1);
			for (int j = 0; j < chainT.length(); j++) {
				if (chainS.charAt(chainSPos) == chainT.charAt(j)) {
					chainSPos ++;
				}
			}
			if (chainSPos == chainS.length()) {
				resultData.append("YES\n");
			} else {
				resultData.append("NO\n");
			}
		}
	
		setDataToFile(resultData.toString());

	}
	
	public static ArrayList<String> getDataFromFile() throws IOException {
		ArrayList<String> inputData = new ArrayList<String>();
		String lineFromFile;
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(INPUT_FILE_URL))){
			while ((lineFromFile = fileReader.readLine()) != null) {
				inputData.add(lineFromFile);
			}
					
		} catch (FileNotFoundException e) {
			System.err.println("File not found!!");
			e.printStackTrace();
		} 
		
		return inputData;
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

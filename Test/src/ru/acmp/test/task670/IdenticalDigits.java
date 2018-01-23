package ru.acmp.test.task670;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class IdenticalDigits {
	private final static String INPUT_FILE_URL = "670/input.txt"; 
	private final static String OUTPUT_FILE_URL = "670/output.txt";
	private final static int MIN_N = 1;
	private final static int MAX_N = 10000;
	
	public static void main(String[] args) throws IOException {
		int positionN;
		
		positionN = getDataFromFile();
		if (positionN < MIN_N || positionN > MAX_N) {
			setDataToFile("Error. Check input data.");
			return;
		}
		
		if(positionN < 10) {
			setDataToFile(String.valueOf(positionN));
			return;			
		}
		
		int num = 10;
		for (int i = 10; i < positionN; i++)
		{
			num++;
			while(isDigitPair(num)) {
				num++;
			}
		}
		
		setDataToFile(String.valueOf(num));
	}
	
	public static boolean isDigitPair(int number) {
		HashSet<Integer> digit = new HashSet<Integer>();
		
		do {
			if (digit.add(number % 10)) {
				number /= 10;
			} else {
				return true;
			}
		} while (number != 0);
		return false;
	}
	
	public static int getDataFromFile() throws IOException {
		int inputNumber = 0;
		String lineFromFile;
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(INPUT_FILE_URL))){
			while ((lineFromFile = fileReader.readLine()) != null) {
				inputNumber = Integer.valueOf(lineFromFile);
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found!!");
			e.printStackTrace();
		} 
		
		return inputNumber;
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

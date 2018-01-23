package ru.acmp.test.task557;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
	private final static String INPUT_FILE_URL = "557/input.txt"; 
	private final static String OUTPUT_FILE_URL = "557/output.txt";
	//m - количество матриц, n - размер каждой из матриц (в диапазоне MIN_SIZE, MAX_SIZE) 
	private final static int MIN_SIZE = 1; 		
	private final static int MAX_SIZE = 130;	
	//максимальное значение числа в матрице
	private final static int MAX_VALUE = 1000;
	
	private static int numOfMatrix;						//Количество матриц
	private static int sizeOfMatrix;					//Размер матриц sizeOfMatrix*sizeOfMatrix
	private static int[] findElement = new int[2];		//Позиция искомого элемента
	private static int numberLimit;						//ограничение значений в матрицах
	private static ArrayList<Matrix> matrixList = new ArrayList<Matrix>(); //Список матриц
	
	public static void main(String[] args) throws IOException {
		int resultNumber;
		if (getDataFromFile()) {		
			if(numOfMatrix == 1) {
				resultNumber = matrixList.get(0).getMatrix()[findElement[0]][findElement[1]];
			} else {
			
				Matrix resultMatrix = multiplyMatrix(matrixList.get(0), matrixList.get(1));
				for (int count = 2; count < numOfMatrix; count ++) {
					resultMatrix = multiplyMatrix(resultMatrix, matrixList.get(count));
				}
				resultNumber = resultMatrix.getMatrix()[findElement[0]][findElement[1]];
			}
			
			/*if (resultNumber >= numberLimit) {
				resultNumber = resultNumber % numberLimit;
			} */
			
			setDataToFile(String.valueOf(resultNumber));
			
		} else {
			setDataToFile("ERROR! Wrong input data");
		}

	}
	
	//Умножение двух матрий
	public static Matrix multiplyMatrix(Matrix a, Matrix b) {
		Matrix res = new Matrix(sizeOfMatrix);

		for (int i = 0; i < sizeOfMatrix; i++) {
			for (int j = 0; j < sizeOfMatrix; j++) {
				res.getMatrix()[i][j] = 0;
				for (int k = 0; k < sizeOfMatrix; k++) {
					res.getMatrix()[i][j] += a.getMatrix()[i][k] * b.getMatrix()[k][j];
				}
				if(res.getMatrix()[i][j] >= numberLimit) {
					res.getMatrix()[i][j] = res.getMatrix()[i][j] % numberLimit;
				}
			}
		}

		return res;
	}
	
	
	public static boolean getDataFromFile() throws IOException {

		String lineFromFile;
		String [] splitLine;
		
		try (BufferedReader fileReader = new BufferedReader(new FileReader(INPUT_FILE_URL))){
			//Получаем количество и размер матриц
			if ((lineFromFile = fileReader.readLine()) != null) {
				splitLine = lineFromFile.trim().split(" ");
				numOfMatrix = Integer.parseInt(splitLine[0]);		
				sizeOfMatrix = Integer.parseInt(splitLine[1]);		
			} 
			
			//Получаем позицию искомого элемента
			if ((lineFromFile = fileReader.readLine()) != null) {
				splitLine = lineFromFile.trim().split(" ");
				findElement[0] = Integer.parseInt(splitLine[0]) - 1; //отнимаем 1 т.к. нумерация в матрицах считаем с нуля
				findElement[1] = Integer.parseInt(splitLine[1]) - 1; //отнимаем 1 т.к. нумерация в матрицах считаем с нуля
			}
			
			//Получаем ограничение по значению чисел в матрицах
			if ((lineFromFile = fileReader.readLine()) != null) {
				numberLimit = Integer.parseInt(lineFromFile);
			}
			
			//Получаем значение матриц из файла
			for (int count = 0; count < numOfMatrix; count++) {
				lineFromFile = fileReader.readLine();
				
				Matrix matrixFromFile = new Matrix(sizeOfMatrix);
				int numFromFile;
				for (int i = 0; i < sizeOfMatrix; i++) {
					lineFromFile = fileReader.readLine();
						
					splitLine = lineFromFile.trim().split(" ");
					for (int j = 0; j < sizeOfMatrix; j++) {
						matrixFromFile.getMatrix()[i][j] = Integer.parseInt(splitLine[j]);
					}
				}
				matrixList.add(matrixFromFile);
			}				
		} catch (FileNotFoundException e) {
			System.err.println("File not found!!");
			e.printStackTrace();
		}
		
		return true;
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

class Matrix {
	int size;
	int[][] matrix;
	
	public Matrix(int size) {
		this.size = size;
		matrix = new int[size][size];
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
}


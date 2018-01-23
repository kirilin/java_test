package ru.acmp.test.task557;

import java.util.Arrays;

public class Matrix {
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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Matrix other = (Matrix) obj;
		if (!Arrays.deepEquals(matrix, other.matrix))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(matrix);
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder matrixStr = new StringBuilder();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				matrixStr.append(String.format("%4d", matrix[i][j]));
			}
			matrixStr.append("\n");
		}
		
		return matrixStr.toString();
	}
	
}

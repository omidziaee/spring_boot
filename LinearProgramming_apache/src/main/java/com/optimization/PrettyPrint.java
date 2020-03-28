package com.optimization;

public class PrettyPrint {
	double[][] matrix;

	public PrettyPrint(double[][] matrix) {
		this.matrix = matrix;
		int rows = this.matrix[0].length;
		int cols = this.matrix[1].length;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				System.out.print(this.matrix[i][j] + "\t");
				if (j == cols - 1) {
					System.out.println();
				}
			}
		}
	}
}

package com.optimization;

public class DiagMatrix {
	public int size;
	private double diagCoeff;
	public double[][] matrix= null;
	public DiagMatrix(int size, double diagCoeff) {
		this.size = size;
		this.diagCoeff = diagCoeff;
		this.matrix = new double[this.size][this.size];
		for(int i = 0; i < this.size; i++) {
			for(int j = 0; j < this.size; j++) {
				if (i == j) {
					this.matrix[i][j] = this.diagCoeff;
				} else {
					this.matrix[i][j] = 0;
				}
			}
		}
	}

}

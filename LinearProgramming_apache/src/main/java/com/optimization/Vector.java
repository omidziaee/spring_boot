package com.optimization;

public class Vector {
	private double coef;
	private int size;
	public double[] diag;
	
	public Vector(double coef, int size) {
		this.coef = coef;
		this.size = size;
	}
	
	public double[] getDiag() {
		this.diag = new double[this.size];
		for(int i = 0; i < this.size; i++) {
			this.diag[i] = this.coef;
		}
		return diag;
	}
}

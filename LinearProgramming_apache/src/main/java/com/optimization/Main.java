package com.optimization;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.DiagonalMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class Main {
	//describe the optimization problem
	public static double[] price = {16.53, 14.59, 13.97, 12.55, 13, 15.05, 
			  						20.06, 23.8, 22.05, 22.18, 21.41, 21.61, 
			  						20.45, 19.88, 19.85, 19.9, 21.49, 24.47, 
			  						25.54, 25.1, 22.41, 20.97,19.12, 18.78};
	
	public static final double[] forecastedLoad = {100, 120, 140, 110, 120, 150, 
 					   						   	   240, 330, 380, 400, 450, 520,  
 					   						   	   540, 520, 500, 520, 550, 480,  
 					   						   	   530, 490, 440,460, 380, 240};
	public static final double BATTERY_CHARGE_MAX = 100; 
	public static final double BATTERY_DISCHARGE_MAX = 100;
	public static final double SOC_INITIAL = 100;
	public static final double SOC_MAX = 500;
	public static final double SOC_MIN = 0.1 * SOC_MAX;
	public static final double NET_LOAD_MAX = 800;
	public static final double CHARGE_EFFICIENCY = 0.9;
	public static final double DISCHARGE_EFFICIENCY = 0.9;

    public static void main(String[] args) {
    	// Create the coeffient for the rest of the variables
    		Vector vectorZeros = new Vector(0, 72);
    		price = ArrayUtils.addAll(price, vectorZeros.getDiag());
    		
        	Vector vectorOne = new Vector(1, 24);
        	DiagonalMatrix A = new DiagonalMatrix(vectorOne.getDiag());
        	Vector vectorCharge = new Vector(-1/CHARGE_EFFICIENCY, 24);
        	DiagonalMatrix B = new DiagonalMatrix(vectorCharge.getDiag());
        	Vector vectorDischarg = new Vector(DISCHARGE_EFFICIENCY, 24);
        	DiagonalMatrix C = new DiagonalMatrix(vectorDischarg.getDiag());
        	Vector vectorZero = new Vector(0, 24);
        	DiagonalMatrix D = new DiagonalMatrix(vectorZero.getDiag());
        	Vector vectorMinusOne = new Vector(-1, 24);
        	DiagonalMatrix E = new DiagonalMatrix(vectorMinusOne.getDiag());
        	
        	LinearObjectiveFunction f = new LinearObjectiveFunction(price, 0);
        	Collection<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
        	//===============================Expected Load(2)============================
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(A.getRow(i), B.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, C.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.EQ, forecastedLoad[i]));
        	}
        	//===============================Charge power limit(5)============================
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), A.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.LEQ, BATTERY_CHARGE_MAX));
        	}
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), A.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.GEQ, 0));
        	}
        	//===============================Discharge power limit(6)============================
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, A.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.LEQ, BATTERY_DISCHARGE_MAX));
        	}
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, A.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.GEQ, 0));
        	}
        	//===============================SOC limit upper side(4)============================
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, A.getRow(i));
        		if(i < 23) {
        			constraints.add(new LinearConstraint(conCoeff, Relationship.GEQ, SOC_MIN));
        		} else {
        			constraints.add(new LinearConstraint(conCoeff, Relationship.EQ, SOC_INITIAL));
        		}
        	}
        	//===============================SOC limit lower side(4)============================
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, A.getRow(i));
        		if(i < 23) {
        			constraints.add(new LinearConstraint(conCoeff, Relationship.LEQ, SOC_MAX));
        		} else {
        			constraints.add(new LinearConstraint(conCoeff, Relationship.EQ, SOC_INITIAL));
        		}
        	}
        	//===============================State of Charge(3)============================
        	double[][] socCoeff = {{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //1
				    			  {-1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //2
				    			  {0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //3
				    			  {0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //4
				    			  {0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //5
				    			  {0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //6
				    			  {0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //7
				    			  {0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //8
				    			  {0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //9
				    			  {0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //10
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //11
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //12
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //13
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //14
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //15
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0}, //16
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0, 0}, //17
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0, 0}, //18
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0, 0}, //19
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0}, //20
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0}, //21
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0}, //22
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0}, //23
				    			  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1}}; //24
        	
        	double[] leftSideSocCons = {SOC_INITIAL, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        	RealMatrix socCoeffMatrix = new Array2DRowRealMatrix(socCoeff);
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(D.getRow(i), E.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, A.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, socCoeffMatrix.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.EQ, leftSideSocCons[i]));
        	}
        	//===============================Net load limit(7)============================
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(A.getRow(i), D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.LEQ, NET_LOAD_MAX));
        	}
        	for(int i = 0; i < 24; i++) {
        		double[] conCoeff = ArrayUtils.addAll(A.getRow(i), D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
        		conCoeff = ArrayUtils.addAll(conCoeff, D.getRow(i));
            	constraints.add(new LinearConstraint(conCoeff, Relationship.GEQ, 0));
        	}
        	//====================================================================================
        	PointValuePair solution = null;
        	long now = System.currentTimeMillis();
        	solution = new SimplexSolver(0.001).optimize(f, new LinearConstraintSet(constraints), GoalType.MINIMIZE);
        	System.out.println("It takes " + String.valueOf(System.currentTimeMillis() - now) + "ms to solve!");
        	
        	if (solution != null) {
                //get solution
                double max = solution.getValue();
                System.out.println("Opt: " + max);
              //print decision variables
                for (int i = 0; i < 24; i++) {
                    System.out.print(solution.getPoint()[i] + "\t");
                }
            }
        }
        
    }

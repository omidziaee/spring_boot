package com.scanner.app;

import java.util.ArrayList;

public class Option {
	private char flag;
	private boolean isRange;
	private boolean isSet;
	private boolean isBlock;
	private ArrayList<String> opt;
	public Option(char c, ArrayList<String> option, boolean hasComma, boolean hasSemiCol, boolean hasDash) {
		super();
		this.flag = c;
		this.opt = option;
		this.isRange = hasSemiCol;
		this.isSet = hasComma;
		this.isBlock = hasDash;
	}
	public char getFlag() {
		return flag;
	}
	public char isRange() {
		if(isRange) {
			return 'T';
		}else {
			return 'F';
		}
	}
	public void setRange(boolean isRange) {
		this.isRange = isRange;
	}
	public char isSet() {
		if(isSet) {
			return 'T';
		}else {
			return 'F';
		}
	}
	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}
	public ArrayList<String> getOpt() {
		return opt;
	}
	public void setOpt(ArrayList<String> opt) {
		this.opt = opt;
	}
	public void setFlag(char flag) {
		this.flag = flag;
	}
	public ArrayList<String> getOption() {
		return opt;
	}
	public void setOption(ArrayList<String> opt) {
		this.opt = opt;
	}
	public char isBlock() {
		if(isBlock) {
			return 'T';
		}else {
			return 'F';
		}
	}
	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}
	@Override
	public String toString() {
		return "Option [flag=" + flag + ", opt=" + opt + "]";
	}
}

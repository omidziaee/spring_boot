package com.multithread;

import java.util.concurrent.TimeUnit;

public class PrintMultiThreaded implements Runnable{
	private String printStatement;
	

	public PrintMultiThreaded(String printStatement) {
		super();
		this.printStatement = printStatement;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		// If you do not put it in a loop it does not make sense to print it continuesly!!
		while(true) {
			try {
				// This is one way to run a timing task in a thread but timertask is better.
				TimeUnit.SECONDS.sleep(1);
				System.out.println(System.currentTimeMillis() + ": " + this.printStatement);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

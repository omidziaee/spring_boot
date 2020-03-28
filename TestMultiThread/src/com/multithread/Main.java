package com.multithread;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		PrintMultiThreaded printMultiThread = new PrintMultiThreaded("This is Omid!");
		Thread t1 = new Thread(printMultiThread);
		// when thread starts the run in the multithreaded class gets run
		t1.start();
		// This part is a separate thing that is run in the main thread and it does not 
		// relate to the job that the other thread is doing without multithread it is not
		// possible to run this. Beacause programs run line by line when somewhere else you 
		// have a while that is still going you can not run anything else before that while loop
		// gets done.
		int a = 5;
		int b = 6;
		TimeUnit.SECONDS.sleep(4);
		System.out.println(a + b);
		Thread.sleep(20);

	}

}

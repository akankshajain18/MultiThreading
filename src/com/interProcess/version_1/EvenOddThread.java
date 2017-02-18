package com.interProcess.version_1;

public class EvenOddThread {
	
	
	public static void main(String args[]){
		PrintClass pp = new PrintClass();
		EvenThread et= new EvenThread(pp);
		et.start();
		
		OddThread ot= new OddThread(pp);
		ot.start();
	}

}

class EvenThread extends Thread{
	
	private PrintClass printClass;
	boolean isEven = true;
	EvenThread(PrintClass printClass){
		this.printClass = printClass;
	}
	
	@Override
	public void run(){
		for(int i =2 ;i<10;i=i+2){
			printClass.print(i, isEven);
		}
		
	}
}


class OddThread extends Thread{
	private PrintClass printClass;
	boolean isEven = false;
	OddThread(PrintClass printClass){
		this.printClass = printClass;
	}
	
	@Override
	public void run(){
		for(int i =1 ;i<10;i=i+2){
			printClass.print(i, isEven);
		}
		
	}
}


class PrintClass{
	
	boolean turn = false;
	
	public synchronized void print(int value, boolean isTurn){
		System.out.println(" " + turn + " " + isTurn +  " " + Thread.currentThread());
		while(isTurn!=turn){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(value);
		turn = !turn;
		System.out.println(" " + turn + " " + isTurn  + " " + Thread.currentThread());
		notifyAll();
	}
}

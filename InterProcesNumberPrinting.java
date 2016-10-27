package com.evenOdd;

public class InterProcesNumberPrinting {
	static boolean even = false;
	static Object obj = new Object();

	public static void main(String[] args) {
		Thread odd = new Thread(new printOdd(obj), "odd");
		Thread even = new Thread(new printEven(obj), "even");
		odd.start();
		even.start();
	}

	static class printOdd implements Runnable {
		Object obj;

		printOdd(Object obj) {
			this.obj = obj;
		}

		@Override
		public void run() {
			for (int i = 1; i < 10; i = i + 2) {
				synchronized (obj) {
					System.out.println(i);
					try {
						even = true;
						obj.notify();
						while (even)
							obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	static class printEven implements Runnable {
		Object obj;

		printEven(Object obj) {
			this.obj = obj;
		}

		@Override
		public void run() {

			for (int i = 2; i <= 10; i = i + 2) {
				synchronized (obj) {
					while (!even) {
						try {
							obj.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							obj.notify();
							e.printStackTrace();
						}
					}
					System.out.println(i);
					even = false;
					obj.notify();

				}
			}
		}
	}

}

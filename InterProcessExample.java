//package com.evenOdd;

public class InterProcessExample{
	static boolean even = false;
	static Object obj = new Object();	
	public static void main(String[] args){	
		printOdd c = new printOdd();
		printEven d = new printEven();
		c.start();
		d.start();					
	}
static class printOdd extends Thread
{


	printOdd()
	{
		
	}
	
	@Override
	public void run() {
		
		for(int i =1;i<10;i=i+2)
		{
			synchronized(obj)
			{
				
				System.out.println(i);
				
				try {
					even = true;
					obj.notify();
					while(even)
						obj.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}


static class printEven extends Thread
{
	
	printEven()
	{
		
	}
	
	@Override
	public void run() {
		
		for(int i =2;i<=10;i=i+2)
		{
			synchronized(obj)
			{
				while(!even)
				{
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

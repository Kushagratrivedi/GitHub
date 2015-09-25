package practice;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosopher
{

	public static void main(String[] args) 
	{
		Philosopher[] philosophers = new Philosopher[5];
		ChopStick [] sticks = new ChopStick[5];
		for(int i = 0 ; i < 5 ; i++)
		{
			sticks[i] = new ChopStick(i+1);
		}
		for(int i = 0 ; i < 5 ; i++)
		{
			philosophers[i] = new Philosopher(sticks[i], sticks[(i+1)%5], i+1);	
		}
		for(int i = 0 ; i < 5 ; i++)
		{
			philosophers[i].start();	
		}
		try {
			philosophers[0].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class ChopStick
{
	Lock lock;
	int name;
	 ChopStick(int name)
	 {
		 this.name = name;
		 lock = new ReentrantLock();
	 }
	 
	 public boolean take()
	 {
		 return lock.tryLock();
	 }
	 
	 public void put()
	 {
		 lock.unlock();
	 }
	 
	 public String toString()
	 {
		 return this.name +""; 
	 }
}

class Philosopher extends Thread
{
	int name;
	private int bites = 10;
	ChopStick left;
	ChopStick right;
	Philosopher(ChopStick left, ChopStick right, int name)
	{
		this.left =left;
		this.right = right;
		this.name = name;
	}
	
	public boolean takeUp()
	{
		if(this.left.take())
			System.out.println(this.name +" took the "+this.left);
		else
		{
			System.out.println(this.name +" did not get "+this.left);
			return false;
		}
			
		if(this.right.take())
			System.out.println(this.name +" took the "+this.right);
		else
		{
			this.left.put();
			System.out.println(this.name +" did not get "+this.right+", so put back "+this.left);
			return false;
		}
		return true;
		
	}
	public void putDown()
	{
		this.left.put();
		System.out.println(this.name +" put the "+this.left);
		this.right.put();
		System.out.println(this.name +" put the "+this.right);
	}
	public void chew()
	{
		System.out.println(this.name +" is chewing with "+this.left +" & "+this.right);
	}
	
	public void eat()
	{
		if(!this.takeUp())
			return;
			this.chew();
		this.putDown();
	}
	public void run()
	{
		for(int i = 0 ; i < 2 ; i++)
		{
			this.eat();
			System.out.println(this.name+" completed bite"+ (i+1));
		}
	}
}
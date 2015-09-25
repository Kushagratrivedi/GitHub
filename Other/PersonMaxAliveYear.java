package practice;

import java.util.Arrays;

public class PersonMaxAliveYear {

	public static void main(String[] args) 
	{
		int [] births = {12,20,10,01,10,23,13,90,83,75};
		int [] deaths = {15,90,98,72,98,82,98,98,99,94};
		int min = 0;
		int max = 100;
		createPeopleGetMaxAliveYear(births, deaths, min, max);

	}
	
	/**
	 * 
	 * @param people 
	 * @param min
	 * @param max 
	 * @return year people lived most
	 */
	
	public  static void createPeopleGetMaxAliveYear(int [] births, 
			int [] deaths, int min, int max)
	{
		Person [] people = new Person[births.length];
		
		for(int i = 0 ; i < people.length ; i++)
		{
			people[i] = new Person(deaths[i], births[i]);
		}
		
		System.out.println("Max alive year - Brute fo"
				+ "rce: "+getMaxAliveYear(people, min, max));
		System.out.println("Max alive year"
				+ " - count: "+_getMaxAliveYear(people, min, max));
		System.out.println("Max alive year - populat"
				+ "ion: "+__getMaxAliveYear(people, min, max));
	}
	
	public static int __getMaxAliveYear(Person [] people, int min, int max)
	{
		int [] population_delta = getPopulationDelta(people, min, max);
		int max_alive_year = getMaxAlive(population_delta);
		return max_alive_year;
	}
	
	
	private static int getMaxAlive(int[] population_delta) 
	{
		int maxAlive = 0;
		int alive = 0;
		int maxAliveYear = 0;
		int count = 0;
		for(int delta : population_delta)
		{
			alive+=delta;
			if(alive > maxAlive)
			{
				maxAlive = alive;
				maxAliveYear = count; 
				
			}
			count++;
				
		}
		return maxAliveYear;
	}

	private static int[] getPopulationDelta(Person[] people, int min, int max) 
	{
		int [] count = new int[max - min + 2];
		for(Person person : people)
		{
			count[person.getBirth() - min]++;
			count[person.getDeath() - min + 1]--;
		}
		// TODO Auto-generated method stub
		return count;
	}

	/**Here we would check if person is alive for that year, we would
	 * increase the birth count, if death we decrease the count
	 * 
	 */
	public static int _getMaxAliveYear(Person [] people, int min, int max)
	{
		//get sorted births
		int [] births = getSortedYears(people, true);
		//get sorted deaths
		int [] deaths = getSortedYears(people, false);
		int birthIndex = 1;
		int deathIndex = 0;
		int alive = 0 ;
		int maxAlive = 0;
		int maxAliveYear = 0;
		
		
		while(birthIndex < births.length)
		{
			//if birth year is less then death year than person is
			//alive so we would increse the alive count and if
			//alive is greater than max alive than we put
			//max alive = alive
			
			if(births[birthIndex] <= deaths[deathIndex])
			{
				alive++;
				if(alive > maxAlive)
				{
					maxAlive = alive;
					maxAliveYear = births[birthIndex];
				}
				birthIndex++;
					
			}
			
			//if death we would increse the death count, 
			//and decrease the alive count
			else if(births[birthIndex] > deaths[deathIndex])
			{
				alive--;
				deathIndex++;
			}
			
		}
		return maxAliveYear;
	}
	
	private static int[] getSortedYears(Person[] people, boolean i) 
	{
		int [] years = new int[people.length];
		for(int k = 0 ; k < people.length ; k++)
		{
			years[k] = i ? people[k].getBirth() : people[k].getDeath();
		}
		Arrays.sort(years);
		return years;
	}


	//Time : O(n ^ 2)
	public static int getMaxAliveYear(Person [] people, int min, int max)
	{
		int maxAlive = 0;
		int maxAliveYear = min;
		for(int i = min ; i <= max ; i++)
		{
			//current alive
			int alive = 0 ;
			//for each year - every person is checked whether alive 
			for(Person person : people)
			{
				if(person.getBirth() <= i &&  person.getDeath() >= i)
					alive++;
				
			}
			//if year count of aliveness is greater then put it as maxAlive
			if(alive > maxAlive)
			{
				maxAlive = alive;
				maxAliveYear = i;
			}
				
		}
		return maxAliveYear;
	}
	
	private static class Person
	{
		private int death;
		private int birth;
		
		public Person(int death, int birth)
		{
			this.death = death;
			this.birth = birth;
			
		}
		
		public int getBirth()
		{
			return this.birth;
			
		}
		
		public int getDeath()
		{
			return this.death;
		}
	}

}

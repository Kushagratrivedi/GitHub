
import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kushagra
 */
public class Question_1
{
    private static Scanner sc; 
    private static ArrayList<NBA_Game> list_of_teams;
    
    public static void main(String args[])
    {
        sc = new Scanner(System.in);
        list_of_teams = new ArrayList<>();
        
        System.out.println("Create Teams: ");
        
        String team;
        int score1;
        int score2;
        
        while(true)
        {
            System.out.println("Please Enter Team Name: ");
            team = sc.next();
            System.out.println("Please Enter Score 1: ");
            score1 = sc.nextInt();
            System.out.println("Please Enter Score 2: ");
            score2 = sc.nextInt();
            System.out.println("Do you want to create more teams? Y or N ");
            String temp = sc.next();
            
            NBA_Game game = new NBA_Game(team, score1, score2);
            list_of_teams.add(game);
            
            if(temp.equalsIgnoreCase("Y"))
            {
            }
            else if(temp.equalsIgnoreCase("N"))
            {
                break;
            }
            else
            {
                System.out.println("Invalid Input");
            }
        }
        
        System.out.println("Teams are created.");
        
        NBA_Game highestDiffObj = getLargestScore(list_of_teams);
        
        System.out.println("Largest Difference: ('"+highestDiffObj.getTeams()+"'),"+highestDiffObj.getScore1()+" ,"+highestDiffObj.getScore2()+" ");
        
        System.out.println("Done!");
        
        
    }
    
    private static NBA_Game getLargestScore(ArrayList<NBA_Game> list)
    {
        ArrayList<Double> differenceList =  new ArrayList<>();
        
        
        
        list.stream().forEach((game) -> {
            differenceList.add((double)(game.getScore1() - game.getScore2()));
        });

        
        double d = java.util.Collections.max(differenceList);
        int index = differenceList.indexOf(d);
        
        NBA_Game temp = list.get(index);
        
        return temp;
    }

  
    
}
class NBA_Game
{
    private String teams;
    private int score1;
    private int score2;

    public NBA_Game(String teams, int score1, int score2) 
    {
        this.teams = teams;
        this.score1 = score1;
        this.score2 = score2;
    }
    
    

    public String getTeams() 
    {
        return teams;
    }

    public void setTeams(String teams) 
    {
        this.teams = teams;
    }

    public int getScore1() 
    {
        return score1;
    }

    public void setScore1(int score1) 
    {
        this.score1 = score1;
    }

    public int getScore2() 
    {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }
    
}

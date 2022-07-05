package simple21;

import java.util.Scanner;
import java.util.Random;


/**
 * This is a simplified version of a common card game, "21". 
 */
public class GameControl {
    
	/**
	 * Human player.
	 */
    HumanPlayer human;
    
    /**
     * Computer player.
     */
    ComputerPlayer player1;
    
    /**
     * Computer player.
     */
    ComputerPlayer player2;
    
    /**
     * Computer player.
     */
    ComputerPlayer player3;
    
    /** 
     * A random number generator to be used for returning random "cards" in a card deck.
     * */
    Random random = new Random();
      
    /**
     * The main method just creates a GameControl object and calls its run method.
     * @param args Not used.
     */
    public static void main(String args[]) {    
        new GameControl().run();
    }
    
    /**
     * Prints a welcome method, then calls methods to perform each of the following actions:
     * - Create the players (one of them a Human)
     * - Deal the initial two cards to each player
     * - Control the play of the game
     * - Print the final results
     */
    public void run() {
    	
        Scanner scanner = new Scanner(System.in); //create a new scanner
        
        System.out.println("Welcome to Simple 21!"); //print welcome message
        System.out.println("You will play against 3 other players.");
        System.out.println("Try to get as close to 21 as possible, without going over.");
        System.out.println("What's your name:");
        String myName = scanner.next();//get next input value as a string
        
        this.createPlayers(myName);//create four players and name the human with myName
        this.deal();//deal the cards
        this.controlPlay(scanner);//call control method to control the play of the game
        this.printResults();//call printResults method to print out the final results.
        
    	
        
        scanner.close();//close the scanner
    }
    
    /**
     * Creates one human player with the given humansName, and three computer players with hard-coded names.
     * @param humansName for human player
     */
    public void createPlayers(String humansName) {
    	human = new HumanPlayer(humansName);//create a new human player object with name humansName
    	
    	player1 = new ComputerPlayer("Player1");//create a new ComputerPlayer object with name "Player1"
    	player2 = new ComputerPlayer("Player2");//create a new ComputerPlayer object with name "Player2"
    	player3 = new ComputerPlayer("Player3");//create a new ComputerPlayer object with name "Player3"
    	
    }
    
    /**
     * Deals two "cards" to each player, one hidden, so that only the player who gets it knows what it is, 
     * and one face up, so that everyone can see it. (Actually, what the other players see is the total 
     * of each other player's cards, not the individual cards.)
     */
    public void deal() { 
    	int hiddenCard = this.nextCard();//call nextCard method to get a random card as hiddenCard
    	int visibleCard = this.nextCard();//call nextCard method to get a random card as visibleCard
    	
    	human.takeHiddenCard(hiddenCard);//call takeHiddenCard method to let human take this card.
    	human.takeVisibleCard(visibleCard); //call takeVisibleCard method to let human take this card.
    	
    	hiddenCard = this.nextCard();//call nextCard method again to get another random card as hiddenCard
    	visibleCard = this.nextCard();//call nextCard method again to get another random card as visibleCard
    	
    	player1.takeHiddenCard(hiddenCard);//call takeHiddenCard method to let player1 take this card.
    	player1.takeVisibleCard(visibleCard);//call takeVisibleCard method to let player1 take this card.
    	
    	hiddenCard = this.nextCard();//call nextCard method again to get another random card as hiddenCard
    	visibleCard = this.nextCard();//call nextCard method again to get another random card as visibleCard
    	
    	player2.takeHiddenCard(hiddenCard);//call takeHiddenCard method to let player2 take this card.
    	player2.takeVisibleCard(visibleCard);//call takeVisibleCard method to let player2 take this card.
    	
    	hiddenCard = this.nextCard();//call nextCard method again to get another random card as hiddenCard
    	visibleCard = this.nextCard();//call nextCard method again to get another random card as visibleCard
    	
    	player3.takeHiddenCard(hiddenCard);//call takeHiddenCard method to let player3 take this card.
    	player3.takeVisibleCard(visibleCard);//call takeVisibleCard method to let player3 take this card.
    }
    
    /**
     * Returns a random "card", represented by an integer between 1 and 10, inclusive. 
     * The odds of returning a 10 are four times as likely as any other value (because in an actual
     * deck of cards, 10, Jack, Queen, and King all count as 10).
     * 
     * Note: The java.util package contains a Random class, which is perfect for generating random numbers.
     * @return a random integer in the range 1 - 10.
     */
    public int nextCard() { 
    	int randomNum = random.nextInt(13)+1;//Get a random number from 1 to 13
    	if (randomNum > 10) {//if random number is 11,12 or 13
    		randomNum = 10;//treat them as 10 and let randomInt equal to 10
    	}
    	return randomNum;//return this random number
    }

    /**
     * Gives each player in turn a chance to take a card, until all players have passed. Prints a message when 
     * a player passes. Once a player has passed, that player is not given another chance to take a card.
     * @param scanner to use for user input
     */
    public void controlPlay(Scanner scanner) { 
        // Students: your code goes here.
    	while (!this.checkAllPlayersHavePassed()) {//when there exists at least one player is still player, go into this while loop
    		System.out.println();
    		if (!human.passed) {//if human player is still playing, hasn't passes
    			if(human.offerCard(player1, player2, player3, scanner)) {//ask human whether to take a new card
    				human.takeVisibleCard(this.nextCard());//if taking, call takeVisibleCard method to add this card to all visible card
    			}else {
        			System.out.println(human.name +" passes.");//if not taking and passes, printing message that human player passes
        		}
    		}
    		if (!player1.passed) {//if player1 is still player, hasn't passes
    			if(player1.offerCard(human, player1, player2, player3)) {//player1 decides whether to take a new card
    				player1.takeVisibleCard(this.nextCard());//if taking, call takeVisibleCard method to add this card to all visible card
    			}else {
        			System.out.println(player1.name +" passes.");//if not taking and passes, printing message that player1 passes
        		}
    		}
    		if (!player2.passed) {//if player2 is still playing, hasn't passes
    			if(player2.offerCard(human, player1, player2, player3)) {//player2 decides whether to take a new card
    				player2.takeVisibleCard(this.nextCard());//if taking, call takeVisibleCard method to add this card to all visible card
    			}else {
        			System.out.println(player2.name +" passes.");//if not taking and passes, printing message that player2 passes
        		}
    		}
    		if (!player3.passed) {//if player3 is still playing, hasn't passes
    			if(player3.offerCard(human, player1, player2, player3)) {//player3 decides whether to take a new card
    				player3.takeVisibleCard(this.nextCard());//if taking, call takeVisibleCard method to add this card to all visible card
    			}else {
        			System.out.println(player3.name +" passes.");//if not taking and passes, printing message that player3 passes
        		}
    		}
    	}
    }
     
    /**
     * Checks if all players have passed.
     * @return true if all players have passed
     */
    public boolean checkAllPlayersHavePassed() {
    	if (human.passed && player1.passed && player2.passed && player3.passed) {//check if human passed and all computer players passed
    		return true;//return true if all passed
    	}else {
    		return false;//return false if at least one not passed
    	}
    }
    
    /**
     * Prints a summary at the end of the game.
     * Displays how many points each player had, and if applicable, who won.
     */
    public void printResults() { 
    	System.out.println();//print blank line
    	System.out.println("Game over.");//print message "Game Over"
    	System.out.println(human.name+" has "+human.getScore()+" total points.");//print human's total points
    	System.out.println(player1.name+" has "+player1.getScore()+" total points.");//print player1's total points
    	System.out.println(player2.name+" has "+player2.getScore()+" total points.");//print player2's total points
    	System.out.println(player3.name+" has "+player3.getScore()+" total points.");//print player3's total points
    	System.out.println();//print blank line
    	this.printWinner();//print winner info
    }

    /**
     * Determines who won the game, and prints the results.
     */
    public void printWinner() { 
    	int humanScore = human.getScore();//call getScore method and get points of human and assign it to humanScore
    	int player1Score = player1.getScore();//call getScore method and get points of player1 and assign it to player1Score
    	int player2Score = player2.getScore();//call getScore method and get points of player2 and assign it to player2Score
    	int player3Score = player3.getScore();//call getScore method and get points of player3 and assign it to player3Score
    	
    	int winnerScore = 0; //initialize a new integer variable winnerScore with value 0
    	String winner = "nobody";//initialize a new string variable winner with value "nobody"
    	boolean tie = false;//initialize a new boolean variable tie with value false
    	
    	if(humanScore <= 21) {//if humanScore is less than or equal to 21
    		winnerScore = humanScore;//set winnerScore to be humanScore
        	winner = human.name;//set winner to be human's name
    	}
    	
    	
    	if (player1Score >= winnerScore && player1Score <=21) {//if player1's score is more than or equal to winnerScore and less than or equal to 21
    		if(player1Score == winnerScore) {//if player1's score is same as winner's, that's a tie
    			//System.out.println(player1.name+" Tie "+winner);
    			tie = true;//set tie to true
    		}else {
    			winnerScore = player1Score;//else update winnerScore to be player1Score
        		winner = player1.name;//update winner to be player1's name
    		}
    		
    	}
    	//Checking same conditions on player2 and player3 as below 
    	if (player2Score >= winnerScore && player2Score <=21) {
    		if(player2Score == winnerScore) {
    			//System.out.println(player2.name+" Tie "+winner);
    			tie = true;
    		}else {
    			winnerScore = player2Score;
        		winner = player2.name;
    		}
    		
    	}
    	
    	if (player3Score >= winnerScore && player3Score <=21) {
    		if(player3Score == winnerScore) {
    			//System.out.println(player3.name+" Tie "+winner);
    			tie = true;
    		}else {
    			winnerScore = player3Score;
        		winner = player3.name;
    		}
    		
    	}
    	
    	if(tie) {
    		System.out.println("Tie, nobody wins.");//if tie is true, print message "Tie", nobody wins
    	}else {
    		System.out.println(winner + " wins with " + winnerScore + " points.");//if not tie, print out winner name and its score.
    	}
    	
    }
}

package simple21;

import java.util.Scanner;

/**
 * Represents a human player in a game of Simple 21.
 */
public class HumanPlayer {
	
	/** 
	 * The name of the player.
	 */
    String name;
    
    /**
     * The player's one hidden card (a value from 1 - 10).
     */
    private int hiddenCard = 0;
    
    /** 
     * The sum of the player's cards, not counting the hidden card. 
     */
    private int sumOfVisibleCards = 0;
    
    /**
     * Flag indicating if the player has passed (asked for no more cards).
     */
    boolean passed = false;
    
    /**
     * Constructs a human player with the given name.
     * @param name of the user.
     */
    public HumanPlayer(String name) {
        this.name = name;
    }
    
    /**
     * Asks the Human player whether to take another card and uses the given scanner to prompt for a response.
     * @param human This human player
     * @param player1 Another (computer) player
     * @param player2 Another (computer) player
     * @param player3 Another (computer) player
     * @param scanner To use for scanning for human input
     * @return true if this human player wants another card
     */
    public boolean offerCard(ComputerPlayer player1, ComputerPlayer player2, ComputerPlayer player3, Scanner scanner) {
    	boolean response = true;//initialize a boolean variable response and set value as true
    	
    	this.showCards(this, player1, player2, player3);//call showCards method to show each player's points
    	
    	response = this.getYesOrNoToQuestion("Take another card?", scanner);//ask human player whether to take another card
    	if (!response) this.passed = true;//if not taking another card, set passed to true
    	
    	return response;//return human's response, true or false
    }
    
    
    /**
     * Prints the sum of all of this Human's cards, and the sum of each of the other (computer) players' 
     * visible cards.
     * @param human This human player
     * @param player1 Another (computer) player
     * @param player2 Another (computer) player
     * @param player3 Another (computer) player
     */
    public void showCards(HumanPlayer human, ComputerPlayer player1, ComputerPlayer player2, ComputerPlayer player3) {
    	// Students: your code goes here.
    	System.out.println();//print blank line
    	System.out.println(human.name + " has "+ human.getScore() +" total points.");//print human's total points: visible + hidden
    	System.out.println(player1.name + " has "+ player1.getSumOfVisibleCards() +" visible points.");//print player1's visible points
    	System.out.println(player2.name + " has "+ player2.getSumOfVisibleCards() +" visible points.");//print player2's visible points
    	System.out.println(player3.name + " has "+ player3.getSumOfVisibleCards() +" visible points.");//print player3's visible points
    	 
    }
    
    /**
     * Displays the given question and prompts for user input using the given scanner.
     * @param question to ask
     * @param scanner to use for user input
     * @return true if the user inputs 'y'
     */
    public boolean getYesOrNoToQuestion(String question, Scanner scanner) {
    	boolean response = true;//initialize a boolean variable response and set value as true
    	
    	String answer;//create a string variable answer

    	System.out.println();//print blank line
        System.out.print(question + " ");//print out question
        
        while(true) {//infinite while loop until reponse is set to true or false
	        answer = scanner.next();
	        
	        if (answer.toLowerCase().charAt(0) == 'y') {//if the input means 'y', set repsonse to true, meaning yes
	        	response = true;
	        	break;
	        } else if (answer.toLowerCase().charAt(0) == 'n') {//if the input means 'n', set repsonse to false, meaning no
	        	response = false;
	        	break;
	        }
        }

        return response;//return human's response, true or false
    }
    
    /**    
     * Puts the specified card in this human's hand as the hidden card.
     * Prints a message saying that the card is being taken, and prints the value of the hidden card.
     * @param card being taken
     */
    public void takeHiddenCard(int card) {
    	
    	this.hiddenCard = card;//put the given card as human's hidden card
    	System.out.println(this.name+" takes a hidden card.");//print the message that human takes a hidden card
    	System.out.println("(It's a "+ this.hiddenCard + " )");//print the message that showing hiddencard's value
    }
    
    /**
     * Adds the given card to the sum of the visible cards for this human player.
     * Prints a message saying that the card is being taken.
     * @param card being taken
     */
    public void takeVisibleCard(int card) { 
    	
    	this.sumOfVisibleCards += card;//add the value of given card to human's sumOfVisibleCards
    	System.out.println(this.name+" takes "+ card);//print message that human takes the given card
    }

    /**
     * Returns the total sum of this player's cards, not counting the hidden card. 
     * @return sumOfVisibleCards
     */
    public int getSumOfVisibleCards() { 
    
    	return this.sumOfVisibleCards;//return the total points of visible cards
    }
    
    /**
     * Return this player's total score (the total of all this human player's cards).
     * That is to say, the sum of the visible cards + the hidden card.
     * @return total score 
     */
    public int getScore() { 
    	
    	return (this.sumOfVisibleCards+this.hiddenCard);//return the total points of visible cards plus hidden cards
    }
}

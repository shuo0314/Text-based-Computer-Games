package hangman;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Hangman {

	
	private String word; // The initial word randomly selected from the cleaned list
	private int length;  // the length of the selected word
	//Example of guessWord: word is "book", starting guessWord is "____", after guessing letter "o", guessWord is "_oo_"
	private String guessWord; //  the word that the user has already guessed, which uses "_" to represent unguessed letters.
	private int numGuess;  //Number of guess which the user has already made.
	private ArrayList<String> incorrectGuess = new ArrayList<>(); // the list for incorrect guesses (no duplicates)

	/**
	 *  Constructor
	 * @param word The initial word randomly selected from the cleaned list
	 * @param numGuess number of guess which the user has already made.
	 * @param guessWord the word that the user has already guessed, which uses "_" to represent unguessed letters.
	 * @param incorrectGuess the list for incorrect guesses (no duplicates)
	 */
	public Hangman(String word, int numGuess, String guessWord,  ArrayList<String> incorrectGuess) {
		this.word = word;
		this.length = word.length();
		//if guessWord has never been initialized, initialize it with "_" on each character position with length of the word length
		if (guessWord == null) {
			this.guessWord = "_".repeat(this.length);		
		}else {
			this.guessWord = guessWord;
		}
		this.numGuess = numGuess;
		//if array list incorrectGuess is not empty, assign it to instance variable
		if(incorrectGuess.size() > 0) {
			this.incorrectGuess = incorrectGuess;
		}
	}
	
	/**
	 * @return the word
	 */
	public String getWord() {
		return this.word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * @return the length
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * @return the guessWord
	 */
	public String getGuessWord() {
		return this.guessWord;
	}

	/**
	 * @param guessWord the guessWord to set
	 */
	public void setGuessWord(String guessWord) {
		this.guessWord = guessWord;
	}

	/**
	 * @return the numGuess
	 */
	public int getNumGuess() {
		return this.numGuess;
	}

	/**
	 * @param numGuess the numGuess to set
	 */
	public void setNumGuess(int numGuess) {
		this.numGuess = numGuess;
	}

	/**
	 * @return the incorrectGuess
	 */
	public ArrayList<String> getIncorrectGuess() {
		return this.incorrectGuess;
	}

	/**
	 * @param incorrectGuess the incorrectGuess to set
	 */
	public void setIncorrectGuess(ArrayList<String> incorrectGuess) {
		this.incorrectGuess = incorrectGuess;
	}
	
	/**
	 * Print the String guess word and array list includes incorrect guesses
	 * @param debugMode 
	 */
	public void print( boolean debugMode) {
		//print the current guessWord
		System.out.println(this.getGuessWord());
		//if the arraylist incorrectGuess is not empty, print it out
		if (this.incorrectGuess.size() > 0 ) {
			System.out.println("Incorrect guesses: " + this.getIncorrectGuess());
		}		
		//print out number of times of player's valid guess(valid guess means no "already guess")
		System.out.println("Total guesses: " + this.getNumGuess());
		//if in debugMode, print out current word player is guessing
		if(debugMode) {
			System.out.println("\nCurrent Word: " + this.getWord());			
		}
	}
	

	/**
	 * Update and return the guessWord word2 using letter 
	 * Check if letter is in the word1, if yes, update word2 with the letter at the locations that letter is in word1
	 * @param word1 the word player is guessing with full letters, e.g. "book"
	 * @param word2 the guessWord with "_" tracking unguessed letters. e.g. "_oo_"
	 * @param letter the guessed letter. e.g. "k"
	 * @return updated word2 with updated letter. e.g. "_ook"
	 */
	public String replaceWord (String word1, String word2, String letter) {
		// Check if letter is in the word1
		if (word1.contains(letter)) {
			char[] chars = word2.toCharArray(); // A list of characters to track the users' guesses
			// Iterate each letter
			for(int j = 0; j < word1.length(); j ++ ) {
				//replace the character which equals the letter in the chars.
				if( word1.charAt(j) == letter.charAt(0)) {
					chars[j] = letter.charAt(0); 
				}
			}
			// Convert the list of characters back to a string.
			word2 = String.valueOf(chars);
		}
		//return updated guessWord
		return word2;
	}
	
	/**
	 * Update the guessWord, the number of guesses and the incorrect guesses
	 * 1) Check if letter is guessed, if yes, print out the message
	 * 2) If not, add 1 to numGuess, then update the guessWord by calling replaceWord().
	 * @param letter guessed by user
	 */
	public void updateGuess(String letter) {
		// If the letter is in the incorrectGuess list or in the guessWord, print a message that the letter is already guessed
		if (this.getIncorrectGuess().contains(letter) || this.getGuessWord().contains(letter)) {
			System.out.println("You already guessed that letter");
		}else {
			// If the letter is not guessed, add 1 to numGuess
			this.setNumGuess(this.getNumGuess() + 1);
			if (this.word.contains(letter)) {
				// If the letter is in the word, replace the "_" with the letter in the guessWord
				this.setGuessWord(this.replaceWord(this.getWord(), this.getGuessWord(), letter));
			}else {
				// Add the letter to the incorrectGuess if the word doesn't include the letter 
				this.getIncorrectGuess().add(letter);
			}
		}
	}
	
	
	/**
	 * Check if the game is over. If yes, print out the total number of guess and the word guessed.
	 * @param word the word guessed so far
	 * @return true or false indicating game is over or not
	 */
	public boolean isGameOver() {
		// If the guessWord include "_", then the game is not over.
		if (this.getGuessWord().contains("_")) {
			return false;
		}else {
			//print out full word and the number of guesses after the game is over.
			System.out.print("\n" + this.getGuessWord());
			System.out.print("\nYou won! \nYou guessed the word \"" + this.guessWord + "\" with " +  this.numGuess + " times in total.");
			return true;
		}
	}
	
	
}

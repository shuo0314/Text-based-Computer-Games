package hangman;

import java.util.ArrayList;

public class HangmanTraditional extends Hangman {
	
	/**
	 * Constructor extends from Hangman
	 * @param word The initial word randomly selected from the cleaned list
	 * @param numGuess number of guess which the user has already made.
	 * @param guessWord the word that the user has already guessed, which uses "_" to represent unguessed letters.
	 * @param incorrectGuess the list for incorrect guesses (no duplicates)
	 */
	public HangmanTraditional(String word, int numGuess, String guessWord, ArrayList<String> incorrectGuess) {
		// Inherit the constructor from Hangman
		super(word, numGuess, guessWord, incorrectGuess);
	}


}

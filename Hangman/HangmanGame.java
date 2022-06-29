/**
 * Wenjing Wu 
 * Corporation work with Shuo Li
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import dictionary.Dictionary;
import hangman.Hangman;
import hangman.HangmanEvil;
import hangman.HangmanTraditional;

public class HangmanGame {

	public static void main(String[] args) {
		//Read file and get cleaned word list
		//String fileName = "words.txt";
		String fileName = "words_clean.txt";
		ArrayList<String> wordList = Dictionary.readFileGetWords(fileName);
		
		//print welcome message
		System.out.println("Welcome to Hangman Game!");
		
		//Initialize debugMode to true
		boolean debugMode = true;
		
		//Randomly pick a game mode: Evil/Traditional
		Random random = new Random();
		
		// setting seed (to get the same results each time)
		// This is for debugging purpose only!
		//random.setSeed(100);
	      
		//If palyEvil is true, play Evil game; If false, play traditional game.
		boolean playEvil = random.nextBoolean();
		//playEvil = false;//true;
		
		//Notify users about the game version when in the debug mode
		if(debugMode) {
			if(playEvil) {
				System.out.println("---You are in debug mode of Evil Hangman game---");
			}else {
				System.out.println("---You are in debug mode of Traditional Hangman game---");
			}
		}

		//randomly select one word from the cleaned word list.
		String word = wordList.get(random.nextInt(wordList.size()));
		//initialize number of guesses
		int numGuess = 0;
		//initialize guessWord which track the guessing result of the target word
		String guessWord = null;
		//Create empty array list incorrectGuess to store incorrect letter the player guessed
		ArrayList<String> incorrectGuess = new ArrayList<>();
		
		// Scanner for users' input
		Scanner scanner = new Scanner(System.in);
		
		// In the evil mode, 
		if(playEvil) {
			// Initiate an evil Hangman instance using the "word", "guessWord", "numGuess", and "incorrectGuess"
			HangmanEvil evilHangman = new HangmanEvil(word, numGuess, guessWord, incorrectGuess);
			// (1) select a list of words with the same length as the word selected in the previous step
			//Store the selected words in arraylist selWordList
			evilHangman.initialSelWordList(wordList, debugMode);
			
			// (2) Keep shrinking the list of words based on users' guessed letter, until there is only one word left
			while(evilHangman.getSelWordList().size() > 1) {
				System.out.println("\nGuess a letter");
				String letter = scanner.next(); // Get users' input.
				letter = letter.toLowerCase();  // In case of upper case input, convert it to lower cases.
				evilHangman.updateSelWordList(letter, debugMode); // update the selected list of words to guess, remove unqualified words
			}
			// (3) Get the full word from the final list
			word = evilHangman.getSelWordList().get(0);
			
			// (4) Get the word (with "_" tracking the unguessed letters) from the final list 
			guessWord = evilHangman.getGuessWord();	
			
			// (5) Record the number of guesses in this process
			numGuess = evilHangman.getNumGuess();
		
			// (6) Record the incorrect guesses so far
			incorrectGuess = evilHangman.getIncorrectGuess();
		}
		
		// Initiate a traditional Hangman instance using the "word", "guessWord", "numGuess", and "incorrectGuess"
		// Note when in the evil mode, when initializing tradHangman, numGuess >0,  guessWord is not null and incorrectGuess is not empty.
		HangmanTraditional tradHangman = new HangmanTraditional(word, numGuess, guessWord, incorrectGuess);
		
		// Play the traditional version until all letters are guessed.
		while(!tradHangman.isGameOver()) {
			System.out.println("\nGuess a letter");
			tradHangman.print(debugMode);  // Print all necessary information: numGuess, guessWord, incorrectGuess, current word(optional)
			String letter = scanner.next(); // Get users' input.
			letter = letter.toLowerCase(); // In case of upper case input, convert it to lower cases.
			tradHangman.updateGuess(letter); // Update the guessWord based on users' input
		}
	    
		//Noticing player which mode is playing at the end of the game
		if(playEvil) {
			System.out.println("\nYou played Hangman Evil.");
		}else {
			System.out.println("\nYou played Hangman Traditional.");
		}
		
	}
	
}

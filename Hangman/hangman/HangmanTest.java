package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HangmanTest {

	private String word ;
	private int numGuess;
	String guessWord;
	ArrayList<String> incorrectGuess;
	HangmanTraditional tradHangman;
	
	@BeforeEach
	void setUp() throws Exception {
		word = "impressive";
		numGuess = 0;
		guessWord = null;
		incorrectGuess = new ArrayList<>();
		tradHangman = new HangmanTraditional(word, numGuess, guessWord, incorrectGuess);
	}


	/**
	 * Test constructor
	 */
	@Test 
	void testConstructor() {
		
		// Case 1: Default case (for traditional version)
		tradHangman = new HangmanTraditional(word, numGuess, guessWord, incorrectGuess);
		assertEquals(word, tradHangman.getWord());
		assertEquals(numGuess, tradHangman.getNumGuess());
		assertEquals("__________", tradHangman.getGuessWord());
		assertEquals(0, tradHangman.getIncorrectGuess().size());
		
		/**
		 * Case 2: for the evil version, when:
		 * guessWord is not null, the guessWord should be identical to it.
		 * IncorrectGuess is not empty
		 * numGuess > 0
		 */
		numGuess = 10;
		guessWord = "b_____";
		incorrectGuess = new ArrayList<String> (Arrays.asList(new String[] {"a", "b"}));
		
		tradHangman = new HangmanTraditional(word, numGuess, guessWord, incorrectGuess);
		assertEquals(word, tradHangman.getWord());
		assertEquals(numGuess, tradHangman.getNumGuess());
		assertEquals(guessWord, tradHangman.getGuessWord());
		
	}
	
	@Test
	void testReplaceWord() {
		
		String letter = "i";
		guessWord = tradHangman.getGuessWord();
		guessWord = tradHangman.replaceWord(word, guessWord, letter);
		assertEquals("i______i__", guessWord);
		
		letter = "s";
		guessWord = tradHangman.replaceWord(word, guessWord, letter);
		assertEquals("i____ssi__", guessWord);
		
		letter = "i";
		guessWord = tradHangman.replaceWord(word, guessWord, letter);
		assertEquals("i____ssi__", guessWord);
		
		letter = "v";
		guessWord = tradHangman.replaceWord(word, guessWord, letter);
		assertEquals("i____ssiv_", guessWord);
		
		letter = "q";
		guessWord = tradHangman.replaceWord(word, guessWord, letter);
		assertEquals("i____ssiv_", guessWord);
	}

	@Test
	void testUpdateGuess() {
		
		tradHangman = new HangmanTraditional(word, numGuess, guessWord, incorrectGuess);
		ArrayList<String> incorrectGuess = new ArrayList<>();
		
		String letter = "i";
		tradHangman.updateGuess(letter);
		assertEquals("i______i__", tradHangman.getGuessWord());
		assertEquals(1,tradHangman.getNumGuess());
		assertEquals(incorrectGuess,tradHangman.getIncorrectGuess());
		
		letter = "q";
		incorrectGuess.add(letter);
		tradHangman.updateGuess(letter);
		assertEquals("i______i__", tradHangman.getGuessWord());
		assertEquals(2,tradHangman.getNumGuess());
		assertEquals(incorrectGuess,tradHangman.getIncorrectGuess());
		
		letter = "s";
		tradHangman.updateGuess(letter);
		assertEquals("i____ssi__", tradHangman.getGuessWord());
		assertEquals(3,tradHangman.getNumGuess());
		assertEquals(incorrectGuess,tradHangman.getIncorrectGuess());
		
		letter = "t";
		incorrectGuess.add(letter);
		tradHangman.updateGuess(letter);
		assertEquals("i____ssi__", tradHangman.getGuessWord());
		assertEquals(4,tradHangman.getNumGuess());
		assertEquals(incorrectGuess,tradHangman.getIncorrectGuess());
	}

	@Test
	void testIsGameOver() {
		tradHangman = new HangmanTraditional(word, numGuess, guessWord, incorrectGuess);
		
		guessWord = "___ie__";
		tradHangman.setGuessWord(guessWord);
		assertFalse(tradHangman.isGameOver());
		
		guessWord = "______";
		tradHangman.setGuessWord(guessWord);
		assertFalse(tradHangman.isGameOver());
		
		guessWord = "impressive";
		tradHangman.setGuessWord(guessWord);
		assertTrue(tradHangman.isGameOver());
		
		guessWord = "dog";
		tradHangman.setGuessWord(guessWord);
		assertTrue(tradHangman.isGameOver());
		
	}

}

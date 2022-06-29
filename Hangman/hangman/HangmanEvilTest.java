package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dictionary.Dictionary;

class HangmanEvilTest{
	String word;
	int numGuess;
	String guessWord;
	HangmanEvil evilHangman;
	ArrayList<String> selWordList;
	ArrayList<String> guessWordList;
	ArrayList<String> incorrectGuess;
	
	@BeforeEach
	void setUp() throws Exception {
		word = "bought";
		numGuess = 0;
		guessWord = null;
		incorrectGuess = new ArrayList<>();
		
		evilHangman = new HangmanEvil(word, numGuess, guessWord, incorrectGuess);
		selWordList = new ArrayList<String>(); 
		guessWordList = new ArrayList<String>();
	
	}

	/**
	 * Test constructor
	 */
	@Test 
	void testConstructor() {
		
		// Case 1: Default case (for traditional version)
		// when guessWord is null, the guessWord should be updated to "_____"  of the length of the word
		assertEquals(word, evilHangman.getWord());
		assertEquals(numGuess, evilHangman.getNumGuess());
		assertEquals("______", evilHangman.getGuessWord());
		
		/**
		 * Case 2: for traditional version, when:
		 * guessWord is not null, the guessWord should be identical to it.
		 * IncorrectGuess is not empty
		 * numGuess > 0
		 */
		numGuess = 10;
		guessWord = "b_____";
		incorrectGuess = new ArrayList<String> (Arrays.asList(new String[] {"a", "b"}));
		
		evilHangman = new HangmanEvil(word, numGuess, guessWord, incorrectGuess);
		assertEquals(word, evilHangman.getWord());
		assertEquals(numGuess, evilHangman.getNumGuess());
		assertEquals(guessWord, evilHangman.getGuessWord());
		
	}
	
	@Test
	void testInitialSelWordList() {
		
		ArrayList<String> wordList = Dictionary.readFileGetWords("words_clean.txt");
		evilHangman.initialSelWordList(wordList, false);
		
		// All the words in the cleaned word list of the same length as "bought"
		String expList[] = new String[] {"loving", "marble", "having", "recall", "eleven", "bought", "coming", "single", "season", "record"};
		ArrayList<String> expectedWordList = new ArrayList<String> (Arrays.asList(expList)) ; 
		
		// The guessed word list corresponding to the selected word list.
		String expList2[] = new String[] {"______", "______", "______", "______", "______", "______", "______", "______", "______", "______"};
		ArrayList<String> expectedWordList2 = new ArrayList<String> (Arrays.asList(expList2)) ; 
		
		assertEquals(expectedWordList, evilHangman.getSelWordList());
		assertEquals(expectedWordList2, evilHangman.getGuessWordList());
		assertEquals("______", evilHangman.getGuessWord());
		assertEquals(0, evilHangman.getIncorrectGuess().size());
		
		
	}

	@Test
	void testFindMaxEntryOfMap() {
		
		HashMap<String, Integer> hashMap2 = new HashMap <String, Integer>();
		hashMap2.put("____", 2);
		hashMap2.put("___i", 1);
		hashMap2.put("i___", 5);
		hashMap2.put("_ii_", 2);
		String maxKey = evilHangman.findMaxEntryOfMap(hashMap2);
		assertEquals("i___", maxKey);
		
		HashMap<String, Integer> hashMap1 = new HashMap <String, Integer>();
		hashMap1.put("____", 2);
		hashMap1.put("___i", 1);
		hashMap1.put("i___", 5);
		hashMap1.put("_ii_", 5);
		maxKey = evilHangman.findMaxEntryOfMap(hashMap1);
		assertTrue(maxKey.equals("i___") || maxKey.equals("_ii_"));
		
		HashMap<String, Integer> hashMap3 = new HashMap <String, Integer>();
		hashMap3.put("____", 5);
		hashMap3.put("___i", 1);
		hashMap3.put("i___", 5);
		hashMap3.put("_ii_", 5);
		maxKey = evilHangman.findMaxEntryOfMap(hashMap3);
		assertTrue(maxKey.equals("i___") || maxKey.equals("_ii_") || maxKey.equals("____"));
		
	}

	@Test
	void testRemoveString() {
		
		guessWordList = new ArrayList<String>();
		guessWordList.add("____");
		guessWordList.add("___i");
		guessWordList.add("_i__");
		guessWordList.add("___i");
		
		String str = "___i";
		evilHangman.removeString(guessWordList, str);
		assertEquals("___i", guessWordList.get(0));
		assertEquals("___i", guessWordList.get(1));
		assertEquals(2, guessWordList.size());
		
		guessWordList = new ArrayList<String>();
		guessWordList.add("____");
		guessWordList.add("__ki");
		guessWordList.add("_ii_");
		guessWordList.add("___i");
		
		str = "__ki";
		evilHangman.removeString(guessWordList, str);
		assertEquals("__ki", guessWordList.get(0));
		assertEquals(1, guessWordList.size());
		
		guessWordList = new ArrayList<String>();
		guessWordList.add("____");
		guessWordList.add("____");
		guessWordList.add("____");
		guessWordList.add("_oki");
		
		str = "____";
		evilHangman.removeString(guessWordList, str);
		assertEquals("____", guessWordList.get(0));
		assertEquals("____", guessWordList.get(1));
		assertEquals("____", guessWordList.get(2));
		assertEquals(3, guessWordList.size());
		
	}

	@Test
	void testUpdateSelWordList() {
		
		ArrayList<String> wordList = Dictionary.readFileGetWords("words_clean.txt");
		evilHangman.initialSelWordList(wordList, false);
		
		// Initial list
		// All the words in the cleaned word list of the same length as "bought"
		String expList[] = new String[] {"loving", "marble", "having", "recall", "eleven", "bought", "coming", "single", "season", "record"};
		ArrayList<String> expectedWordList = new ArrayList<String> (Arrays.asList(expList)) ; 
		
		// The guessed word list corresponding to the selected word list.
		String expList2[] = new String[] {"______", "______", "______", "______", "______", "______", "______", "______", "______", "______"};
		ArrayList<String> expectedWordList2 = new ArrayList<String> (Arrays.asList(expList2)) ; 
		
		
		assertEquals(expectedWordList, evilHangman.getSelWordList());
		assertEquals(expectedWordList2, evilHangman.getGuessWordList());
		assertEquals("______", evilHangman.getGuessWord());
		assertEquals(0, evilHangman.getNumGuess());
		assertEquals(0, evilHangman.getIncorrectGuess().size());
		
		
		// First guess.
		String letter = "b";
		evilHangman.updateSelWordList(letter, false);
		
		// After guessing "b", the word list will excludes "bought" and "marble" since they have "e" while the rest doesn't.
		String expList3[] = new String[] {"loving", "having", "recall", "eleven", "coming", "single", "season", "record"};
		ArrayList<String> expectedWordList3 = new ArrayList<String> (Arrays.asList(expList3)) ; 
		
		// The guessed word list will excludes the placeholder for "bought" and "marble" 
		String expList4[] = new String[] {"______", "______", "______", "______", "______", "______", "______", "______"};
		ArrayList<String> expectedWordList4 = new ArrayList<String> (Arrays.asList(expList4)) ; 
		
		assertEquals(expectedWordList3, evilHangman.getSelWordList());
		assertEquals(expectedWordList4, evilHangman.getGuessWordList());
		assertEquals("______", evilHangman.getGuessWord());
		assertEquals(1, evilHangman.getNumGuess());
		// Check incorrect guessed list
		assertEquals(1, evilHangman.getIncorrectGuess().size());
		assertEquals("b", evilHangman.getIncorrectGuess().get(0));
	
		
		// Second guess.
		letter = "i";
		evilHangman.updateSelWordList(letter, false);
		
		// After guessing "i", the word list will excludes [loving, having, coming] and [single], since they have i at different locations. 
		// the remaining four words don't have i.
		String expList5[] = new String[] { "recall", "eleven", "season", "record"};
		ArrayList<String> expectedWordList5 = new ArrayList<String> (Arrays.asList(expList5)) ; 
		
		// The guessed word list will also excludes the placeholder for [loving, having, coming] and [single].
		String expList6[] = new String[] { "______", "______", "______", "______"};
		ArrayList<String> expectedWordList6 = new ArrayList<String> (Arrays.asList(expList6)) ; 
		
		assertEquals(expectedWordList5, evilHangman.getSelWordList());
		assertEquals(expectedWordList6, evilHangman.getGuessWordList());
		assertEquals("______", evilHangman.getGuessWord());
		assertEquals(2, evilHangman.getNumGuess());
		
		// Check incorrect guessed list
		assertEquals(2, evilHangman.getIncorrectGuess().size());
		assertEquals("b", evilHangman.getIncorrectGuess().get(0));
		assertEquals("i", evilHangman.getIncorrectGuess().get(1));
		
		// 3rd guess.
		letter = "e";
		evilHangman.updateSelWordList(letter, false);
		
		// After guessing "e", the word list will excludes "eleven" since it has three "e" while the rest three only have one "e" at the same location.
		String expList7[] = new String[] { "recall", "season", "record"};
		ArrayList<String> expectedWordList7 = new ArrayList<String> (Arrays.asList(expList7)) ; 
		
		// The guessed word list will also excludes the placeholder for "eleven"
		String expList8[] = new String[] { "_e____", "_e____", "_e____"};
		ArrayList<String> expectedWordList8 = new ArrayList<String> (Arrays.asList(expList8)) ; 
		
		assertEquals(expectedWordList7, evilHangman.getSelWordList());
		assertEquals(expectedWordList8, evilHangman.getGuessWordList());
		assertEquals("_e____", evilHangman.getGuessWord());
		assertEquals(3, evilHangman.getNumGuess());
		
		// Check incorrect guessed list
		assertEquals(2, evilHangman.getIncorrectGuess().size());
	    assertEquals("b", evilHangman.getIncorrectGuess().get(0));
		assertEquals("i", evilHangman.getIncorrectGuess().get(1));
				
				
		// 4th guess.
		letter = "a";
		evilHangman.updateSelWordList(letter, false);
		
		// After guessing "a", the three words "recall", "season", "record" are in three different groups, 
		// so any of them could be selected.
		String selWord = evilHangman.getSelWordList().get(0);
		assertTrue("recall".equals(selWord) || "season".equals(selWord) || "record".equals(selWord));
		assertTrue("_e_a__".equals(evilHangman.getGuessWord()) || "_ea___".equals(evilHangman.getGuessWord()) || "_e____".equals(evilHangman.getGuessWord()));
		assertEquals(4, evilHangman.getNumGuess());	
		
		// Check incorrect guessed list
		assertTrue(evilHangman.getIncorrectGuess().size() == 2 || evilHangman.getIncorrectGuess().size() == 3);
		assertEquals("b", evilHangman.getIncorrectGuess().get(0));
		assertEquals("i", evilHangman.getIncorrectGuess().get(1));
				
	}

}

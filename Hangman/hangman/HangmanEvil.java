package hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Map.Entry;

public class HangmanEvil extends Hangman {

	// create an empty arraylist as the selected word list 
	private ArrayList<String> selWordList = new ArrayList<String>(); 
	// create an empty arraylist as the guess word list with "_" to denote unguessed letters
	private ArrayList<String> guessWordList = new ArrayList<String>(); 
	
	/**
	 * Constructor extends from Hangman
	 * @param word The initial word randomly selected from the cleaned list
	 * @param numGuess number of guess which the user has already made.
	 * @param guessWord the word that the user has already guessed, which uses "_" to represent unguessed letters.
	 * @param incorrectGuess the list for incorrect guesses (no duplicates)
	 */
	public HangmanEvil(String word, int numGuess, String guessWord, ArrayList<String> incorrectGuess) {
		// Inherit the constructor from Hangman
		super(word, numGuess, guessWord, incorrectGuess);
	}
	
	/**
	 * @return the selWordList
	 */
	public ArrayList<String> getSelWordList() {
		return selWordList;
	}

	/**
	 * @param selWordList the selWordList to set
	 */
	public void setSelWordList(ArrayList<String> selWordList) {
		this.selWordList = selWordList;
	}
	
	/**
	 * @return the guessWordList
	 */
	public ArrayList<String> getGuessWordList() {
		return guessWordList;
	}

	/**
	 * @param guessWordList the guessWordList to set
	 */
	public void setguessWordList(ArrayList<String> guessWordList) {
		this.guessWordList = guessWordList;
	}
	
	
	/**
	 * Initialize the selected word list
	 * @param WordList Original full word list
	 * @param debugMode
	 */
	public void initialSelWordList(ArrayList<String> WordList, boolean debugMode) {
		// For each word in the list, select those with the same length as the starting "word"
		for (String s: WordList) {
			//if the word s's length is same as randomly selected word at the start of the game
			if(s.length() == this.getLength()) {
				
				// Add the word s to the selected word list, e.g. length is 4, s is "book"
				this.getSelWordList().add(s);
				
				// Add a string with repeated "_" of the length to the list that saves the guessed word, 
				// e.g. s is "book", "____" is added to guessWordList
				this.getGuessWordList().add("_".repeat(this.getLength()));	
			}
		}
		
		// Print out the selected word list when in the debug mode
		if(debugMode) {
			System.out.println("The initial selected word is " + this.getWord());	
			System.out.println("The initial selected word list (full) is " + this.getSelWordList());		
			System.out.println("The initial selected word list (guessed) is " + this.getGuessWordList());		
		}
	}
	
	
	/**
	 * Find the max entry of a HashMap with the largest value.
	 * If there are more entries with the same largest values, randomly choose one of them.
	 * @param hashMap of <String, Integer>
	 * @return the key with largest value
	 */
	public String findMaxEntryOfMap(HashMap<String, Integer> hashMap){
		
		// Find the first entry with the largest values. 
		Entry<String, Integer> maxEntry = null;
		//iterate over hashmap to find the key and value of the largest value and store is in maxEntry
		for(Entry<String, Integer> entry: hashMap.entrySet()) {
			if(maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			}	
		}
		
		// Find a list of keys whole values equal the largest value in the first step
		ArrayList<String> maxKeyList = new ArrayList<String>(); 
		// For each key and value in hashmap, if value equals to maxEntry's value, add the key to arraylist maxKeyList
		for(Entry<String, Integer> entry: hashMap.entrySet()) {
			if(entry.getValue() == maxEntry.getValue()) {
				maxKeyList.add(entry.getKey());
			}	
		}
		
		// If there are more than one entries with the same largest values, randomly choose one of them and store in maxKey
		String maxKey;
		if(maxKeyList.size() > 1) {
			Random random = new Random();
			maxKey = maxKeyList.get(random.nextInt(maxKeyList.size()));
		//else, store the only one key with max value in maxKey
		}else {
			maxKey = maxEntry.getKey();
		}
		return maxKey;
	}
	
	
	/**
	 * Remove the string in an arrayList that is not equal to a base string.
	 * @param list an ArrayList of values
	 * @param str a base string
	 */
	public void removeString(ArrayList<String> list , String str) {
		//Create iterator to iterate over list and remove items in place
		Iterator<String> iterator = list.iterator();
		//iterate over each element in list
		while(iterator.hasNext()) {
			String next = iterator.next();
			//if next string doesn't equal to str, remove it from the list
			if (!next.equals(str)) {
				iterator.remove();
			}					
		}
	}
	
	/**
	 * Update selected word list based on users' input letter
	 * Used two hashMaps to track selected word lists, where:
	 * (1) hashMap1 uses guessed word (with "_") as keys, while uses a list of full word as values;
	 * (2) hashMap2 uses guessed word (with "_") as keys, while uses an integer to track number of counts.
	 * Then select the entry in hashMap2 with the largest counts, then update the guessWord and the two selected word list, 
	 * where selWordList is the list with full words, and guessWordList is the list of words that tracking the guesses.
	 * @param letter
	 * @param debugMode
	 */
	public void updateSelWordList(String letter, boolean debugMode) {
		
		// Check if letter is guessed before, i.e. if the letter is in the incorrectGuess list or in the guessWord
		if (this.getIncorrectGuess().contains(letter) || this.getGuessWord().contains(letter)) {
			System.out.println("You already guessed that letter");
		}else {
			// add 1 to numGuess if the letter is not guessed before
			this.setNumGuess(this.getNumGuess() + 1);
		}
		
		// hashMap1 uses guessed word (with "_") as keys, while uses a list of full word as values;
		//e.g. key is "_oo_", value is "["good","book","mood"]"
		HashMap<String, ArrayList<String>> hashMap1 = new HashMap <String, ArrayList<String>>();
		
		//hashMap2 uses guessed word (with "_") as keys, while uses an integer to track number of counts.
		//e.g. key is "_oo_", value is 3 meaning that there are 3 words follow this pattern
		HashMap<String, Integer> hashMap2 = new HashMap <String, Integer>();
	
		// update each word in the selected word list, and categorize them into different groups.
		for(int i = 0; i < this.getSelWordList().size(); i ++ ) {
			String s1 = this.getSelWordList().get(i); // s1 is the full word
			String s2 = this.getGuessWordList().get(i); // s2 is the guessed version for the full word
			
			// Update the "_" in s2 with the letter if the letter is in s1
			s2 = this.replaceWord(s1, s2, letter);
			
			// Replace the i-th guessWordList with s2
			this.getGuessWordList().set(i, s2);
			
			// Update hashMap1 and hashMap2 under the key s2, which is the guessed word.
			if (hashMap1.containsKey(s2)) {
				// hashMap1: If the key exists, append the s1 to the list that corresponds to the values of the key.
				hashMap1.get(s2).add(s1);
				
				// hashMap2: If the key exists, add 1 to the count that corresponds to the values of the key.
				hashMap2.replace(s2, hashMap2.get(s2) + 1);
			}else {
				// hashMap1: If the key doesn't exist, create a new entry with the key and a list to save the full word s1
				ArrayList<String> list = new ArrayList<String>();
				list.add(s1);
				hashMap1.put(s2, list);
				
				// hashMap2: If the key doesn't exist, create an entry with the key and the count 1
				hashMap2.put(s2, 1);
			}		
		}
		
		// Find the key with the largest number of counts
		String maxKey = findMaxEntryOfMap(hashMap2);
		
		// Update the selected word list to be the largest list with the most words in hashMap1
		this.setSelWordList(hashMap1.get(maxKey));
		
		// Remove words that don't equal the maxKey from the guessed word list 
		this.removeString(this.getGuessWordList(), maxKey);
		
		// Update the guessWord to be the maxKey.
		this.setGuessWord(maxKey);
		
		// If the letter is not correct, i.e. not in the guessWord and not in the incorrectGuess list,
		// then add this letter to the incorrectGuess
		if (!this.getGuessWord().contains(letter) & !this.getIncorrectGuess().contains(letter)) {
			this.getIncorrectGuess().add(letter);
		}
	
		// Print necessary information based on the debugMode.
		this.print(debugMode);
	}

	/**
	 * Override the print() function in the Hangman class
	 * Print information of the evil game
	 * @param debugMode 
	 */
	@Override
	public void print( boolean debugMode) {

		// Print the guessed word so far.
		System.out.println(this.getGuessWord());
		
		// Print the incorrect list if it is not empty.
		if (this.getIncorrectGuess().size() > 0 ) {
			System.out.println("Incorrect guesses: " + this.getIncorrectGuess());
		}		
		
		// Print total number of guesses.
		System.out.println("Total guesses: " + this.getNumGuess());
		
		
		// Print the status when in the debug mode.
		if(debugMode) {
			// Print at most 10 words in the selected word list
			System.out.print("Current word list sample: [");
			
			// Limit the length to be the minimum of the size of selWordList and 10
			int len = Math.min(this.getSelWordList().size(), 10);
			for(int i = 0; i < len - 1; i++) {
				System.out.print( this.getSelWordList().get(i) + ", ");
			}
			System.out.print( this.getSelWordList().get(len-1) + "]\n");
			//System.out.println("Current word list sample (guessed)" + this.getGuessWordList());		
		}
			
	}
	

}

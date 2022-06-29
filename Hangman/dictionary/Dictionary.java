package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dictionary {
	
	/**
	 * Read file and put qualified word into a String ArrayList
	 * Qualified word: with all lower case character
	 * Unqualified word: with upper case letter, abbreviations('.'), apostrophe('''),hyphen('-'), digit, compound(" ")
	 * @param fileName
	 * @return ArrayList of qualified words
	 */
	public static ArrayList<String> readFileGetWords(String fileName){
		
		//Initialize an ArrayList to store qualified words
		ArrayList<String> wordList = new ArrayList<String>();
		
		//Read the file
		File file = new File(fileName);
		
		//Initialize fileReader and bufferedReader
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		//try-catch statement to catch IOException
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			//Read each line of the file by while loop 
			while((line=bufferedReader.readLine())!=null) {
				
				//remove the leading and trailing white spaces of the word
				line = line.trim();
				boolean qualifiedWord = true;
				
				//iterate over each character of the string
				for(int i=0; i<line.length(); i++) {
					char c = line.charAt(i);
					//if any character in word is not lower case, set qualifiedWord to false, break the for loop
					if(!(Character.isLowerCase(c))) {
						qualifiedWord = false;
						break;
					}
				}
				//if qualifiedWord is true, this word is with all lower case letters, add to wordList
				if(qualifiedWord) {
					wordList.add(line);
					//System.out.println(line);
				}
				
			}
			
		} catch (IOException e) {
			//catch IOException and print stack trace
			e.printStackTrace();
		}
		
		//return the arraylist of qualified word
		return wordList;
	}		
	
}

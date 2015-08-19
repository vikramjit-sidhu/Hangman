/*
 * File: HangmanLexicon.java
 * -------------------------
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class HangmanLexicon {
	
/** Reading the words from HangmanLexicon.txt into an array list 
 * 	using a buffered reader	
 */
	public HangmanLexicon() {
		wordList = new ArrayList<String>();
		BufferedReader file = null;
		/* Opening the file to read  */
		try {
			file = new BufferedReader(new FileReader("HangmanLexicon.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		/* Reading file contents into array list*/
		try {
			while (true) {
				String word = file.readLine();
				/* The readLine method returns null if end of stream is reached */
				if (word == null) {
					break;
				}
				wordList.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			/* Closing the file */
			try {
				file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return wordList.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return wordList.get(index);
	}
	
	
/** The Array List containing the word list */
	private ArrayList<String> wordList;
}

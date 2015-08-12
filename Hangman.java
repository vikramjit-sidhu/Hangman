/*
 * File: Hangman.java
 * ------------------
 * 
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

/**	The total number of guesses the user has */	
	private static final int INITIAL_NUMBER_OF_GUESSES = 8;

/**	The character which is placed on the screen in place of the actual letters */	
	private static final char WORD_BLANK_CHAR = '-';
	
/**	In case the user tries to act too smart and enter a String as a guess instead of a character, display this message */	
	private static final String INVALID_LENGTH_GUESS_MESSAGE = "Please enter a single character as your guess";
	
/**	The error message displayed if the user enters a non-alphabetical character as her guess */	
	private static final String INVALID_CHAR_ENTERED_MESSAGE = "Please enter only an alphabetical character as your guess";
	
    public void run() {
    	playHangmanGame();
    }
    
/**	This is the main method which creates the console part of the hangman game
 *  
 */
    private void playHangmanGame() {
    	outputWelcomeString();
    	String wordToGuess = getGameWord();
    	/* Setting the word left to guess as initially the whole word */
    	remainingWordToGuess = wordToGuess;
    	/* The initial blank word that the user has to guess set to be blanks (eg. ---- ) */
    	String wordGuessedSoFar = generateGuessedWord(wordToGuess.length());
    	while (numGuessesLeft > 0) {
    		printCurrentlyGuessedWord(wordGuessedSoFar);
    		printCurrentChancesRemaining();
    		char userInput = getUserInput();
    		/* Check the char guessed by user is still in word remaining */
    		if (checkCharInString(wordToGuess, userInput)) {
    			println("That guess is correct");
    			wordGuessedSoFar = modifyGuessedWordFromUserInput(wordGuessedSoFar, userInput);
    		}
    		else {
    			println("There are no " + userInput + "'s in the word");
    		}
    		numGuessesLeft--;
    	}
    }
    
/** Check if the character ch is present in the String word
 * 	Using indexOf built in String method for this, it returns -1 if not found
 * @param word The word which has to be checked to contain the char
 * @param ch The character to check inside the string
 * @return true if ch is contained in word
 */
    private boolean checkCharInString(String word, char ch) {
    	if (word.indexOf(ch) != -1) {
    		return true;
    	}
    	return false;
    }
    
/**	The user has guessed a character correctly, this character is in remainingWordToGuess instance variable and will 
 * 	replace the blanks (default char '-') in the wordGuessedSoFar, the positions it replaces the blanks in is
 * 	the same positions it occurs in the wordToGuess.
 * 
 * 	After finishing the replacing, update the remainingWordToGuess by replacing the occurrence of that character with
 * 	blanks, this is done so that guessing the same char again does not throw errors.
 * 
 * 	Using the indexOf string method to find the occurrence of the char, it is updated at the new string by taking
 * 	the substring.
 *   
 * @param wordGuessedSoFar The word guessed so far, this is updated with blanks (-) replaced with the character the user has guessed
 * @param userInput	The character entered by the user which has to be replaced at the positions it matches in the wortToGuess in the wordGuessedSoFar
 * @return The updated word guessed so far, with userInput char replaced at the required positions.
 */
    private String modifyGuessedWordFromUserInput(String wordGuessedSoFar, char userInput) {
    	/* The last index that the char was found at, initially setting to 0 */
    	int lastIndexFound = 0;
    	/* The next position at which the char is found */
    	int indexCharFound;
    	/* This is the word which is returned as the updated guessed word */
    	String updatedGuessWord = "";
    	
    	while (true) {
    		indexCharFound = remainingWordToGuess.indexOf(userInput, lastIndexFound);
    		if (indexCharFound == -1) {
    			break;
    		}
    		/* Taking the substring upto the latest position that the char is found, from the last position that
    		 * the char was found at, appending char at the end */
    		updatedGuessWord += wordGuessedSoFar.substring(lastIndexFound, indexCharFound) + userInput;
    	}
    	/* Taking care of any trailing chars that may be left */
    	updatedGuessWord += wordGuessedSoFar.substring(lastIndexFound);
    	updateRemainingWordToGuess(userInput);
    	return updatedGuessWord;
    }
    
/**	Update the remainingWordToGuess instance variable.
 * 	if the user successfully guesses a char, updating the remaining word to guess so that in the next guesses, 
 * 	the same char does not match again, simply replacing all occurances of that character with the blank (-) char constant
 * @param userInput
 */
    private void updateRemainingWordToGuess(char userInput) {
    	
    }
    
    
/**	Print the welcome message to the user on console */
    private void outputWelcomeString() {
    	println("Welcome to Hangman !");
    }
    
/**	Before taking input from the user, display the word that she has guessed so far */    
    private void printCurrentlyGuessedWord(String wordGuessedSoFar) {
    	println("The word now looks like this: " + wordGuessedSoFar);
    }
    
/**	Print the number of chances that the user has remaining */    
    private void printCurrentChancesRemaining() {
    	println("You have " + numGuessesLeft + " guesses left");
    }
    
/**	Get the character as input from the user, the character obtained is an alphabetic char in the upper case
 * @return The upper case alphabetic character that the user entered 
 */
    private char getUserInput() {
    	char userChar = readUpperCaseAlphabet("Your guess: ");
    	return userChar;
    }
    
/** Read a alphabet as input from the user.
 * 
 * 	Since a line has to be read (i.e. a String), checking if its length is 1,
 * 	the user is prompted for input until he enters a string of length 1
 * 
 * 	The length 1 string is then checked to see if it is a valid alphabet (a-z or A-Z),
 * 	if a valid alphabet is not entered, prompting the user for input again.
 * @param prompt The prompt to display to the user for initial input
 * @return	An upper case alphabetical character (A-Z) 
 */
    private char readUpperCaseAlphabet(String prompt) {
    	String userInput;
    	char alphabetEntered;
    	while (true) {
    		userInput = readLine(prompt);
    		/* The user input should be a single char, i.e. a string of length 1 */
    		if (userInput.length() > 1) {
    			println(INVALID_LENGTH_GUESS_MESSAGE);
    			continue;
    		}
    		char charEntered = userInput.charAt(0);
    		if (!isAlphabeticChar(charEntered)) {
    			println(INVALID_CHAR_ENTERED_MESSAGE);
    			continue;
    		}
    		alphabetEntered = convertToUpperCase(charEntered);
    		break;
    	}
    	return alphabetEntered;
    }
    
/** Checks if the character passed is an alphabetical character, i.e. a-z or A-Z
 * @param charEntered The character to check
 * @return true if it is an alphabetical char
 */
    private boolean isAlphabeticChar(char ch) {
    	if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
    		return true;
    	}
    	return false;
    }
    
/**	Given a character as input, convert it to upper case and return
 * 	If the character is already upper case, return as it is, if it is lower case converting it.
 * @param ch The character input which is converted to upper case
 * @return the upper case value of the character input
 */
    private char convertToUpperCase(char ch) {
    	if (ch >= 'a' && ch <= 'z') {
    		ch = (char) ((ch - 'a') + 'A');
    	}
    	return ch;
    }
 
/**	Creates a String of form "-----" with length of the parameter passed
 * 	The '-' character is taken from a constant, it can be changed
 * @param guessedWordLen The length of the return word
 * @return The string in form of "---"
 */
    private String generateGuessedWord(int guessedWordLen) {
    	/* Create a new array of chars with the length required, replace \0 with the string required
    	 * The character WORD_BLANK_CHAR has to be converted to a string to use it in replace method */
    	String guessWord = new String(new char[guessedWordLen]).replace("\0", String.valueOf(WORD_BLANK_CHAR));
    	return guessWord;
    }
    
    
/**	Get the word which the user has to guess
 * Uses the Lexicon class to get the total number of words,
 * using this as a limit, the random number generator gets a number within this range.
 * This randomly generated number is the index of the number which is obtained
 * @return The word which is to be guessed
 */
    private String getGameWord() {
    	String wordToReturn = null;
    	int numTotalWords = getTotalGameWords();
    	/* The index of the word which is to be obtained from the lexicon class
    	 * nextInt(num) generates a random int in range [0, num) */
    	int wordIndex = randomGen.nextInt(numTotalWords);
    	try {
    		wordToReturn = lexicon.getWord(wordIndex);
    	} catch (ErrorException e) {
    		println("Error encountered, could not get your word");
    	}
    	return wordToReturn;
    }
   
/**	Gets the total number of words that the lexicon holds
 * @return total number of words
 */
    private int getTotalGameWords() {
    	return lexicon.getWordCount();
    }
    
    
/**	Keep track of the number of guesses the user has left in this variable
 * 	Initial value is the total guesses the user has */    
    private int numGuessesLeft = INITIAL_NUMBER_OF_GUESSES;
    
/**	The object which is used to get a random word for the game */    
    private HangmanLexicon lexicon = new HangmanLexicon();
    
/** Random number generator, used to get a random word for the game */    
    private RandomGenerator randomGen = RandomGenerator.getInstance();
    
/**	This word is initially equal to the word randomly chosen from the lexical class, as the user makes her
 * 	guesses, it is updated, at each point it shows the characters remaining for the user to guess. 
 * 	It is initialized in playHangmanGame() */    
    private String remainingWordToGuess;
}

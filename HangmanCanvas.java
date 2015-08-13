/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 400;	//earlier value was 360
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		setWindowDimensions();
		/* The scaffold is the only element whose co-ordinates are calculated, 
		 * the co-ordinates of the remaining elements are calculated relative to it*/
		calculateSetScaffoldDimensions();
		
		/* Draw the initial parts of the game */
		drawScaffold();
		drawBeam();
		drawRope();
		
		/* Set up the labels which keep track of the incorrect guesses and word so far */
		addIncorrectGuessLabel();
		addWordGuessedLabel();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		
	}

/** Sets the instance variables windowWidth and windowWidth,
 *  to the height and width of the application window */	
	private void setWindowDimensions() {
		windowWidth = getWidth();
		windowHeight = getHeight();
	}
	
	private void calculateSetScaffoldDimensions() {
		
	}
	
	private void drawScaffold() {
		
	}
	
	private void drawBeam() {
		
	}
	
	private void drawRope() {
		
	}
	
	
	private void addIncorrectGuessLabel() {
		
	}
	
	private void addWordGuessedLabel() {
		
	}
	
/** The number of incorrect guesses made so far, on the basis of this number, the various
 *  body parts are drawn on the window */
	private int incorrectGuessesMade = 0;
	
/** The label displaying the word which the user has guessed so far */
	private GLabel wordGuessedSoFar;
	
/**	The label recording all the incorrect guesses made by the user */
	private GLabel incorrectGuesses;
	
/** The width and height of the application window, where the elements are drawn */	
	private int windowWidth;
	private int windowHeight;
}

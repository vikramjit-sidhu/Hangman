/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Font;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;	//earlier value was 360
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
	
/** The X offset for the labels which keep track of the incorrect guesses,
 *  and the word guessed so far*/	
	private static final int LABEL_X_OFFSET = 50;
	
/** The y offset between the labels*/	
	private static final int Y_OFFSET_BETWEEN_LABELS = 25;
/** The y offset between the scaffold and the first label */	
	private static final int Y_OFFSET_BETWEEN_SCAFFOLD_LABEL = 40;
	
/** Two constructors defined, if height and width of window is passed, to the constructor,
 * 	setting it to the values passed
 * 	
 * 	If the value is not passed, setting width and height using methods of the GCanvas
 */
	public HangmanCanvas() {
		windowWidth = getWidth();
		windowHeight = getHeight();
	}
	
	public HangmanCanvas(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		/* The scaffold is the only element whose co-ordinates are calculated, 
		 * the co-ordinates of the remaining elements are calculated relative to it */
		setScaffoldOffset();
		
		/* Draw the initial parts of the game */
		drawScaffold();
		drawBeam();
		drawRope();
		
		/* Set up the labels which keep track of the incorrect guesses and word so far */
		addWordGuessedLabel();
		addIncorrectGuessLabel();
		
		/*Since the game is just starting, set the number of wrong guesses to 0 */
		incorrectGuessesMade = 0;
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 * 
 * Removes the label displaying the currently guessed word and adds it again
 * to the same co-ordinates with the updated word
 */
	public void displayWord(String word) {
		wordGuessedSoFarLabel.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		String oldCharsGuessed = incorrectGuessesLabel.getLabel();
		String currCharsGuessed = oldCharsGuessed + letter;
		incorrectGuessesLabel.setLabel(currCharsGuessed);
		
		switch (incorrectGuessesMade) {
			case 0:
				drawHead();
				break;
			case 1:
				drawBody();
				break;
			case 2:
				drawLeftArm();
				break;
			case 3:
				drawRightArm();
				break;
			case 4:
				drawLeftLeg();
				break;
			case 5:
				drawRightLeg();
				break;
			case 6:
				drawLeftFoot();
				break;
			case 7:
				drawRightFoot();
				break;
			case 8:
				/* The user has lost the game */
				break;
			default:
				System.err.println("Incorrect guess made");
				break;
		}
		incorrectGuessesMade++;
				
	}

	
///** Sets the instance variables windowWidth and windowWidth,
// *  to the height and width of the application window */	
//	private void setWindowDimensions() {
//		windowWidth = getWidth();
//		windowHeight = getHeight();
//	}
	
	private void setScaffoldOffset() {
		/* The head has to be at the centre of the window, hence taking into account 
		 * the beam length, this is the x position of the scaffold  */
		scaffoldTopX = (windowWidth / 2) - BEAM_LENGTH;
		
		/* This is the space remaining in the y dimension after all the elements are added 
		 * not including the heights of the labels, so that will be an error*/
		int spaceRemY = windowHeight - 
								(SCAFFOLD_HEIGHT + Y_OFFSET_BETWEEN_LABELS + Y_OFFSET_BETWEEN_SCAFFOLD_LABEL);
		scaffoldTopY = spaceRemY / 2;
	}
	
	
/** Draw the scaffold, i.e. the 'pole' which supports the hanging
 * 	It is a simple GLine object added to screen
 * 
 * 	The top x and y co-ordinates are already ready as instance variables,
 * 	since it is a vertical line, the x co-ordinate remains the same, only 
 * 	the lower y co-ordinate is calculated which has the height added to it.		
 */
	private void drawScaffold() {
		double scaffoldLowerY = scaffoldTopY + SCAFFOLD_HEIGHT;
		GLine scaffold = new GLine(scaffoldTopX, scaffoldTopY, scaffoldTopX, scaffoldLowerY);
		add(scaffold);
	}
	
/**	Add the beam supporting the rope and the man on the screen
 * 
 * 	The GLine representing the beam begins from the same co-ordinates as the
 * 	top of the scaffold.
 * 	Since it is a horizontal line, for its endpoint only the x co-ordinate changes
 *  with the length of the beam added to it. 	
 */
	private void drawBeam() {
		/* The start and end co-ordinates of the line representing the beam,
		 * remember it is a horizontal line */
		double beamY = scaffoldTopY;
		double beamStartX = scaffoldTopX;
		double beamEndX = beamStartX + BEAM_LENGTH;
		GLine beam = new GLine(beamStartX, beamY, beamEndX, beamY);
		add(beam);
	}
	
/** Add the rope which the person is hung on 
 * 	
 * 	It is a vertical line starting from the ending co-ordinate of the beam,
 *  this is used as the starting point and end points of the rope are calculated
 *  as required
 */
	private void drawRope() {
		/* The start and end co-ordinates of the line representing the rope, 
		 * it is a vertical line */
		double ropeX = scaffoldTopX + BEAM_LENGTH;
		double ropeStartY = scaffoldTopY;
		double ropeEndY = ropeStartY + ROPE_LENGTH;
		GLine rope = new GLine(ropeX, ropeStartY, ropeX, ropeEndY);
		add(rope);
	}
	
/** Draw the head using the offsets defined for the scaffold
 * 	The co-ordinates obtained for the head are that of the top left corner 
 * 	of the square which contains the oval.
 * 
 * 	The constructor of the GOval contains is passed the x, y co-ordinates and the
 * 	length of the sides making up the square
 */
	private void drawHead() {
		GPoint headCoOrdinates = getHeadPosition();
		GOval head = new GOval(headCoOrdinates.getX(), headCoOrdinates.getY(), 2*HEAD_RADIUS, 2*HEAD_RADIUS);
		add(head);
	}
	
/** Set the position for the head, using the position of the scaffold 
 * 	as a reference. The co-ordinates returned is the top left position of the
 * 	square holding the circle
 * 
 * 	For the x co-ordinates, the offset because of the beam will have to be added, 
 * 	the radius of the head will have to be subtracted.
 * 	For the y co-ordinate, the rope length will have to be considered.
 */
	private GPoint getHeadPosition() {
		double xpos, ypos;
		xpos = scaffoldTopX + BEAM_LENGTH - HEAD_RADIUS;
		ypos = scaffoldTopY + ROPE_LENGTH;
		return new GPoint(xpos, ypos);
	}
	
/** Draw the line, which represents the body, it starts just when the head 
 * 	ends, and goes on as a horizontal line.	
 */
	private void drawBody() {
		GPoint startPointBody = getBodyStartPoint();
		double startx = startPointBody.getX();
		double endx = startx;
		double starty = startPointBody.getY();
		double endy = starty + BODY_LENGTH; 
		GLine body = new GLine(startx, starty, endx, endy);
		add(body);
		
	}
	
/** Get the starting point for the line representing the body
 * 	The x co-ordinate has to add the beam length to the scaffold x co-or
 * 	The y co-ordinate is the scaffold y co-or along with the rope length and 
 *  the head diameter
 *  
 *  This method is used to calculate the co-ordinates for all the body parts below it,
 *  i.e. the arm, leg and foot
 * @return The GPoint object representing the starting location for the line
 */
	private GPoint getBodyStartPoint() {
		double xpos, ypos;
		xpos = scaffoldTopX + BEAM_LENGTH;
		ypos = scaffoldTopY + ROPE_LENGTH + (2 * HEAD_RADIUS);
		return new GPoint(xpos, ypos);
	}
	
/** Draw the left arm.
 * 	The starting position for the arm is in between the body, a specific y offset from the head
 * 	hence the starting position for the body can be used, with the same x co-ordinate and an offset 
 * 	added to the y co-ordinate.
 * 
 * 	Draw the upper arm as a horizontal line and lower arm as a vertical line. 
 */
	private void drawLeftArm() {
		GPoint bodyStartPt = getBodyStartPoint();
		double xpos = bodyStartPt.getX();
		double ypos = bodyStartPt.getY() + ARM_OFFSET_FROM_HEAD;
		/* drawing the upper arm (horizontal line) */
		add(new GLine(xpos, ypos, xpos - UPPER_ARM_LENGTH, ypos));
		/* drawing the lower arm (vertical line) */
		add(new GLine(xpos - UPPER_ARM_LENGTH, ypos, xpos - UPPER_ARM_LENGTH, ypos + LOWER_ARM_LENGTH));
	}
	
/** Draw the right arm.
 * 	The starting position for the arm is in between the body, a specific y offset from the head
 * 	hence the starting position for the body can be used, with the same x co-ordinate and an offset 
 * 	added to the y co-ordinate.
 * 
 * 	Drawing the upper arm as a horizontal line and the lower arm as a vertical line
 */
	private void drawRightArm() {
		GPoint bodyStartPt = getBodyStartPoint();
		double xpos = bodyStartPt.getX();
		double ypos = bodyStartPt.getY() + ARM_OFFSET_FROM_HEAD;
		/* drawing the upper arm (horizontal line) */
		add(new GLine(xpos, ypos, xpos + UPPER_ARM_LENGTH, ypos));
		/* drawing the lower arm (vertical line) */
		add(new GLine(xpos + UPPER_ARM_LENGTH, ypos, xpos + UPPER_ARM_LENGTH, ypos + LOWER_ARM_LENGTH));
	}
	
/**	Draw the left leg of the body.
 * 	Calculating the starting point for the leg from the start
 * 	co-ordinate of the body.
 * 
 * 	It is a horizontal line, followed by a vertical line where the horizontal line ends	
 */
	private void drawLeftLeg() {
		GPoint bodyStartPt = getBodyStartPoint();
		double xpos = bodyStartPt.getX();
		double ypos = bodyStartPt.getY() + BODY_LENGTH;
		/* Adding the hip */
		add(new GLine(xpos, ypos, xpos - HIP_WIDTH, ypos));
		add(new GLine(xpos - HIP_WIDTH, ypos, xpos - HIP_WIDTH, ypos + LEG_LENGTH));
	}
	
/**	Draw the left leg of the body.
 * 	Calculating the starting point for the leg from the start
 * 	co-ordinate of the body.
 * 
 * 	It is a horizontal line, followed by a vertical line where the horizontal line ends	
 */
	private void drawRightLeg() {
		GPoint bodyStartPt = getBodyStartPoint();
		double xpos = bodyStartPt.getX();
		double ypos = bodyStartPt.getY() + BODY_LENGTH;
		/* Adding the hip */
		add(new GLine(xpos, ypos, xpos + HIP_WIDTH, ypos));
		add(new GLine(xpos + HIP_WIDTH, ypos, xpos + HIP_WIDTH, ypos + LEG_LENGTH));
	}	
	
/** Draw the left leg of the man.
 * 	
 * 	Calculating co-ordinates of the leg, relative to the starting position of the body (getBodyStartPoint)
 * 	The x co-ordinate is offset by the hip length
 * 	The y co-ordinate is offset by the body length and the leg length.
 * 
 * 	It is a single horizontal line	
 */
	private void drawLeftFoot() {
		GPoint bodyStartPt = getBodyStartPoint();
		double xpos = bodyStartPt.getX() - HIP_WIDTH;
		double ypos = bodyStartPt.getY() + BODY_LENGTH + LEG_LENGTH;
		/* Adding the leg, it is a single line */
		add(new GLine(xpos, ypos, xpos - FOOT_LENGTH, ypos));
	}

/** Draw the left leg of the man.
 * 	
 * 	Calculating co-ordinates of the leg, relative to the starting position of the body (getBodyStartPoint)
 * 	The x co-ordinate is offset by the hip length
 * 	The y co-ordinate is offset by the body length and the leg length.
 * 
 * 	It is a single horizontal line	
 */
	private void drawRightFoot() {
		GPoint bodyStartPt = getBodyStartPoint();
		double xpos = bodyStartPt.getX() + HIP_WIDTH;
		double ypos = bodyStartPt.getY() + BODY_LENGTH + LEG_LENGTH;
		/* Adding the leg, it is a single line */
		add(new GLine(xpos, ypos, xpos + FOOT_LENGTH, ypos));
	}
	

/** Add the label which displays the status of the word guessed so far
 * 	Also sets the instance variable point which contains the co-ordinates of
 * 	the baseline of this label.
 * 	This is done so that it is easier to draw the label later on without
 *  re-calculating the point each time.	
 */
	private void addWordGuessedLabel() {
		setWordGuessedPoint();
		wordGuessedSoFarLabel = new GLabel("");
		wordGuessedSoFarLabel.setLocation(wordGuessedSoFarStartPoint);
		add(wordGuessedSoFarLabel);
	}
	
/** Set the point for the word guessed so far label 
 *  This method assumes that the offset for the scaffold has already been set */
	private void setWordGuessedPoint() {
		double ypos = scaffoldTopY + SCAFFOLD_HEIGHT + Y_OFFSET_BETWEEN_SCAFFOLD_LABEL; 
		wordGuessedSoFarStartPoint.setLocation(LABEL_X_OFFSET, ypos);
	}
	
/** The label showing the incorrect guesses is added
 * */	
	private void addIncorrectGuessLabel() {
		setIncorrectGuessPoint();
		incorrectGuessesLabel = new GLabel("");
		incorrectGuessesLabel.setLocation(incorrectGuessesStartPoint);
		add(incorrectGuessesLabel);
	}
	
/** Set the point which describes the location of the label 
 * 	holding the incorrect guesses made.
 *  This method assumes that the point for the word guessed so far, i.e.
 *  wordGuessedSoFarStartPoint is set.
 */
	private void setIncorrectGuessPoint() {
		double ypos = wordGuessedSoFarStartPoint.getY() + Y_OFFSET_BETWEEN_LABELS;
		incorrectGuessesStartPoint.setLocation(LABEL_X_OFFSET, ypos);
	}
	
	
/** The number of incorrect guesses made so far, on the basis of this number, the various
 *  body parts are drawn on the window */
	private int incorrectGuessesMade = 0;
	
/** The label displaying the word which the user has guessed so far */
	private GLabel wordGuessedSoFarLabel;
/** The baseline co-ordinates of the word guessed so far label */	
	private GPoint wordGuessedSoFarStartPoint = new GPoint();
	
/**	The label recording all the incorrect guesses made by the user */
	private GLabel incorrectGuessesLabel;
/** The baseline co-ordinates of the incorrect guesses label*/	
	private GPoint incorrectGuessesStartPoint = new GPoint();
	
/** The width and height of the application window, where the elements are drawn */	
	private int windowWidth;
	private int windowHeight;
	
/** The x,y co-ordinates of the top of the scaffold, 
 *  every co-ordinate is calculated relative to them */
	private double scaffoldTopX = 0;
	private double scaffoldTopY = 0;
	
}

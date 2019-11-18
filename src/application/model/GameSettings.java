package application.model;

import javafx.scene.input.KeyCode;

/**
 * A class to hold values set by the user in the settings menu
 * 
 * This class will hold various key bindings and mouse sensitivity elements that will be used 
 * by the GameControl object.
 *
 */
public class GameSettings {
	private double sensitivity;
	private KeyCode forward;
	private KeyCode backward;
	private KeyCode left;
	private KeyCode right;
	private KeyCode up;
	private KeyCode down;
	private KeyCode pause;

	/**
	 * setter function for forward element
	 * @param set KeyCode that should be assigned
	 */
	public void setForward(KeyCode set) {
		forward = set;
	}

	/**
	 * setter function for backward element
	 * @param set KeyCode that should be assigned
	 */
	public void setBack(KeyCode set) {
		backward = set;
	}

	/**
	 * setter function for right element
	 * @param set KeyCode that should be assigned
	 */
	public void setRight(KeyCode set) {
		right = set;
	}

	/**
	 * setter function for left element
	 * @param set KeyCode that should be assigned
	 */
	public void setLeft(KeyCode set) {
		left = set;
	}

	/**
	 * setter function for up element
	 * @param set KeyCode that should be assigned
	 */
	public void setUp(KeyCode set) {
		up = set;
	}

	/**
	 * setter function for down element
	 * @param set KeyCode that should be assigned
	 */
	public void setDown(KeyCode set) {
		down = set;
	}

	/**
	 * setter function for down element
	 * @param set KeyCode that should be assigned
	 */
	public void setPause(KeyCode set) {
		pause = set;
	}

	/**
	 * setter function for sensitivity element
	 * @param set double that should be assigned
	 */
	public void setSensitivity(double set) {
		this.sensitivity = set;
	}

	/**
	 * getter for KeyCode held in forward
	 * @return
	 */
	public KeyCode getForward() {
		return forward;
	}

	/**
	 * getter for KeyCode held in backward
	 * @return
	 */
	public KeyCode getBack() {
		return backward;
	}

	/**
	 * getter for KeyCode held in left
	 * @return
	 */
	public KeyCode getLeft() {
		return left;
	}

	/**
	 * getter for KeyCode held in right
	 * @return
	 */
	public KeyCode getRight() {
		return right;
	}

	/**
	 * getter for KeyCode held in up
	 * @return
	 */
	public KeyCode getUp() {
		return up;
	}

	/**
	 * getter for KeyCode held in down
	 * @return
	 */
	public KeyCode getDown() {
		return down;
	}

	/**
	 * getter for KeyCode held in pause
	 * @return
	 */
	public KeyCode getPause() {
		return pause;
	}

	/**
	 * getter for double held in sensitivity
	 * @return
	 */
	public double getSensitivity() {
		return sensitivity;
	}

	/**
	 * default constructor
	 * 
	 * sets forward to KeyCode.W, backward to KeyCode.S, left to KeyCode.A, right to KeyCode.D and
	 * sensitivity to 1.
	 */
	public GameSettings() {
		setSensitivity(1);
		setForward(KeyCode.W);
		setBack(KeyCode.S);
		setLeft(KeyCode.A);
		setRight(KeyCode.D);
		setUp(KeyCode.SPACE);
		setDown(KeyCode.CONTROL);
		setPause(KeyCode.ESCAPE);
	}
}

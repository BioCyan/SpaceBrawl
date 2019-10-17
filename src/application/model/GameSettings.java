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
	// used to determine how sensitive the controls are to mouse movement
	private double sensitivity;
	// used to move player location forward
	private KeyCode forward;
	// used to move player location backwards 
	private KeyCode backward;
	// used to move player location left
	private KeyCode left;
	// used to move player location right
	private KeyCode right;
	/**
	 * setter function for forward element
	 * @param set KeyCode that should be assigned
	 */
	public void setForward(KeyCode set) {
		this.forward = set;
	}
	/**
	 * setter function for backward element
	 * @param set KeyCode that should be assigned
	 */
	public void setBack(KeyCode set) {
		this.backward = set;
	}
	/**
	 * setter function for right element
	 * @param set KeyCode that should be assigned
	 */
	public void setRight(KeyCode set) {
		this.right = set;
	}
	/**
	 * setter function for left element
	 * @param set KeyCode that should be assigned
	 */
	public void setLeft(KeyCode set) {
		this.left = set;
	}
	/**
	 * setter function for sensitivity element
	 * @param set double that should be assigned
	 */
	public void setSensitivity(double set) {
		this.sensitivity = set;
	}
	/**
	 * Original setter function called by constructor. 
	 * 
	 * @param f value to set forward to
	 * @param b value to set backward to
	 * @param l value to set left to
	 * @param r value to set right to
	 * @param s value to set sensitivity to
	 */
	public void setDefualts(KeyCode f, KeyCode b, KeyCode l, KeyCode r, double s ) {
		setForward(f);
		setBack(b);
		setLeft(l);
		setRight(r);
		setSensitivity(s);
		
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
	public KeyCode getright() {
		return right;
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
	public GameSettings(){
		setDefualts(KeyCode.W, KeyCode.S, KeyCode.A, KeyCode.D, 1);
	}
}

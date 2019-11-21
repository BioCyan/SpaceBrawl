package application.model;

import java.util.HashMap;
import javafx.scene.input.KeyCode;

/**
 * A class to hold values set by the user in the settings menu
 * 
 * This class will hold various key bindings and mouse sensitivity elements that will be used 
 * by the GameControl object.
 *
 */
public class GameSettings {
	public enum ActionType {
		Forward,
		Back,
		Left,
		Right,
		Up,
		Down,
		Pause,
	};

	private HashMap<ActionType, KeyCode> keyCodes;
	private double sensitivity;
	/*
	private KeyCode forward;
	private KeyCode backward;
	private KeyCode left;
	private KeyCode right;
	private KeyCode up;
	private KeyCode down;
	private KeyCode pause;
	*/

	public void setActionKey(ActionType action, KeyCode key) {
		keyCodes.put(action, key);
	}

	/*
	/**
	 * setter function for forward element
	 * @param set KeyCode that should be assigned
	 */
	public void setForward(KeyCode set) {
		keyCodes.put(ActionType.Forward, set);
	}

	/**
	 * setter function for backward element
	 * @param set KeyCode that should be assigned
	 */
	public void setBack(KeyCode set) {
		keyCodes.put(ActionType.Back, set);
	}

	/**
	 * setter function for right element
	 * @param set KeyCode that should be assigned
	 */
	public void setRight(KeyCode set) {
		keyCodes.put(ActionType.Right, set);
	}

	/**
	 * setter function for left element
	 * @param set KeyCode that should be assigned
	 */
	public void setLeft(KeyCode set) {
		keyCodes.put(ActionType.Left, set);
	}

	/**
	 * setter function for up element
	 * @param set KeyCode that should be assigned
	 */
	public void setUp(KeyCode set) {
		keyCodes.put(ActionType.Up, set);
	}

	/**
	 * setter function for down element
	 * @param set KeyCode that should be assigned
	 */
	public void setDown(KeyCode set) {
		keyCodes.put(ActionType.Down, set);
	}

	/**
	 * setter function for down element
	 * @param set KeyCode that should be assigned
	 */
	public void setPause(KeyCode set) {
		keyCodes.put(ActionType.Pause, set);
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
		return keyCodes.get(ActionType.Forward);
	}

	/**
	 * getter for KeyCode held in backward
	 * @return
	 */
	public KeyCode getBack() {
		return keyCodes.get(ActionType.Back);
	}

	/**
	 * getter for KeyCode held in left
	 * @return
	 */
	public KeyCode getLeft() {
		return keyCodes.get(ActionType.Left);
	}

	/**
	 * getter for KeyCode held in right
	 * @return
	 */
	public KeyCode getRight() {
		return keyCodes.get(ActionType.Right);
	}

	/**
	 * getter for KeyCode held in up
	 * @return
	 */
	public KeyCode getUp() {
		return keyCodes.get(ActionType.Up);
	}

	/**
	 * getter for KeyCode held in down
	 * @return
	 */
	public KeyCode getDown() {
		return keyCodes.get(ActionType.Down);
	}

	/**
	 * getter for KeyCode held in pause
	 * @return
	 */
	public KeyCode getPause() {
		return keyCodes.get(ActionType.Pause);
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
		keyCodes = new HashMap<>();

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

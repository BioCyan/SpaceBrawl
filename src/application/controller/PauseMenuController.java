package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * a controller for the pause menu
 *
 * should be called by a KeyEvent handler in the GameController object. Will display a menu based on
 * an .fxml and will give the option to quit the game or to open the settings menu.
 */
public class PauseMenuController {

	//The resume method is the action handle for when the Resume buttton is clicked to go to the main menu
	@FXML
	public void resume(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Game);
	}

	//The setting method is the action handle for when the Settings button is clicked to go to the change setting scene
	@FXML
	public void settings(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Settings);
	}

	//The menu method is the action handle for when the Menu button is clicked to go to the main menu scene
	@FXML
	public void menu(ActionEvent e) throws IOException {
		Main.game = null;
		Main.switchScene(Main.SceneType.Main);
	}

	//The quit method is the action handle for when the Quit button is clicked to quit the game
	@FXML
	public void quit(ActionEvent e) {
		Main.exit();
	}
}

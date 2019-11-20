package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.stage.Stage;
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
	void resume(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Game);
	}

	//The setting method is the action handle for when the Settings button is clicked to go to the change setting scene
	@FXML
	void settings(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Settings);
	}

	//The menu method is the action handle for when the Menu button is clicked to go to the main menu scene
	@FXML
	void menu(ActionEvent e) throws IOException {
		Main.game = null;
		Main.switchScene(Main.SceneType.Main);
	}

	//The quit method is the action handle for when the Quit button is clicked to quit the game
	@FXML
	void quit(ActionEvent e) {
		Main.exit();
	}
}

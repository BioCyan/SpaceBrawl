package application.controller;

import application.Main;
import application.model.Game;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
	@FXML private Button startButton;

	@FXML private Button settingsButton;

	@FXML private Button exitButton;

	//The start method is the action handle for Start button in main menu to began the game
	@FXML
	void start(ActionEvent e) throws IOException {
		Main.game = new Game();
		Main.switchScene(Main.SceneType.Game);
	}

	//The settings method is the action handle for the Settings button in main menu to got to the settings page
	@FXML
	void settings(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Settings);
	}

	//The exit method is the action handle for the Exit button in the main menu to exit the game
	@FXML
	void exit(ActionEvent e) {
		Main.exit();
	}
}

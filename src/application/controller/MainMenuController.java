package application.controller;

import application.Main;
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

	@FXML
	void start(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Game);
	}

	@FXML
	void settings(ActionEvent e) throws IOException {
		Main.switchScene(Main.SceneType.Settings);
	}

	@FXML
	void exit(ActionEvent e) {
		Main.exit();
	}
}

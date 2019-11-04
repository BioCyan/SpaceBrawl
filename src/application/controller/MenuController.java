package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
	@FXML
	private Button startButton;

	@FXML
	private Button settingsButton;

	@FXML
	private Button exitButton;

	private Stage stage;

	@FXML
	void start(ActionEvent e) throws IOException {
		GameController controller = new GameController();
		controller.start(stage);
	}

	@FXML
	void settings(ActionEvent e) {
		// TODO: needs a call to SettingsController and a close to parent stage
		System.out.println("settings called");
	}

	@FXML
	void exit(ActionEvent e) {
		//TODO: needs a close to parent stage
		System.out.println("exit called");
		stage.close();
	}

	public void initialize(Stage stage) {
		this.stage = stage;
	}
}

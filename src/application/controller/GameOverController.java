package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class GameOverController {
	@FXML private Label shotsLabel;
	@FXML private Label scoreLabel;

	@FXML public void initialize() {
		shotsLabel.setText("Shots: " + Main.game.shots.size());
		scoreLabel.setText("Score: " + Main.game.score);
	}

	@FXML
	public void menu(ActionEvent e) {
		Main.game = null;
		Main.switchScene(Main.SceneType.Main);
	}
}

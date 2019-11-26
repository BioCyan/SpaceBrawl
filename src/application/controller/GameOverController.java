package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class GameOverController {
	@FXML private Label shotsLabel;
	@FXML private Label scoreLabel;

	/**
	 * The initialize method displays the score and number
	 * of shots accumulated through out the game
	 */
	@FXML public void initialize() {
		shotsLabel.setText("Shots: " + Main.game.shots.size());
		scoreLabel.setText("Score: " + Main.game.score);
	}

	/**
	 * The menu Event Handle handles the event when the player
	 * clicks the button, it goes to the Main Menu screen
	 * @param e
	 */
	@FXML
	public void menu(ActionEvent e) {
		Main.game = null;
		Main.switchScene(Main.SceneType.Main);
	}
}

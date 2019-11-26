package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.IOException;

public class GameOverController {
	@FXML private Label shotsLabel;
	@FXML private Label scoreLabel;

	@FXML
	public void menu(ActionEvent e) {
		Main.switchScene(Main.SceneType.Main);
	}
}

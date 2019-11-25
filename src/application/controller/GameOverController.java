package application.controller;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class GameOverController {
	@FXML
	public void menu(ActionEvent e) {
		Main.switchScene(Main.SceneType.Main);
	}
}

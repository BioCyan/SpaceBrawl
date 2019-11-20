package application.controller;

/**
 * a controller for the settingsMenu view.
 *
 * This object should be passed a GameSettings object at construction and should allow
 * for the user to make changes to the elements of this class to be used by GameController.
 */

import application.Main;
import application.model.GameSettings;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SettingsMenuController {
	@FXML private GridPane grid;
	private HashMap<Button, Label> buttonsToLabels;
	private GameSettings settings;

	@FXML public void initialize() {
		settings = Main.settings;

		HashMap<Integer, Button> buttons = new HashMap<>();
		HashMap<Integer, Label> labels = new HashMap<>();
		for (Node node : grid.getChildren()) {
			Integer column = GridPane.getColumnIndex(node);
			if (column == null) {
				continue;
			} else if (column == 1) {
				labels.put(grid.getRowIndex(node), (Label)node);
			} else if (column == 2) {
				buttons.put(grid.getRowIndex(node), (Button)node);
			}
		}

		buttonsToLabels = new HashMap<>();
		for (Integer row : buttons.keySet()) {
			int i;
			if (row == null) {
				i = 0;
			} else {
				i = row;
			}
			buttonsToLabels.put(buttons.get(i), labels.get(i));
		}
	}

	public void menuButton(ActionEvent event) {
		if (Main.game == null) {
			Main.switchScene(Main.SceneType.Main);
		} else {
			Main.switchScene(Main.SceneType.Pause);
		}
	}

	public void changeButton(ActionEvent event) {
	}
}

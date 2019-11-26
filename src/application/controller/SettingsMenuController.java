package application.controller;

/**
 * a controller for the settingsMenu view.
 *
 * This object should be passed a GameSettings object at construction and should allow
 * for the user to make changes to the elements of this class to be used by GameController.
 */

import application.Main;
import application.model.GameSettings;
import application.model.GameSettings.ActionType;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SettingsMenuController {
	@FXML private GridPane grid;
    private HashMap<Button, ActionType> buttons;
    private HashMap<ActionType, Label> labels;
	private GameSettings settings;
	private Button changingButton;

	@FXML
	private Slider senSlider;

	/**
	 * The initialize method changes value label in the
	 * SettingsMenuController that has been changed
	 */
	@FXML 
	public void initialize() {
		settings = Main.settings;

		buttons = new HashMap<>();
		labels = new HashMap<>();
        HashMap<Integer, ActionType> actions = new HashMap<>();
        actions.put(0, ActionType.Left);
        actions.put(1, ActionType.Right);
        actions.put(2, ActionType.Forward);
        actions.put(3, ActionType.Back);
        actions.put(4, ActionType.Up);
        actions.put(5, ActionType.Down);
        actions.put(6, ActionType.Pause);

		for (Node node : grid.getChildren()) {
			Integer column = GridPane.getColumnIndex(node);
			if (column == null) {
				continue;
			}

			Integer row = grid.getRowIndex(node);
			if (row == null) {
				row = 0;
			}
			if(row == 7)
				break;
			else if (column == 1) {
				labels.put(actions.get(row), (Label)node);
			} else if (column == 2) {
				buttons.put((Button)node, actions.get(row));
			}
			
		}
	}

	/**
	 * The menuButton Event Handle handles the event when
	 * the user clicks the Back button which goes to the
	 * Main Menu screen or Pause Menu screen
	 * @param event
	 */
	public void menuButton(ActionEvent event) {
		if(changingButton == null) {
			if (Main.game == null) {
				Main.switchScene(Main.SceneType.Main);
			} else {
				Main.switchScene(Main.SceneType.Pause);
			}
		}else {
			changingButton.setText("Change");
			changingButton.setOnKeyPressed(null);
			ActionType action = buttons.get(changingButton);
			labels.get(action).setText(settings.getActionKey(action).getName());
			changingButton = null;
		}
	}

	/**
	 * The changeButton Event Handle handles the event when the
	 * user clicks the Change button which allows them to make
	 * changes to the game controller. This also changes the
	 * GameControllers controls
	 * @param event
	 */
	public void changeButton(MouseEvent event) {
		if (changingButton != null) {
			changingButton.setText("Change");
			labels.get(buttons.get(changingButton)).setText(settings.getActionKey(buttons.get(changingButton)).getName());
		}
		Button button = (Button)event.getSource();
		button.setText("...");
		changingButton = button;
		ActionType action = buttons.get(changingButton);
		labels.get(action).setText("Press any Key");
		changingButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				ActionType action = buttons.get(changingButton);
				KeyCode key = event.getCode();
				settings.setActionKey(action, key);
				labels.get(action).setText(key.getName());
				changingButton.setText("Change");
				changingButton.setOnKeyPressed(null);
				changingButton = null;
			}
		});
	}

	/**
	 * The slide Event Handle handles when the user
	 * changes the sensitivity of the mouse in the
	 * SettingMenuController scene
	 * @param event
	 */
	@FXML
	public void slide(MouseEvent event) {
		settings.setSensitivity(senSlider.getValue());
	}
}

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
	private Stage stage;
	private FXMLLoader loader;
	private Parent root;

	public PauseMenuController(Stage stage) throws IOException {
		this.stage = stage;
		loader = new FXMLLoader(getClass().getResource("/application/view/PauseMenu.fxml"));
		loader.setController(this);
		root  = loader.load();
	}

	public void init() {
		this.stage.setTitle("Pause Menu");
		this.stage.setScene(new Scene(root,800,800));
		this.stage.show();
	}

	@FXML
	void resume(ActionEvent e) throws IOException {
		GameController controller = new GameController();
		controller.start(stage);
	}

	@FXML
	void settings(ActionEvent e)
	{
	}

	@FXML
	void menu(ActionEvent e) throws IOException {
		MenuController menuController = new MenuController();
		menuController.home(stage);

	}

	@FXML
	void quit(ActionEvent e)
	{
        stage.close();
	}
}

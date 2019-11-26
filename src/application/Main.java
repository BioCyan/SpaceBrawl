package application;

import application.controller.GameController;
import application.controller.MainMenuController;
import application.model.Game;
import application.model.GameSettings;
import java.util.HashMap;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application {
	public enum SceneType {
		Main,
		Settings,
		Pause,
		GameOver,
		Game,
	};

	public static Game game;
	public static GameSettings settings;

	private static GameController gameController;
	private static HashMap<SceneType, Parent> roots;
	private static Stage stage;
	private static Scene menuScene;

	/**
	 * The start method loads up Main scene
	 * @param primaryStage
	 * @throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		settings = new GameSettings();
		stage = primaryStage;
		preloadScenes();
		switchScene(SceneType.Main);
		stage.show();
	}

	/**
	 * The switchScene method is used to switch between the different
	 * scene in the game
	 * @param sceneType
	 */
	public static void switchScene(SceneType sceneType) {
		if (sceneType == SceneType.Game) {
			gameController = new GameController();
			gameController.start(stage);
			return;
		} else {
			gameController = null;
		}

		Parent root = roots.get(sceneType);
		if (root == null) {
			try {
				root = loadScene("GameOver");
			} catch (IOException e) {
			}
		}
		if (menuScene == null) {
			menuScene = new Scene(root, 960, 720);
		} else {
			menuScene.setRoot(root);
		}

		menuScene.setCursor(Cursor.CROSSHAIR);
		stage.setScene(menuScene);
	}

	/**
	 * The exit Event Handle handles the event for
	 * when the user clicks the Exit button to exit
	 * the game
	 */
	public static void exit() {
		stage.close();
	}

	/**
	 * The preloadScenes loads all the menu scenes in the game
	 * @throws IOException
	 */
	private static void preloadScenes() throws IOException {
		roots = new HashMap<>();
		roots.put(SceneType.Main, loadScene("Main"));
		roots.put(SceneType.Settings, loadScene("Settings"));
		roots.put(SceneType.Pause, loadScene("Pause"));
		//roots.put(SceneType.GameOver, loadScene("GameOver"));
	}

	/**
	 * The loadScene method loads tbe fxml file for the game
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private static Parent loadScene(String name) throws IOException{
		return FXMLLoader.load(Main.class.getResource("view/" + name + ".fxml"));
	}

	/**
	 * The main method loads all the args in program
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

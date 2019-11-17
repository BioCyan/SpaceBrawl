// This was just a quick and dirty prototype to hint at what's possible
// Much of what's in here should be replaced with proper abstractions
package application;

import application.controller.GameController;
import application.controller.MenuController;
import application.controller.MainMenuController;
import java.util.HashMap;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
		Game,
	};

	private static GameController gameController;
	private static HashMap<SceneType, Parent> roots;
	private static Stage stage;
	private static Scene menuScene;

	@Override
	public void start(Stage primaryStage) throws IOException {
		stage = primaryStage;
		preloadScenes();
		gameController = new GameController();
		switchScene(SceneType.Main);
		stage.show();
	}

	public static void switchScene(SceneType sceneType) {
		if (sceneType == SceneType.Game) {
			gameController.start(stage);
			return;
		}

		Parent root = roots.get(sceneType);
		if (menuScene == null) {
			menuScene = new Scene(root, 960, 720);
		} else {
			menuScene.setRoot(root);
		}
		stage.setScene(menuScene);
	}

	public static void exit() {
		stage.close();
	}

	private static void preloadScenes() throws IOException {
		roots = new HashMap<>();
		roots.put(SceneType.Main, loadScene("Main"));
		roots.put(SceneType.Settings, loadScene("Settings"));
		roots.put(SceneType.Pause, loadScene("Pause"));
	}

	private static Parent loadScene(String name) throws IOException{
		return FXMLLoader.load(Main.class.getResource("view/" + name + ".fxml"));
	}

	public static void main(String[] args) {
		launch(args);
	}
}

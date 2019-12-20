package application.controller;

import application.Main;
import java.io.IOException;
import java.util.ArrayList;
import application.model.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * The controller for the game view
 *
 * This object will interact with all of the model objects in order to make the 3d rendered game
 * work.
 *
 */
public class GameController {
	private Game game;
	private boolean active;
	private int score;
	private Text scoreText;
	private boolean paused;
	private AnimationTimer timer;
	private Scene mainScene;

	/**
	 * The GameController is a default constructor to load up the game controller
	 */
	public GameController() {
		game = Main.game;

		scoreText = new Text(100, 100, "Score: 0\n Missiles: 0");
		scoreText.setFont(new Font(30));
		scoreText.setFill(Color.WHITE);
		Group mainGroup = new Group(game, scoreText);
		mainScene = new Scene(mainGroup, 960, 720);

		game.widthProperty().bind(mainScene.widthProperty());
		game.heightProperty().bind(mainScene.heightProperty());
	}

	/**
	 * The start method is the Event Handle for when button Start is clicked in main menu to go to game scene
	 */
	public void start(Stage stage) {
		paused = false;
		game.paused = false;
		game.setCursor(Cursor.CROSSHAIR);
		stage.setScene(mainScene);
		stage.show();

		timer = new AnimationTimer() {
			long lastUpdate = -1;

			@Override
			public void handle(long now) {
				if (lastUpdate == -1) {
					lastUpdate = now;
					return;
				}

				if (!game.paused && !game.gameOver) {
					update((now - lastUpdate) / 1000000000.0);
				} else if (!paused) {
					if (game.gameOver) {
						Main.switchScene(Main.SceneType.GameOver);
					} else {
						Main.switchScene(Main.SceneType.Pause);
					}
					paused = true;
					timer.stop();
				}

				lastUpdate = now;
			}
		};
		timer.start();

		game.connect(mainScene, stage);
	}

	/**
	 * The update method is a score count that is used to keep track of all rocks hit in the game
	 *
	 * @param deltaTime
	 */
	private void update(double deltaTime) {
		game.update(deltaTime);

		//updates missile and score count at every update
		scoreText.setText("Score: " + game.score + "\n Shots: " + game.shotCount);
	}
}

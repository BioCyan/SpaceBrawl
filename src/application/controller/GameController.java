package application.controller;

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
	private World world;
	private int score;
	private Text scoreText;
	private PauseMenuController pauseController;
	private boolean paused;

	public void start(Stage stage) throws IOException {
		//TODO starry background, rock textures, textured star, and laser shape


		//loads controller at startup to prevent breaking of game pace
		world = new World();
		pauseController = new PauseMenuController(stage);

		scoreText = new Text(100, 100, "Score: 0\n Missiles: 0");
		scoreText.setFont(new Font(30));
		scoreText.setFill(Color.WHITE);
		Group mainGroup = new Group(world, scoreText);
		Scene mainScene = new Scene(mainGroup, 960, 720);
		world.widthProperty().bind(mainScene.widthProperty());
		world.heightProperty().bind(mainScene.heightProperty());

		stage.setTitle("Space Brawl");
		stage.setScene(mainScene);
		//stage.setMaximized(true);
		mainScene.setCursor(Cursor.NONE);
		stage.show();

		new AnimationTimer() {
			long lastUpdate = -1;

			@Override
			public void handle(long now) {
				if (lastUpdate == -1) {
					lastUpdate = now;
					return;
				}

				if (!world.paused) {
					update((now - lastUpdate) / 1000000000.0);
				} else if (!paused) {
					pauseController.init();
					paused = true;
				}

				lastUpdate = now;
			}
		}.start();

		world.connect(mainScene, stage);
	}

	private void update(double deltaTime) {
		world.update(deltaTime);

		//updates missile and score count at every update
		scoreText.setText("Score: " + world.score + "\n Missiles: " + world.shots.size());
	}
}

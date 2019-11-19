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
	private World world;
	private int score;
	private Text scoreText;
	private boolean paused;
	private AnimationTimer timer;
	private Scene mainScene;

	//The gamecontroller is a default constructor to load up the game controller
	public GameController() {
		world = new World();
		paused = false;

		scoreText = new Text(100, 100, "Score: 0\n Missiles: 0");
		scoreText.setFont(new Font(30));
		scoreText.setFill(Color.WHITE);
		Group mainGroup = new Group(world, scoreText);
		mainScene = new Scene(mainGroup, 960, 720);

		world.widthProperty().bind(mainScene.widthProperty());
		world.heightProperty().bind(mainScene.heightProperty());
	}

	//The start method is the action handle for when button Start is clicked in main menu to go to game scene
	public void start(Stage stage) {
		//TODO starry background, rock textures, textured star, and laser shape
		paused = false;
		world.paused = false;
		stage.setTitle("Space Brawl");
		//stage.setMaximized(true);
		mainScene.setCursor(Cursor.NONE);
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

				if (!world.paused) {
					update((now - lastUpdate) / 1000000000.0);
				} else if (!paused) {
					Main.switchScene(Main.SceneType.Pause);
					paused = true;
					timer.stop();
				}

				lastUpdate = now;
			}
		};
		timer.start();

		world.connect(mainScene, stage);
	}

	//The update method is a score count that is used to keep track of all rocks hit in the game
	private void update(double deltaTime) {
		world.update(deltaTime);

		//updates missile and score count at every update
		scoreText.setText("Score: " + world.score + "\n Missiles: " + world.shots.size());
	}
}

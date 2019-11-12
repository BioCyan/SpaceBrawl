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
	private Player player;

	private SubScene scene;
	public Group group;
	public Sphere star;
	private ArrayList<Rock> rocks;
	public ArrayList<PlasmaBolt> shots;
	private int score;
	private Text scoreText;
	private boolean paused = false;
	private PauseMenuController pauseController;

	public void start(Stage stage) throws IOException {
		//TODO starry background, rock textures, textured star, and laser shape


		//loads controller at startup to prevent breaking of game pace
		pauseController = new PauseMenuController(stage);
		rocks = new ArrayList<>();
		shots = new ArrayList<>();
		player = new Player(this);

		PointLight light = new PointLight();
		AmbientLight aLight = new AmbientLight(Color.rgb(24, 24, 24));
		star = new Sphere(1);

		group = new Group(player, star, light, aLight);

		for (int i = 0; i < 32; i++) {
			Rock rock = new Rock();
			rocks.add(rock);
			group.getChildren().add(rock);
		}

		scene = new SubScene(group, 960, 720, true, SceneAntialiasing.BALANCED);
		scene.setCamera(player);
		scene.setFill(Color.BLACK);

		scoreText = new Text(100, 100, "Score: 0\n Missiles: 0");
		scoreText.setFont(new Font(30));
		scoreText.setFill(Color.WHITE);
		Group mainGroup = new Group(scene, scoreText);
		Scene mainScene = new Scene(mainGroup, 960, 720);
		scene.widthProperty().bind(mainScene.widthProperty());
		scene.heightProperty().bind(mainScene.heightProperty());

		stage.setTitle("Space Brawl");
		stage.setScene(mainScene);
		//stage.setMaximized(true);
		scene.setCursor(Cursor.NONE);
		stage.show();

		new AnimationTimer() {
			long lastUpdate = -1;

			@Override
			public void handle(long now) {
				if (lastUpdate == -1) {
					lastUpdate = now;
					return;
				}

				if(!paused)
					update((now - lastUpdate) / 1000000000.0);

				lastUpdate = now;
			}
		}.start();

		player.connect(mainScene);
	}

	public void pause() {
		paused = true;
		pauseController.init();
	}

	public void addShot(PlasmaBolt shot) {
		shots.add(shot);
		group.getChildren().add(shot);
	}

	private void update(double deltaTime) {
		//updates missile and score count at every update
		scoreText.setText("Score: " + score+ "\n Missiles: " + shots.size());
		player.update(deltaTime);

		for (PlasmaBolt shot : shots) {
			shot.update(deltaTime);

			ArrayList<Node> remove = new ArrayList<Node>();

			for (Rock rock : rocks) {
				if (shot.checkCollision(rock)) {
					score += 100;
					group.getChildren().remove(shot);
					remove.add(rock);
				}
			}
			for (Node rock : remove) {
				rocks.remove(rock);
				group.getChildren().remove(rock);
			}
		}
	}
}

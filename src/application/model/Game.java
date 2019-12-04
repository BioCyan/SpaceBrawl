package application.model;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class Game extends SubScene {
	private double mouseX;
	private double mouseY;
	private double oldMouseX;
	private double oldMouseY;
	private double yaw;
	private double pitch;

	public int score;
	public int shotCount;
	public boolean paused;
	public boolean gameOver;
	public Player player;
	public Star star;
	public ArrayList<Rock> rocks;
	public ArrayList<PlasmaBolt> shots;

	/**
	 * The Game default constructor is used to load the effect, color, and view of the game screen
	 */
	public Game() {
		super(new Group(), 960, 720, true,  SceneAntialiasing.BALANCED);
		rocks = new ArrayList<>();
		shots = new ArrayList<>();
		player = new Player(this);

		PointLight light = new PointLight();
		AmbientLight aLight = new AmbientLight(Color.rgb(24, 24, 24));
		star = new Star();

		Group group = new Group(player, star, light, aLight);
		setRoot(group);

		for (int i = 0; i < 32; i++) {
			Rock rock = new Rock();
			rocks.add(rock);
			group.getChildren().add(rock);
		}

		setCamera(player);
		setFill(Color.BLACK);
	}

	/**
	 * The connect method connects the controller events in the game
	 * @param scene
	 * @param stage
	 */
	public void connect(Scene scene, Stage stage) {
		player.connect(scene, stage);
	}

	/**
	 * The update method is used to keep a score count for when a rocket hits a rock it updates the score count
	 * @param deltaTime
	 */
	public void update(double deltaTime) {
		if (paused) {
			return;
		}

		Group group = (Group)getRoot();
		player.update(deltaTime);

		if (star.checkCollision(player)) {
			gameOver = true;
			return;
		}

		ArrayList<PlasmaBolt> removedShots = new ArrayList<>();
		for (PlasmaBolt shot : shots) {
			shot.update(deltaTime);

			for (Rock rock : rocks) {
				if (shot.checkCollision(rock)) {
					score += 100;
					removedShots.add(shot);
					rock.explode();
					break;
				}
			}
		}
		for (PlasmaBolt shot : removedShots) {
			shots.remove(shot);
			group.getChildren().remove(shot);
		}

		ArrayList<Rock> removedRocks = new ArrayList<>();
		for (Rock rock : rocks) {
			rock.update(deltaTime);
			if (rock.isDestroyed()) {
				removedRocks.add(rock);
			}
		}
		for (Rock rock : removedRocks) {
			rocks.remove(rock);
			group.getChildren().remove(rock);
		}

	}

	/**
	 * The pause method pauses the game for when the esc button is clicced
	 */
	public void pause() {
		paused = true;
	}

	/**
	 * The addShot method shoots rockets for when a mouse right click button is clicked
	 * @param shot
	 */
	public void addShot(PlasmaBolt shot) {
		shotCount++;
		shots.add(shot);
		((Group)getRoot()).getChildren().add(shot);
	}
}

package application.model;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

public class World extends SubScene {
	private double mouseX;
	private double mouseY;
	private double oldMouseX;
	private double oldMouseY;
	private double yaw;
	private double pitch;
	private GameSettings settings;

	public int score;
	public boolean paused = false;
	public Player player;
	public Sphere star;
	public ArrayList<Rock> rocks;
	public ArrayList<PlasmaBolt> shots;

	public World() {
		super(new Group(), 960, 720, true,  SceneAntialiasing.BALANCED);
		rocks = new ArrayList<>();
		shots = new ArrayList<>();
		player = new Player(this);

		PointLight light = new PointLight();
		AmbientLight aLight = new AmbientLight(Color.rgb(24, 24, 24));
		star = new Sphere(1);

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

	public void connect(Scene scene, Stage stage) {
		player.connect(scene, stage);
	}

	public void update(double deltaTime) {
		if (paused) {
			return;
		}

		Group group = (Group)getRoot();
		player.update(deltaTime);

		for (PlasmaBolt shot : shots) {
			shot.update(deltaTime);

			for (Rock rock : rocks) {
				if (shot.checkCollision(rock)) {
					score += 100;
					group.getChildren().remove(shot);
					rock.explode();
				}
			}
		}

		ArrayList<Rock> remove = new ArrayList<>();
		for (Rock rock : rocks) {
			rock.update(deltaTime);
			if (rock.isDestroyed()) {
				remove.add(rock);
			}
		}
		for (Rock rock : remove) {
			rocks.remove(rock);
			group.getChildren().remove(rock);
		}

	}

	public void pause() {
		paused = true;
	}

	public void addShot(PlasmaBolt shot) {
		shots.add(shot);
		((Group)getRoot()).getChildren().add(shot);
	}
}

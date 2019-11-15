package application.model;

import javafx.scene.Subscene;

public class World extends SubScene {
	private Scene scene;
	private Stage stage;
	private double mouseX;
	private double mouseY;
	private double oldMouseX;
	private double oldMouseY;
	private double yaw;
	private double pitch;
	private GameSettings settings;
	private ArrayList<PlasmaBolt> shots;
	public int score;
	public boolean paused = false;

	private Player player;
	private Group group;
	private Sphere star;
	private ArrayList<Rock> rocks;

	public void update(double deltaTime) {
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

	public void pause() {
		paused = true;
	}

	public void addShot(PlasmaBolt shot) {
		shots.add(shot);
		group.getChildren().add(shot);
	}
}

package application.controller;

import java.awt.Robot;
import java.io.IOException;
import java.util.ArrayList;
import application.model.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
//import javafx.scene.robot.Robot;
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
	public static double mouseX;
	public static double mouseY;

	private static double oldMouseX;
	private static double oldMouseY;

	private double yaw;
	private double pitch;
	private PerspectiveCamera camera;
	private SubScene scene;
	private Group group;
	private Sphere star;
	private ArrayList<Rock> rocks;
	private ArrayList<Sphere> shots;
	private int score;
	private Text scoreText;
	private GameSettings settings;
	Point3D moveDir = Point3D.ZERO;
	Point3D velocity = Point3D.ZERO;

	public void start(Stage stage) throws IOException {
		//TODO starry background, rock textures, textured star, and laser shape

		//loads controller at startup to prevent breaking of game pace
		PauseMenuController controller = new PauseMenuController(stage);
		settings = new GameSettings();
		settings = new GameSettings();
		rocks = new ArrayList<Rock>();
		shots = new ArrayList<Sphere>();
		camera = new PerspectiveCamera(true);

		PointLight light = new PointLight();
		//light.setTranslateZ(-3);
		AmbientLight aLight = new AmbientLight(Color.rgb(24, 24, 24));
		star = new Sphere(1);
		//star.setTranslateZ(-3);

		group = new Group(camera, star, light, aLight);

		for (int i = 0; i < 32; i++) {
			Rock rock = new Rock();
			rocks.add(rock);
			group.getChildren().add(rock);
		}

		scene = new SubScene(group, 960, 720, true, SceneAntialiasing.BALANCED);
		camera.setFieldOfView(70);
		camera.setVerticalFieldOfView(true);
		camera.setTranslateZ(-5);
		scene.setCamera(camera);
		scene.setFill(Color.BLACK);

		scoreText = new Text(100, 100, "Score: 0\n Missles: 0");
		scoreText.setFont(new Font(30));
		scoreText.setFill(Color.WHITE);
		Group mainGroup = new Group(scene, scoreText);
		Scene mainScene = new Scene(mainGroup, 960, 720);
		scene.widthProperty().bind(mainScene.widthProperty());
		scene.heightProperty().bind(mainScene.heightProperty());

		stage.setTitle("Space Brawl Prototype");
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

				update((now - lastUpdate) / 1000000000.0);

				lastUpdate = now;
			}
		}.start();

		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				GameController.mouseX = event.getScreenX();
				GameController.mouseY = event.getScreenY();

				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					Sphere shot = new Sphere(0.1);
					PhongMaterial material = new PhongMaterial(Color.RED);
					shot.setMaterial(material);

					Transform transform = new Translate(0, 0, 1);
					Transform camTransform = camera.getLocalToParentTransform();
					transform = camTransform.createConcatenation(transform);
					shot.getTransforms().setAll(transform);

					shots.add(shot);
					group.getChildren().add(shot);
				}
			}
		};
		scene.setOnMouseMoved(handler);
		scene.setOnMouseClicked(handler);

		EventHandler<KeyEvent> keyHandler = new EventHandler<KeyEvent>() {
			private boolean wDown;
			private boolean aDown;
			private boolean sDown;
			private boolean dDown;
			private boolean spaceDown;
			private boolean ctrlDown;

			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ESCAPE)) {
					controller.init();
					return;
				}

				boolean newState;
				if (event.getEventType() == KeyEvent.KEY_PRESSED) {
					newState = true;
				} else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
					newState = false;
				} else {
					return;
				}

				if (event.getCode() == KeyCode.W) {
					wDown = newState;
				} else if (event.getCode() == KeyCode.A) {
					aDown = newState;
				} else if (event.getCode() == KeyCode.S) {
					sDown = newState;
				} else if (event.getCode() == KeyCode.D) {
					dDown = newState;
				} else if (event.getCode() == KeyCode.SPACE) {
					spaceDown = newState;
				} else if (event.getCode() == KeyCode.CONTROL) {
					ctrlDown = newState;
				}

				moveDir = Point3D.ZERO;
				if (wDown) {
					moveDir = moveDir.add(0, 0, -1);
				}
				if (aDown) {
					moveDir = moveDir.add(1, 0, 0);
				}
				if (sDown) {
					moveDir = moveDir.add(0, 0, 1);
				}
				if (dDown) {
					moveDir = moveDir.add(-1, 0, 0);
				}
				if (spaceDown) {
					moveDir = moveDir.add(0, 1, 0);
				}
				if (ctrlDown) {
					moveDir = moveDir.add(0, -1, 0);
				}
			}
		};
		mainScene.setOnKeyPressed(keyHandler);
		mainScene.setOnKeyReleased(keyHandler);
	}

	private void update(double deltaTime) {
		//updates missle and score count at every update
		scoreText.setText("Score: " + score+ "\n Missles: " + shots.size());
		double middleX = scene.getWidth() / 2;
		double middleY = scene.getHeight() / 2;

		double yaw = 0.1 * (mouseX - oldMouseX);
		double pitch = 0.1 * (mouseY - oldMouseY);
		Rotate yawRotate = new Rotate(yaw, Rotate.Y_AXIS);
		Rotate pitchRotate = new Rotate(-pitch, Rotate.X_AXIS);

		Transform oldTransform = camera.getLocalToSceneTransform();
		Point3D disp = star.localToScene(Point3D.ZERO, false)
			.subtract(camera.localToScene(Point3D.ZERO, false));
		double force = 10 / disp.dotProduct(disp);
		Point3D accelBy = oldTransform.deltaTransform(moveDir.multiply(1*deltaTime));
		accelBy = accelBy.add(disp.normalize().multiply(-force*deltaTime));
		velocity = velocity.add(accelBy);
		Point3D moveBy = velocity.multiply(deltaTime);
		Translate translateBy = new Translate(
			moveBy.getX(),
			moveBy.getY(),
			moveBy.getZ()).createInverse();

		camera.getTransforms().removeAll();
		camera.getTransforms().setAll(translateBy, oldTransform, yawRotate, pitchRotate);
		camera.setTranslateX(0);
		camera.setTranslateY(0);
		camera.setTranslateZ(0);

		// Unfortunately JavaFX didn't get Robot until Java 11
		// and we'll be running on machines with only Java 8
		// so we're using the AWT API for this which is not safe
		try {
			Robot robot = new Robot();
			robot.mouseMove((int)middleX, (int)middleY);
			mouseX = (int)middleX;
			mouseY = (int)middleY;
			oldMouseX = mouseX;
			oldMouseY = mouseY;
		} catch (Exception e) {
		}

		oldMouseX = mouseX;
		oldMouseY = mouseY;

		for (Sphere shot : shots) {
			Translate translate = new Translate(0, 0, 10 * deltaTime);
			Transform transform = shot.getLocalToParentTransform().createConcatenation(translate);
			shot.getTransforms().setAll(transform);

			ArrayList<Node> remove = new ArrayList<Node>();

			for (Rock rock : rocks) {
				Point3D shotPos = transform.transform(Point3D.ZERO);
				Point3D rockPos = rock.getLocalToParentTransform().transform(Point3D.ZERO);
				if (shotPos.distance(rockPos) < rock.getRadius() + shot.getRadius()) {
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

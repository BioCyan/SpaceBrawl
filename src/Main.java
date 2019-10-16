// This was just a quick and dirty prototype to hint at what's possible
// Much of what's in here should be replaced with proper abstractions

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.transform.*;

public class Main extends Application {
	public static double mouseX;
	public static double mouseY;

	private static double oldMouseX;
	private static double oldMouseY;

	private double yaw;
	private double pitch;
	private PerspectiveCamera camera;
	private SubScene scene;
	private Group group;
	private ArrayList<Rock> rocks;
	private ArrayList<Sphere> shots;
	private int score;
	private Text scoreText;
	private Point3D moveDir;

	@Override
	public void start(Stage stage) {
		rocks = new ArrayList<Rock>();
		shots = new ArrayList<Sphere>();
		camera = new PerspectiveCamera(true);

		PointLight light = new PointLight();
		light.setTranslateZ(-3);
		AmbientLight aLight = new AmbientLight(Color.rgb(24, 24, 24));

		group = new Group(camera, light, aLight);

		Random random = new Random(1);
		for (int i = 0; i < 32; i++) {
			double x = random.nextDouble() * 10 - 5;
			double y = random.nextDouble() * 10 - 5;
			double z = random.nextDouble() * 10 - 5;
			double radius = random.nextDouble() * 0.5;

			Rock rock = new Rock(radius);
			rock.setTranslateX(x);
			rock.setTranslateY(y);
			rock.setTranslateZ(z);
			rock.setMaterial(new PhongMaterial(Color.rgb(128, 128, 128)));
			rocks.add(rock);
			group.getChildren().add(rock);
		}

		scene = new SubScene(group, 1280, 720, true, SceneAntialiasing.BALANCED);
		camera.setFieldOfView(70);
		camera.setVerticalFieldOfView(true);
		camera.setTranslateZ(-5);
		scene.setCamera(camera);
		scene.setFill(Color.BLACK);

		scoreText = new Text(100, 100, "Score: 0");
		scoreText.setFont(new Font(30));
		scoreText.setFill(Color.WHITE);
		Group mainGroup = new Group(scene, scoreText);
		Scene mainScene = new Scene(mainGroup, 1250, 900);
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

				update((now - lastUpdate) / 1000000000.0, now);

				lastUpdate = now;
			}
		}.start();

		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Main.mouseX = event.getScreenX();
				Main.mouseY = event.getScreenY();

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

			@Override
			public void handle(KeyEvent event) {
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
				}

				moveDir = Point3D.ZERO;
				if (wDown) {
					moveDir = moveDir.add(0, 0, 1);
				}
				if (aDown) {
					moveDir = moveDir.add(1, 0, 0);
				}
				if (sDown) {
					moveDir = moveDir.add(0, 0, -1);
				}
				if (dDown) {
					moveDir = moveDir.add(-1, 0, 0);
				}
			}
		};
	}

	private void update(double deltaTime, long now) {
		double middleX = scene.getWidth() / 2;
		double middleY = scene.getHeight() / 2;

		yaw += 0.1 * (mouseX - oldMouseX);
		pitch += 0.1 * (mouseY - oldMouseY);
		Rotate yawRotate = new Rotate(yaw, Rotate.Y_AXIS);
		Rotate pitchRotate = new Rotate(-pitch, Rotate.X_AXIS);
		camera.getTransforms().removeAll();
		camera.getTransforms().setAll(yawRotate, pitchRotate);

		// Unfortunately JavaFX didn't get Robot until Java 11
		// and we'll be running on machines with only Java 8
		// so we're using the AWT API for this
		Platform.runLater(() -> {
			try {
				Robot robot = new Robot();
				robot.mouseMove((int)middleX, (int)middleY);
				mouseX = (int)middleX;
				mouseY = (int)middleY;
				oldMouseX = mouseX;
				oldMouseY = mouseY;
			} catch (AWTException e) {}
		});

		oldMouseX = mouseX;
		oldMouseY = mouseY;

		for (Sphere shot : shots) {
			Translate translate = new Translate(0, 0, 10*deltaTime);
			Transform transform = shot.getLocalToParentTransform().createConcatenation(translate);
			shot.getTransforms().setAll(transform);

			ArrayList<Node> remove = new ArrayList<Node>();
			for (Rock rock : rocks) {
				Point3D shotPos = transform.transform(Point3D.ZERO);
				Point3D rockPos = rock.getLocalToParentTransform().transform(Point3D.ZERO);
				if (shotPos.distance(rockPos) < rock.getRadius() + shot.getRadius()) {
					score += 100;
					scoreText.setText("Score: " + score);
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

	public static void main(String[] args) {
		Application.launch(args);
	}
}

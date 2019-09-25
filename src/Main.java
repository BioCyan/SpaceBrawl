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
		Scene mainScene = new Scene(mainGroup, 1280, 720);
		scene.widthProperty().bind(mainScene.widthProperty());
		scene.heightProperty().bind(mainScene.heightProperty());

		stage.setTitle("Space Brawl Prototype");
		stage.setScene(mainScene);
		stage.setMaximized(true);
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

class Rock extends MeshView {
	private double radius;
	private Random random;

	public Rock(double radius) {
		super();
		this.radius = radius;
		random = new Random();
		setMesh(buildMesh((float)radius));
	}

	public double getRadius() {
		return radius;
	}

	private TriangleMesh buildMesh(float radius) {
		TriangleMesh mesh = new TriangleMesh();
		mesh.getTexCoords().addAll(0, 0);
		mesh.getPoints().addAll(0, -genDistance(radius), 0);
		mesh.getPoints().addAll(0, genDistance(radius), 0);
		for (int i = 0; i < 10; i++) {
			double yaw = 2 * Math.PI * i / 10;
			for (int j = 1; j < 10; j++) {
				double pitch = Math.PI * (-0.5 + (double)j / 10);

				float distance = genDistance(radius);
				float x = (float)Math.sin(yaw);
				float z = (float)Math.cos(yaw);
				float s = distance * (float)Math.cos(pitch);
				x *= s;
				z *= s;
				float y = distance * (float)Math.sin(pitch);

				mesh.getPoints().addAll(x, y, z);
			}
		}
		for (int i = 0; i < 10; i++) {
			mesh.getFaces().addAll(2 + i * 9, 0);
			mesh.getFaces().addAll(0, 0);
			mesh.getFaces().addAll(2 + (i + 1) % 10 * 9, 0);

			for (int j = 0; j < 10 - 2; j++) {
				mesh.getFaces().addAll(2 + i * 9 + j, 0);
				mesh.getFaces().addAll(2 + (i + 1) % 10 * 9 + j, 0);
				mesh.getFaces().addAll(2 + (i + 1) % 10 * 9 + j + 1, 0);

				mesh.getFaces().addAll(2 + (i + 1) % 10 * 9 + j + 1, 0);
				mesh.getFaces().addAll(2 + i * 9 + j + 1, 0);
				mesh.getFaces().addAll(2 + i * 9 + j, 0);
			}

			mesh.getFaces().addAll(1, 0);
			mesh.getFaces().addAll(2 + i * 9 + 8, 0);
			mesh.getFaces().addAll(2 + (i + 1) % 10 * 9 + 8, 0);
		}

		return mesh;
	}

	private float genDistance(float radius) {
		return radius - random.nextFloat() * radius * 0.2f;
	}
}

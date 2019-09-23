import java.awt.AWTException;
import java.awt.Robot;
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
import javafx.stage.Stage;
import javafx.scene.transform.Rotate;

public class Main extends Application {
	public static double mouseX;
	public static double mouseY;

	private static double oldMouseX;
	private static double oldMouseY;

	private double yaw;
	private double pitch;
	private PerspectiveCamera camera;
	private Scene scene;

	@Override
	public void start(Stage stage) {
		camera = new PerspectiveCamera(true);

		TriangleMesh mesh = new TriangleMesh();
		mesh.getPoints().addAll(
			0, 0, -1,
			-1, -1, 1,
			-1, 1, 1,
			1, 1, 1,
			1, -1, 1);
		mesh.getTexCoords().addAll(0, 0);
		mesh.getFaces().addAll(
			0, 0, 1, 0, 2, 0,
			0, 0, 2, 0, 3, 0,
			0, 0, 3, 0, 4, 0,
			0, 0, 4, 0, 1, 0,
			3, 0, 2, 0, 1, 0,
			1, 0, 4, 0, 3, 0);
		MeshView view = new MeshView(mesh);

		PointLight light = new PointLight();
		light.setTranslateZ(-3);

		AmbientLight aLight = new AmbientLight(Color.rgb(24, 24, 24));

		Group group = new Group(camera, light, aLight);

		Random random = new Random(1);
		for (int i = 0; i < 32; i++) {
			double x = random.nextDouble() * 10 - 5;
			double y = random.nextDouble() * 10 - 5;
			double z = random.nextDouble() * 10 - 5;
			double radius = random.nextDouble() * 0.5;

			Sphere sphere = new Sphere(radius);
			sphere.setTranslateX(x);
			sphere.setTranslateY(y);
			sphere.setTranslateZ(z);
			group.getChildren().add(sphere);
		}

		scene = new Scene(group, 1280, 720, true);
		camera.setFieldOfView(70);
		camera.setVerticalFieldOfView(true);
		camera.setTranslateZ(-5);
		scene.setCamera(camera);
		scene.setFill(Color.BLACK);

		stage.setTitle("Space Brawl Prototype");
		stage.setScene(scene);
		stage.setFullScreen(true);
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

			}
		};

		scene.setOnMouseMoved(handler);
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
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}

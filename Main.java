import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.ConditionalFeature;
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

		Scene scene = new Scene(group, 1280, 720, true);
		camera.setFieldOfView(70);
		camera.setVerticalFieldOfView(true);
		camera.setTranslateZ(-5);
		scene.setCamera(camera);
		scene.setFill(Color.BLACK);

		stage.setTitle("Space Brawl Prototype");
		stage.setScene(scene);
		stage.setMaximized(true);
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
				Main.mouseX = event.getScreenX();
				Main.mouseY = event.getScreenY();
			}
		};

		scene.setOnMouseMoved(handler);
	}

	private void update(double deltaTime) {
		yaw += 0.1 * (mouseX - oldMouseX);
		pitch += 0.1 * (mouseY - oldMouseY);
		Rotate yawRotate = new Rotate(yaw, new Point3D(0, 1, 0));
		Rotate pitchRotate = new Rotate(-pitch, new Point3D(1, 0, 0));
		camera.getTransforms().setAll(yawRotate, pitchRotate);
		oldMouseX = mouseX;
		oldMouseY = mouseY;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}

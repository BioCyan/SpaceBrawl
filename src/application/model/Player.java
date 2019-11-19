package application.model;

import application.controller.GameController;
import java.awt.Robot;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
//import javafx.scene.robot.Robot;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

/**
 * a class to model the player's location and actions.
 * 
 * This class should be called by the GameController object and will interact with PlasmaBolt, Rock, and others
 * model objects.
 *
 */
public class Player extends PerspectiveCamera {
	private World world;
	private Scene scene;
	private Stage stage;
	private double mouseX;
	private double mouseY;
	private double oldMouseX;
	private double oldMouseY;
	private double yaw;
	private double pitch;
	private GameSettings settings;
	private Point3D moveDir = Point3D.ZERO;
	private Point3D velocity = new Point3D(-1.2, 0, 0);

	//The Player default constructor is used to load the players view
	public Player(World world) {
		super(true);
		setFieldOfView(70);
		setVerticalFieldOfView(true);
		setTranslateZ(-5);
		this.world = world;
		settings = new GameSettings();
	}

	public void connect(Scene scene, Stage stage) {
		this.scene = scene;
		this.stage = stage;
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseX = event.getScreenX();
				mouseY = event.getScreenY();

				if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
					Transform transform = new Translate(0, 0, 1);
					Transform camTransform = getLocalToParentTransform();
					transform = camTransform.createConcatenation(transform);

					PlasmaBolt shot = new PlasmaBolt(transform);
					world.addShot(shot);
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

			//The handle method is the action handle for all the player controls in the game
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == settings.getPause()) {
					world.pause();
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

				if (event.getCode() == settings.getForward()) {
					wDown = newState;
				} else if (event.getCode() == settings.getLeft()) {
					aDown = newState;
				} else if (event.getCode() == settings.getBack()) {
					sDown = newState;
				} else if (event.getCode() == settings.getRight()) {
					dDown = newState;
				} else if (event.getCode() == settings.getUp()) {
					spaceDown = newState;
				} else if (event.getCode() == settings.getDown()) {
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
		scene.setOnKeyPressed(keyHandler);
		scene.setOnKeyReleased(keyHandler);
	}

	//The update method is to update the players view while in the game
	public void update(double deltaTime) {
		double yaw = 0.1 * (mouseX - oldMouseX);
		double pitch = 0.1 * (mouseY - oldMouseY);
		Rotate yawRotate = new Rotate(yaw, Rotate.Y_AXIS);
		Rotate pitchRotate = new Rotate(-pitch, Rotate.X_AXIS);

		Transform oldTransform = getLocalToSceneTransform();
		Point3D disp = world.star.localToScene(Point3D.ZERO, false)
			.subtract(localToScene(Point3D.ZERO, false));
		double force = 10 / disp.dotProduct(disp);
		Point3D accelBy = oldTransform.deltaTransform(moveDir.multiply(1*deltaTime));
		accelBy = accelBy.add(disp.normalize().multiply(-force*deltaTime));
		velocity = velocity.add(accelBy);
		Point3D moveBy = velocity.multiply(deltaTime);
		Translate translateBy = new Translate(
			moveBy.getX(),
			moveBy.getY(),
			moveBy.getZ()).createInverse();

		getTransforms().removeAll();
		getTransforms().setAll(translateBy, oldTransform, yawRotate, pitchRotate);
		setTranslateX(0);
		setTranslateY(0);
		setTranslateZ(0);

		// Unfortunately JavaFX didn't get Robot until Java 11
		// and we'll be running on machines with only Java 8
		// so we're using the AWT API for this which is not safe
		try {
			double middleX = scene.getWidth() / 2 + stage.getX();
			double middleY = scene.getHeight() / 2 + stage.getY();
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
	}
}

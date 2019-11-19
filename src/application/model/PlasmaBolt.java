package application.model;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

/**
 * A class to model a projectile shooting the rocks.
 * 
 * This object should be created by the Player object on mouse clicks and
 * checks every update if it is colliding with a rock object.
 *
 */
public class PlasmaBolt extends Sphere {

	//The PlasmaBolt default constructor is used to set the shape for the rockets
	public PlasmaBolt(Transform transform) {
		super(0.1);
		setMaterial(new PhongMaterial(Color.RED));
		getTransforms().setAll(transform);
	}

	//The update method is to update a rockets movementx
	public void update(double deltaTime) {
		Translate translate = new Translate(0, 0, 10 * deltaTime);
		Transform transform = getLocalToParentTransform().createConcatenation(translate);
		getTransforms().setAll(transform);
	}

	//The checkCollision method checks if a rocket has hit a rock
	public boolean checkCollision(Rock rock) {
		Transform transform = getLocalToParentTransform();
		Point3D shotPos = transform.transform(Point3D.ZERO);
		Point3D rockPos = rock.getLocalToParentTransform().transform(Point3D.ZERO);
		return shotPos.distance(rockPos) < rock.getRadius() + getRadius();
	}
}

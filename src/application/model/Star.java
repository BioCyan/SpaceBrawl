package application.model;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public class Star extends Sphere {
	public Star() {
		super(1);
	}

	public boolean checkCollision(Player player) {
		Point3D pos = getLocalToParentTransform().transform(Point3D.ZERO);
		Point3D playerPos = player.getLocalToParentTransform().transform(Point3D.ZERO);
		return pos.distance(playerPos) < getRadius();
	}
}

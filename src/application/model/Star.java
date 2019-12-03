package application.model;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Star extends Sphere {
	public Star() {
		super(1);
		PhongMaterial material = new PhongMaterial(Color.rgb(255, 192, 96));
		Image white = new Image(Star.class.getResourceAsStream("/sun.jpg"));
		material.setSelfIlluminationMap(white);
		setMaterial(material);
	}

	public boolean checkCollision(Player player) {
		Point3D pos = getLocalToParentTransform().transform(Point3D.ZERO);
		Point3D playerPos = player.getLocalToParentTransform().transform(Point3D.ZERO);
		return pos.distance(playerPos) < getRadius();
	}
}

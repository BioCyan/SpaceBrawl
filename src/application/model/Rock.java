package application.model;

import java.util.Random;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.paint.Color;

public class Rock extends MeshView {
	private double radius;
	private boolean exploded;
	private double explosionTime;

	/**
	 * The Rock default constructor loads the rocks in the game scene
	 */
	public Rock() {
		super();

		Random random = new Random();
		setTranslateX(random.nextDouble() * 10 - 5);
		setTranslateY(random.nextDouble() * 10 - 5);
		setTranslateZ(random.nextDouble() * 10 - 5);
		radius = random.nextDouble() * 0.5;

		setMesh(buildMesh((float)radius, random));
		setMaterial(new PhongMaterial(Color.rgb(128, 128, 128)));
	}

	/**
	 * The getRadius method is used to get the radius of the rocks
	 * @return
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * The explode method is used for when a rocket hits a rock, the rock will have exploding effects
	 */
	public void explode() {
		exploded = true;
		setMaterial(new PhongMaterial(Color.rgb(255, 96, 0)));
	}

	/**
	 * The update method loads the effect for when the rock is hit
	 *
	 * @param deltaTime
	 */
	public void update(double deltaTime) {
		if (exploded) {
			explosionTime += deltaTime;

			double scale = 1 + explosionTime * 3;
			setScaleX(scale);
			setScaleY(scale);
			setScaleZ(scale);
		}
	}

	/**
	 * The isDestroyed method is used for how long the rock stays visible when hit by a rocket
	 *
	 * @return
	 */
	public boolean isDestroyed() {
		return explosionTime > 0.5;
	}

	/**
	 * The TriangleMesh method is used to build the shape of every rock
	 *
	 * @param radius
	 * @param random
	 * @return
	 */
	private TriangleMesh buildMesh(float radius, Random random) {
		TriangleMesh mesh = new TriangleMesh();
		mesh.getTexCoords().addAll(0, 0);
		mesh.getPoints().addAll(0, -genDistance(radius, random), 0);
		mesh.getPoints().addAll(0, genDistance(radius, random), 0);
		for (int i = 0; i < 10; i++) {
			double yaw = 2 * Math.PI * i / 10;
			for (int j = 1; j < 10; j++) {
				double pitch = Math.PI * (-0.5 + (double)j / 10);

				float distance = genDistance(radius, random);
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

	/**
	 * The genDistance is used to generate the vertex of each rock
	 *
	 * @param radius
	 * @param random
	 * @return
	 */
	private float genDistance(float radius, Random random) {
		return radius - random.nextFloat() * radius * 0.2f;
	}
}

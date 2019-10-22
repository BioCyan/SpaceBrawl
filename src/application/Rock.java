package application;

import java.util.Random;
import javafx.scene.shape.*;

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

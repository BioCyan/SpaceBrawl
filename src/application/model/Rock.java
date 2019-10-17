package application.model;

import java.util.Random;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.paint.Color;

public class Rock extends MeshView {
	private double radius;
	private Random random;
	
	private void setElem(double x, double y, double z, double r) {
		setTranslateX(x);
		setTranslateY(y);
		setTranslateZ(z);
		radius = r;
	}
	
	public Rock() {
		super();
		random = new Random(1);
		setElem(random.nextDouble()*10-5,random.nextDouble()*10-5,random.nextDouble()*10-5,random.nextDouble()*0.5 );
		setMesh(buildMesh((float)radius));
		setMaterial(new PhongMaterial(Color.rgb(128, 128, 128)));
	}
	
	public Rock(double radius) {
		super();
		random = new Random();
		setElem(random.nextDouble()*10-5,random.nextDouble()*10-5,random.nextDouble()*10-5,radius );
		setMesh(buildMesh((float)radius));
		setMaterial(new PhongMaterial(Color.rgb(128, 128, 128)));
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

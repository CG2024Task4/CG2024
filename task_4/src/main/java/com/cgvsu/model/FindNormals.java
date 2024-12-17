package com.cgvsu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cgvsu.math.typesVectors.Vector3f;

public class FindNormals {
	public static ArrayList<Vector3f> findNormals(Model m) {
		List<Polygon> polygons = m.polygons;
		List<Vector3f> vertices = m.vertices;

		ArrayList<Vector3f> temporaryNormals = new ArrayList<>();
		ArrayList<Vector3f> normals = new ArrayList<>();

		for (Polygon p : polygons) {
			temporaryNormals.add(FindNormals.findPolygonsNormals(vertices.get(p.getVertexIndices().get(0)),
					vertices.get(p.getVertexIndices().get(1)), vertices.get(p.getVertexIndices().get(2))));
		}

		Map<Integer, Set<Vector3f>> vertexPolygonsMap = new HashMap<>();
		for (int j = 0; j < polygons.size(); j++) {
			List<Integer> vertexIndices = polygons.get(j).getVertexIndices();
			Vector3f vec = temporaryNormals.get(j);
			for (Integer index : vertexIndices) {
		        vertexPolygonsMap.computeIfAbsent(index, k -> new HashSet<>()).add(vec);
		    }
		}

		for (int i = 0; i < vertices.size(); i++) {
			normals.add(findVertexNormals(vertexPolygonsMap.get(i)));
		}
		return normals;
	}

	public static Vector3f findPolygonsNormals(Vector3f... vs) {
		Vector3f a = vs[0].subtracted(vs[1]);
		Vector3f b = vs[0].subtracted(vs[2]);

		Vector3f c = vectorProduct(a, b);
		if (determinant(a, b, c) < 0) {
			c = vectorProduct(b, a);
		}

		return normalize(c);
	}

	public static Vector3f findVertexNormals(Set<Vector3f> vs) {
		double xs = 0, ys = 0, zs = 0;

		for (Vector3f v : vs) {
			xs += v.getBase()[0];
			ys += v.getBase()[1];
			zs += v.getBase()[2];
		}

		xs /= vs.size();
		ys /= vs.size();
		zs /= vs.size();

		return normalize(new Vector3f(xs, ys, zs));
	}

	public static double determinant(Vector3f a, Vector3f b, Vector3f c) {
		double ax = a.getBase()[0];
		double ay = a.getBase()[1];
		double az = a.getBase()[2];
		double bx = b.getBase()[0];
		double by = b.getBase()[1];
		double bz = b.getBase()[2];
		double cx = c.getBase()[0];
		double cy = c.getBase()[1];
		double cz = c.getBase()[2];
		return ax * (by * cz) - ay * (bx * cz - cx * bz) + az * (bx * cy - cx * by);
	}

	public static Vector3f normalize(Vector3f v) {
		if (v == null) {
			return null;
		}
		double x = v.getBase()[0];
		double y = v.getBase()[1];
		double z = v.getBase()[2];


		double length = Math.sqrt(x * x + y * y + z * z);

		if (length == 0) {
			return new Vector3f(0, 0, 0);
		}

		x /= length;
		y /= length;
		z /= length;

		return new Vector3f(x, y, z);
	}

	public static Vector3f vectorProduct(Vector3f a, Vector3f b) {
		double ax = a.getBase()[0];
		double ay = a.getBase()[1];
		double az = a.getBase()[2];
		double bx = b.getBase()[0];
		double by = b.getBase()[1];
		double bz = b.getBase()[2];
		return new Vector3f(ay * bz - by * az, -ax * bz + bx * az, ax * by - bx * ay);
	}
}

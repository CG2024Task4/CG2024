package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    // Геттеры

    public List<Vector3f> getVertices() {
        return vertices;
    }


    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }


    public List<Vector3f> getNormals() {
        return normals;
    }


    public List<Polygon> getPolygons() {
        return polygons;
    }
}

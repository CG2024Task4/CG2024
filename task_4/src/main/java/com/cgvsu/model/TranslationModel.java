package com.cgvsu.model;

import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.render_engine.GraphicConveyor;

public class TranslationModel {
    public static void move(Matrix4f transposeMatrix, Model model) {
        for (Vector3f vertex : model.vertices) {
            Vector3f newVertex = mul(vertex, transposeMatrix);
            vertex.set(0, newVertex.getX());
            vertex.set(1, newVertex.getY());
            vertex.set(2, newVertex.getZ());
        }
        model.normalize();
    }

    public static Vector3f mul(Vector3f vector3f, Matrix4f modelMatrix) {
        return GraphicConveyor.multiplyMatrix4ByVector3(modelMatrix, vector3f);
    }
}

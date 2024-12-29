package com.cgvsu.model;

import com.cgvsu.math.ATTransformator;
import com.cgvsu.math.core.MatrixUtils;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.math.typesMatrix.Matrix4f;

public class ModelTransformer {
    public static Matrix4f modelMatrix(double tx, double ty, double tz,
                                       double rX, double rY, double rZ,
                                       double sx, double sy, double sz){
        Matrix4f modelMatrix = new Matrix4f(true);
        Matrix4f transitionMatrix = translateMatrix(tx, ty, tz);
        Matrix4f rotationMatrix = rotateMatrix(rX, rY, rZ);
        Matrix4f scaleMatrix = scaleMatrix(sx, sy, sz);
        modelMatrix = MatrixUtils.multiplied(transitionMatrix, rotationMatrix, scaleMatrix, modelMatrix);
        return modelMatrix;
    }

    public static Matrix4f translateMatrix(double tX, double tY, double tZ) {
        ATTransformator.ATBuilder builder = new ATTransformator.ATBuilder();
        Vector3f vector3f = new Vector3f(tX, tY, tZ);
        ATTransformator transformator = builder.translateByVector(vector3f).build();
        return transformator.getTransformationMatrix();
    }

    public static Matrix4f rotateMatrix(double rX, double rY, double rZ) {
        ATTransformator.ATBuilder builder = new ATTransformator.ATBuilder();
        ATTransformator transformator = builder.rotateByX(rX).rotateByY(rY).rotateByZ(rZ).build();
        return transformator.getTransformationMatrix();
    }

    public static Matrix4f scaleMatrix(double sX, double sY, double sZ) {
        ATTransformator.ATBuilder builder = new ATTransformator.ATBuilder();
        Vector3f vector3f = new Vector3f(sX, sY, sZ);
        ATTransformator transformator = builder.scaleByVertor(vector3f).build();
        return transformator.getTransformationMatrix();
    }
}

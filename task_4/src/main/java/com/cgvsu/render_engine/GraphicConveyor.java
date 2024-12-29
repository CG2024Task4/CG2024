package com.cgvsu.render_engine;

import com.cgvsu.math.ATTransformator;
import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.math.typesVectors.Vector4f;
import com.cgvsu.model.Model;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate(Model model) {
//        ATTransformator.ATBuilder builder = new ATTransformator.ATBuilder();
//        Vector3f center = model.getCenter().multiplied(-1);
//        ATTransformator transformator = builder.translateByVector(center).build();
//        Matrix4f matrix = transformator.getTransformationMatrix();
//        return matrix;
        return new Matrix4f(true);
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target) {
        return lookAt(eye, target, new Vector3f(0.0, 1.0, 0.0));
    }

    public static Matrix4f lookAt(Vector3f eye, Vector3f target, Vector3f up) {
        Vector3f resultZ = target.subtracted(eye).normalize();
        Vector3f resultX = up.crossProduct(resultZ).normalize();
        Vector3f resultY = resultZ.crossProduct(resultX);

        double[] matrix = new double[]{
                resultX.getX(), resultX.getY(), resultX.getZ(), -resultX.dotProduct(eye),
                resultY.getX(), resultY.getY(), resultY.getZ(), -resultY.dotProduct(eye),
                resultZ.getX(), resultZ.getY(), resultZ.getZ(), -resultZ.dotProduct(eye),
                0, 0, 0, 1
        };

        return new Matrix4f(matrix);
    }

    public static Matrix4f perspective(double fov, double aspectRatio, double nearPlane, double farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        Matrix4f result = new Matrix4f();
        result.set(0, 0, tangentMinusOnDegree / aspectRatio);
        result.set(1, 1, tangentMinusOnDegree);
        result.set(2, 2, (farPlane + nearPlane) / (farPlane - nearPlane));
        result.set(2, 3, 2 * (nearPlane * farPlane) / (nearPlane - farPlane));
        result.set(3, 2, 1);
        return result;
    }

    public static Vector3f multiplyMatrix4ByVector3(final Matrix4f matrix, final Vector3f vertex) {
        double[] baseVec4 = new double[]{vertex.getX(), vertex.getY(), vertex.getZ(), 1};

        Vector4f resultVector = matrix.multiplied(new Vector4f(baseVec4));
        double x = resultVector.getX();
        double y = resultVector.getY();
        double z = resultVector.getZ();
        double w = resultVector.getW();

        if (w == 0) {
            return new Vector3f();
        }

        return new Vector3f(x / w, y / w, z / w);
    }

    public static Vector2f vertexToPoint(final Vector3f vertex, final int width, final int height) {

        return new Vector2f((vertex.getX() * width + width / 2.0), (-vertex.getY() * height + height / 2.0));
    }
}

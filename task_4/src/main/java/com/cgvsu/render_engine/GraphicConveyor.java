package com.cgvsu.render_engine;



import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.math.typesVectors.Vector4f;

import javax.vecmath.Point2f;

public class GraphicConveyor {

    public static Matrix4f rotateScaleTranslate() {
        // единичная матрица
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
                resultX.get(0), resultX.get(1), resultX.get(2), -resultX.dotProduct(eye),
                resultY.get(0), resultY.get(1), resultY.get(2), -resultY.dotProduct(eye),
                resultZ.get(0), resultZ.get(1), resultZ.get(2), -resultZ.dotProduct(eye),
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
        double[] baseVec4 = new double[]{vertex.get(0), vertex.get(1), vertex.get(2), 1};

        Vector4f resultVector = matrix.multiplied(new Vector4f(baseVec4));
        double x = resultVector.get(0);
        double y = resultVector.get(1);
        double z = resultVector.get(2);
        double w = resultVector.get(3);

        if (w == 0) {
            throw new IllegalArgumentException("Invalid transformation: w = 0");
        }

        return new Vector3f(x / w, y / w, z / w);
    }

    public static Point2f vertexToPoint(final Vector3f vertex, final int width, final int height) {

        return new Point2f((float) (vertex.get(0) * width + width / 2.0F), (float) (-vertex.get(1) * height + height / 2.0F));
    }
}
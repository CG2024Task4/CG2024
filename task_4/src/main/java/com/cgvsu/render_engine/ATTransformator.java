package com.cgvsu.render_engine;


import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector3f;
import com.cgvsu.math.typesVectors.Vector4f;
import com.cgvsu.model.Model;

import java.util.ArrayList;

public class ATTransformator {

    private Matrix4f transformationMatrix;

    private ATTransformator(Matrix4f transformationMatrix) {
        this.transformationMatrix = new Matrix4f(transformationMatrix.getBase());
    }

    public Vector3f applyTransformationToVector(Vector3f vector) {
        Vector4f fourVector = new Vector4f(vector.getX(), vector.getY(), vector.getZ(), 1);
        Vector4f transformedVector = transformationMatrix.multiplied(fourVector);
        if (transformedVector.get(3) == 0) {
            return new Vector3f(transformedVector.getX(), transformedVector.getY(), transformedVector.getZ());
        } else {
            return new Vector3f(new double[]{transformedVector.getX()/transformedVector.getW(), transformedVector.getY()/transformedVector.getW(), transformedVector.getZ()/transformedVector.getW()});
        }
    }

    public ArrayList<Vector3f> applyTransformationToVectorList(ArrayList<Vector3f> vectorList) {
        ArrayList<Vector3f> transformedVectorList = new ArrayList<>();
        for (Vector3f vector : vectorList) {
            transformedVectorList.add(applyTransformationToVector(vector));
        }
        return transformedVectorList;
    }

    public Model applyTransformationToModel(Model originalModel) {
        Model transformedModel = new Model();

        for (Vector3f vertex : originalModel.vertices) {
            transformedModel.vertices.add(applyTransformationToVector(vertex));
        }

        transformedModel.textureVertices.addAll(originalModel.textureVertices);
        transformedModel.normals.addAll(originalModel.normals);
        transformedModel.polygons.addAll(originalModel.polygons);

        return transformedModel;
    }

    public static class ATBuilder {
        private Matrix4f currentMatrix;

        public ATBuilder() {
            this.currentMatrix = new Matrix4f(true);
        }

        private ATBuilder scale(double sX, double sY, double sZ) {
            Matrix4f scaleMatrix = new Matrix4f(new double[][]{
                    {sX, 0, 0, 0},
                    {0, sY, 0, 0},
                    {0, 0, sZ, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(scaleMatrix);
            return this;
        }

        private ATBuilder rotateX(double rX) {
            double cosX = Math.cos(rX);
            double sinX = Math.sin(rX);

            Matrix4f rotationMatrix = new Matrix4f(new double[][]{
                    {1, 0, 0, 0},
                    {0, cosX, sinX, 0},
                    {0, -sinX, cosX, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(rotationMatrix);
            return this;
        }

        private ATBuilder rotateY(double rY) {
            double cosY = Math.cos(rY);
            double sinY = Math.sin(rY);

            Matrix4f rotationMatrix = new Matrix4f(new double[][]{
                    {cosY, 0, sinY, 0},
                    {0, 1, 0, 0},
                    {-sinY, 0, cosY, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(rotationMatrix);
            return this;
        }

        private ATBuilder rotateZ(double rZ) {
            double cosZ = Math.cos(rZ);
            double sinZ = Math.sin(rZ);

            Matrix4f rotationMatrix = new Matrix4f(new double[][]{
                    {cosZ, sinZ, 0, 0},
                    {-sinZ, cosZ, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(rotationMatrix);
            return this;
        }

        private ATBuilder translate(double tX, double tY, double tZ) {
            Matrix4f translationMatrix = new Matrix4f(new double[][]{
                    {1, 0, 0, tX},
                    {0, 1, 0, tY},
                    {0, 0, 1, tZ},
                    {0, 0, 0, 1}
            });

            this.currentMatrix = this.currentMatrix.multiplied(translationMatrix);
            return this;
        }

        public ATBuilder scaleByX(double sX) {
            return scale(sX, 1, 1);
        }

        public ATBuilder scaleByY(double sY) {
            return scale(1, sY, 1);
        }

        public ATBuilder scaleByZ(double sZ) {
            return scale(1, 1, sZ);
        }

        public ATBuilder scaleByVertor(Vector3f vector) {
            return scale(vector.getX(), vector.getY(), vector.getZ());
        }

        public ATBuilder scaleByCoordinates(double sX, double sY, double sZ) {
            return scale(sX, sY, sZ);
        }

        public ATBuilder translateByX(double tX) {
            return translate(tX, 0, 0);
        }

        public ATBuilder translateByY(double tY) {
            return translate(0, tY, 0);
        }

        public ATBuilder translateByZ(double tZ) {
            return translate(0, 0, tZ);
        }

        public ATBuilder translateByVector(Vector3f vector) {
            return translate(vector.get(0), vector.get(1), vector.get(2));
        }

        public ATBuilder translateByCoordinates(double tX, double tY, double tZ) {
            return translate(tX, tY, tZ);
        }

        public ATBuilder rotateByX(double rX) {
            return rotateX(rX);
        }

        public ATBuilder rotateByY(double rY) {
            return rotateY(rY);
        }

        public ATBuilder rotateByZ(double rZ) {
            return rotateZ(rZ);
        }

        public ATTransformator build() {
            return new ATTransformator(currentMatrix);
        }
    }


    @Override
    public String toString() {
        return transformationMatrix.toString();
    }

    public void clean() {
        this.transformationMatrix = new Matrix4f(true);
    }
}
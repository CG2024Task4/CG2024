package com.cgvsu.render_engine;

import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.model.Model;
import com.cgvsu.rasterization.Rasterization;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.math.core.MatrixUtils;
import com.cgvsu.math.typesMatrix.Matrix4f;
import com.cgvsu.math.typesVectors.Vector3f;
import javafx.scene.paint.Color;
import java.util.ArrayList;

import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            double[][] zBuffer,
            boolean polyGrid,
            boolean coloring) {

        // Матрицы модели, вида и проекции
        Matrix4f modelMatrix = rotateScaleTranslate(mesh);
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        // Итоговая матрица MVP
        Matrix4f modelViewProjectionMatrix = MatrixUtils.multiplied(projectionMatrix, viewMatrix, modelMatrix);

        final int nPolygons = mesh.polygons.size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.polygons.get(polygonInd).getVertexIndices().size();


            ArrayList<Double> arrayZ = new ArrayList<>();
            ArrayList<Vector2f> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                // Получаем вершину
                Vector3f vertex = mesh.vertices.get(mesh.polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));
                Vector3f transformedVertex = multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex);
                arrayZ.add(transformedVertex.getZ());
                // Преобразуем в координаты экрана
                Vector2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex), width, height);
                resultPoints.add(resultPoint);
            }


            // Растеризация полигонов
            int[] arrX = {(int) resultPoints.get(0).getX(), (int) resultPoints.get(1).getX(), (int) resultPoints.get(2).getX()};
            int[] arrY = {(int) resultPoints.get(0).getY(), (int) resultPoints.get(1).getY(), (int) resultPoints.get(2).getY()};
            double[] arrZ = {arrayZ.get(0), arrayZ.get(1), arrayZ.get(2)};
            javafx.scene.paint.Color[] colors = {Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY};
            Rasterization.fillTriangle(graphicsContext, arrX, arrY, arrZ, colors, zBuffer, polyGrid, coloring);
        }
    }
}

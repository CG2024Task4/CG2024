package com.cgvsu.rasterization;

import com.cgvsu.model.Model;
import com.cgvsu.math.typesVectors.Vector2f;
import com.cgvsu.math.typesVectors.Vector3f;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Rasterization {

    public static void fillTriangle(
            final GraphicsContext graphicsContext,
            final int[] arrX,
            final int[] arrY,
            final double[] arrZ,
            final Color[] colors,
            double[][] zBuffer,
            boolean polyGrid,
            Model mesh,
            Vector2f[] textures,
            double[] light,
            Vector3f[] normals) {

        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        sort(arrX, arrY, arrZ, normals, textures, colors);

        for (int y = arrY[0]; y <= arrY[1]; y++) {
            final int x1 = (arrY[1] - arrY[0] == 0) ? arrX[0] :
                    (y - arrY[0]) * (arrX[1] - arrX[0]) / (arrY[1] - arrY[0]) + arrX[0];
            final int x2 = (arrY[0] - arrY[2] == 0) ? arrX[2] :
                    (y - arrY[2]) * (arrX[0] - arrX[2]) / (arrY[0] - arrY[2]) + arrX[2];
            final int Ax = Math.min(x1, x2);
            final int Bx = Math.max(x1, x2);
            if (y < 0){
                break;
            }
            for (int x = Ax; x <= Bx; x++) {
                if (x < 0 || x >= zBuffer.length || y >= zBuffer[0].length){
                    break;
                }
                double[] barizenticCoordinate = barizentricCalculator(x, y, arrX, arrY);
                if (!Double.isNaN(barizenticCoordinate[0]) && !Double.isNaN(barizenticCoordinate[1]) && !Double.isNaN(barizenticCoordinate[2]) &&
                        ((barizenticCoordinate[0] + barizenticCoordinate[1] + barizenticCoordinate[2] - 1) < 1e-7f)) {
                    double z = arrZ[0]*barizenticCoordinate[0] + arrZ[1]*barizenticCoordinate[1] + arrZ[2]*barizenticCoordinate[2];
                    int[] rgb = getGradientCoordinatesRGB(barizenticCoordinate, colors);
                    if (z < zBuffer[x][y]) {
                        zBuffer[x][y] = z;
                        if ((barizenticCoordinate[0] < 0.01 || barizenticCoordinate[1] < 0.01 || barizenticCoordinate[2] < 0.01) & polyGrid) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                            continue;
                        } else if (mesh.isActiveTexture) {
                            texture(barizenticCoordinate, textures, mesh, rgb);
                        }
                        if (mesh.isActiveLighting){
                            light(barizenticCoordinate, normals, light, rgb);
                        }
                        pixelWriter.setColor(x, y,  Color.rgb(rgb[0], rgb[1], rgb[2]));
                    }
                }
            }
        }

        for (int y = arrY[1]; y <= arrY[2]; y++) {
            final int x1 = (arrY[2] - arrY[1] == 0) ? arrX[1] :
                    (y - arrY[1]) * (arrX[2] - arrX[1]) / (arrY[2] - arrY[1]) + arrX[1];
            final int x2 = (arrY[0] - arrY[2] == 0) ? arrX[2] :
                    (y - arrY[2]) * (arrX[0] - arrX[2]) / (arrY[0] - arrY[2]) + arrX[2];
            final int Ax = Math.min(x1, x2);
            final int Bx = Math.max(x1, x2);
            if (y < 0){
                break;
            }
            for (int x = Ax; x <= Bx; x++) {
                if (x < 0 || x >= zBuffer.length || y >= zBuffer[0].length){
                    break;
                }
                double[] barizenticCoordinate = barizentricCalculator(x, y, arrX, arrY);
                if (!Double.isNaN(barizenticCoordinate[0]) && !Double.isNaN(barizenticCoordinate[1]) && !Double.isNaN(barizenticCoordinate[2]) &&
                        ((barizenticCoordinate[0] + barizenticCoordinate[1] + barizenticCoordinate[2] - 1) < 1e-7f)) {
                    double z = arrZ[0] * barizenticCoordinate[0] + arrZ[1] * barizenticCoordinate[1] + arrZ[2] * barizenticCoordinate[2];
                    int[] rgb = getGradientCoordinatesRGB(barizenticCoordinate, colors);
                    if (z < zBuffer[x][y]) {
                        zBuffer[x][y] = z;
                        if ((barizenticCoordinate[0] < 0.01 || barizenticCoordinate[1] < 0.01 || barizenticCoordinate[2] < 0.01) & polyGrid) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                            continue;
                        }else if (mesh.isActiveTexture) {
                            texture(barizenticCoordinate, textures, mesh, rgb);
                        }
                        if (mesh.isActiveLighting){
                            light(barizenticCoordinate, normals, light, rgb);
                        }
                        pixelWriter.setColor(x, y,  Color.rgb(rgb[0], rgb[1], rgb[2]));
                    }
                }
            }
        }


    }

    private static double determinator(int[][] arr) {
        return arr[0][0] * arr[1][1] * arr[2][2] + arr[1][0] * arr[0][2] * arr[2][1] +
                arr[0][1] * arr[1][2] * arr[2][0] - arr[0][2] * arr[1][1] * arr[2][0] -
                arr[0][0] * arr[1][2] * arr[2][1] - arr[0][1] * arr[1][0] * arr[2][2];
    }

    private static double[] barizentricCalculator(int x, int y, int[] arrX, int[] arrY){
        final double generalDeterminant = determinator(new int[][]{arrX, arrY, new int[]{1, 1, 1}});
        final double coordinate0 = Math.abs(determinator(
                new int[][]{new int[]{x, arrX[1], arrX[2]}, new int[]{y, arrY[1], arrY[2]}, new int[]{1, 1, 1}}) /
                generalDeterminant);
        final double coordinate1 = Math.abs(determinator(
                new int[][]{new int[]{arrX[0], x, arrX[2]}, new int[]{arrY[0], y, arrY[2]}, new int[]{1, 1, 1}}) /
                generalDeterminant);
        final double coordinate2 = Math.abs(determinator(
                new int[][]{new int[]{arrX[0], arrX[1], x}, new int[]{arrY[0], arrY[1], y}, new int[]{1, 1, 1}}) /
                generalDeterminant);
        return new double[]{coordinate0, coordinate1, coordinate2};
    }

    public static int[] getGradientCoordinatesRGB(final double[] baristicCoords, final Color[] color) {
        int r = Math.min(255, (int) Math.abs(color[0].getRed() * 255 * baristicCoords[0] + color[1].getRed()
                * 255 * baristicCoords[1] + color[2].getRed() * 255 * baristicCoords[2]));
        int g = Math.min(255, (int) Math.abs(color[0].getGreen() * 255 * baristicCoords[0] + color[1].getGreen()
                * 255 * baristicCoords[1] + color[2].getGreen() * 255 * baristicCoords[2]));
        int b = Math.min(255, (int) Math.abs(color[0].getBlue() * 255 * baristicCoords[0] + color[1].getBlue()
                * 255 * baristicCoords[1] + color[2].getBlue() * 255 * baristicCoords[2]));

        return new int[]{r, g, b};
    }

    private static void sort(int[] x, int[] y, double[] z, Vector3f[] n, Vector2f[] t, Color[] c) {
        if (y[0] > y[1]) {
            swap(x, y, z, c, n, t, 0, 1);
        }
        if (y[1] > y[2]) {
            swap(x, y, z, c, n, t, 1, 2);
        }
        if (y[0] > y[1]) {
            swap(x, y, z, c, n, t, 0, 1);
        }
    }

    private static void swap(int[] x, int[] y, double[] z, Color[] c, Vector3f[] n, Vector2f[] t, int i, int j) {
        int tempY = y[i];
        int tempX = x[i];
        double tempZ = z[i];
        Color tempC = c[i];
        Vector3f tempN = n[i];
        Vector2f tempT = t[i];
        x[i] = x[j];
        y[i] = y[j];
        z[i] = z[j];
        c[i] = c[j];
        n[i] = n[j];
        t[i] = t[j];
        x[j] = tempX;
        y[j] = tempY;
        z[j] = tempZ;
        c[j] = tempC;
        n[j] = tempN;
        t[j] = tempT;
    }

    public static double[] getGradientCoordinatesTexture(double[] barizentric, Vector2f[] texture) {
        return new double[] {(barizentric[0] * texture[0].getX()) +  (barizentric[1] * texture[1].getX()) +  (barizentric[2] * texture[2].getX()),
                (barizentric[0] * texture[0].getX()) + (barizentric[1] * texture[1].getY()) + (barizentric[2] * texture[2].getY())};
    }

    public static void texture(double[] barizentric, Vector2f[] textures, Model mesh, int[] rgb){
        double[] texture = getGradientCoordinatesTexture(barizentric, textures);
        int u = (int) Math.round(texture[0] * (mesh.texture.wight - 1));
        int v = (int) Math.round(texture[1] * (mesh.texture.height - 1));
        if (u < mesh.texture.wight && v < mesh.texture.height) {
            rgb[0] = mesh.texture.pixelData[u][v][0];
            rgb[1] = mesh.texture.pixelData[u][v][1];
            rgb[2] = mesh.texture.pixelData[u][v][2];
        }
    }
    public static void calculateLight(int[] rgb, double[] light, Vector3f normal){
        double k = 0.5;
        double l = -(light[0] * normal.getX() + light[1] * normal.getY() + light[2] * normal.getZ());
        if(l < 0){
            l = 0;
        }
        rgb[0] = Math.min(255, (int) (rgb[0] * (1 - k) + rgb[0] * k * l));
        rgb[1] = Math.min(255, (int) (rgb[1] * (1 - k) + rgb[1] * k * l));
        rgb[2] = Math.min(255, (int) (rgb[2] * (1 - k) + rgb[2] * k * l));
    }
    public static Vector3f smoothingNormal(final double[] baristicCoords, final Vector3f[] normals) {
        return new Vector3f((float) (baristicCoords[0] * normals[0].getX() + baristicCoords[1] * normals[1].getX() + baristicCoords[2] * normals[2].getX()),
                (float) (baristicCoords[0] * normals[0].getY() + baristicCoords[1] * normals[1].getY() + baristicCoords[2] * normals[2].getY()),
                (float) (baristicCoords[0] * normals[0].getZ() + baristicCoords[1] * normals[1].getZ() + baristicCoords[2] * normals[2].getZ()));
    }

    public static void light(final double[] barizentric, final Vector3f[] normals, double[] light, int[] rgb){
        Vector3f smooth = smoothingNormal(barizentric, normals);
        calculateLight(rgb, light, smooth);
    }
}
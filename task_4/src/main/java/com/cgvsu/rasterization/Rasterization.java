package com.cgvsu.rasterization;

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
            boolean coloring) {

        final PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        sort(arrX, arrY, arrZ, colors);

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
                    if (z < zBuffer[x][y]) {

                        if ((barizenticCoordinate[0] < 0.01 || barizenticCoordinate[1] < 0.01 || barizenticCoordinate[2] < 0.01) & polyGrid) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                        } else if (coloring) {
                            pixelWriter.setColor(x, y, getColor(barizenticCoordinate, colors));
                        }else {continue;}
                        zBuffer[x][y] = z;
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
                    if (z < zBuffer[x][y]) {
                        if ((barizenticCoordinate[0] < 0.01 || barizenticCoordinate[1] < 0.01 || barizenticCoordinate[2] < 0.01) & polyGrid) {
                            pixelWriter.setColor(x, y, Color.WHITE);
                        } else if (coloring) {
                            pixelWriter.setColor(x, y, getColor(barizenticCoordinate, colors));
                        }else {continue;}
                        zBuffer[x][y] = z;
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

    private static Color getColor(double[] barycentricCoords, Color[] colors) {

        final double red = barycentricCoords[0] * colors[0].getRed() +
                barycentricCoords[1] * colors[1].getRed() +
                barycentricCoords[2] * colors[2].getRed();
        final double green = barycentricCoords[0] * colors[0].getGreen() +
                barycentricCoords[1] * colors[1].getGreen() +
                barycentricCoords[2] * colors[2].getGreen();
        final double blue = barycentricCoords[0] * colors[0].getBlue() +
                barycentricCoords[1] * colors[1].getBlue() +
                barycentricCoords[2] * colors[2].getBlue();

        return new Color(
                Math.max(0, Math.min(1, red)),
                Math.max(0, Math.min(1, green)),
                Math.max(0, Math.min(1, blue)),
                1);
    }

    private static void sort(int[] x, int[] y, double[] z, Color[] c) {
        if (y[0] > y[1]) {
            swap(x, y, z, c, 0, 1);
        }
        if (y[1] > y[2]) {
            swap(x, y, z, c, 1, 2);
        }
        if (y[0] > y[1]) {
            swap(x, y, z, c, 0, 1);
        }
    }

    private static void swap(int[] x, int[] y, double[] z, Color[] c, int i, int j) {
        int tempY = y[i];
        int tempX = x[i];
        double tempZ = z[i];
        Color tempC = c[i];
        x[i] = x[j];
        y[i] = y[j];
        z[i] = z[j];
        c[i] = c[j];
        x[j] = tempX;
        y[j] = tempY;
        z[j] = tempZ;
        c[j] = tempC;
    }
}
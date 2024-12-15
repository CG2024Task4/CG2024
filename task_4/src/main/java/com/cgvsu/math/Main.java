package com.cgvsu.math;


import com.cgvsu.math.types.RecMatrix;
import com.cgvsu.math.typesMatrix.Matrix2D;
import com.cgvsu.math.typesMatrix.Matrix3D;
import com.cgvsu.math.typesVectors.Vector3C;

/**
 * Некоторые примеры использования библиотеки
 */
public class Main {
    public static void main(String[] args) {
        // Работа с Matrix2D
        System.out.println("=== Работа с Matrix2D ===");
        Matrix2D matrix2D_1 = new Matrix2D(new double[][]{
                {1, 2},
                {3, 4}
        });
        Matrix2D matrix2D_2 = new Matrix2D(new double[][]{
                {5, 6},
                {7, 8}
        });

        // Сложение
        Matrix2D sum2D = matrix2D_1.added(matrix2D_2);
        System.out.println("Matrix2D A:");
        matrix2D_1.print();
        System.out.println("+");
        System.out.println("Matrix2D B:");
        matrix2D_2.print();
        System.out.println("=");
        sum2D.print();

        // Умножение на число
        Matrix2D scaled2D = matrix2D_1.multiplied(2);
        System.out.println("Matrix2D A умноженная на 2:");
        matrix2D_1.print();
        System.out.println("* 2 =");
        scaled2D.print();

        // Умножение на матрицу
        Matrix2D product2D = matrix2D_1.multiplied(matrix2D_2);
        System.out.println("Matrix2D A умноженная на Matrix2D B:");
        matrix2D_1.print();
        System.out.println("*");
        matrix2D_2.print();
        System.out.println("=");
        product2D.print();

        // Работа с Matrix3D
        System.out.println("\n=== Работа с Matrix3D ===");
        Matrix3D matrix3D_1 = new Matrix3D(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3D matrix3D_2 = new Matrix3D(new double[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });

        // Сложение
        Matrix3D sum3D = matrix3D_1.added(matrix3D_2);
        System.out.println("Matrix3D A:");
        matrix3D_1.print();
        System.out.println("+");
        System.out.println("Matrix3D B:");
        matrix3D_2.print();
        System.out.println("=");
        sum3D.print();

        // Умножение на число
        Matrix3D scaled3D = matrix3D_1.multiplied(3);
        System.out.println("Matrix3D A умноженная на 3:");
        matrix3D_1.print();
        System.out.println("* 3 =");
        scaled3D.print();

        // Умножение на матрицу
        Matrix3D product3D = matrix3D_1.multiplied(matrix3D_2);
        System.out.println("Matrix3D A умноженная на Matrix3D B:");
        matrix3D_1.print();
        System.out.println("*");
        matrix3D_2.print();
        System.out.println("=");
        product3D.print();

        // Работа с RecMatrix
        System.out.println("\n=== Работа с RecMatrix ===");
        RecMatrix recMatrix1 = new RecMatrix(2, 3, new double[][]{
                {1, 2, 3},
                {4, 5, 6}
        });
        RecMatrix recMatrix2 = new RecMatrix(3, 2, new double[][]{
                {7, 8},
                {9, 10},
                {11, 12}
        });

        // Умножение прямоугольной матрицы
        RecMatrix productRec = recMatrix1.multiplied(recMatrix2);
        System.out.println("RecMatrix A:");
        recMatrix1.print();
        System.out.println("*");
        System.out.println("RecMatrix B:");
        recMatrix2.print();
        System.out.println("=");
        productRec.print();

        // Работа с VectorC
        System.out.println("\n=== Работа с VectorC ===");
        Vector3C vector = new Vector3C(new double[]{1, 2, 3});

        // Умножение матрицы на вектор
        Vector3C resultVector = matrix3D_1.multiplied(vector);
        System.out.println("Matrix3D A умноженная на VectorC:");
        matrix3D_1.print();
        System.out.println("*");
        vector.print();
        System.out.println("=");
        resultVector.print();
    }
}

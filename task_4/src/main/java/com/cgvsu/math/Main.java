package com.cgvsu.math;


import com.cgvsu.math.types.RecMatrix;
import com.cgvsu.math.typesMatrix.Matrix2f;
import com.cgvsu.math.typesMatrix.Matrix3f;
import com.cgvsu.math.typesVectors.Vector3f;

/**
 * Некоторые примеры использования библиотеки
 */
public class Main {
    public static void main(String[] args) {
        // Работа с Matrix2f
        System.out.println("=== Работа с Matrix2f ===");
        Matrix2f matrix2D_1 = new Matrix2f(new double[][]{
                {1, 2},
                {3, 4}
        });
        Matrix2f matrix2D_2 = new Matrix2f(new double[][]{
                {5, 6},
                {7, 8}
        });

        // Сложение
        Matrix2f sum2D = matrix2D_1.added(matrix2D_2);
        System.out.println("Matrix2f A:");
        matrix2D_1.print();
        System.out.println("+");
        System.out.println("Matrix2f B:");
        matrix2D_2.print();
        System.out.println("=");
        sum2D.print();

        // Умножение на число
        Matrix2f scaled2D = matrix2D_1.multiplied(2);
        System.out.println("Matrix2f A умноженная на 2:");
        matrix2D_1.print();
        System.out.println("* 2 =");
        scaled2D.print();

        // Умножение на матрицу
        Matrix2f product2D = matrix2D_1.multiplied(matrix2D_2);
        System.out.println("Matrix2f A умноженная на Matrix2f B:");
        matrix2D_1.print();
        System.out.println("*");
        matrix2D_2.print();
        System.out.println("=");
        product2D.print();

        // Работа с Matrix3f
        System.out.println("\n=== Работа с Matrix3f ===");
        Matrix3f matrix3D_1 = new Matrix3f(new double[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
        Matrix3f matrix3D_2 = new Matrix3f(new double[][]{
                {9, 8, 7},
                {6, 5, 4},
                {3, 2, 1}
        });

        // Сложение
        Matrix3f sum3D = matrix3D_1.added(matrix3D_2);
        System.out.println("Matrix3f A:");
        matrix3D_1.print();
        System.out.println("+");
        System.out.println("Matrix3f B:");
        matrix3D_2.print();
        System.out.println("=");
        sum3D.print();

        // Умножение на число
        Matrix3f scaled3D = matrix3D_1.multiplied(3);
        System.out.println("Matrix3f A умноженная на 3:");
        matrix3D_1.print();
        System.out.println("* 3 =");
        scaled3D.print();

        // Умножение на матрицу
        Matrix3f product3D = matrix3D_1.multiplied(matrix3D_2);
        System.out.println("Matrix3f A умноженная на Matrix3f B:");
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
        Vector3f vector = new Vector3f(new double[]{1, 2, 3});

        // Умножение матрицы на вектор
        Vector3f resultVector = matrix3D_1.multiplied(vector);
        System.out.println("Matrix3f A умноженная на VectorC:");
        matrix3D_1.print();
        System.out.println("*");
        vector.print();
        System.out.println("=");
        resultVector.print();
    }
}

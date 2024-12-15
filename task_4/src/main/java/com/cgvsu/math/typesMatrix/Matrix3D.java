package com.cgvsu.math.typesMatrix;


import com.cgvsu.math.core.MatrixWrapper;
import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.typesVectors.Vector3C;

/**
 * Класс для работы с матрицами размером 3x3.
 * <p>
 * Этот класс представляет собой матрицу размером 3x3. Он позволяет создавать матрицы с различными типами данных,
 * а также предоставляет методы для работы с ними.
 */
public class Matrix3D extends MatrixWrapper<Matrix3D, Vector3C> {

    /**
     * Конструктор для создания матрицы 3x3 из двумерного массива.
     *
     * @param base двумерный массив, представляющий значения матрицы 3x3.
     */
    public Matrix3D(double[][] base) {
        super(3, base);
    }

    /**
     * Конструктор для создания матрицы 3x3 из одномерного массива.
     *
     * @param base одномерный массив, представляющий значения матрицы 3x3.
     */
    public Matrix3D(double[] base) {
        super(3, base);
    }

    /**
     * Конструктор для создания нулевой матрицы 3x3.
     */
    public Matrix3D() {
        super(3);
    }

    /**
     * Конструктор для создания единичной матрицы 3x3.
     *
     * @param unit если true, создаётся единичная матрица, если false — нулевая.
     */
    public Matrix3D(boolean unit) {
        super(3, unit);
    }

    /**
     * Метод для создания нового объекта Matrix3D на основе SquareMatrix.
     *
     * @param matrix квадратная матрица.
     * @return новый объект Matrix3D.
     */
    @Override
    protected Matrix3D newMatrix(SquareMatrix matrix) {
        return new Matrix3D(matrix.getBase());
    }
}

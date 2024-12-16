package com.cgvsu.math.typesMatrix;


import com.cgvsu.math.core.MatrixWrapper;
import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.typesVectors.Vector3f;

/**
 * Класс для работы с матрицами размером 3x3.
 * <p>
 * Этот класс представляет собой матрицу размером 3x3. Он позволяет создавать матрицы с различными типами данных,
 * а также предоставляет методы для работы с ними.
 */
public class Matrix3f extends MatrixWrapper<Matrix3f, Vector3f> {

    /**
     * Конструктор для создания матрицы 3x3 из двумерного массива.
     *
     * @param base двумерный массив, представляющий значения матрицы 3x3.
     */
    public Matrix3f(double[][] base) {
        super(3, base);
    }

    /**
     * Конструктор для создания матрицы 3x3 из одномерного массива.
     *
     * @param base одномерный массив, представляющий значения матрицы 3x3.
     */
    public Matrix3f(double[] base) {
        super(3, base);
    }

    /**
     * Конструктор для создания нулевой матрицы 3x3.
     */
    public Matrix3f() {
        super(3);
    }

    /**
     * Конструктор для создания единичной матрицы 3x3.
     *
     * @param unit если true, создаётся единичная матрица, если false — нулевая.
     */
    public Matrix3f(boolean unit) {
        super(3, unit);
    }

    /**
     * Метод для создания нового объекта Matrix3f на основе SquareMatrix.
     *
     * @param matrix квадратная матрица.
     * @return новый объект Matrix3f.
     */
    @Override
    protected Matrix3f newMatrix(SquareMatrix matrix) {
        return new Matrix3f(matrix.getBase());
    }
}

package com.cgvsu.math.typesMatrix;


import com.cgvsu.math.core.MatrixWrapper;
import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.typesVectors.Vector2C;

/**
 * Класс для работы с матрицами размером 2x2.
 * <p>
 * Этот класс представляет собой матрицу размером 2x2. Он позволяет создавать матрицы с различными типами данных,
 * а также предоставляет методы для работы с ними.
 */
public class Matrix2D extends MatrixWrapper<Matrix2D, Vector2C> {

    /**
     * Конструктор для создания матрицы 2x2 из двумерного массива.
     *
     * @param base двумерный массив, представляющий значения матрицы 2x2.
     */
    public Matrix2D(double[][] base) {
        super(2, base);
    }

    /**
     * Конструктор для создания матрицы 2x2 из одномерного массива.
     *
     * @param base одномерный массив, представляющий значения матрицы 2x2.
     */
    public Matrix2D(double[] base) {
        super(2, base);
    }

    /**
     * Конструктор для создания нулевой матрицы 2x2.
     */
    public Matrix2D() {
        super(2);
    }

    /**
     * Конструктор для создания единичной матрицы 2x2.
     *
     * @param unit если true, создаётся единичная матрица, если false — нулевая.
     */
    public Matrix2D(boolean unit) {
        super(2, unit);
    }

    /**
     * Метод для создания нового объекта Matrix2D на основе SquareMatrix.
     *
     * @param matrix квадратная матрица.
     * @return новый объект Matrix2D.
     */
    @Override
    protected Matrix2D newMatrix(SquareMatrix matrix) {
        return new Matrix2D(matrix.getBase());
    }
}

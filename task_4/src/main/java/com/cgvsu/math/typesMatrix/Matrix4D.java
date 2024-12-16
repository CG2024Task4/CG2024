package com.cgvsu.math.typesMatrix;


import com.cgvsu.math.core.MatrixWrapper;
import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.typesVectors.Vector4C;

/**
 * Класс для работы с матрицами размером 4x4.
 * <p>
 * Этот класс представляет собой матрицу размером 4x4. Он предоставляет методы для создания и работы с такими матрицами,
 * а также для выполнения различных операций над ними.
 */
public class Matrix4D extends MatrixWrapper<Matrix4D, Vector4C> {

    /**
     * Конструктор для создания матрицы 4x4 из двумерного массива.
     *
     * @param base двумерный массив, представляющий значения матрицы 4x4.
     */
    public Matrix4D(double[][] base) {
        super(4, base);
    }

    /**
     * Конструктор для создания матрицы 4x4 из одномерного массива.
     *
     * @param base одномерный массив, представляющий значения матрицы 4x4.
     */
    public Matrix4D(double[] base) {
        super(4, base);
    }

    /**
     * Конструктор для создания нулевой матрицы 4x4.
     */
    public Matrix4D() {
        super(4);
    }

    /**
     * Конструктор для создания единичной матрицы 4x4.
     *
     * @param unit если true, создаётся единичная матрица, если false — нулевая.
     */
    public Matrix4D(boolean unit) {
        super(4, unit);
    }

    /**
     * Метод для создания нового объекта Matrix4D на основе SquareMatrix.
     *
     * @param matrix квадратная матрица.
     * @return новый объект Matrix4D.
     */
    @Override
    protected Matrix4D newMatrix(SquareMatrix matrix) {
        return new Matrix4D(matrix.getBase());
    }
}

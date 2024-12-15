package com.cgvsu.math.types;


import com.cgvsu.math.core.AbstractMatrix;

/**
 * Класс для работы с векторами-столбцами.
 * <p>
 * Вектор-столбец — это матрица размером N×1. Этот класс предоставляет методы нормализации,
 * вычисления длины и операций с другими векторами.
 */
public class VectorC extends AbstractMatrix<VectorC> {

    // Конструкторы

    /**
     * Создает нулевой вектор-столбец заданной длины.
     *
     * @param n количество элементов (длина вектора).
     */
    public VectorC(int n) {
        super(n, 1);
    }

    /**
     * Создает вектор-столбец с элементами из одномерного массива.
     *
     * @param n    количество элементов (длина вектора).
     * @param base массив элементов вектора.
     */
    public VectorC(int n, double[] base) {
        super(n, 1, base);
    }

    /**
     * Создает вектор-столбец на основе общей матрицы.
     *
     * @param matrix базовая матрица.
     * @throws IllegalArgumentException если базовая матрица не имеет 1 столбец.
     */
    protected VectorC(Matrix matrix) {
        super(matrix.getRows(), matrix.getCols(), matrix.getBase());
        validateColumnVector(matrix.getCols());
    }

    // Методы операций

    /**
     * Умножение текущего вектора-столбца на вектор-строку.
     *
     * @param vector вектор-строка (VectorR).
     * @return результат умножения в виде прямоугольной матрицы.
     */
    public RecMatrix multiplied(VectorR vector) {
        Matrix result = getMatrix().multiplied(new Matrix(vector.getRows(), vector.getCols(), vector.getBase()));
        return new RecMatrix(result);
    }

    /**
     * Нормализация вектора (приведение длины к единице).
     *
     * @return нормализованный вектор-столбец.
     */
    public VectorC normalize() {
        return new VectorC(getMatrix().normalize());
    }

    /**
     * Вычисление длины (нормы) вектора.
     *
     * @return длина вектора.
     */
    public double getLength() {
        return getMatrix().getLength();
    }

    /**
     * Векторное произведение текущего вектора с другим вектором-столбцом.
     *
     * @param vector другой вектор-столбец.
     * @return результат в виде нового вектора-столбца.
     * @throws IllegalArgumentException если размеры векторов не соответствуют для операции.
     */
    public VectorC crossProduct(VectorC vector) {
        return new VectorC(getMatrix().crossProduct(vector.getMatrix()));
    }

    // Вспомогательные методы

    /**
     * Проверяет, что матрица является вектором-столбцом.
     *
     * @param col количество столбцов.
     * @throws IllegalArgumentException если количество столбцов не равно 1.
     */
    private void validateColumnVector(int col) {
        if (col != 1) {
            throw new IllegalArgumentException("Матрица должна быть вектором-столбцом (1 столбец).");
        }
    }

    /**
     * Создает новый вектор-столбец на основе базовой матрицы.
     *
     * @param matrix базовая матрица.
     * @return новый вектор-столбец.
     */
    @Override
    protected VectorC newMatrix(Matrix matrix) {
        return new VectorC(matrix);
    }

    public double dotProduct(VectorC vector) {
        return getMatrix().dot(vector.getMatrix());
    }
}

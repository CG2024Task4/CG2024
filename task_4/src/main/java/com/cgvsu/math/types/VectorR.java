package com.cgvsu.math.types;


import com.cgvsu.math.core.AbstractMatrix;

/**
 * Класс для работы с векторами-строками.
 * <p>
 * Вектор-строка — это матрица размером 1×N. Этот класс предоставляет методы нормализации,
 * вычисления длины, скалярного произведения и операций с матрицами.
 */
public class VectorR extends AbstractMatrix<VectorR> {

    // Конструкторы

    /**
     * Создает нулевой вектор-строку заданной длины.
     *
     * @param n количество элементов (длина вектора).
     */
    public VectorR(int n) {
        super(1, n);
    }

    /**
     * Создает вектор-строку с элементами из одномерного массива.
     *
     * @param n    количество элементов (длина вектора).
     * @param base массив элементов вектора.
     */
    public VectorR(int n, double[] base) {
        super(1, n, base);
    }

    /**
     * Создает вектор-строку на основе общей матрицы.
     *
     * @param matrix базовая матрица.
     * @throws IllegalArgumentException если базовая матрица не является вектором-строкой.
     */
    protected VectorR(Matrix matrix) {
        super(matrix.getRows(), matrix.getCols(), matrix.getBase());
        validateRowVector(matrix.getCols());
    }

    // Методы операций

    /**
     * Умножение текущего вектора-строки на квадратную матрицу.
     *
     * @param matrix квадратная матрица.
     * @return результат умножения в виде нового вектора-строки.
     */
    public VectorR multiplied(SquareMatrix matrix) {
        Matrix result = getMatrix().multiplied(new Matrix(matrix.getRows(), matrix.getCols(), matrix.getBase()));
        return new VectorR(result);
    }

    /**
     * Умножение текущего вектора-строки на прямоугольную матрицу.
     *
     * @param matrix прямоугольная матрица.
     * @return результат умножения в виде нового вектора-строки.
     */
    public VectorR multiplied(RecMatrix matrix) {
        Matrix result = getMatrix().multiplied(new Matrix(matrix.getRows(), matrix.getCols(), matrix.getBase()));
        return new VectorR(result);
    }

    /**
     * Скалярное произведение текущего вектора-строки и вектора-столбца.
     *
     * @param vector вектор-столбец.
     * @return результат умножения (скаляр).
     */
    public double multiplied(VectorC vector) {
        Matrix result = getMatrix().multiplied(new Matrix(vector.getRows(), vector.getCols(), vector.getBase()));
        return result.getBase()[0];
    }

    /**
     * Нормализация вектора-строки (приведение длины к единице).
     *
     * @return нормализованный вектор-строка.
     */
    public VectorR normalize() {
        return new VectorR(getMatrix().normalize());
    }

    /**
     * Вычисление длины (нормы) вектора-строки.
     *
     * @return длина вектора.
     */
    public double getLength() {
        return getMatrix().getLength();
    }

    // Вспомогательные методы

    /**
     * Проверяет, что матрица является вектором-строкой.
     *
     * @param row количество строк.
     * @throws IllegalArgumentException если количество строк не равно 1.
     */
    private void validateRowVector(int row) {
        if (row != 1) {
            throw new IllegalArgumentException("Матрица должна быть вектором-строкой (1 строка).");
        }
    }

    /**
     * Создает новый вектор-строку на основе базовой матрицы.
     *
     * @param matrix базовая матрица.
     * @return новый вектор-строка.
     */
    @Override
    protected VectorR newMatrix(Matrix matrix) {
        return new VectorR(matrix);
    }
}

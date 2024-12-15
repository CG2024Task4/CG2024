/**
 * Пакет, содержащий основные типы матриц.
 */
package com.cgvsu.math.types;


import com.cgvsu.math.core.AbstractMatrix;

/**
 * Класс для работы с прямоугольными матрицами.
 * <p>
 * Прямоугольная матрица — это матрица, у которой количество строк не равно количеству столбцов.
 * Этот класс предоставляет базовые операции, такие как транспонирование, умножение и создание новых матриц.
 */
public class RecMatrix extends AbstractMatrix<RecMatrix> {

    // Конструкторы

    /**
     * Конструктор для создания нулевой прямоугольной матрицы заданных размеров.
     *
     * @param row количество строк.
     * @param col количество столбцов.
     * @throws IllegalArgumentException если размеры матрицы не соответствуют требованиям.
     */
    public RecMatrix(int row, int col) {
        super(row, col);
        validateNonSquare(row, col);
    }

    /**
     * Конструктор для создания прямоугольной матрицы на основе общей матрицы.
     *
     * @param matrix общая матрица.
     * @throws IllegalArgumentException если размеры матрицы не соответствуют требованиям.
     */
    protected RecMatrix(Matrix matrix) {
        super(matrix.getRows(), matrix.getCols(), matrix.getBase());
        validateNonSquare(matrix.getRows(), matrix.getCols());
    }

    /**
     * Конструктор для создания прямоугольной матрицы с элементами из одномерного массива.
     *
     * @param row  количество строк.
     * @param col  количество столбцов.
     * @param base массив элементов (одномерный).
     * @throws IllegalArgumentException если размеры матрицы не соответствуют требованиям.
     */
    public RecMatrix(int row, int col, double[] base) {
        super(row, col, base);
        validateNonSquare(row, col);
    }

    /**
     * Конструктор для создания прямоугольной матрицы с элементами из двумерного массива.
     *
     * @param row  количество строк.
     * @param col  количество столбцов.
     * @param base массив элементов (двумерный).
     * @throws IllegalArgumentException если размеры матрицы не соответствуют требованиям.
     */
    public RecMatrix(int row, int col, double[][] base) {
        super(row, col, base);
        validateNonSquare(row, col);
    }

    // Проверка корректности размеров

    /**
     * Проверяет, что размеры матрицы соответствуют требованиям для прямоугольной матрицы.
     *
     * @param row количество строк.
     * @param col количество столбцов.
     * @throws IllegalArgumentException если матрица является вектором или квадратной.
     */
    private void validateNonSquare(int row, int col) {
        if (row == 1 || col == 1) {
            throw new IllegalArgumentException("Прямоугольная матрица не может быть вектором. Проверьте размеры (должны быть больше 1x1).");
        }
    }

    // Операции с матрицами

    /**
     * Транспонирует текущую матрицу.
     */
    public void transpose() {
        setMatrix(getMatrix().transposed());
    }

    /**
     * Создает транспонированную копию текущей матрицы.
     *
     * @return новая транспонированная прямоугольная матрица.
     */
    public RecMatrix transposed() {
        return newMatrix(getMatrix().transposed());
    }

    /**
     * Перемножает текущую матрицу с другой прямоугольной матрицей.
     *
     * @param matrix другая прямоугольная матрица.
     */
    public void multiply(RecMatrix matrix) {
        setMatrix(getMatrix().multiplied(matrix.getMatrix()));
    }

    /**
     * Создает новую матрицу как результат умножения текущей на другую прямоугольную матрицу.
     *
     * @param matrix другая прямоугольная матрица.
     * @return новая прямоугольная матрица.
     */
    public RecMatrix multiplied(RecMatrix matrix) {
        return newMatrix(getMatrix().multiplied(matrix.getMatrix()));
    }

    /**
     * Умножает текущую матрицу на квадратную матрицу.
     *
     * @param matrix квадратная матрица.
     * @return новая прямоугольная матрица.
     */
    public RecMatrix multiplied(SquareMatrix matrix) {
        Matrix result = getMatrix().multiplied(new Matrix(matrix.getRows(), matrix.getCols(), matrix.getBase()));
        return new RecMatrix(result);
    }

    /**
     * Умножает текущую матрицу на вектор-столбец.
     *
     * @param vector вектор-столбец.
     * @return новый вектор-столбец.
     */
    public VectorC multiplied(VectorC vector) {
        Matrix result = getMatrix().multiplied(new Matrix(vector.getRows(), vector.getCols(), vector.getBase()));
        return new VectorC(result);
    }

    // Абстрактный метод для создания новой матрицы

    /**
     * Создает новую прямоугольную матрицу на основе базовой матрицы.
     *
     * @param matrix базовая матрица.
     * @return новая прямоугольная матрица.
     */
    @Override
    protected RecMatrix newMatrix(Matrix matrix) {
        return new RecMatrix(matrix);
    }
}

package com.cgvsu.math.types;


import com.cgvsu.math.core.AbstractMatrix;

/**
 * Класс для работы с квадратными матрицами.
 * <p>
 * Квадратная матрица — это матрица, у которой количество строк равно количеству столбцов.
 * Этот класс предоставляет операции, такие как транспонирование, умножение, возведение в степень.
 */
public class SquareMatrix extends AbstractMatrix<SquareMatrix> {

    // Конструкторы

    /**
     * Конструктор квадратной матрицы с элементами из одномерного массива.
     *
     * @param n    количество строк и столбцов.
     * @param base тело матрицы (одномерный массив).
     */
    public SquareMatrix(int n, double[] base) {
        super(n, n, base);
    }

    /**
     * Конструктор квадратной матрицы с элементами из двумерного массива.
     *
     * @param n    количество строк и столбцов.
     * @param base тело матрицы (двумерный массив).
     */
    public SquareMatrix(int n, double[][] base) {
        super(n, n, base);
    }

    /**
     * Конструктор нулевой квадратной матрицы.
     *
     * @param n количество строк и столбцов.
     */
    public SquareMatrix(int n) {
        super(n, n);
    }

    /**
     * Конструктор единичной квадратной матрицы.
     *
     * @param n    количество строк и столбцов.
     * @param unit если true, создается единичная матрица.
     */
    public SquareMatrix(int n, boolean unit) {
        super(n, n, unit);
    }

    /**
     * Конструктор квадратной матрицы на основе общей матрицы.
     *
     * @param matrix общая матрица.
     * @throws IllegalArgumentException если переданная матрица не квадратная.
     */
    protected SquareMatrix(Matrix matrix) {
        super(matrix.getRows(), matrix.getCols(), matrix.getBase());
        validateSquare(matrix.getRows(), matrix.getCols());
    }

    // Операции с квадратной матрицей

    /**
     * Транспонирует текущую матрицу.
     */
    public void transpose() {
        setMatrix(getMatrix().transposed());
    }

    /**
     * Создает транспонированную копию текущей матрицы.
     *
     * @return новая транспонированная квадратная матрица.
     */
    public SquareMatrix transposed() {
        return newMatrix(getMatrix().transposed());
    }

    /**
     * Возводит текущую матрицу в степень.
     *
     * @param n степень.
     */
    public void pow(int n) {
        setMatrix(getMatrix().pows(n));
    }

    /**
     * Создает новую матрицу, возведенную в заданную степень.
     *
     * @param n степень.
     * @return новая квадратная матрица.
     */
    public SquareMatrix pows(int n) {
        return newMatrix(getMatrix().pows(n));
    }

    /**
     * Умножает текущую матрицу на другую квадратную матрицу.
     *
     * @param matrix матрица-множитель.
     */
    public void multiply(SquareMatrix matrix) {
        setMatrix(getMatrix().multiplied(matrix.getMatrix()));
    }

    /**
     * Создает новую матрицу как результат умножения текущей на другую квадратную матрицу.
     *
     * @param matrix матрица-множитель.
     * @return новая квадратная матрица.
     */
    public SquareMatrix multiplied(SquareMatrix matrix) {
        return newMatrix(getMatrix().multiplied(matrix.getMatrix()));
    }

    /**
     * Умножает текущую матрицу на вектор-столбец.
     *
     * @param vector вектор-множитель.
     * @return результат в виде нового вектора-столбца.
     */
    public VectorC multiplied(VectorC vector) {
        Matrix result = getMatrix().multiplied(new Matrix(vector.getRows(), vector.getCols(), vector.getBase()));
        return new VectorC(result);
    }

    /**
     * Умножает текущую матрицу на прямоугольную матрицу.
     *
     * @param matrix матрица-множитель.
     * @return новая прямоугольная матрица.
     */
    public RecMatrix multiplied(RecMatrix matrix) {
        Matrix result = getMatrix().multiplied(new Matrix(matrix.getRows(), matrix.getCols(), matrix.getBase()));
        return new RecMatrix(result);
    }

    // Проверка корректности размеров

    /**
     * Проверяет, что матрица квадратная.
     *
     * @param row количество строк.
     * @param col количество столбцов.
     * @throws IllegalArgumentException если матрица не квадратная или является вектором.
     */
    private void validateSquare(int row, int col) {
        if (row != col) {
            throw new IllegalArgumentException("Матрица должна быть квадратной (количество строк должно совпадать с количеством столбцов).");
        }
        if (row == 1) {
            throw new IllegalArgumentException("Квадратная матрица не может быть вектором. Проверьте размеры (должны быть больше 1x1).");
        }
    }

    // Фабричный метод

    /**
     * Создает новую квадратную матрицу на основе базовой матрицы.
     *
     * @param matrix базовая матрица.
     * @return новая квадратная матрица.
     */
    @Override
    protected SquareMatrix newMatrix(Matrix matrix) {
        return new SquareMatrix(matrix);
    }

    // TODO: Добавить реализацию вычисления определителя, обратной матрицы и других полезных методов.
}

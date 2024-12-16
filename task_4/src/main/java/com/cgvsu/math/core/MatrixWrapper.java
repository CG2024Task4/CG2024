package com.cgvsu.math.core;


import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.types.VectorC;

/**
 * Класс обертка для квадратных матриц заданного размера.
 * <p>
 * Этот класс предоставляет методы для выполнения различных операций над квадратными матрицами,
 * таких как сложение, вычитание, умножение, транспонирование и возведение в степень.
 * Абстрактный метод `newMatrix` позволяет создать новые экземпляры матрицы
 * через наследников, обеспечивая поддержку конкретных реализаций.
 *
 * @param <T> конкретный класс квадратной матрицы с заданным размером.
 * @param <E> конкретный класс вектора-столбца с заданным размером.
 */
public abstract class MatrixWrapper<T extends MatrixWrapper<T, E>, E extends VectorWrapperC<E>> {
    /**
     * Квадратная матрица
     */
    SquareMatrix matrix;


    /**
     * Конструктор квадратной матрицы из одномерного массива значений.
     *
     * @param n    количество строк и столбцов.
     * @param base одномерный массив, содержащий элементы матрицы.
     */
    public MatrixWrapper(int n, double[] base) {
        matrix = new SquareMatrix(n, base);
    }

    /**
     * Конструктор квадратной матрицы из двумерного массива значений.
     *
     * @param n    количество строк и столбцов.
     * @param base двумерный массив, содержащий элементы матрицы.
     */
    public MatrixWrapper(int n, double[][] base) {
        matrix = new SquareMatrix(n, base);
    }

    /**
     * Конструктор для создания нулевой квадратной матрицы.
     *
     * @param n количество строк и столбцов.
     */
    public MatrixWrapper(int n) {
        matrix = new SquareMatrix(n);
    }

    /**
     * Конструктор для создания единичной квадратной матрицы.
     *
     * @param n    количество строк и столбцов.
     * @param unit флаг, определяющий создание единичной матрицы (true) или нулевой (false).
     */
    public MatrixWrapper(int n, boolean unit) {
        matrix = new SquareMatrix(n, unit);
    }
    //Геттеры сеттеры

    /**
     * Получить значения матрицы
     *
     * @return одномерный массив со всеми значениями матрицы
     */
    public double[] getBase() {
        return matrix.getBase();
    }


    /**
     * Получить количество строк
     *
     * @return значение
     */
    public int getRows() {
        return matrix.getRows();
    }

    /**
     * Получить количество столбцов
     *
     * @return значение
     */
    public int getCols() {
        return matrix.getCols();
    }

    /**
     * Получит тело матрицы
     *
     * @return матрица - базовый объект
     */
    protected SquareMatrix getMatrix() {
        return matrix;
    }

    /**
     * Изменить тело матрицы
     */
    protected void setMatrix(SquareMatrix matrix) {
        this.matrix = matrix;
    }

    /**
     * Получить элементы по индексу
     *
     * @param row строка
     * @param col столбец
     * @return значение
     */
    public double get(int row, int col) {
        return matrix.get(row, col);
    }


    /**
     * Изменить значение по индексу
     *
     * @param row   строка
     * @param col   столбец
     * @param value значение
     */
    public void set(int row, int col, double value) {
        matrix.set(row, col, value);
    }

    /**
     * Вывод матрицы
     */
    public void print() {
        matrix.print();
    }


    /**
     * Вспомогательный метод
     *
     * @param matrix квадратная матрица
     * @return матрицу с ограниченным значением
     */
    protected abstract T newMatrix(SquareMatrix matrix);

    /**
     * Сложение матриц
     *
     * @param other слагаемое
     */
    public void add(T other) {
        setMatrix(matrix.added(other.getMatrix()));
    }

    /**
     * Сложение текущей матрицы с другой матрицей.
     *
     * @param other другая квадратная матрица.
     * @return новая матрица, содержащая результат сложения.
     */
    public T added(T other) {
        return newMatrix(matrix.added(other.getMatrix()));
    }

    /**
     * Вычитание матриц
     *
     * @param other вычитаемое
     */
    public void subtract(T other) {
        setMatrix(matrix.subtracted(other.getMatrix()));
    }

    /**
     * Вычитание другой матрицы из текущей.
     *
     * @param other другая квадратная матрица.
     * @return новая матрица, содержащая результат вычитания.
     */
    public T subtracted(T other) {
        return newMatrix(matrix.subtracted(other.getMatrix()));
    }

    /**
     * Умножение матрицы на число
     *
     * @param number множитель
     */
    public void multiply(double number) {
        setMatrix(matrix.multiplied(number));
    }

    /**
     * Умножение текущей матрицы на число.
     *
     * @param number множитель.
     * @return новая матрица, содержащая результат умножения.
     */
    public T multiplied(double number) {
        return newMatrix(matrix.multiplied(number));
    }

    /**
     * Деление матрицы на число
     *
     * @param number делитель
     */
    public void divide(double number) {
        setMatrix(matrix.divided(number));
    }

    /**
     * Деление матрицы на число
     *
     * @param number делитель
     * @return новая матрица - результат
     */
    public T divided(double number) {
        return newMatrix(matrix.divided(number));
    }

    /**
     * Вывод в консоль объекта
     *
     * @return текст
     */
    @Override
    public String toString() {
        return matrix.toString();
    }

    /**
     * Транспонирование квадратной матрицы
     */
    public void transpose() {
        setMatrix(getMatrix().transposed());
    }

    /**
     * Транспонирование текущей матрицы.
     *
     * @return новая матрица, которая является транспонированной версией текущей.
     */
    public T transposed() {
        return newMatrix(getMatrix().transposed());
    }

    /**
     * Возведение матрицы в целую степень.
     *
     * @param n степень, в которую будет возведена матрица.
     * @return новая матрица, содержащая результат операции.
     */
    public T pows(int n) {
        return newMatrix(getMatrix().pows(n));
    }

    /**
     * Возведение матрицы в степень
     *
     * @param n степень
     */

    public void pow(int n) {
        setMatrix(getMatrix().pows(n));
    }

    /**
     * Умножение квадратной матрицы
     *
     * @param matrix матрица-множитель
     */
    public void multiply(T matrix) {
        setMatrix(getMatrix().multiplied(matrix.getMatrix()));
    }

    /**
     * Умножение текущей матрицы на другую матрицу.
     *
     * @param matrix матрица-множитель.
     * @return новая матрица, содержащая результат умножения.
     */
    public T multiplied(T matrix) {
        return newMatrix(getMatrix().multiplied(matrix.getMatrix()));
    }

    /**
     * Умножение квадратной матрицы на вектор-столбец
     *
     * @param vector вектор-множитель
     * @return вектор-столбец
     */
    public E multiplied(E vector) {
        VectorC result = matrix.multiplied(new VectorC(vector.getRows(), vector.getBase()));
        return vector.newMatrix(result);
    }
}

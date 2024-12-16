/**
 * * Пакет абстракций для библиотеки Matrix
 */
package com.cgvsu.math.core;

import java.util.Arrays;
import java.util.Objects;

/**
 * Класс обертка для основных типов ->Прямоугольная, Квадратная, Вектор-строка, Вектор-столбец
 *
 * @param <T>
 */
public abstract class AbstractMatrix<T extends AbstractMatrix<T>> {

    /**
     * Основной объект, содержащий реализацию логики работы с матрицами.
     */
    protected Matrix matrix;

    /**
     * Базовый абстрактный класс для работы с матрицами различных типов
     * (прямоугольная, квадратная, вектор-строка, вектор-столбец).
     * <p></p>
     * Конструкторы позволяют создавать матрицы различных типов: нулевые, единичные,
     * с использованием одномерных и двумерных массивов.
     *
     * @param row количество строк.
     * @param col количество столбцов.
     * @throws IllegalArgumentException если количество строк или столбцов меньше 1.
     */
    public AbstractMatrix(int row, int col) {
        matrix = new Matrix(row, col);
    }

    /**
     * Конструктор абстрактной матрицы
     *
     * @param row  строки
     * @param col  столбцы
     * @param base одномерный массив - тело матрицы
     */
    public AbstractMatrix(int row, int col, double[] base) {
        matrix = new Matrix(row, col, base);
    }

    /**
     * Конструктор абстрактной матрицы
     *
     * @param row  строки
     * @param col  столбцы
     * @param base двумерный массив - тело матрицы
     */
    public AbstractMatrix(int row, int col, double[][] base) {
        matrix = new Matrix(row, col, base);
    }

    /**
     * Конструктор единичной матрицы
     *
     * @param row  строки
     * @param col  столбцы
     * @param unit flag
     */
    public AbstractMatrix(int row, int col, boolean unit) {
        matrix = new Matrix(row, col, unit);
    }

    /**
     * Вложенный класс для реализации базовой логики работы с матрицами.
     * Обеспечивает основные операции, такие как сложение, умножение, транспонирование,
     * а также вспомогательные функции (нормализация, возведение в степень и др.).
     * <p>Матрица хранится в виде одномерного массива для оптимизации операций
     * с памятью и вычислений.</p>
     */
    protected static class Matrix {
        /**
         * Строки
         */
        private int rows;
        /**
         * Столбцы
         */
        private int cols;
        /**
         * Значения в матрице
         */
        private double[] base;


        /**
         * Метод инициализации параметров матрицы
         *
         * @param rows строки
         * @param cols столбцы
         * @param base значения
         */
        public void initialize(int rows, int cols, double[] base) {
            setRows(rows);
            setCols(cols);
            setBase(base);
        }

        /**
         * Основной конструктор Матрицы N*M
         *
         * @param rows количество строк
         * @param cols количество столбцов
         * @param base тело матрицы
         */
        public Matrix(int rows, int cols, double[] base) {
            initialize(rows, cols, base);

        }
        //Что-то сгенерировала IDEA

        /**
         * Сравнение полей двух матриц
         *
         * @param o сравниваемый объект
         * @return true or false
         */

        @Override
        public boolean equals(Object o) {
            if (this == o) return true; // Проверка на одинаковые ссылки
            if (!(o instanceof Matrix matrix)) return false; // Проверка, что объект — это Matrix (или его наследник)

            // Сравниваем размеры матриц
            if (rows != matrix.rows || cols != matrix.cols) {
                return false;
            }

            // Сравниваем элементы массива base
            return Arrays.equals(this.base, matrix.base);
        }


        /**
         * HASH-код
         *
         * @return значение
         */
        @Override
        public int hashCode() {
            return Objects.hash(rows, cols, Arrays.hashCode(base));
        }

        /**
         * Красивый вывод матрицы
         *
         * @return Текст
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("[\n");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    sb.append(String.format("%10.2f ", get(i, j)));
                }
                sb.append("\n");
            }
            sb.append("]");
            return sb.toString();
        }

        /**
         * Основной конструктор Матрицы N*M
         *
         * @param rows количество строк
         * @param cols количество столбцов
         * @param base тело матрицы (двумерный массив)
         */
        public Matrix(int rows, int cols, double[][] base) {
            initialize(rows, cols, unwrap(base));
        }

        /**
         * Конструктор нулевой матрицы
         *
         * @param rows количество строк
         * @param cols количество столбцов
         */
        public Matrix(int rows, int cols) { //Нулевая матрица
            initialize(rows, cols, new double[rows * cols]);
        }

        /**
         * Конструктор единичной матрицы
         *
         * @param rows количество строк
         * @param cols количество столбцов
         */
        public Matrix(int rows, int cols, boolean unit) {//Единичная матрица
            this(rows, cols);
            if (unit) {
                this.setBase(this.createUnitMatrix().getBase());
            }
        }

        /*
        Геттеры и сеттеры
         */

        /**
         * Получение элемента матрицы по индексам
         *
         * @param row строка
         * @param col столбец
         * @return значение
         */
        public double get(int row, int col) {
            validateIndex(row, col);
            return base[row * cols + col];
        }

        /**
         * Изменение элемента матрицы по индексам
         *
         * @param row   строка
         * @param col   столбец
         * @param value значение
         */

        public void set(int row, int col, double value) {
            validateIndex(row, col);
            base[row * cols + col] = value;
        }

        /**
         * Метод проверки корректности индексов
         *
         * @param row строка
         * @param col столбец
         */
        private void validateIndex(int row, int col) {
            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw new IndexOutOfBoundsException("Invalid index");
            }
        }

        /**
         * Получить количество строк для данной матрицы
         *
         * @return количество строк [>0]
         */
        public int getRows() {
            return rows;
        }

        /**
         * Получить количество столбцов для данной матрицы
         *
         * @return количество столбцов [>0]
         */
        public int getCols() {
            return cols;
        }

        /**
         * Получить тело матрицы
         *
         * @return double[] тело матрицы
         */
        public double[] getBase() {
            return base.clone();
        }

        /**
         * Установить значение строк в матрице
         *
         * @param rows количество строк [>0]
         */
        private void setRows(int rows) {
            if (rows <= 0) { //Обработка некорректных значений
                throw new IllegalArgumentException("Incorrect arguments for rows");
            }
            this.rows = rows;
        }

        /**
         * Установить значение столбцов в матрице
         *
         * @param cols количество столбцов [>0]
         */
        private void setCols(int cols) {
            if (cols <= 0) {
                throw new IllegalArgumentException("Incorrect arguments for cols");
            }
            this.cols = cols;
        }

        /**
         * Изменение тела матрицы (новый массив)
         *
         * @param base новый массив
         */
        private void setBase(double[] base) {
            if (base.length != (rows * cols)) {
                throw new IllegalArgumentException("Incorrect array size in " + this.getClass().getSimpleName());
            }
            this.base = base.clone();
        }

        /**
         * Вывод матрицы в консоль в виде <p>
         * <p>[A11, A12,<p>
         * A21, A22]
         */
        public void print() {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    System.out.printf("%10.2f ", base[i * cols + j]);
                }
                System.out.println();
            }
            System.out.printf("Rows: %d, Cols: %d\n", rows, cols);
        }

        /**
         * Метод для транспонирования матрицы
         *
         * @return транспонированная матрица
         */
        public Matrix transposed() {
            double[] transposed = new double[getCols() * getRows()];
            for (int i = 0; i < getRows(); i++) {
                for (int j = 0; j < getCols(); j++) {
                    transposed[j * getRows() + i] = get(i, j);
                }
            }
            return new Matrix(getCols(), getRows(), transposed);
        }

        /**
         * Умножение текущей матрицы на другую матрицу.
         *
         * @param matrix матрица-множитель.
         *               Количество столбцов текущей матрицы должно быть равно
         *               количеству строк матрицы-множителя.
         * @return новая матрица, являющаяся результатом умножения.
         * @throws IllegalArgumentException если размеры матриц не соответствуют
         *                                  условию умножения.
         *                                  <p>
         *                                  Пример:
         *                                  <pre>{@code
         *                                                                                                                                                                                                                                                                                                                                           Matrix m1 = new Matrix(2, 3, new double[] {1, 2, 3, 4, 5, 6});
         *                                                                                                                                                                                                                                                                                                                                           Matrix m2 = new Matrix(3, 2, new double[] {7, 8, 9, 10, 11, 12});
         *                                                                                                                                                                                                                                                                                                                                           Matrix result = m1.multiplied(m2);
         *                                                                                                                                                                                                                                                                                                                                           }</pre>
         */
        public Matrix multiplied(Matrix matrix) {
            if (getCols() != matrix.getRows()) {
                throw new IllegalArgumentException("Количество столбцов первой матрицы должно совпадать с количеством строк второй.");
            }
            double[] result = new double[getRows() * matrix.getCols()];
            for (int i = 0; i < getRows(); i++) {
                for (int j = 0; j < matrix.getCols(); j++) {
                    double sum = 0;
                    for (int k = 0; k < getCols(); k++) {
                        sum += get(i, k) * matrix.get(k, j);
                    }
                    result[i * matrix.getCols() + j] = sum;
                }
            }
            return new Matrix(getRows(), matrix.getCols(), result);
        }

        /**
         * Умножение матрицы на число
         *
         * @param number множитель
         * @return результат умножения - матрица
         */
        public Matrix multiplied(double number) {
            double[] newMatrix = new double[getRows() * getCols()];
            for (int i = 0; i < getBase().length; i++) {
                newMatrix[i] = getBase()[i] * number;
            }
            return new Matrix(getRows(), getCols(), newMatrix);
        }

        /**
         * Сложение матриц
         *
         * @param matrix слагаемое
         * @return результат сложения
         */
        public Matrix added(Matrix matrix) {
            if (getRows() != matrix.getRows() || getCols() != matrix.getCols()) {
                throw new IllegalArgumentException("Матрицы должны быть одинакового размера для сложения.");
            }
            double[] result = new double[getRows() * getCols()];
            for (int i = 0; i < getBase().length; i++) {
                result[i] = getBase()[i] + matrix.getBase()[i];
            }
            return new Matrix(getRows(), getCols(), result);
        }

        /**
         * Вычитание матриц
         *
         * @param matrix вычитаемое
         * @return результат вычитания - матрица
         */
        public Matrix subtracted(Matrix matrix) {
            return added(matrix.multiplied(-1));
        }

        /**
         * Деление матрицы на число
         *
         * @param number делитель
         * @return результат матрица
         */
        public Matrix divided(double number) {
            if (number == 0) {
                throw new IllegalArgumentException("Деление на ноль недопустимо.");
            }
            return multiplied(1 / number);
        }

        /**
         * Скалярное произведение векторов.
         *
         * @param other другой вектор (должен быть того же размера).
         * @return число - результат скалярного произведения.
         * @throws IllegalArgumentException если размеры векторов не совпадают.
         */
        public double dot(Matrix other) {
            if (this.getRows() != other.getRows() || this.getCols() != other.getCols()) {
                throw new IllegalArgumentException("Размеры векторов должны совпадать для вычисления скалярного произведения.");
            }

            double result = 0;
            for (int i = 0; i < this.getBase().length; i++) {
                result += this.getBase()[i] * other.getBase()[i];
            }

            return result;
        }


        /**
         * Создание единичной матрицы на основе текущей
         *
         * @return единичная матрица
         */
        public Matrix createUnitMatrix() {
            if (this.getRows() != this.getCols()) {
                throw new IllegalArgumentException("The number of rows and columns does not match");
            }
            double[] unitMatrix = new double[getRows() * getCols()];
            for (int i = 0; i < getCols(); i++) {
                unitMatrix[i * getRows() + i] = 1;
            }
            return new Matrix(getRows(), getCols(), unitMatrix);
        }

        /**
         * Приватный метод для преобразования двумерного массива в одномерный
         *
         * @param base двумерный массив
         * @return одномерный массив
         */
        private static double[] unwrap(double[][] base) {
            double[] result = new double[base.length * base[0].length];
            int i = 0;
            for (double[] doubles : base) {
                for (double aDouble : doubles) {
                    result[i] = aDouble;
                    i++;
                }
            }
            return result;
        }

        /**
         * Возведение квадратной матрицы в степень.
         * Использует алгоритм быстрого возведения в степень.
         *
         * @param n степень, в которую нужно возвести матрицу.
         *          Должна быть неотрицательной.
         * @return матрица, возведённая в степень.
         * @throws IllegalArgumentException если степень отрицательная.
         * @throws IllegalArgumentException если матрица не является квадратной.
         *                                  <p>
         *                                  Пример:
         *                                  <pre>{@code
         *                                           Matrix m = new Matrix(2, 2, new double[] {2, 0, 0, 2});
         *                                           Matrix result = m.pows(3); // Результат: [8, 0; 0, 8]
         *                                           }</pre>
         *                                  </p>
         */
        public Matrix pows(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Степень не может быть отрицательной.");
            }
            if (n == 0) {
                return createUnitMatrix(); // A^0 = I (единичная матрица)
            }
            if (n == 1) {
                return this; // A^1 = A
            }

            Matrix result = createUnitMatrix();
            Matrix base = new Matrix(this.getRows(), this.getCols(), this.getBase());

            while (n > 0) {
                if (n % 2 == 1) { // Если степень нечётная
                    result = result.multiplied(base);
                }
                base = base.multiplied(base); // Квадрат матрицы
                n /= 2;
            }
            return result;
        }


        /**
         * Метод вычисления длинны вектора
         *
         * @return число
         */
        public double getLength() {
            double[] vector = getBase();
            double sum = 0;
            for (double v : vector) {
                sum += v * v;
            }
            return Math.sqrt(sum);
        }

        public Matrix crossProduct(Matrix other) {
            if (getRows() != 3 || other.getRows() != 3) {
                throw new IllegalArgumentException("Векторное произведение определено только для векторов размерности 3");
            }
            double[] a = getBase();
            double[] b = other.getBase();

            double[] result = new double[3];
            result[0] = a[1] * b[2] - a[2] * b[1];
            result[1] = a[2] * b[0] - a[0] * b[2];
            result[2] = a[0] * b[1] - a[1] * b[0];

            return new Matrix(getRows(), getCols(), result);
        }


        /**
         * Нормализация вектора (приведение к единичной длине).
         * Доступно только для векторов.
         *
         * @return нормализованный вектор.
         * @throws ArithmeticException если вектор является нулевым (длина равна 0).
         */
        public Matrix normalize() {
            double length = getLength();
            if (length == 0) {
                throw new ArithmeticException("Невозможно нормализовать нулевой вектор.");
            }
            double[] vector = getBase();
            double[] normalized = new double[vector.length];
            for (int i = 0; i < vector.length; i++) {
                normalized[i] = vector[i] / length;
            }
            return new Matrix(getRows(), getCols(), normalized);
        }

    }

    //Геттеры сеттеры


    /**
     * Значения матрицы в виде одномерного массива
     *
     * @return одномерный массив значений матрицы
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
    protected Matrix getMatrix() {
        return matrix;
    }

    /**
     * Изменить тело матрицы
     */
    protected void setMatrix(Matrix matrix) {
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
     * Метод -> вспомогательный
     *
     * @param matrix основная матрица
     * @return конкретный объект матрицу
     */
    protected abstract T newMatrix(Matrix matrix);

    /**
     * Сложение матриц
     *
     * @param other слагаемое
     */
    public void add(T other) {
        setMatrix(matrix.added(other.getMatrix()));
    }

    /**
     * Сложение матриц
     *
     * @param other слагаемое
     * @return новая матрица - результат
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
     * Вычитание матриц
     *
     * @param other вычитаемое
     * @return новая матрица - результат
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
     * Умножение матрицы на число
     *
     * @param number множитель
     * @return новая матрица - результат
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
     * Сравнение матриц
     *
     * @param o с кем сравниваем
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMatrix<?> that = (AbstractMatrix<?>) o;
        return Objects.equals(matrix, that.matrix);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(matrix);
    }
}

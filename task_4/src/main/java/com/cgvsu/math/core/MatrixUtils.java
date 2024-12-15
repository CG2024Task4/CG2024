package com.cgvsu.math.core;

/**
 * Класс MatrixUtils предоставляет методы для работы с векторами и матрицами.
 * Методы реализуют операции, такие как скалярное произведение векторов, сложение векторов и умножение матриц.
 */
public class MatrixUtils {

    /**
     * Вычисляет скалярное произведение двух векторов на основе их длин и угла между ними.
     *
     * @param v1    Первый вектор (объект, реализующий интерфейс VectorWrapperC).
     * @param v2    Второй вектор (объект, реализующий интерфейс VectorWrapperC).
     * @param angle Угол между векторами в радианах.
     * @param <T>   Тип вектора, должен быть подклассом VectorWrapperC.
     * @return Скалярное произведение двух векторов.
     */
    public static <T extends VectorWrapperC<T>> double dotProduct(T v1, T v2, double angle) {
        return v1.getLength() * v2.getLength() * Math.cos(angle);
    }

    /**
     * Складывает несколько векторов и возвращает результирующий вектор.
     * Вектор, переданный первым, будет модифицирован, чтобы содержать сумму всех векторов.
     *
     * @param vectors Массив векторов для сложения.
     * @param <T>     Тип вектора, должен быть подклассом VectorWrapperC.
     * @return Результат сложения всех векторов.
     */
    @SafeVarargs
    public static <T extends VectorWrapperC<T>> T add(T... vectors) {
        T vector = vectors[0];
        for (int i = 1; i < vectors.length; i++) {
            vector.add(vectors[i]);
        }
        return vector;
    }

    /**
     * Выполняет последовательное умножение нескольких матриц.
     * Матрица, переданная первой, будет модифицирована, чтобы содержать результат умножения всех матриц.
     *
     * @param matrix Массив матриц для умножения.
     * @param <T>    Тип матрицы, должен быть подклассом MatrixWrapper.
     * @return Результат умножения всех матриц.
     */
    @SafeVarargs
    public static <T extends MatrixWrapper<T, ?>> T multiplied(T... matrix) {
        T result = matrix[0];
        for (int i = 1; i < matrix.length; i++) {
            result.multiply(matrix[i]);
        }
        return result;
    }
}

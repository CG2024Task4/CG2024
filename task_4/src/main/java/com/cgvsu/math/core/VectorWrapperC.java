package com.cgvsu.math.core;


import com.cgvsu.math.types.VectorC;

import java.util.Objects;

/**
 * Абстрактный класс-обертка для вектора-столбца фиксированного размера.
 * <p>
 * Этот класс предоставляет методы для выполнения основных операций над векторами-столбцами, таких, как сложение, вычитание,
 * умножение на число, нормализация и вычисление длины. Абстрактный метод `newMatrix` позволяет наследникам создавать
 * новые экземпляры конкретного типа вектора-столбца.
 *
 * @param <T> конкретный класс, реализующий вектор-столбец.
 */
public abstract class VectorWrapperC<T extends VectorWrapperC<T>> {
    /**
     * Вектор-столбец.
     */
    private VectorC vector;

    // Конструкторы

    /**
     * Конструктор для создания нулевого вектора-столбца заданной размерности.
     *
     * @param n количество элементов вектора.
     */
    public VectorWrapperC(int n) {
        vector = new VectorC(n);
    }

    /**
     * Конструктор для создания вектора-столбца с заданными элементами.
     *
     * @param n    количество элементов.
     * @param base массив элементов вектора.
     */
    public VectorWrapperC(int n, double[] base) {
        vector = new VectorC(n, base);
    }
    // Геттеры и сеттеры

    /**
     * Получить массив элементов вектора.
     *
     * @return массив элементов.
     */
    public double[] getBase() {
        return vector.getBase();
    }

    /**
     * Получить количество строк в векторе.
     *
     * @return количество строк.
     */
    public int getRows() {
        return vector.getRows();
    }

    /**
     * Получить количество столбцов в векторе.
     *
     * @return количество столбцов (всегда 1 для вектора-столбца).
     */
    public int getCols() {
        return vector.getCols();
    }

    /**
     * Получить внутренний объект вектора-столбца.
     *
     * @return внутренний объект `VectorC`.
     */
    protected VectorC getVector() {
        return vector;
    }

    /**
     * Задать внутренний объект вектора-столбца.
     *
     * @param vector объект `VectorC`.
     */
    protected void setVector(VectorC vector) {
        this.vector = vector;
    }

    // Основные операции

    /**
     * Получить элемент вектора по индексу.
     *
     * @param row индекс элемента.
     * @return значение элемента.
     */
    public double get(int row) {
        return vector.get(row, 0);
    }

    /**
     * Установить элемент вектора по индексу.
     *
     * @param row   индекс строки.
     * @param value новое значение.
     */
    public void set(int row, double value) {
        vector.set(row, 0, value);
    }

    /**
     * Создать новый экземпляр конкретного вектора-столбца.
     *
     * @param vector внутренний объект `VectorC`.
     * @return новый экземпляр конкретного типа вектора.
     */
    public abstract T newMatrix(VectorC vector);

    // Арифметические операции

    /**
     * Сложить текущий вектор с другим.
     *
     * @param other вектор-столбец для сложения.
     */
    public void add(T other) {
        setVector(vector.added(other.getVector()));
    }

    /**
     * Создать новый вектор как сумму текущего и другого.
     *
     * @param other вектор-столбец для сложения.
     * @return новый вектор-столбец.
     */
    public T added(T other) {
        return newMatrix(vector.added(other.getVector()));
    }

    /**
     * Вычесть другой вектор из текущего.
     *
     * @param other вектор-столбец для вычитания.
     */
    public void subtract(T other) {
        setVector(vector.subtracted(other.getVector()));
    }

    /**
     * Создать новый вектор как разность текущего и другого.
     *
     * @param other вектор-столбец для вычитания.
     * @return новый вектор-столбец.
     */
    public T subtracted(T other) {
        return newMatrix(vector.subtracted(other.getVector()));
    }

    /**
     * Умножить текущий вектор на число.
     *
     * @param number множитель.
     */
    public void multiply(double number) {
        setVector(vector.multiplied(number));
    }

    /**
     * Создать новый вектор как результат умножения текущего на число.
     *
     * @param number множитель.
     * @return новый вектор-столбец.
     */
    public T multiplied(double number) {
        return newMatrix(vector.multiplied(number));
    }

    /**
     * Разделить текущий вектор на число.
     *
     * @param number делитель.
     */
    public void divide(double number) {
        setVector(vector.divided(number));
    }

    /**
     * Создать новый вектор как результат деления текущего на число.
     *
     * @param number делитель.
     * @return новый вектор-столбец.
     */
    public T divided(double number) {
        return newMatrix(vector.divided(number));
    }

    // Дополнительные операции

    /**
     * Вычислить длину вектора.
     *
     * @return длина вектора.
     */
    public double getLength() {
        return vector.getLength();
    }

    /**
     * Нормализовать текущий вектор.
     *
     * @return новый нормализованный вектор-столбец.
     */
    public T normalize() {
        return newMatrix(vector.normalize());
    }

    /**
     * Вычислить векторное произведение текущего вектора с другим.
     *
     * @param other другой вектор-столбец.
     * @return новый вектор-столбец, являющийся результатом операции.
     */
    public T crossProduct(T other) {
        return newMatrix(vector.crossProduct(other.getVector()));
    }

    // Прочее

    /**
     * Печать текущего вектора в консоль.
     */
    public void print() {
        vector.print();
    }

    /**
     * Представление текущего вектора в виде строки.
     *
     * @return строковое представление вектора.
     */
    @Override
    public String toString() {
        return vector.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorWrapperC<?> that = (VectorWrapperC<?>) o;
        return Objects.equals(vector, that.vector);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vector);
    }

    public double dotProduct(T other) {
        return vector.dotProduct(other.getVector());
    }
}

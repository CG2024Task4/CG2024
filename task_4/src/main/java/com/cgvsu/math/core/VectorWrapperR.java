package com.cgvsu.math.core;


import com.cgvsu.math.types.VectorR;

/**
 * Абстрактный класс-обертка для вектора-строки фиксированного размера.
 * <p>
 * Этот класс предоставляет методы для выполнения операций над векторами-строками, включая сложение, вычитание,
 * умножение на число, нормализацию и вычисление длины. Абстрактный метод `newMatrix` позволяет наследникам создавать
 * экземпляры конкретного типа вектора-строки.
 *
 * @param <T> конкретный класс, реализующий вектор-строку.
 */
public abstract class VectorWrapperR<T extends VectorWrapperR<T>> {
    /**
     * Вектор-строка.
     */
    private VectorR vector;

    // Конструкторы

    /**
     * Конструктор для создания нулевого вектора-строки заданной размерности.
     *
     * @param n количество элементов вектора.
     */
    public VectorWrapperR(int n) {
        vector = new VectorR(n);
    }

    /**
     * Конструктор для создания вектора-строки с заданными элементами.
     *
     * @param n    количество элементов.
     * @param base массив элементов вектора.
     */
    public VectorWrapperR(int n, double[] base) {
        vector = new VectorR(n, base);
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
     * @return количество строк (всегда 1 для вектора-строки).
     */
    public int getRows() {
        return vector.getRows();
    }

    /**
     * Получить количество столбцов в векторе.
     *
     * @return количество столбцов.
     */
    public int getCols() {
        return vector.getCols();
    }

    /**
     * Получить внутренний объект вектора-строки.
     *
     * @return внутренний объект `VectorR`.
     */
    protected VectorR getVector() {
        return vector;
    }

    /**
     * Установить внутренний объект вектора-строки.
     *
     * @param vector объект `VectorR`.
     */
    protected void setVector(VectorR vector) {
        this.vector = vector;
    }

    // Основные операции

    /**
     * Получить элемент вектора по индексу.
     *
     * @param row индекс строки (всегда 0 для вектора-строки).
     * @param col индекс столбца.
     * @return значение элемента.
     */
    public double get(int row, int col) {
        return vector.get(row, col);
    }

    /**
     * Установить элемент вектора по индексу.
     *
     * @param row   индекс строки (всегда 0 для вектора-строки).
     * @param col   индекс столбца.
     * @param value новое значение.
     */
    public void set(int row, int col, double value) {
        vector.set(row, col, value);
    }

    /**
     * Создать новый экземпляр конкретного вектора-строки.
     *
     * @param vector внутренний объект `VectorR`.
     * @return новый экземпляр конкретного типа вектора.
     */
    public abstract T newMatrix(VectorR vector);

    // Арифметические операции

    /**
     * Сложить текущий вектор с другим.
     *
     * @param other вектор-строка для сложения.
     */
    public void add(T other) {
        setVector(vector.added(other.getVector()));
    }

    /**
     * Создать новый вектор как сумму текущего и другого.
     *
     * @param other вектор-строка для сложения.
     * @return новый вектор-строка.
     */
    public T added(T other) {
        return newMatrix(vector.added(other.getVector()));
    }

    /**
     * Вычесть другой вектор из текущего.
     *
     * @param other вектор-строка для вычитания.
     */
    public void subtract(T other) {
        setVector(vector.subtracted(other.getVector()));
    }

    /**
     * Создать новый вектор как разность текущего и другого.
     *
     * @param other вектор-строка для вычитания.
     * @return новый вектор-строка.
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
     * @return новый вектор-строка.
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
     * @return новый вектор-строка.
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
     * @return новый нормализованный вектор-строка.
     */
    public T normalize() {
        return newMatrix(vector.normalize());
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
}

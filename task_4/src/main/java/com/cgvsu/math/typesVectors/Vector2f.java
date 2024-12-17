package com.cgvsu.math.typesVectors;


import com.cgvsu.math.core.VectorWrapperC;
import com.cgvsu.math.types.VectorC;

/**
 * Вектор размерности 2 (двумерный).
 * <p>
 * Этот класс представляет собой вектор-столбец с двумя элементами. Он расширяет базовый функционал класса VectorWrapperC.
 */
public class Vector2f extends VectorWrapperC<Vector2f> {

    /**
     * Конструктор для создания нулевого вектора размерности 2.
     */
    public Vector2f() {
        super(2);
    }

    /**
     * Конструктор для создания вектора размерности 2 с заданными значениями.
     *
     * @param base массив значений, представляющий элементы вектора.
     */
    public Vector2f(double[] base) {
        super(2, base);
    }

    /**
     * Создаёт новый вектор-столбец Vector2f на основе другого вектора VectorC.
     *
     * @param vector вектор, на основе которого будет создан новый.
     * @return новый вектор размерности 2.
     */
    @Override
    public Vector2f newMatrix(VectorC vector) {
        return new Vector2f(vector.getBase());
    }

    public Vector2f(double x, double y) {
        super(2, new double[]{x, y});
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }
}

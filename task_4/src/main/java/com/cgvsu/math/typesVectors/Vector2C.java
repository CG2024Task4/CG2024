package com.cgvsu.math.typesVectors;


import com.cgvsu.math.core.VectorWrapperC;
import com.cgvsu.math.types.VectorC;

/**
 * Вектор размерности 2 (двумерный).
 * <p>
 * Этот класс представляет собой вектор-столбец с двумя элементами. Он расширяет базовый функционал класса VectorWrapperC.
 */
public class Vector2C extends VectorWrapperC<Vector2C> {

    /**
     * Конструктор для создания нулевого вектора размерности 2.
     */
    public Vector2C() {
        super(2);
    }

    /**
     * Конструктор для создания вектора размерности 2 с заданными значениями.
     *
     * @param base массив значений, представляющий элементы вектора.
     */
    public Vector2C(double[] base) {
        super(2, base);
    }

    /**
     * Создаёт новый вектор-столбец Vector2C на основе другого вектора VectorC.
     *
     * @param vector вектор, на основе которого будет создан новый.
     * @return новый вектор размерности 2.
     */
    @Override
    public Vector2C newMatrix(VectorC vector) {
        return new Vector2C(vector.getBase());
    }

    public Vector2C(double x, double y) {
        super(2, new double[]{x, y});
    }
}

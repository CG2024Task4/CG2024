package com.cgvsu.math.typesVectors;

import com.cgvsu.math.core.VectorWrapperC;
import com.cgvsu.math.types.VectorC;

/**
 * Вектор размерности 3 (трехмерный).
 * <p>
 * Этот класс представляет собой вектор-столбец с тремя элементами. Он расширяет базовый функционал класса VectorWrapperC.
 */
public class Vector3f extends VectorWrapperC<Vector3f> {

    /**
     * Конструктор для создания нулевого вектора размерности 3.
     * <p>
     * Инициализирует вектор размерностью 3 с нулевыми значениями.
     */
    public Vector3f() {
        super(3);
    }

    /**
     * Конструктор для создания вектора размерности 3 с заданными значениями.
     * <p>
     * Инициализирует вектор размерностью 3 с элементами, переданными в массиве.
     *
     * @param base массив значений, представляющий элементы вектора.
     */
    public Vector3f(double[] base) {
        super(3, base);
    }

    /**
     * Вспомогательный метод для создания нового объекта Vector3f.
     * <p>
     * Этот метод используется для создания нового вектора размерности 3 на основе другого вектора VectorC.
     *
     * @param vector вектор, на основе которого будет создан новый.
     * @return новый вектор размерности 3.
     */
    @Override
    public Vector3f newMatrix(VectorC vector) {
        return new Vector3f(vector.getBase());
    }

    public Vector3f(double x, double y, double z) {
        super(3, new double[]{x, y, z});
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }

    public double getZ() {
        return get(2);
    }
}

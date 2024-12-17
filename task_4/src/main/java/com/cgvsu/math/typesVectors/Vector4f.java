package com.cgvsu.math.typesVectors;


import com.cgvsu.math.core.VectorWrapperC;
import com.cgvsu.math.types.VectorC;

/**
 * Вектор размерности 4 (четырехмерный).
 * <p>
 * Этот класс представляет собой вектор-столбец с четырьмя элементами. Он расширяет базовый функционал класса VectorWrapperC.
 */
public class Vector4f extends VectorWrapperC<Vector4f> {

    /**
     * Конструктор для создания нулевого вектора размерности 4.
     * <p>
     * Инициализирует вектор размерностью 4 с нулевыми значениями.
     */
    public Vector4f() {
        super(4);
    }

    /**
     * Конструктор для создания вектора размерности 4 с заданными значениями.
     * <p>
     * Инициализирует вектор размерностью 4 с элементами, переданными в массиве.
     *
     * @param base массив значений, представляющий элементы вектора.
     */
    public Vector4f(double[] base) {
        super(4, base);
    }

    /**
     * Вспомогательный метод для создания нового объекта Vector4f.
     * <p>
     * Этот метод используется для создания нового вектора размерности 4 на основе другого вектора VectorC.
     *
     * @param vector вектор, на основе которого будет создан новый.
     * @return новый вектор размерности 4.
     */
    @Override
    public Vector4f newMatrix(VectorC vector) {
        return new Vector4f(vector.getBase());
    }

    public Vector4f(double x, double y, double z, double t) {
        super(4, new double[]{x, y, z, t});
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

    public double getW() {
        return get(3);
    }
}

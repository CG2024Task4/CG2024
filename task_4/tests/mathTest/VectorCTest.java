package mathTest;

import com.cgvsu.math.types.VectorC;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VectorCTest {

    @Test
    public void testDivideByScalar() {
        // Вектор-столбец
        double[] data = {2.0, 4.0, 6.0};
        VectorC vector = new VectorC(3, data);

        // Делим вектор на скаляр 2.0
        double[] expectedData = {1.0, 2.0, 3.0};
        VectorC expectedVector = new VectorC(3, expectedData);

        // Проверяем деление
        VectorC result = vector.divided(2.0);
        assertEquals(expectedVector, result);
    }

    @Test
    public void testDivideByZero() {
        // Вектор-столбец
        double[] data = {2.0, 4.0, 6.0};
        VectorC vector = new VectorC(3, data);

        // Проверяем исключение при делении на 0
        assertThrows(IllegalArgumentException.class, () -> vector.divided(0.0));
    }

    @Test
    public void testMultiplyByScalar() {
        // Вектор-столбец
        double[] data = {1.0, 2.0, 3.0};
        VectorC vector = new VectorC(3, data);

        // Умножаем на скаляр 3.0
        double[] expectedData = {3.0, 6.0, 9.0};
        VectorC expectedVector = new VectorC(3, expectedData);

        // Проверяем умножение
        VectorC result = vector.multiplied(3.0);
        assertEquals(expectedVector, result);
    }

    @Test
    public void testGetLength() {
        // Вектор-столбец
        double[] data = {3.0, 4.0};
        VectorC vector = new VectorC(2, data);

        // Длина вектора: sqrt(3^2 + 4^2) = 5.0
        double expectedLength = 5.0;

        // Проверяем длину
        assertEquals(expectedLength, vector.getLength(), 1e-9);
    }

    @Test
    public void testNormalize() {
        // Вектор-столбец
        double[] data = {3.0, 4.0};
        VectorC vector = new VectorC(2, data);

        // Нормализованный вектор: {3/5, 4/5}
        double[] expectedData = {0.6, 0.8};
        VectorC expectedVector = new VectorC(2, expectedData);

        // Проверяем нормализацию
        VectorC result = vector.normalize();
        assertEquals(expectedVector, result);
    }

    @Test
    public void testNormalizeZeroVector() {
        // Вектор-столбец с нулевыми элементами
        double[] data = {0.0, 0.0};
        VectorC vector = new VectorC(2, data);

        // Нормализация нулевого вектора должна вызвать исключение
        assertThrows(ArithmeticException.class, vector::normalize);
    }

    @Test
    public void testCrossProduct() {
        // Векторы 3x1
        double[] data1 = {1.0, 0.0, 0.0};
        double[] data2 = {0.0, 1.0, 0.0};

        VectorC vector1 = new VectorC(3, data1);
        VectorC vector2 = new VectorC(3, data2);

        // Ожидаемый результат векторного произведения
        double[] expectedData = {0.0, 0.0, 1.0};
        VectorC expectedVector = new VectorC(3, expectedData);

        // Проверяем векторное произведение
        VectorC result = vector1.crossProduct(vector2);
        assertEquals(expectedVector, result);
    }

    @Test
    public void testCrossProductDimensionMismatch() {
        // Векторы разных размерностей
        double[] data1 = {1.0, 0.0};
        double[] data2 = {0.0, 1.0, 0.0};

        VectorC vector1 = new VectorC(2, data1);
        VectorC vector2 = new VectorC(3, data2);

        // Проверяем исключение при несовпадении размерностей
        assertThrows(IllegalArgumentException.class, () -> vector1.crossProduct(vector2));
    }

    @Test
    public void testAddition() {
        // Два вектора одинаковой размерности
        double[] data1 = {1.0, 2.0, 3.0};
        double[] data2 = {4.0, 5.0, 6.0};

        VectorC vector1 = new VectorC(3, data1);
        VectorC vector2 = new VectorC(3, data2);

        // Ожидаемый результат
        double[] expectedData = {5.0, 7.0, 9.0};
        VectorC expectedVector = new VectorC(3, expectedData);

        // Выполняем сложение
        VectorC result = vector1.added(vector2);

        // Проверяем результат
        assertEquals(expectedVector, result);
    }

    @Test
    public void testAdditionDimensionMismatch() {
        // Вектора разной размерности
        double[] data1 = {1.0, 2.0};
        double[] data2 = {4.0, 5.0, 6.0};

        VectorC vector1 = new VectorC(2, data1);
        VectorC vector2 = new VectorC(3, data2);

        // Проверяем, что выбрасывается исключение при несовпадении размерностей
        assertThrows(IllegalArgumentException.class, () -> vector1.added(vector2));
    }

    @Test
    public void testSubtraction() {
        // Два вектора одинаковой размерности
        double[] data1 = {4.0, 5.0, 6.0};
        double[] data2 = {1.0, 2.0, 3.0};

        VectorC vector1 = new VectorC(3, data1);
        VectorC vector2 = new VectorC(3, data2);

        // Ожидаемый результат
        double[] expectedData = {3.0, 3.0, 3.0};
        VectorC expectedVector = new VectorC(3, expectedData);

        // Выполняем вычитание
        VectorC result = vector1.subtracted(vector2);

        // Проверяем результат
        assertEquals(expectedVector, result);
    }

    @Test
    public void testSubtractionDimensionMismatch() {
        // Вектора разной размерности
        double[] data1 = {4.0, 5.0};
        double[] data2 = {1.0, 2.0, 3.0};

        VectorC vector1 = new VectorC(2, data1);
        VectorC vector2 = new VectorC(3, data2);

        // Проверяем, что выбрасывается исключение при несовпадении размерностей
        assertThrows(IllegalArgumentException.class, () -> vector1.subtracted(vector2));
    }

    @Test
    public void testAdditionWithZeroVector() {
        // Вектор и нулевой вектор
        double[] data = {1.0, 2.0, 3.0};
        double[] zeroData = {0.0, 0.0, 0.0};

        VectorC vector = new VectorC(3, data);
        VectorC zeroVector = new VectorC(3, zeroData);

        // Ожидаемый результат: вектор остается неизменным
        VectorC expectedVector = new VectorC(3, data);

        // Выполняем сложение
        VectorC result = vector.added(zeroVector);

        // Проверяем результат
        assertEquals(expectedVector, result);
    }

    @Test
    public void testSubtractionWithZeroVector() {
        // Вектор и нулевой вектор
        double[] data = {1.0, 2.0, 3.0};
        double[] zeroData = {0.0, 0.0, 0.0};

        VectorC vector = new VectorC(3, data);
        VectorC zeroVector = new VectorC(3, zeroData);

        // Ожидаемый результат: вектор остается неизменным
        VectorC expectedVector = new VectorC(3, data);

        // Выполняем вычитание
        VectorC result = vector.subtracted(zeroVector);

        // Проверяем результат
        assertEquals(expectedVector, result);
    }
}

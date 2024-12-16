package mathTest;

import com.cgvsu.math.types.RecMatrix;
import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.types.VectorC;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecMatrixTest {

    @Test
    public void testSetAndGet() {
        // Создаём прямоугольную матрицу 2x3
        RecMatrix matrix = new RecMatrix(2, 3);
        matrix.set(0, 0, 5.0);
        matrix.set(1, 2, 10.0);

        // Проверяем установленные значения
        assertEquals(5.0, matrix.get(0, 0));
        assertEquals(10.0, matrix.get(1, 2));
    }

    @Test
    public void testAdd() {
        // Прямоугольная матрица 2x3
        double[][] data1 = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix matrix1 = new RecMatrix(2, 3, data1);

        // Ещё одна матрица 2x3
        double[][] data2 = {
                {6.0, 5.0, 4.0},
                {3.0, 2.0, 1.0}
        };
        RecMatrix matrix2 = new RecMatrix(2, 3, data2);

        // Ожидаемый результат
        double[][] expectedData = {
                {7.0, 7.0, 7.0},
                {7.0, 7.0, 7.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 3, expectedData);

        // Проверяем сложение
        RecMatrix result = matrix1.added(matrix2);
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testSubtract() {
        // Прямоугольная матрица 2x3
        double[][] data1 = {
                {10.0, 20.0, 30.0},
                {40.0, 50.0, 60.0}
        };
        RecMatrix matrix1 = new RecMatrix(2, 3, data1);

        // Ещё одна матрица 2x3
        double[][] data2 = {
                {5.0, 4.0, 3.0},
                {2.0, 1.0, 0.0}
        };
        RecMatrix matrix2 = new RecMatrix(2, 3, data2);

        // Ожидаемый результат
        double[][] expectedData = {
                {5.0, 16.0, 27.0},
                {38.0, 49.0, 60.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 3, expectedData);

        // Проверяем вычитание
        RecMatrix result = matrix1.subtracted(matrix2);
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testMultiplyByScalar() {
        // Прямоугольная матрица 2x3
        double[][] data = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix matrix = new RecMatrix(2, 3, data);

        // Умножаем на скаляр 2.0
        double[][] expectedData = {
                {2.0, 4.0, 6.0},
                {8.0, 10.0, 12.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 3, expectedData);

        // Проверяем результат
        RecMatrix result = matrix.multiplied(2.0);
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testTranspose() {
        // Прямоугольная матрица 2x3
        double[][] data = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix matrix = new RecMatrix(2, 3, data);

        // Ожидаемая транспонированная матрица 3x2
        double[][] expectedData = {
                {1.0, 4.0},
                {2.0, 5.0},
                {3.0, 6.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(3, 2, expectedData);

        // Проверяем транспонирование
        RecMatrix result = matrix.transposed();
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testMultiplyWithSquareMatrix() {
        // Прямоугольная матрица 2x3
        double[][] recData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix recMatrix = new RecMatrix(2, 3, recData);

        // Квадратная матрица 3x3
        double[][] squareData = {
                {1.0, 0.0, 0.0},
                {0.0, 1.0, 0.0},
                {0.0, 0.0, 1.0}
        };
        SquareMatrix squareMatrix = new SquareMatrix(3, squareData);

        // Ожидаемый результат: та же матрица 2x3
        double[][] expectedData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 3, expectedData);

        // Проверяем умножение
        RecMatrix result = recMatrix.multiplied(squareMatrix);
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testEquals() {
        // Две идентичные прямоугольные матрицы
        double[][] data = {
                {1.0, 2.0},
                {3.0, 4.0}
        };
        RecMatrix matrix1 = new RecMatrix(2, 2, data);
        RecMatrix matrix2 = new RecMatrix(2, 2, data);

        // Проверяем equals
        assertEquals(matrix1, matrix2);
    }

    @Test
    public void testDivideByScalar() {
        // Прямоугольная матрица 2x3
        double[][] data = {
                {2.0, 4.0, 6.0},
                {8.0, 10.0, 12.0}
        };
        RecMatrix matrix = new RecMatrix(2, 3, data);

        // Делим на скаляр 2.0
        double[][] expectedData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 3, expectedData);

        // Проверяем результат
        RecMatrix result = matrix.divided(2.0);
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testMultiplyWithRecMatrix() {
        // Прямоугольная матрица 2x3
        double[][] data1 = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix matrix1 = new RecMatrix(2, 3, data1);

        // Прямоугольная матрица 3x2
        double[][] data2 = {
                {7.0, 8.0},
                {9.0, 10.0},
                {11.0, 12.0}
        };
        RecMatrix matrix2 = new RecMatrix(3, 2, data2);

        // Ожидаемый результат: прямоугольная матрица 2x2
        double[][] expectedData = {
                {58.0, 64.0},
                {139.0, 154.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 2, expectedData);

        // Проверяем умножение
        RecMatrix result = matrix1.multiplied(matrix2);
        assertEquals(expectedMatrix, result);
    }

    @Test
    public void testMultiplyWithVectorC() {
        // Прямоугольная матрица 2x3
        double[][] matrixData = {
                {2.0, 3.0, 4.0},
                {5.0, 6.0, 7.0}
        };
        RecMatrix matrix = new RecMatrix(2, 3, matrixData);

        // Вектор-столбец
        double[] vectorData = {1.0, 2.0, 3.0};
        VectorC vector = new VectorC(3, vectorData);

        // Ожидаемый результат: вектор-столбец 2x1
        double[] expectedData = {20.0, 38.0};
        VectorC expectedVector = new VectorC(2, expectedData);

        // Проверяем умножение
        VectorC result = matrix.multiplied(vector);
        assertEquals(expectedVector, result);
    }
}

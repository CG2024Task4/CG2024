package mathTest;

import com.cgvsu.math.types.RecMatrix;
import com.cgvsu.math.types.SquareMatrix;
import com.cgvsu.math.types.VectorC;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SquareMatrixTest {

    @Test
    public void testSetAndGet() {
        SquareMatrix matrix = new SquareMatrix(2);
        matrix.set(0, 0, 5.0);
        matrix.set(1, 1, 10.0);

        double[][] expectedData = {{5.0, 0.0}, {0.0, 10.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(matrix, expectedMatrix);
    }

    @Test
    public void testTranspose() {
        double[][] data = {{1.0, 2.0}, {3.0, 4.0}};
        SquareMatrix matrix = new SquareMatrix(2, data);

        SquareMatrix transposed = matrix.transposed();
        double[][] expectedData = {{1.0, 3.0}, {2.0, 4.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(transposed, expectedMatrix);
    }

    @Test
    public void testPow() {
        double[][] data = {{2.0, 0.0}, {0.0, 2.0}};
        SquareMatrix matrix = new SquareMatrix(2, data);

        SquareMatrix result = matrix.pows(3);
        double[][] expectedData = {{8.0, 0.0}, {0.0, 8.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(result, expectedMatrix);
    }

    @Test
    public void testAdd() {
        double[][] data1 = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] data2 = {{5.0, 6.0}, {7.0, 8.0}};
        SquareMatrix matrix1 = new SquareMatrix(2, data1);
        SquareMatrix matrix2 = new SquareMatrix(2, data2);

        SquareMatrix result = matrix1.added(matrix2);
        double[][] expectedData = {{6.0, 8.0}, {10.0, 12.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(result, expectedMatrix);
    }

    @Test
    public void testSubtract() {
        double[][] data1 = {{5.0, 6.0}, {7.0, 8.0}};
        double[][] data2 = {{1.0, 2.0}, {3.0, 4.0}};
        SquareMatrix matrix1 = new SquareMatrix(2, data1);
        SquareMatrix matrix2 = new SquareMatrix(2, data2);

        SquareMatrix result = matrix1.subtracted(matrix2);
        double[][] expectedData = {{4.0, 4.0}, {4.0, 4.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(result, expectedMatrix);
    }

    @Test
    public void testMultiplyWithScalar() {
        double[][] data = {{1.0, 2.0}, {3.0, 4.0}};
        SquareMatrix matrix = new SquareMatrix(2, data);

        SquareMatrix result = matrix.multiplied(2.0);
        double[][] expectedData = {{2.0, 4.0}, {6.0, 8.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(result, expectedMatrix);
    }

    @Test
    public void testMultiplyWithMatrix() {
        double[][] data1 = {{1.0, 2.0}, {3.0, 4.0}};
        double[][] data2 = {{2.0, 0.0}, {1.0, 2.0}};
        SquareMatrix matrix1 = new SquareMatrix(2, data1);
        SquareMatrix matrix2 = new SquareMatrix(2, data2);

        SquareMatrix result = matrix1.multiplied(matrix2);
        double[][] expectedData = {{4.0, 4.0}, {10.0, 8.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);

        assertEquals(result, expectedMatrix);
    }

    @Test
    public void testDivide() {
        double[][] data = {{4.0, 8.0}, {12.0, 16.0}};
        SquareMatrix matrix = new SquareMatrix(2, data);

        SquareMatrix result = matrix.divided(4.0);

        double[][] expectedData = {{1.0, 2.0}, {3.0, 4.0}};
        SquareMatrix expectedMatrix = new SquareMatrix(2, expectedData);
        assertEquals(result, expectedMatrix);
    }

    @Test
    public void testDivideByZero() {
        double[][] data = {{4.0, 8.0}, {12.0, 16.0}};
        SquareMatrix matrix = new SquareMatrix(2, data);

        // Проверяем, что деление на 0 вызывает исключение
        assertThrows(IllegalArgumentException.class, () -> {
            matrix.divide(0.0);
        });
    }

    @Test
    public void testMultiplyWithVectorC() {
        // Матрица 2x2
        double[][] matrixData = {
                {2.0, 3.0},
                {4.0, 5.0}
        };
        SquareMatrix matrix = new SquareMatrix(2, matrixData);

        // Вектор-столбец
        double[] vectorData = {1.0, 2.0};
        VectorC vector = new VectorC(2, vectorData);

        double[] expectedData = {8.0, 14.0};
        VectorC expectedVector = new VectorC(2, expectedData);

        VectorC result = matrix.multiplied(vector);

        assertEquals(result, expectedVector);
    }

    @Test
    public void testMultiplyWithVectorCInvalidSize() {
        // Матрица 2x2
        double[][] matrixData = {
                {2.0, 3.0},
                {4.0, 5.0}
        };
        SquareMatrix matrix = new SquareMatrix(2, matrixData);

        double[] vectorData = {1.0, 2.0, 3.0};
        VectorC vector = new VectorC(3, vectorData);

        assertThrows(IllegalArgumentException.class, () -> {
            matrix.multiplied(vector);
        });
    }

    @Test
    public void testMultiplyWithRectangularMatrix() {
        // Квадратная матрица 2x2
        double[][] squareData = {
                {2.0, 3.0},
                {4.0, 5.0}
        };
        SquareMatrix squareMatrix = new SquareMatrix(2, squareData);

        // Прямоугольная матрица 2x3
        double[][] rectData = {
                {1.0, 2.0, 3.0},
                {4.0, 5.0, 6.0}
        };
        RecMatrix rectMatrix = new RecMatrix(2, 3, rectData);

        double[][] expectedData = {
                {14.0, 19.0, 24.0},
                {24.0, 33.0, 42.0}
        };
        RecMatrix expectedMatrix = new RecMatrix(2, 3, expectedData);

        RecMatrix resultMatrix = squareMatrix.multiplied(rectMatrix);

        // Проверяем результат
        assertEquals(expectedMatrix, resultMatrix);
    }
}

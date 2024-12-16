package com.cgvsu.math;



import com.cgvsu.math.types.SquareMatrix;

import java.util.Random;

public class TEST {
    //Показательный тест!
    public static void main(String[] args) {
        Random rand = new Random();
        double[][] c1 = new double[5][5];
        double[][] c2 = new double[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                c1[i][j] = rand.nextInt(0,20);
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                c2[i][j] = rand.nextInt(0,20);
            }
        }

        SquareMatrix q1 = new SquareMatrix(5, c1);
        SquareMatrix q2 = new SquareMatrix(5, c2);
        System.out.println("Матрица 1");
        q1.print();
        System.out.println("Матрица 2");
        q2.print();
        System.out.println("Умножение");
        q1.multiplied(q2).print();
        System.out.println("Сумма");
        q1.added(q2).print();
        System.out.println("Разность");
        q1.subtracted(q2).print();
    }
}

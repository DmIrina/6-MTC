package lab2;

import lab2.classic.ClassicAlgParallel;
import lab2.fox.FoxAlg;
import lab2.striped.StripeAlg;
import lab2.utils.MatrixUtils;
import lab2.utils.Result;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Технології паралельних обчислень\nКомп'ютерний практикум № 2\n");
//        int[][] A = new int[][]{{1, 2, 1, 2}, {2, 3, 2, 1}, {1, 3, 1, 2}};
//        int[][] B = new int[][]{{1, 2, 1, 3, 1}, {1, 2, 1, 2, 1}, {2, 1, 3, 1, 2}, {3, 2, 1, 2, 3}};

//        int[][] A = {
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//        };
//
//        int[][] B = {
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//        };

        int size = 1000;
        System.out.println("Розмірність матриць: " + size);

        int[][] A = new int[size][size];
        int[][] B = new int[size][size];
        MatrixUtils.generateRandomMatrix(A);
        MatrixUtils.generateRandomMatrix(B);

        if (A[0].length != B.length) {         // colA != rowsB
            throw new IllegalArgumentException("Розмірність матриць не дозволяє їх перемножити");
        }
        // int threadCount = 1;
        // int threadCount = 2;
        // int threadCount = 5;
        int threadCount = Runtime.getRuntime().availableProcessors();


        System.out.println("Кількість потоків: " + threadCount + "\n");

        // System.out.println("Звичайне множення матриць");
        // Result classicAlg = new ClassicAlg(A, B).multiply();
        // classicAlg.printResult();
        // classicAlg.printDuration();

         // System.out.println("Паралельне множення матриць");
         Result classicAlgPar = new ClassicAlgParallel(A, B).multiply(threadCount);
         // classicAlgPar.printResult();
         // classicAlgPar.printDuration();

        System.out.println("Cтрічковий алгоритм множення матриць");
        Result stripedAlg = new StripeAlg(A, B).multiply(threadCount);
        // stripedAlg.printResult();
        stripedAlg.printDuration();

        System.out.println("Алгоритм Фокса множення матриць");
        Result foxAlg = new FoxAlg(A, B).multiply(threadCount);
        // foxAlg.printResult();
        foxAlg.printDuration();


        System.out.println("Матриці класичного і стрічкового алгоритмів рівні: " +
                Arrays.deepEquals(classicAlgPar.getMatrix(), stripedAlg.getMatrix()));
        System.out.println("Матриці Фокса і стрічкового алгоритмів рівні: " +
                Arrays.deepEquals(foxAlg.getMatrix(), stripedAlg.getMatrix()));
        System.out.println("Матриці Фокса і класичного алгоритмів рівні: " +
                Arrays.deepEquals(foxAlg.getMatrix(), classicAlgPar.getMatrix()));
    }
}
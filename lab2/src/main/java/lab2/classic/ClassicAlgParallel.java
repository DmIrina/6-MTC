package lab2.classic;

import lab2.utils.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClassicAlgParallel {
    private final int[][] A;
    private final int[][] B;
    private int[][] C;

    private final int rowsA;
    private final int colsB;

    public ClassicAlgParallel(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;

        this.rowsA = A.length;
        this.colsB = B[0].length;

        this.C = new int[rowsA][colsB];
    }

    public Result multiply(int threadCount) {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                executor.execute(new ClassicAlgThread(A, B, C, i, j));
            }
        }

        long endTime = System.currentTimeMillis();
        executor.shutdown();

        return new Result(C, endTime - startTime);
    }
}

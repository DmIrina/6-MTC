package lab2.striped;

import lab2.utils.Result;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class StripeAlg {
    private final int[][] A;
    private final int[][] B;
    private int[][] C;

    private final int rowsA;
    private final int colB;

    public StripeAlg(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;

        this.rowsA = A.length;
        this.colB = B[0].length;

        this.C = new int[rowsA][colB];
    }

    public Result multiply(int threadCount) {
        long startTime = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colB; j++) {
                executor.execute(new StripeAlgThread(i, j, A, B, C[i]));
            }
        }

        try {
            executor.shutdown();
            executor.awaitTermination(100L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        return new Result(C, endTime - startTime);
    }
}
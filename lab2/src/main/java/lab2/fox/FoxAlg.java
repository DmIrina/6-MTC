package lab2.fox;

import lab2.utils.Result;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FoxAlg {
    private final int[][] A;
    private final int[][] B;
    private final int rowsA;
    private final int colsA;
    private final int colsB;

    public FoxAlg(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;

        this.rowsA = A.length;
        this.colsA = A[0].length;
        this.colsB = B[0].length;
    }

    public Result multiply(int threadCount) {
        long startTime = System.currentTimeMillis();

        int[][] C = new int[rowsA][colsB];

        ExecutorService executorAddition = Executors.newFixedThreadPool(threadCount);

        int subMatrixRowsA = (int) Math.ceil((double) rowsA / threadCount);
        int subMatrixColsA = (int) Math.ceil((double) colsA / threadCount);
        int subMatrixColsB = (int) Math.ceil((double) colsB / threadCount);

        for (int i = 0; i < threadCount; i++) {
            for (int j = 0; j < threadCount; j++) {
                ExecutorService executorMultiplication = Executors.newFixedThreadPool(threadCount);

                List<int[][]> matrices = Collections.synchronizedList(new ArrayList<>());

                for (int k = 0; k < threadCount; k++) {
                    int startRowA = i * subMatrixRowsA;
                    int startColA = k * subMatrixColsA;
                    int startColB = j * subMatrixColsB;

                    // subMatrix array:
                    // 0 - start row
                    // 1 - end row
                    // 2 - start column
                    // 3 - end column

                    int endRowA = Math.min(startRowA + subMatrixRowsA, rowsA);
                    int endColA = Math.min(startColA + subMatrixColsA, colsA);
                    int endColB = Math.min(startColB + subMatrixColsB, colsB);

                    executorMultiplication.submit(new FoxAlgMultiplicationThread(
                            A, B, matrices, subMatrixRowsA, subMatrixColsB,
                            // subMatrixA
                            new int[]{startRowA, endRowA, startColA, endColA},
                            // subMatrixB
                            new int[]{startColA, endColA, startColB, endColB}));
                }
                try {
                    executorMultiplication.shutdown();
                    executorMultiplication.awaitTermination(100L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                executorAddition.submit(new FoxAlgAdditionThread(
                        i * subMatrixRowsA, j * subMatrixColsB, C, matrices));
            }
        }
        try {
            executorAddition.shutdown();
            executorAddition.awaitTermination(100L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        return new Result(C, endTime - startTime);
    }
}

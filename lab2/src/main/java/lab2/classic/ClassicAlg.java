package lab2.classic;

import lab2.utils.Result;

public class ClassicAlg {
    private final int[][] A;
    private final int[][] B;
    private int[][] C;

    private final int rowsA;
    private final int colsB;

    public ClassicAlg(int[][] A, int[][] B) {
        this.A = A;
        this.B = B;

        this.rowsA = A.length;
        this.colsB = B[0].length;

        this.C = new int[rowsA][colsB];
    }

    public Result multiply() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        long endTime = System.currentTimeMillis();

        return new Result(C, endTime - startTime);
    }
}

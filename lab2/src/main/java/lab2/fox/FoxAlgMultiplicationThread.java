package lab2.fox;

import java.util.List;

public class FoxAlgMultiplicationThread implements Runnable {
    private final int[][] A;
    private final int[][] B;

    private int[] subMatrixA;
    private int[] subMatrixB;

    private List<int[][]> matrices;

    private final int maxRow;
    private final int maxCol;

    public FoxAlgMultiplicationThread(int[][] A, int[][] B, List<int[][]> matrices, int maxRow, int maxCol,
                                      int[] subMatrixA, int[] subMatrixB) {
        this.A = A;
        this.B = B;
        this.subMatrixA = subMatrixA;
        this.subMatrixB = subMatrixB;
        this.matrices = matrices;
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }

    // subMatrix array:
    // 0 - start row
    // 1 - end row
    // 2 - start column
    // 3 - end column

    @Override
    public void run() {
        int startRowA = subMatrixA[0];
        int endRowA = subMatrixA[1];
        int startColA = subMatrixA[2];
        int endColA = subMatrixA[3];

        int startColB = subMatrixB[2];
        int endColB = subMatrixB[3];

        int rowA = endRowA - startRowA; // row length
        int colB = endColB - startColB; // col length

        int[][] matrix = new int[rowA][colB];

        for (int row = startRowA; row < endRowA; row++) {
            for (int col = startColB; col < endColB; col++) {
                int x = 0;
                for (int k = startColA; k < endColA; k++) {
                    x += A[row][k] * B[k][col];
                }

                int tempRow = row;
                int tempCol = col;

                if (rowA != maxRow) {
                    tempRow = row - (maxRow - rowA);
                } else if (colB != maxCol) {
                    tempCol = col - (maxCol - colB);
                }

                matrix[tempRow % rowA][tempCol % colB] = x;
            }
        }
        matrices.add(matrix);
    }
}

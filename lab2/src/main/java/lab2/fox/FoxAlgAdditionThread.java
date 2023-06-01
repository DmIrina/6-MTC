package lab2.fox;

import java.util.List;

public class FoxAlgAdditionThread implements Runnable {
    private List<int[][]> matrices;

    private final int row;
    private final int col;

    private int[][] C;

    public FoxAlgAdditionThread(int row, int col, int[][] C, List<int[][]> matrices) {
        this.row = row;
        this.col = col;
        this.matrices = matrices;
        this.C = C;
    }

    @Override
    public void run() {
        for (int[][] matrix : matrices) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    C[i + row][j + col] += matrix[i][j];
                }
            }
        }
    }
}
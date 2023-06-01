package lab2.classic;

public class ClassicAlgThread implements Runnable {
    private final int[][] A;
    private final int[][] B;
    private int[][] C;

    private final int rowA;
    private final int colB;


    public ClassicAlgThread(int[][] A, int[][] B, int[][] C, int rowA, int colB) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.rowA = rowA;
        this.colB = colB;
    }

    @Override
    public void run() {
        for (int k = 0; k < A[0].length; k++) {
            C[rowA][colB] += A[rowA][k] * B[k][colB];
        }
    }
}

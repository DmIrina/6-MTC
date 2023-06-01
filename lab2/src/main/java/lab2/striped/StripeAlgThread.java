package lab2.striped;

public class StripeAlgThread implements Runnable {
    private final int rowsB;

    private int rowC;
    private final int colB;

    private final int[][] A;
    private final int[][] B;
    private int[] stripeC;

    public StripeAlgThread(int rowC, int colB, int[][] A, int[][] B, int[] stripeC) {
        this.A = A;
        this.B = B;
        this.stripeC = stripeC;
        this.rowC = rowC;
        this.colB = colB;
        this.rowsB = B.length;
    }

    private int getRowNumber(int i, int count) {
        if (i == 0) {
            return 0;
        } else {
            return count - i;
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < rowsB; i++) {
            int rowN = getRowNumber(i, rowsB);
            stripeC[colB] = stripeC[colB] + A[rowC][rowN] * B[rowN][colB];
        }

    }
}
package nonblocking;

import models.Chunk;
import models.MatrixUtils;
import models.Result;
import mpi.MPI;
import mpi.Request;

public class NonBlockingMatrixMultiplication {
    private final int FROM_MASTER = 1;
    private final int FROM_WORKER = 10;

    private final int[][] A;
    private final int[][] B;

    private final int rowsA;
    private final int colsA;
    private final int rowsB;
    private final int colsB;
    private final String[] args;

    public NonBlockingMatrixMultiplication(int[][] A, int[][] B, String[] args) {
        this.A = A;
        this.B = B;

        this.rowsA = this.A.length;
        this.colsA = this.A[0].length;
        this.rowsB = this.B.length;
        this.colsB = this.B[0].length;

        this.args = args;
    }

    public Result multiply() {
        try {
            MPI.Init(args);
            int size = MPI.COMM_WORLD.Size();
            int rank = MPI.COMM_WORLD.Rank();
            if (size < 2) {
                return null;
            }
            if (rank == 0) {
                return processMaster(size);
            } else {
                processWorker();
            }
            return null;
        } finally {
            MPI.Finalize();
        }
    }

    private Result processMaster(int size) {
        long startTime = System.currentTimeMillis();

        int workerCount = size - 1;
        int rowsPerWorker = rowsA / workerCount;
        int extraRows = rowsA % workerCount;

        int[][] result = new int[rowsA][rowsB];
        byte[] matrixBBuffer = MatrixUtils.intMatrixToByteArray(B);

        Chunk[] chunks = new Chunk[workerCount];

        for (var worker = 1; worker <= workerCount; worker++) {
            int startRow = (worker - 1) * rowsPerWorker;
            int endRow = startRow + rowsPerWorker;

            if (worker == workerCount) {
                endRow += extraRows;
            }

            chunks[worker - 1] = new Chunk(startRow, endRow);

            int[][] subMatrixA = MatrixUtils.getSubMatrix(A, startRow, endRow, rowsB);
            byte[] subMatrixABuffer = MatrixUtils.intMatrixToByteArray(subMatrixA);
            int subMatrixSize = (endRow - startRow + 1) * rowsA;

            MPI.COMM_WORLD.Send(new int[]{startRow}, 0, 1, MPI.INT, worker, FROM_MASTER);
            MPI.COMM_WORLD.Send(new int[]{endRow}, 0, 1, MPI.INT, worker, FROM_MASTER);
            MPI.COMM_WORLD.Send(subMatrixABuffer, 0,
                    MatrixUtils.int32ByteSize * subMatrixSize, MPI.BYTE, worker, FROM_MASTER);
            MPI.COMM_WORLD.Send(matrixBBuffer, 0,
                    MatrixUtils.int32ByteSize * rowsB * colsB, MPI.BYTE, worker, FROM_MASTER);
        }

        Request[] results = new Request[workerCount];

        for (int worker = 1; worker <= workerCount; worker++) {
            Chunk chunk = chunks[worker - 1];

            int start = chunk.startIndex();
            int end = chunk.endIndex();

            int resultItemsCount = (end - start + 1) * rowsA;
            byte[] buffer = new byte[MatrixUtils.int32ByteSize * resultItemsCount];

            chunk.setBuffer(buffer);
            results[worker - 1] = MPI.COMM_WORLD.Irecv(buffer, 0,
                    MatrixUtils.int32ByteSize * resultItemsCount, MPI.BYTE, worker, FROM_WORKER);
        }

        Request.Waitall(results);

        for (int worker = 1; worker <= workerCount; worker++) {
            Chunk chunk = chunks[worker - 1];

            int end = chunk.endIndex();
            int start = chunk.startIndex();
            byte[] buffer = chunk.getBuffer();

            int[][] resultSubMatrix = MatrixUtils.bytesToIntMatrix(buffer, end - start, colsA);
            MatrixUtils.addSubMatrixToMatrix(resultSubMatrix, result, start, end);
        }

        return new Result(result, (System.currentTimeMillis() - startTime));
    }

    private void processWorker() {
        int[] rowStartIndex = new int[1];
        int[] rowEndIndex = new int[1];

        Request startRow = MPI.COMM_WORLD.Irecv(rowStartIndex, 0, 1, MPI.INT, 0, FROM_MASTER);
        Request endRow = MPI.COMM_WORLD.Irecv(rowEndIndex, 0, 1, MPI.INT, 0, FROM_MASTER);

        Request.Waitall(new Request[]{startRow, endRow});

        int subMatrixSize = (rowEndIndex[0] - rowStartIndex[0] + 1) * rowsA;
        byte[] subMatrixBuffer = new byte[MatrixUtils.int32ByteSize * subMatrixSize];
        byte[] matrixBuffer = new byte[MatrixUtils.int32ByteSize * rowsB * colsB];

        Request subBuffer = MPI.COMM_WORLD.Irecv(subMatrixBuffer, 0,
                MatrixUtils.int32ByteSize * subMatrixSize, MPI.BYTE, 0, FROM_MASTER);

        Request buffer = MPI.COMM_WORLD.Irecv(matrixBuffer, 0,
                MatrixUtils.int32ByteSize * rowsB * colsB, MPI.BYTE, 0, FROM_MASTER);

        Request.Waitall(new Request[]{subBuffer, buffer});

        int[][] matrix = MatrixUtils.bytesToIntMatrix(matrixBuffer, rowsB, colsB);
        int[][] subMatrix = MatrixUtils.bytesToIntMatrix(subMatrixBuffer, rowEndIndex[0] - rowStartIndex[0], colsA);
        int[][] matrixResult = MatrixUtils.multiplyMatrices(subMatrix, matrix);
        byte[] matrixResultBuffer = MatrixUtils.intMatrixToByteArray(matrixResult);

        MPI.COMM_WORLD.Isend(matrixResultBuffer, 0, matrixResultBuffer.length, MPI.BYTE, 0, FROM_WORKER);
    }
}

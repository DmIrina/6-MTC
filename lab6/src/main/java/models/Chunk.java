package models;

public class Chunk {
    private byte[] buffer;
    private final int startIndex;
    private final int endIndex;

    public Chunk(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.buffer = null;
    }

    public int startIndex() {
        return startIndex;
    }

    public int endIndex() {
        return endIndex;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}

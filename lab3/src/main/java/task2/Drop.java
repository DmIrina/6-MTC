package task2;

public class Drop {
    private int message;
    private boolean notFull = true;

    public synchronized int take() {
        while (notFull) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        notFull = true;
        notifyAll();
        return message;
    }

    public synchronized void put(int message) {
        while (!notFull) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        notFull = false;
        this.message = message;
        notifyAll();
    }
}

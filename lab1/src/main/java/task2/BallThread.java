package task2;

public class BallThread extends Thread {
    private Ball b;

    public BallThread(Ball ball) {
        this.b = ball;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10000; i++) {
                int holeNum = b.move();
                if (holeNum != 0) {
                    System.out.println("М'ячик " + Thread.currentThread().getName() + " влучив у лузу " + holeNum);
                    break;
                }
                Thread.sleep(5);
            }
        } catch (InterruptedException ex) {
            System.out.println(Thread.currentThread().getName() + " stopped.");
        }
    }
}

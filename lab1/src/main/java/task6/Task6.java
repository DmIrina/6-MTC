package task6;

public class Task6 {
    public static void main(String[] args) {
        System.out.println("Технології паралельних обчислень\nКомп'ютерний практикум № 1");
        System.out.println("Task 6");

        int MAX_COUNT = 100000;

        Counter counter = new Counter();

        System.out.println("# 1: " + counter.getCount());

        for (int i = 0; i < MAX_COUNT; i++) {
            counter.increment();
        }

        System.out.println("# 2: " + counter.getCount());

        for (int i = 0; i < MAX_COUNT; i++) {
            counter.decrement();
        }

        System.out.println("# 3: " + counter.getCount());

        Thread incThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MAX_COUNT; i++) {
                    //counter.increment();
                    // counter.syncIncrement();
                    // counter.syncStatementIncrement();
                    counter.lockIncrement();
                }
            }
        });

        Thread decThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MAX_COUNT; i++) {
                    // counter.decrement();
                    // counter.syncDecrement();
                    // counter.syncStatementDecrement();
                     counter.lockDecrement();
                }
            }
        });

        incThread.start();
        decThread.start();

        try {
            incThread.join();
            decThread.join();
            System.out.println("# 4 memory consistency error: " + counter.getCount());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
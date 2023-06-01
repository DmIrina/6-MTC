package task6;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
    private int count = 0;

    private Object sync = new Object();

    private ReentrantLock lock = new ReentrantLock();

    public void increment() {
        count++;
    }

    public void decrement() {
        count--;
    }

    public synchronized void syncIncrement() {
        count++;
    }

    public synchronized void syncDecrement() {
        count--;
    }

    public void syncStatementIncrement() {
        {
            synchronized (sync) {
                count++;
                count--;
                count++;
            }
        }
    }

    public void syncStatementDecrement() {
        {
            synchronized (sync) {
                count--;
                count++;
                count--;
            }
        }
    }

    public void lockIncrement() {
        lock.lock();
        try {
            count++;
            count--;
            count++;
        } finally {
            lock.unlock();
        }
    }

    public void lockDecrement() {
        lock.lock();
        try {
            count--;
            count++;
            count--;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
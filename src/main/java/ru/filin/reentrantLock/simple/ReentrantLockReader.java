package ru.filin.reentrantLock.simple;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantLockReader extends Thread {
    private final StringBuilder buffer;

    private Lock locker;
    private Condition condition;

    public ReentrantLockReader(StringBuilder buffer, Lock locker, Condition condition) {
        this.buffer = buffer;
        this.locker = locker;
        this.condition = condition;
    }

    public void read() {
        while (buffer.length() > 0) {
            int i = buffer.indexOf(" ");
            String result = buffer.substring(0, i);
            buffer.delete(0, i + 1);
            System.out.println("Reader " + Thread.currentThread().getName() + "red" + " " + result);
        }
    }

    @Override
    public void run() {
        while (true) {
            locker.lock();

            try {
                while (buffer.length() < 1) {
                    condition.await();
                }

                if (buffer.length() > 0) {
                    read();
                }

                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }
}

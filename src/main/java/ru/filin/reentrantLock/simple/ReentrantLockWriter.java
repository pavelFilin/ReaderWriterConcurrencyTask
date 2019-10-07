package ru.filin.reentrantLock.simple;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReentrantLockWriter extends Thread {
    private final StringBuilder buffer;

    private Lock locker;
    private Condition condition;

    private String message;
    private int count;


    public ReentrantLockWriter(String message, int count, StringBuilder buffer, Lock locker, Condition condition) {
        this.message = message;
        this.count = count;
        this.buffer = buffer;
        this.locker = locker;
        this.condition = condition;
    }

    public void write() {
        for (int i = 0; i < count; i++) {
            System.out.println("Write " + Thread.currentThread().getName() + "red" + " " + message + i + " ");
            buffer.append(message).append(count).append(" ");
        }
    }

    @Override
    public void run() {
        while (true) {
            locker.lock();
            try {
                while (buffer.length() != 0) {
                    condition.await();
                }

                if (buffer.length() == 0) {
                    write();
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

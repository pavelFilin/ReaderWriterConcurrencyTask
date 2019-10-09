package ru.filin.synchronizedReadWrite;


import javax.management.OperationsException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CycleReentrantLockWriter extends Thread {
    private final CycleCollection buffer;

    private Lock locker;
    private Condition condition;

    private String message;
    private int count;
    private int current;

    private boolean finish;

    public CycleReentrantLockWriter(String message, int count, CycleCollection buffer, Lock locker, Condition condition) {
        this.message = message;
        this.count = count;
        this.buffer = buffer;
        this.locker = locker;
        this.condition = condition;
    }

    public void write() {

        if (current < count) {
            System.out.println("Write " + Thread.currentThread().getName() + "red" + " " + message + current + " ");
            try {
                buffer.write(message + current + " ");
            } catch (OperationsException e) {
                e.printStackTrace();
            }
            current++;
            //                currentThread().wait(10L);
        } else {
            isFinish();
        }
    }

    @Override
    public void run() {
        while (!finish) {
            locker.lock();
            try {
                while (buffer.isFull()) {
                    condition.await();
                }

                write();

                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }

    public void isFinish() {
        this.finish = true;
    }
}

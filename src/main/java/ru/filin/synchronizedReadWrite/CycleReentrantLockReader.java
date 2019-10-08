package ru.filin.synchronizedReadWrite;

import javax.management.OperationsException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CycleReentrantLockReader extends Thread {
    private final CycleCollection buffer;

    private Lock locker;
    private Condition condition;

    public CycleReentrantLockReader(CycleCollection buffer, Lock locker, Condition condition) {
        this.buffer = buffer;
        this.locker = locker;
        this.condition = condition;
    }

    public void read() {
        while (!buffer.isEmpty()) {
            String result = null;
            try {
                result = (String) buffer.readNext();
            } catch (OperationsException e) {
                e.printStackTrace();
            }
            System.out.println("Reader " + Thread.currentThread().getName() + "red" + " " + result);
        }
    }

    @Override
    public void run() {
        while (true) {
            locker.lock();

            try {
                while (buffer.isEmpty()) {
                    condition.await();
                }

                read();

                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                locker.unlock();
            }
        }
    }
}

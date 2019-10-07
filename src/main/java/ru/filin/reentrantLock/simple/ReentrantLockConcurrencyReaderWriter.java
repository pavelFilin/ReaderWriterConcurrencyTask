package ru.filin.reentrantLock.simple;

import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ReentrantLockConcurrencyReaderWriter {

    private final StringBuilder buffer = new StringBuilder();

    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();

    @Setter
    private boolean empty;

    public void test() throws Exception {
        List<ReentrantLockReader> reentrantLockReaders = initReaders();
        List<ReentrantLockWriter> reentrantLockWriters = initWriters();

        for (ReentrantLockWriter reentrantLockWriter : reentrantLockWriters) {
            reentrantLockWriter.start();
        }

        for (ReentrantLockReader reentrantLockReader : reentrantLockReaders) {
            reentrantLockReader.start();
        }

        for (ReentrantLockWriter reentrantLockWriter : reentrantLockWriters) {
            reentrantLockWriter.join();
        }

        for (ReentrantLockReader reentrantLockReader : reentrantLockReaders) {
            reentrantLockReader.join();
        }

    }

    private List<ReentrantLockWriter> initWriters() {
        List<ReentrantLockWriter> reentrantLockWriters = new ArrayList<>();

        reentrantLockWriters.add(new ReentrantLockWriter("aaaa", 9, buffer, locker, condition));
        reentrantLockWriters.add(new ReentrantLockWriter("bbbb", 9, buffer, locker, condition));
        reentrantLockWriters.add(new ReentrantLockWriter("cccc", 9, buffer, locker, condition));
        reentrantLockWriters.add(new ReentrantLockWriter("dddd", 9, buffer, locker, condition));
        reentrantLockWriters.add(new ReentrantLockWriter("eeee", 9, buffer, locker, condition));
        return reentrantLockWriters;
    }

    private List<ReentrantLockReader> initReaders() {
        List<ReentrantLockReader> reentrantLockReaders = new LinkedList<>();
        reentrantLockReaders.add(new ReentrantLockReader(buffer, locker, condition));
        reentrantLockReaders.add(new ReentrantLockReader(buffer, locker, condition));
        return reentrantLockReaders;
    }

}

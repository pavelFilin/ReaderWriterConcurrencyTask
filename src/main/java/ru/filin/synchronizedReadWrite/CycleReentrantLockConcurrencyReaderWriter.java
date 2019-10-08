package ru.filin.synchronizedReadWrite;

import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class CycleReentrantLockConcurrencyReaderWriter {

    CycleCollection<String> buffer = new CycleCollection<String>(String.class, 10);

    private Lock lockerReader = new ReentrantLock();
    private Condition conditionReader = lockerReader.newCondition();

    private Lock lockerWriter = new ReentrantLock();
    private Condition conditionWriter = lockerWriter.newCondition();


    public void test() throws Exception {
        List<CycleReentrantLockReader> cycleReentrantLockReaders = initReaders();
        List<CycleReentrantLockWriter> cycleReentrantLockWriters = initWriters();

        for (CycleReentrantLockWriter cycleReentrantLockWriter : cycleReentrantLockWriters) {
            cycleReentrantLockWriter.start();
        }

        for (CycleReentrantLockReader cycleReentrantLockReader : cycleReentrantLockReaders) {
            cycleReentrantLockReader.start();
        }

        for (CycleReentrantLockWriter cycleReentrantLockWriter : cycleReentrantLockWriters) {
            cycleReentrantLockWriter.join();
        }

        for (CycleReentrantLockReader cycleReentrantLockReader : cycleReentrantLockReaders) {
            cycleReentrantLockReader.join();
        }

    }

    private List<CycleReentrantLockWriter> initWriters() {
        List<CycleReentrantLockWriter> cycleReentrantLockWriters = new ArrayList<>();

        cycleReentrantLockWriters.add(new CycleReentrantLockWriter("aaaa", 3, buffer, lockerWriter, conditionWriter));
        cycleReentrantLockWriters.add(new CycleReentrantLockWriter("bbbb", 3, buffer, lockerWriter, conditionWriter));
        cycleReentrantLockWriters.add(new CycleReentrantLockWriter("cccc", 3, buffer, lockerWriter, conditionWriter));
        cycleReentrantLockWriters.add(new CycleReentrantLockWriter("dddd", 3, buffer, lockerWriter, conditionWriter));
        cycleReentrantLockWriters.add(new CycleReentrantLockWriter("eeee", 3, buffer, lockerWriter, conditionWriter));
        return cycleReentrantLockWriters;
    }

    private List<CycleReentrantLockReader> initReaders() {
        List<CycleReentrantLockReader> cycleReentrantLockReaders = new LinkedList<>();
        cycleReentrantLockReaders.add(new CycleReentrantLockReader(buffer, lockerReader, conditionReader));
        cycleReentrantLockReaders.add(new CycleReentrantLockReader(buffer, lockerReader, conditionReader));
        return cycleReentrantLockReaders;
    }

}
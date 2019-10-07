package ru.filin;

import ru.filin.reentrantLock.simple.ReentrantLockConcurrencyReaderWriter;
import ru.filin.simple.SimpleConcurrencyReaderWriter;

public class Main {
    public static void main(String[] args) {
        ReentrantLockConcurrencyReaderWriter reentrantLockConcurrencyReaderWriter = new ReentrantLockConcurrencyReaderWriter();
        try {
            reentrantLockConcurrencyReaderWriter.test();
        } catch (Exception e) {
            e.printStackTrace();
        }


//        SimpleConcurrencyReaderWriter simpleConcurrencyReaderWriter = new SimpleConcurrencyReaderWriter();
//        try {
//            simpleConcurrencyReaderWriter.test();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


}

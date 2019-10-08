package ru.filin.synchronizedReadWrite;

public class Main2 {
    public static void main(String[] args) throws Exception {
        CycleReentrantLockConcurrencyReaderWriter readerWriter = new CycleReentrantLockConcurrencyReaderWriter();
        readerWriter.test();
    }
}

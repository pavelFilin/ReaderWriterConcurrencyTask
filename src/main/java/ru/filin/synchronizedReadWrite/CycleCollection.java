package ru.filin.synchronizedReadWrite;

import lombok.Getter;

import javax.management.OperationsException;
import java.lang.reflect.Array;

public class CycleCollection<T> {
    private T[] array;

    @Getter
    private int capacity;

    private int writePointer;
    private int readPointer;

    private boolean fistCycle = true;

    public CycleCollection(Class<T> clazz, int capacity) {
        this.capacity = capacity;
        writePointer = -1;
        readPointer = -1;
        this.array = (T[]) Array.newInstance(clazz, capacity);
    }

    public int getWritePointer() {
        return writePointer;
    }

    public int getReadPointer() {
        return readPointer;
    }

    public T readNext() throws OperationsException {
        if (isEmpty()) {
            throw new OperationsException();
        }

//        System.out.println("rp = " + readPointer);

        readPointer++;
        if (readPointer == array.length) {
            readPointer = 0;
        }

        T result = array[readPointer];
        array[readPointer] = null;

        return result;
    }

    public void write(T obj) throws OperationsException {
        if (isFull()) {
            throw new OperationsException();
        }

//        System.out.println("wp = " + writePointer);

        pickNextNumber();

        array[writePointer] = obj;
    }

    private void pickNextNumber() {
        writePointer++;
        if (writePointer == array.length) {
            fistCycle = false;
            writePointer = 0;
        }
    }

    public boolean isEmpty() {
        return readPointer == writePointer;
    }

    public boolean isFull() {
//        System.out.println("is full " + ((readPointer == writePointer && writePointer != -1)));
//        return fistCycle ? readPointer == writePointer && writePointer != -1 : isFullSp();
        int temp = writePointer;
        writePointer++;
        if (writePointer == array.length) {
            fistCycle = false;
            writePointer = 0;
        }

        if (readPointer == -1) {
            boolean b = fistCycle ? readPointer == writePointer : readPointer + 1 == writePointer;
            writePointer = temp;
            return b;
        } else {
            boolean b = readPointer == writePointer;
            writePointer = temp;
            return b;
        }
    }

//    private boolean isFullSp() {
//        int temp = writePointer;
//        writePointer++;
//        if (writePointer == array.length) {
//            fistCycle = false;
//            writePointer = 0;
//        }
//
//        if (readPointer == -1) {
//            writePointer = temp;
//            return readPointer + 1 == writePointer;
//        } else {
//            writePointer = temp;
//            return readPointer == writePointer;
//        }
//    }
}

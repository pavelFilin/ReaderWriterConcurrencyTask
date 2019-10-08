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

        readPointer++;
        if (readPointer == array.length) {
            readPointer = 0;
        }

        return array[readPointer];
    }

    public void write(T obj) throws OperationsException {
        if (isFull()) {
            throw new OperationsException();
        }

        writePointer++;
        if (writePointer == array.length) {
            writePointer = 0;
        }

        array[writePointer] = obj;
    }

    public boolean isEmpty() {
        return readPointer == writePointer;
    }

    public boolean isFull() {
        return readPointer == writePointer && writePointer != -1;
    }


}

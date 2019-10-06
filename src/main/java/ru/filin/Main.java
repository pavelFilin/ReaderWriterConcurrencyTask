package ru.filin;

import ru.filin.simple.SimpleConcurrencyReaderWriter;

public class Main {
    public static void main(String[] args) {
        SimpleConcurrencyReaderWriter simpleConcurrencyReaderWriter = new SimpleConcurrencyReaderWriter();
        try {
            simpleConcurrencyReaderWriter.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

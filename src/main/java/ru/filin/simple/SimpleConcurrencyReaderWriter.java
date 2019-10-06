package ru.filin.simple;

import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SimpleConcurrencyReaderWriter {

    private final StringBuilder buffer = new StringBuilder();

    @Setter
    private boolean empty;

    public void test() throws Exception {
        List<SimpleReader> simpleReaders = initReaders();
        List<SimpleWriter> simpleWriters = initWriters();

        for (SimpleWriter simpleWriter : simpleWriters) {
            simpleWriter.start();
        }

        for (SimpleReader simpleReader : simpleReaders) {
            simpleReader.start();
        }

        for (SimpleWriter simpleWriter : simpleWriters) {
            simpleWriter.join();
        }

        for (SimpleReader simpleReader : simpleReaders) {
            simpleReader.join();
        }

    }

    private List<SimpleWriter> initWriters() {
        List<SimpleWriter> simpleWriters = new ArrayList<>();
        simpleWriters.add(new SimpleWriter("aaaa", 9, buffer));
        simpleWriters.add(new SimpleWriter("bbbb", 9, buffer));
        simpleWriters.add(new SimpleWriter("cccc", 9, buffer));
        simpleWriters.add(new SimpleWriter("dddd", 9, buffer));
        simpleWriters.add(new SimpleWriter("eeee", 9, buffer));
        return simpleWriters;
    }

    private List<SimpleReader> initReaders() {
        List<SimpleReader> simpleReaders = new LinkedList<>();
        simpleReaders.add(new SimpleReader(buffer));
        simpleReaders.add(new SimpleReader(buffer));
        return simpleReaders;
    }

}

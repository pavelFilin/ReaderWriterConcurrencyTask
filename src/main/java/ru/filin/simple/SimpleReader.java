package ru.filin.simple;

public class SimpleReader extends Thread {
    final StringBuilder buffer;

    public SimpleReader(StringBuilder buffer) {
        this.buffer = buffer;
    }

    public void read() {
        while (buffer.length() > 0) {
            int i = buffer.indexOf(" ");
            String result = buffer.substring(0, i);
            buffer.delete(0, i+1);
            System.out.println("Reader " + Thread.currentThread().getName() + "red" + " " + result);
        }
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                if (buffer.length() > 0) {
                    read();
                }
            }

        }
    }
}

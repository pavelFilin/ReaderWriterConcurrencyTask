package ru.filin.simple;


public class SimpleWriter extends Thread {
    private final StringBuilder buffer;

    private String message;
    private int count;


    public SimpleWriter(String message, int count, StringBuilder buffer) {
        this.message = message;
        this.count = count;
        this.buffer = buffer;
    }

    public void write() {
        for (int i = 0; i < count; i++) {
            System.out.println("Write " + Thread.currentThread().getName() + "red" + " " + message + i + " ");
            buffer.append(message).append(count).append(" ");
        }
    }

    @Override
    public void run() {
        while (true) {
            if (buffer.length()==0) {
                synchronized (buffer) {
                    write();
                }
            }
        }
    }
}

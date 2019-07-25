package com.abseliamov.multithread;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
    public static final String SOURCE_DIR = "src/main/resources/source";
    private final BlockingQueue<File> blockingQueue;
    private String threadName;

    public Producer(BlockingQueue<File> blockingQueue, String threadName) {
        this.blockingQueue = blockingQueue;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        File[] files = new File(SOURCE_DIR).listFiles();
        try {
            for (File file : files) {
                blockingQueue.put(file);
                System.out.println(Thread.currentThread().getName()
                        + ": put file " + file.getName() + " to queue.");
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}

package com.abseliamov.multithread;

import java.io.File;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<File> blockingQueue = new ArrayBlockingQueue<>(15);
        int numberConsumer = 4; //Runtime.getRuntime().availableProcessors();

        ExecutorService producerExecutor = Executors.newFixedThreadPool(1);
        ExecutorService consumerExecutor = Executors.newFixedThreadPool(numberConsumer);

        producerExecutor.submit(new Producer(blockingQueue, "P-R-O-D-U-C-E-R"));

        for (int i = 1; i <= numberConsumer; i++)
            consumerExecutor.submit(new Consumer(blockingQueue, "Consumer " + i));

        producerExecutor.shutdown();
        consumerExecutor.shutdown();
    }
}

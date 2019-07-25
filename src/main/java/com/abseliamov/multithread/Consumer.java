package com.abseliamov.multithread;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    public static final String BACKUP_DIR = "src/main/resources/backup/";
    private final BlockingQueue<File> blockingQueue;
    private String threadName;

    public Consumer(BlockingQueue<File> blockingQueue, String threadName) {
        this.blockingQueue = blockingQueue;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        try {
            while (blockingQueue.size() != 0) {
                File file = blockingQueue.take();
//                System.out.println(Thread.currentThread().getName() + ": take task.");
                String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                double sizeFile = file.length();
                File backup = new File(BACKUP_DIR + fileName + "-backup." + extension);
                copyFile(file, backup);
                System.out.println(Thread.currentThread().getName() + ": write file with name \'"
                        + fileName + "\' extension \'" + extension + "\' and size " + fileSize(sizeFile));
                Thread.sleep(1000);
//                System.out.println(Thread.currentThread().getName() + " complete task.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void copyFile(File source, File backup) {
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(source));
             BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(backup))) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error copyFile method " + e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String fileSize(double fileSize) {
        double bytes = 1024;
        if (fileSize < bytes)
            return String.format("%.1f", fileSize) + "bytes.";
        else if (fileSize >= bytes && fileSize < bytes * bytes)
            return String.format("%.2f", fileSize / bytes) + "kB.";
        else if (fileSize >= Math.pow(bytes, 2) && fileSize < Math.pow(bytes, 3))
            return String.format("%.2f", fileSize / Math.pow(bytes, 2)) + "Mb";
        else
            return String.format("%.2f", fileSize / Math.pow(bytes, 3)) + "Gb";
    }
}

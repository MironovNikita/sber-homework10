package org.example;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class FactorialPrinter extends Thread {
    private final BlockingQueue<Long> outputNumbersQueue;
    private final BlockingQueue<BigInteger> outputQueue;

    public FactorialPrinter(BlockingQueue<Long> outputNumbersQueue, BlockingQueue<BigInteger> outputQueue) {
        this.outputNumbersQueue = outputNumbersQueue;
        this.outputQueue = outputQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Long number = outputNumbersQueue.take();
                if (number == -1L) break;

                BigInteger factorial = outputQueue.take();
                System.out.println("Факториал числа " + number + " : " + factorial);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

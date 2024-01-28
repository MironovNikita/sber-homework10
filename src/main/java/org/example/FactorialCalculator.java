package org.example;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class FactorialCalculator extends Thread {
    private final BlockingQueue<Long> inputQueue;
    private final BlockingQueue<BigInteger> outputQueue;

    public FactorialCalculator(BlockingQueue<Long> inputQueue, BlockingQueue<BigInteger> outputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    private BigInteger factorial(long n) {
        BigInteger result = BigInteger.ONE;
        for (long i = 1; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    @Override
    public void run() {
        try {
            while (true) {
                long answer = inputQueue.take();
                if (answer == -1) {
                    outputQueue.put(BigInteger.valueOf(-1L));
                    break;
                }
                BigInteger factorial = factorial(answer);
                outputQueue.put(factorial);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

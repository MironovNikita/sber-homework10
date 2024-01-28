package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class FactorialCalculatorTest {

    @DisplayName("Проверка верного вычисления факториала и завершения потока")
    @Test
    void shouldCountNumberFactorialsFromQueuePerfectly() throws InterruptedException {
        BlockingQueue<Long> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<BigInteger> outputQueue = new LinkedBlockingQueue<>();
        FactorialCalculator calculator = new FactorialCalculator(inputQueue, outputQueue);

        inputQueue.put(5L);
        inputQueue.put(10L);
        inputQueue.put(-1L);

        calculator.start();
        calculator.join();

        assertEquals(BigInteger.valueOf(120), outputQueue.take());
        assertEquals(BigInteger.valueOf(3628800), outputQueue.take());
        assertEquals(BigInteger.valueOf(-1), outputQueue.take());
        assertFalse(calculator.isAlive());
    }
}
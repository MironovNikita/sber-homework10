package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        String filePath = "src/main/resources/numbers.txt";
        CreateFile.createFile(filePath);

        BlockingQueue<Long> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputNumbersQueue = new LinkedBlockingQueue<>();
        BlockingQueue<BigInteger> outputQueue = new LinkedBlockingQueue<>();

        FactorialCalculator calculator = new FactorialCalculator(inputQueue, outputQueue);
        FactorialPrinter printer = new FactorialPrinter(outputNumbersQueue, outputQueue);

        calculator.start();
        printer.start();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long number = Long.parseLong(line.trim());
                inputQueue.put(number);
                outputNumbersQueue.put(number);
            }
            inputQueue.put(-1L);
            outputNumbersQueue.put(-1L);
        } catch (IOException | InterruptedException e) {
            System.out.printf("Ошибка чтения файла по пути: %s%n", filePath);
        }
    }
}
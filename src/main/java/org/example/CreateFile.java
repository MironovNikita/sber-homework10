package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateFile {
    public static void createFile(String path) {
        try (FileWriter fileWriter = new FileWriter(path);) {
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int i = 1; i <= 50; i++) {
                printWriter.println(i);
            }

            System.out.println("Файл \"numbers.txt\" успешно создан!");

        } catch (IOException e) {
            System.out.printf("Ошибка при создании файла \"numbers.txt\": %s", e.getMessage());
        }
    }
}


# Многопоточная работа в Java – MultiThreading

### ✨Немного теории

🎭 **Многопоточность** - это возможность параллельного выполнения нескольких потоков кода внутри одной программы. Поток представляет собой отдельную последовательность инструкций, которые выполняются независимо от других потоков.

Многопоточность полезна во многих ситуациях. Несколько примеров:

1. **Повышение производительности**. Задачи могут быть разделены между несколькими потоками, что позволяет использовать доступные ресурсы системы эффективнее. Это особенно полезно при работе с задачами, которые можно распараллелить, например, обработка больших объёмов данных или выполнение вычислительно сложных алгоритмов.

2. **Отзывчивость интерфейса**. Отдельный поток может быть выделен для выполнения операций, которые могут блокировать основной поток выполнения, таких как сетевые операции или долгие вычисления. Это позволяет приложению оставаться отзывчивым и не блокировать пользовательский интерфейс.

3. **Асинхронное программирование**. Многопоточность позволяет создавать асинхронные задачи, которые выполняются параллельно, без необходимости ожидания завершения предыдущей задачи. Это особенно полезно при работе с вводом/выводом, сетевыми операциями или запросами к базе данных.

**`Java`** предоставляет богатый набор классов и методов для работы с многопоточностью, включая **`Thread`** и **`Runnable`**, синхронизацию с помощью ключевых слов **`synchronized`** и **`volatile`**, а также примитивы синхронизации, такие как **`Lock`** и **`Condition`**.

Пример создания потока с помощью **`Thread`**:
```java
class MyThread extends Thread {
    @Override
    public void run() {
        // Код, который будет выполняться в потоке
        System.out.println("Привет из потока!");
    }
}

public class Main {
    public static void main(String[] args) {
        // Создание и запуск потока
        MyThread myThread = new MyThread();
        myThread.start();

        // Остальной код программы
        System.out.println("Привет из главного потока!");
    }
}
```

Пример создания потока с помощью **`Runnable`**:
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        // Код, который будет выполняться в потоке
        System.out.println("Привет из потока!");
    }
}

public class Main {
    public static void main(String[] args) {
        // Создание объекта Runnable
        MyRunnable myRunnable = new MyRunnable();
        
        // Создание потока и передача ему объекта Runnable
        Thread myThread = new Thread(myRunnable);
        
        // Запуск потока
        myThread.start();

        // Остальной код программы
        System.out.println("Привет из главного потока!");
    }
}
```

### 🚀Практика

В данной работе представлена реализация 1 задания, связанного с вышеописанной темой:
1. Расчёт и вывод в консоль факториала числа
## Задание

Дан файл, содержащий несколько натуральных чисел от 1 до 50. Необходимо написать многопоточное приложение, которое параллельно рассчитает и выведет в консоль факториал для каждого числа из файла.

## Описание результатов

Для реализации данного задания был создан класс, создающий текстовый файл [**numbers.txt**](https://github.com/MironovNikita/sber-homework10/blob/main/src/main/resources/numbers.txt), содержащий в себе число от 1 до 50. Данный класс называется [**`CreateFile`**](https://github.com/MironovNikita/sber-homework10/blob/main/src/main/java/org/example/CreateFile.java). Он сохраняет файл с числами в папку [**resources**](https://github.com/MironovNikita/sber-homework10/tree/main/src/main/resources).

Сам файл после создания выглядит так:

![numbers](https://github.com/MironovNikita/sber-homework10/blob/main/res/numbers.png)

Затем были реализованы классы [**`FactorialCalculator`**](https://github.com/MironovNikita/sber-homework10/blob/main/src/main/java/org/example/FactorialCalculator.java) и [**`FactorialPrinter`**](https://github.com/MironovNikita/sber-homework10/blob/main/src/main/java/org/example/FactorialPrinter.java).

**`🧮 FactorialCalculator 🧮`** отвечает за подсчёт факториала представленных чисел в файле. В качестве полей содержит:
- _private final BlockingQueue<Long> inputQueue_ - считанные числа из файла;
- _private final BlockingQueue<BigInteger> outputQueue_ - рассчитанные факториалы чисел;

⏳ [**BlockingQueue**](https://docs.oracle.com/en/java/javase/20/docs/api/java.base/java/util/concurrent/BlockingQueue.html) ⏳ (очередь с блокировкой) - это интерфейс, который представляет собой коллекцию элементов с методами для добавления и извлечения элементов. Что отличает **BlockingQueue** от обычной очереди, это то, что он предоставляет блокировку при попытке извлечения элемента из пустой очереди или добавлении элемента в полную очередь.

**BlockingQueue** предоставляет следующие базовые методы:

- **`put()`**: метод блокирует выполнение потока, если очередь полна, и добавляет элемент в очередь, когда место освобождается;
- **`take()`**: метод блокирует выполнение потока, если очередь пуста, и извлекает и возвращает элемент из очереди, когда она становится не пустой.
- **`offer()`**: метод пытается добавить элемент в очередь, но если очередь полна, то он возвращает **`false`**.
- **`poll()`**: метод пытается извлечь и вернуть элемент из очереди, если очередь пуста, то он возвращает **`null`**.
**BlockingQueue** может быть использована для _синхронизации_ работы множества потоков, где одни потоки помещают элементы в очередь, а другие потоки извлекают эти элементы.

**`🖨️ FactorialPrinter 🖨️`** отвечает за вывод посчитанных факториалов чисел из файла в результате работы **`FactorialCalculator`**. В качестве полей содержит:
- _private final BlockingQueue<Long> outputNumbersQueue_ - значения чисел, считанных из файла (нужны для красивого и понятного вывода в консоль, для какого именно числа посчитан факториал);
- _private final BlockingQueue<BigInteger> outputQueue_ - рассчитанные факториалы чисел, которые нужно вывести;

### 💡 Важно 💡
Для _BlockingQueue<BigInteger> outputQueue_ применяется тип данных [**BigInteger**](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/math/BigInteger.html).

**BigInteger** - это класс, который предоставляет возможность работать с целыми числами произвольной длины. В отличие от примитивных типов данных, таких как **`int`** или **`long`**, **BigInteger** _не имеет ограничений на размер числа_, которое может хранить. Это означает, что можно использовать **BigInteger** для работы с очень большими числами, которые не помещаются в диапазон примитивных типов данных.

С помощью класса **BigInteger** можно выполнять различные арифметические операции, такие как сложение, вычитание, умножение и деление, с большими числами. Кроме того, класс **BigInteger** предоставляет методы для выполнения операций сравнения, возведения в степень, извлечения корня и многих других.

Однако, стоит помнить, что операции с **BigInteger** могут быть _более затратными по времени и памяти_, чем операции с примитивными типами данных.

####
Проведём небольшое тестирование в классе [**`Main`**](https://github.com/MironovNikita/sber-homework10/blob/main/src/main/java/org/example/Main.java). Осуществляем создание файла с помощью **`CreateFile.createFile()`**. Далее создаём необходимые блокирующие очереди, создаём экземпляры классов **`FactorialCalculator`** и **`FactorialPrinter`** и запускаем их.
```java
String filePath = "src/main/resources/numbers.txt";
        CreateFile.createFile(filePath);

        BlockingQueue<Long> inputQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Long> outputNumbersQueue = new LinkedBlockingQueue<>();
        BlockingQueue<BigInteger> outputQueue = new LinkedBlockingQueue<>();

        FactorialCalculator calculator = new FactorialCalculator(inputQueue, outputQueue);
        FactorialPrinter printer = new FactorialPrinter(outputNumbersQueue, outputQueue);

        calculator.start();
        printer.start();
```

Затем после заполнения очередей **`inputQueue`**, **`outputNumbersQueue`** и **`outputQueue`** поток **`FactorialCalculator`** начинает свою работу. Он бесконечно ожидает элементы из **`inputQueue`**, вычисляет факториал для каждого числа и помещает результат в **`outputQueue`**. Когда в **`inputQueue`** появляется маркер **-1L**, он помещает маркер **-1L** в **`outputQueue`** и завершает свою работу.

Как только поток **`FactorialCalculator`** завершает работу, поток **`FactorialPrinter`** стартует. Он также бесконечно ожидает элементы из **`outputNumbersQueue`** и **`outputQueue`**. Когда он получает **-1L** из **`outputNumbersQueue`**, это означает конец данных, и он завершает свою работу.

Результат выполнения в классе **`Main`**:

![mainResults](https://github.com/MironovNikita/sber-homework10/blob/main/res/mainResults.png)

Также для проверки подсчёта факториала чисел был написан простой тест в классе [**`FactorialCalculatorTest`**](https://github.com/MironovNikita/sber-homework10/blob/main/src/test/java/org/example/FactorialCalculatorTest.java). Он проверяет, записались ли верно посчитанные факториалы чисел в **`outputQueue`**. Также осуществляется проверка, завершается ли поток после выполнения своей задачи.

Результат выполнения теста:
![testResults](https://github.com/MironovNikita/sber-homework10/blob/main/res/testResults.png)

### 💡 Примечание

Тесты написаны с помощью библиотеки JUnit (*junit-jupiter*). Соответствующая зависимость добавлена в [**`pom.xml`**](https://github.com/MironovNikita/sber-homework10/blob/main/pom.xml) 

Версия зависимости прописаны в блоке *<properties> </properties>*:

```java
<junit.version>5.10.1</junit.version>
```

Результат сборки проекта:

```java
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.977 s
[INFO] Finished at: 2024-01-28T22:31:49+03:00
[INFO] ------------------------------------------------------------------------
```







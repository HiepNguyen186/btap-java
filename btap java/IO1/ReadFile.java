package IO1;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ReadFile {
    public static void main(String[] args) throws IOException, InterruptedException {
        String[] inputFiles = {"input1.txt", "input2.txt"};
        String outputFile = "output.txt";

        ExecutorService executor = Executors.newFixedThreadPool(inputFiles.length);
        List<Future<String>> futures = new ArrayList<>();

        for (String file : inputFiles) {
            futures.add(executor.submit(() -> readFile(file)));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (Future<String> future : futures) {
                writer.write(future.get());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
}

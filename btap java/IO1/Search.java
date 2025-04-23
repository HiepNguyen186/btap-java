package IO1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Search {
    public static void main(String[] args) throws InterruptedException {
        String[] inputFiles = {"input1.txt", "input2.txt"};
        String keyword = "example";

        ExecutorService executor = Executors.newFixedThreadPool(inputFiles.length);
        List<Future<Integer>> futures = new ArrayList<>();

        for (String file : inputFiles) {
            futures.add(executor.submit(() -> countKeywordInFile(file, keyword)));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        int totalOccurrences = 0;
        for (Future<Integer> future : futures) {
            try {
                totalOccurrences += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total occurrences of keyword \"" + keyword + "\": " + totalOccurrences);
    }

    public static int countKeywordInFile(String fileName, String keyword) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count += line.split(keyword, -1).length - 1;
            }
        }
        return count;
    }
}

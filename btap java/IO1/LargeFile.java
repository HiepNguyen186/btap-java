package IO1;

import java.io.*;
import java.util.concurrent.*;

public class LargeFile {
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB

    public static void main(String[] args) throws IOException, InterruptedException {
        String inputFile = "largefile.txt";
        String outputFile = "output_large.txt";

        RandomAccessFile file = new RandomAccessFile(inputFile, "r");
        long fileSize = file.length();
        int numChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);
        file.close();

        ExecutorService executor = Executors.newFixedThreadPool(numChunks);
        String[] results = new String[numChunks];

        for (int i = 0; i < numChunks; i++) {
            final int chunkIndex = i;
            executor.submit(() -> {
                try {
                    results[chunkIndex] = readChunk(inputFile, chunkIndex);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        }
    }

    public static String readChunk(String fileName, int chunkIndex) throws IOException {
        RandomAccessFile file = new RandomAccessFile(fileName, "r");
        file.seek(chunkIndex * CHUNK_SIZE);
        byte[] buffer = new byte[CHUNK_SIZE];
        int bytesRead = file.read(buffer);
        file.close();
        return new String(buffer, 0, bytesRead);
    }
}


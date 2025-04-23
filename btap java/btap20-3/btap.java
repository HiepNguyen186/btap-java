package baitap20_3;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadFiles {
    private static final String[] URLS = {
            "https://daotao.vku.udn.vn/sv/lich-hoc",
            "https://www.youtube.com/",
            "https://www.youtube.com/",
            "https://www.youtube.com/"
    };
    private static final String OUTPUT_DIR = "downloads";
    private static final int THREAD_COUNT = 5;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        new File(OUTPUT_DIR).mkdir(); 

        for (String url : URLS) {
            executor.execute(() -> downloadFile(url));
        }

        executor.shutdown();
    }

    private static void downloadFile(String urlString) {
        String fileName = OUTPUT_DIR + "/" + urlString.replaceAll("[:/.]", "_") + ".txt";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            System.out.println("Downloaded: " + urlString);
        } catch (IOException e) {
            System.err.println("Error downloading " + urlString + ": " + e.getMessage());
        }
    }
}
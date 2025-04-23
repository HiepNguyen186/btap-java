package IO1;

import org.jsoup.Jsoup;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class Scraper {
    private static final String[] URLS = {
        "https://example.com/article1",
        "https://example.com/article2",
        "https://example.com/article3",
        "https://example.com/article4",
        "https://example.com/article5"
    };
    private static final String OUTPUT_DIR = "downloads/";
    private static final String MERGED_FILE = "merged_output.txt";

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(URLS.length);

        // Tạo thư mục nếu chưa có
        new File(OUTPUT_DIR).mkdirs();

        // Tải nội dung từ các URL song song
        List<Future<String>> downloadFutures = new ArrayList<>();
        for (int i = 0; i < URLS.length; i++) {
            final int index = i;
            downloadFutures.add(executor.submit(() -> downloadWebContent(URLS[index], "file" + (index + 1) + ".txt")));
        }

        // Chờ các tệp tải xuống hoàn tất
        for (Future<String> future : downloadFutures) {
            try {
                System.out.println("Downloaded: " + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Xử lý các file song song (loại bỏ HTML, gộp vào file chung)
        List<Future<String>> processFutures = new ArrayList<>();
        for (int i = 0; i < URLS.length; i++) {
            final String fileName = "file" + (i + 1) + ".txt";
            processFutures.add(executor.submit(() -> cleanHtmlAndRead(OUTPUT_DIR + fileName)));
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);

        // Ghi nội dung đã làm sạch vào file merged_output.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MERGED_FILE))) {
            for (Future<String> future : processFutures) {
                writer.write(future.get());
                writer.newLine();
            }
        }
        System.out.println("Merged content saved to " + MERGED_FILE);
    }

    // Tải nội dung từ URL và lưu vào file
    private static String downloadWebContent(String urlString, String fileName) {
        String outputPath = OUTPUT_DIR + fileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
             BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(urlString).openStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            return outputPath;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed: " + urlString;
        }
    }

    // Đọc nội dung file và loại bỏ thẻ HTML
    private static String cleanHtmlAndRead(String filePath) {
        StringBuilder cleanedContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line, content = "";
            while ((line = reader.readLine()) != null) {
                content += line + "\n";
            }
            cleanedContent.append(Jsoup.parse(content).text());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cleanedContent.toString();
    }
}


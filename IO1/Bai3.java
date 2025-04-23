package IO1;

import java.io.*;

public class Bai3 {
    public static void main(String[] args) {
        String filePath = "example.txt";  // Tệp cần đếm

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lineCount = 0;
            while (reader.readLine() != null) {
                lineCount++;
            }

            System.out.println("Số dòng trong tệp: " + lineCount);
        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}


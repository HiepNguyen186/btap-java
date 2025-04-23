package IO1;

import java.io.*;

public class Bai1 {
    public static void main(String[] args) {
        String sourceFile = "source.txt";  // Tệp nguồn
        String destinationFile = "destination.txt";  // Tệp đích

        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            int byteData;
            while ((byteData = fis.read()) != -1) { // Đọc từng byte
                fos.write(byteData); // Ghi vào tệp đích
            }

            System.out.println("Sao chép tệp thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}


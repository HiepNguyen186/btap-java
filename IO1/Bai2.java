package IO1;

import java.io.*;

public class Bai2 {
    public static void main(String[] args) {
        String filePath = "output.txt";  // Tệp cần ghi

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            System.out.println("Nhập nội dung (gõ 'exit' để dừng):");
            String line;
            while (!(line = reader.readLine()).equalsIgnoreCase("exit")) {
                writer.write(line);
                writer.newLine();
            }

            System.out.println("Ghi tệp thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}


package a;
import java.io.*;

public class FileEdit {
    public static void main(String[] args) {
        String filePath = "example.txt"; // Đường dẫn tệp
        String additionalContent = "\nThông tin mới được chèn vào tệp.";
        
        try {
            // Đọc nội dung cũ từ tệp
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            
            // Chèn thêm thông tin mới
            content.append(additionalContent);
            
            // Ghi lại nội dung mới vào tệp
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(content.toString());
            }
            
            System.out.println("Tệp đã được cập nhật thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi xử lý tệp: " + e.getMessage());
        }
    }
}

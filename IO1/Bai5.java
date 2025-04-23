package IO1;

import java.io.File;

public class Bai5 {
    public static void main(String[] args) {
        String directoryPath = ".";  // Thư mục hiện tại

        File directory = new File(directoryPath);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                System.out.println("Danh sách tệp trong thư mục:");
                for (File file : files) {
                    System.out.println(file.getName());
                }
            } else {
                System.out.println("Không thể liệt kê tệp.");
            }
        } else {
            System.out.println("Đường dẫn không phải thư mục hợp lệ.");
        }
    }
}


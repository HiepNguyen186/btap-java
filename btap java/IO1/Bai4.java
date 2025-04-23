package IO1;

import java.io.*;

public class Bai4 {
    public static void main(String[] args) {
        String filePath = "numbers.dat";  // Tệp nhị phân chứa số nguyên

        // Ghi danh sách số nguyên vào tệp
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(filePath))) {
            int[] numbers = {10, 20, 30, 40, 50};
            for (int num : numbers) {
                dos.writeInt(num);
            }
            System.out.println("Ghi số nguyên vào tệp thành công!");
        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }

        // Đọc danh sách số nguyên từ tệp
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            System.out.println("Các số nguyên trong tệp:");
            while (dis.available() > 0) {
                System.out.print(dis.readInt() + " ");
            }
        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}


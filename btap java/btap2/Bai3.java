package b;

//Ban đầu, chương trình bị deadlock do hai luồng khóa tài nguyên theo thứ tự ngược nhau.
//Sau khi sửa lỗi bằng cách khóa tài nguyên theo cùng thứ tự, chương trình không còn bị deadlock.

public class Bai3 {
    public static void main(String[] args) {
        Thread highPriority = new Thread(() -> {
            while (true) {
                System.out.println("High Priority Running...");
                Thread.yield();  // Nhường CPU để luồng khác có thể chạy
            }
        });

        Thread lowPriority = new Thread(() -> {
            while (true) {
                System.out.println("Low Priority Running...");
            }
        });

        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        highPriority.start();
        lowPriority.start();
    }
}


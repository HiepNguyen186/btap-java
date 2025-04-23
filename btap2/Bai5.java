package b;

//Nếu không dùng volatile, có thể không nhìn thấy thay đổi do bộ nhớ đệm CPU.
//Khi thêm volatile, luồng nhìn thấy thay đổi ngay lập tức.

public class Bai5 {
    private static volatile boolean flag = false;

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            flag = true;
            System.out.println("Flag set to true");
        });

        Thread reader = new Thread(() -> {
            while (!flag) {
                // Nếu không dùng volatile, vòng lặp này có thể không bao giờ kết thúc
            }
            System.out.println("Reader detected flag change!");
        });

        reader.start();
        writer.start();
    }
}

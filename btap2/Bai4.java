package b;

//Ban đầu, luồng chạy vô hạn.
//Sau khi gọi interrupt(), luồng dừng lại sau 3 giây.

public class Bai4 {
    public static void main(String[] args) {
        Thread infiniteLoop = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Running...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted!");
                    return;
                }
            }
        });

        infiniteLoop.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}

        infiniteLoop.interrupt();
    }
}

package b;

//Ban đầu, chương trình bị deadlock do hai luồng khóa tài nguyên theo thứ tự ngược nhau.
//Sau khi sửa lỗi bằng cách khóa tài nguyên theo cùng thứ tự, chương trình không còn bị deadlock.

class Resource {
}

public class Bai2 {
    public static void main(String[] args) {
        Resource res1 = new Resource();
        Resource res2 = new Resource();

        Thread t1 = new Thread(() -> {
            synchronized (res1) {
                System.out.println("Thread 1 locked Resource 1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                synchronized (res2) {
                    System.out.println("Thread 1 locked Resource 2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (res1) {  // Đổi thứ tự khóa tài nguyên
                System.out.println("Thread 2 locked Resource 1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                synchronized (res2) {
                    System.out.println("Thread 2 locked Resource 2");
                }
            }
        });

        t1.start();
        t2.start();
    }
}


package a;

class TicketStore {
 private int tickets;
 
 public TicketStore(int initialTickets) {
     this.tickets = initialTickets;
 }

 public synchronized boolean buyTicket(String branchName) {
     if (tickets > 0) {
         System.out.println(branchName + "Bán vé số " + tickets);
         tickets--; 
         return true;
     } else {
         System.out.println(branchName + "Hết vé!");
         return false;
     }
 }

 public synchronized void addTickets(int amount) {
     tickets += amount;
     System.out.println("🚆 Nhà cung cấp thêm " + amount + " vé. Tổng vé: " + tickets);
 }
}

class TicketSupplier extends Thread {
 private TicketStore store;

 public TicketSupplier(TicketStore store) {
     this.store = store;
 }

 public void run() {
     while (true) {
         try {
             Thread.sleep(5000); 
             store.addTickets(3); 
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
 }
}

class TicketBranch extends Thread {
 private TicketStore store;
 private String branchName;

 public TicketBranch(TicketStore store, String branchName) {
     this.store = store;
     this.branchName = branchName;
 }

 public void run() {
     while (true) {
         try {
             Thread.sleep((int)(Math.random() * 2000 + 1000));
             if (!store.buyTicket(branchName)) {
                
                 break; 
             }
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }
 }
}

public class TrainTicketSystem {
 public static void main(String[] args) {
     TicketStore store = new TicketStore(10);

     TicketBranch branch1 = new TicketBranch(store, "Chi nhánh 1");
     TicketBranch branch2 = new TicketBranch(store, "Chi nhánh 2");

     TicketSupplier supplier = new TicketSupplier(store);

     branch1.start();
     branch2.start();
     supplier.start();
 }
}

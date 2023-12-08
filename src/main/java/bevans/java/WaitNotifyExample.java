package bevans.java;

public class WaitNotifyExample {
    private static final Object lock = new Object();
    private static boolean isOddTurn = true;

    public static void main(String[] args) {

        Thread oddThread = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                synchronized (lock) {
                    while (!isOddTurn) {
                        try {
                            lock.wait(); // Wait until it's the odd thread's turn
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.println("Odd: " + i);

                    isOddTurn = false; // Satisfy the waiting condition
                    lock.notify(); // Notify the even thread
                }
            }
        });

        Thread evenThread = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                synchronized (lock) {
                    while (isOddTurn) {
                        try {
                            lock.wait(); // Wait until it's the even thread's turn
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                    System.out.println("Even: " + i);

                    isOddTurn = true; // Satisfy the waiting condition
                    lock.notify(); // Notify the odd thread
                }
            }
        });

        oddThread.start();
        evenThread.start();
    }
}

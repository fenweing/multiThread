package thread;
public class BadThreads {

    static String message;

    private static class CorrectorThread
        extends Thread {

        public void run() {
            try {
                sleep(1000); 
            } catch (InterruptedException e) {}
            // Key statement 1:
            message = "Mares do eat oats."; 
        }
    }

    public static void main(String args[])
        throws InterruptedException {

        (new CorrectorThread()).start();
        message = "Mares do not eat oats.";
        System.out.println("==1");
        Thread.sleep(2000);
        System.out.println("==2");
        // Key statement 2:
        System.out.println(message);
    }
}
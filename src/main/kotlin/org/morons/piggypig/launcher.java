package org.morons.piggypig;

public class launcher implements Runnable {
    static String[] Args;

    public static void main(String[] args) {
        Args = args;
        new Thread(
                new launcher(),
                "Launcher Thread"
        ).start();
    }

    @Override
    public void run() {
        System.out.println("Running Thread: " + Thread.currentThread().getName());
        piggyMainApplication.main(Args);
    }
}
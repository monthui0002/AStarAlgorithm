package AStarAlgorithm;

import java.util.Timer;

public class TestThread implements Runnable{

    public TestThread(){
//        Thread thread = new Thread(this);
//        thread.start();
    }

    public static void main(String[] args) throws InterruptedException {
        TestThread thread1 = new TestThread();
        thread1.run();
//        thread1.wait(5);
        TestThread thread2 = new TestThread();
        thread2.run();
    }

    @Override
    public void run() {
        for(int i = 0; i < 3; i++){
            System.out.println(i);
            try {
                    Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

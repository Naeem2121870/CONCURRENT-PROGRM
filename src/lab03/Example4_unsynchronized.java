package lab03;

/* File: Example4_unsynchronized.java    Starting point CM3113 Lab3 Exercise 2 */
/**
 * This version of exercise 4 is unsynchronized. The version suffers from "lost
 * updates" to the shared static variable theTotalLoopCount. The effect of lost
 * updates is easily verified by running each of the CountingThread threads for
 * a fixed number of iterations and comparing the final sum of theLoopCount for
 * each thread and theTotalLoopCount
 */
public class Example4_unsynchronized {

    public static void main(String[] args) {
        long count1, count2, countTotal = 0;
        CountingThread t1 = new CountingThread("t1");
        CountingThread t2 = new CountingThread("t2");
        t1.start();
        t2.start();
        while(countTotal < 1000000000L){
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            count1 = t1.getThisCount();
            count2 = t2.getThisCount();
            countTotal = CountingThread.getSharedCount();
            System.out.println("Actual C1 + C2: " + (count1 + count2)
                    + ", Recorded C1 + C2 " + countTotal
                    + ", Lost: " + (count1 + count2 - countTotal));
        }
    }

    static class CountingThread extends Thread {

        private static long sharedCount = 0L;
        private long thisCount;

        public CountingThread(String name) {
            super(name);
            thisCount = 0L;
            this.setDaemon(true);
        }

        public void run() {
            for (;;) {
                increaseThisCount();
                increaseSharedCount();
            }
        }

        public void increaseThisCount() {
            thisCount++;
        }

        public static void increaseSharedCount() {
            sharedCount++;
        }

        public long getThisCount() {
            return thisCount;
        }

        public static long getSharedCount() {
            return sharedCount;
        }
    }

}

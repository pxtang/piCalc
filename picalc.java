import java.util.concurrent.ThreadLocalRandom;


public class picalc implements Runnable {
    static volatile int in = 0;
    static final int LOOP_MAX = 1000000000;
    static final int THREAD_COUNT = 2;

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        long startTime = System.currentTimeMillis();
        Thread[] ths = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            ths[i] = new Thread(new picalc());
            ths[i].start();
        }
        
        for (Thread th : ths) {
            try {
                th.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        double pi = ((double)(in))/(LOOP_MAX)*4;
        long endTime = System.currentTimeMillis();

        System.out.println("Calculated pi is: "+pi);
        System.out.format("Runtime for %d threads with %d calculations is %fs\n",THREAD_COUNT,LOOP_MAX,((endTime-startTime)/1000.0));
        double error = pi-Math.PI;
        System.out.format("Error is %f or %f percent\n",error,error/Math.PI*100);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int t_in = 0;

        for (int i = 0; i < LOOP_MAX/THREAD_COUNT; i++) {
            double x = ThreadLocalRandom.current().nextDouble();
            double y = ThreadLocalRandom.current().nextDouble();
            if (x*x+y*y <= 1)
                t_in++;
        }

        in += t_in;
    }

}

import java.util.ArrayList;
import java.util.Arrays;

public class HomeWork5 {

    static final int SIZE = 10000000;
    static final int H = SIZE/2;

    public static void main(String[] args) {
        singleThread();
        multiThreading();
    }

    private static void singleThread() {
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);
        long time1 = System.currentTimeMillis();
        calculate(arr, 0);
        long time2 = System.currentTimeMillis();
        System.out.println("Один поток: " + (time2 - time1));
    }

    private static void multiThreading(){
        float[] arr = new float[SIZE];
        Arrays.fill(arr, 1f);

        float[] halfOne = new float[H];
        float[] halfTwo = new float[H];

        long time1 = System.currentTimeMillis();
        System.arraycopy(arr, 0, halfOne, 0, H);
        System.arraycopy(arr, H, halfTwo, 0, H);

        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                calculate(halfOne, 0);
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                calculate(halfTwo, H);
            }
        });

        try {
            threadOne.start();
            threadTwo.start();
            System.arraycopy(halfOne, 0, arr, 0, H);
            System.arraycopy(halfTwo, 0, arr, H, H);
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        long time2 = System.currentTimeMillis();
        System.out.println("Mногопоточный режим: " + (time2 - time1)); }

    public static void calculate(float[] arr, int shift) {
        for (int i = 0; i < arr.length; i++ ){
            int k = shift + i;
            arr[i] = (float)(arr[i] * Math.sin(0.2f + k / 5) * Math.cos(0.2f + k / 5) * Math.cos(0.4f + k / 2));
        }
    }
}


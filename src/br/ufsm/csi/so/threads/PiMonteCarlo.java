package br.ufsm.csi.so.threads;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PiMonteCarlo {

    private Random rnd = new SecureRandom();

    public static void main(String[] args) throws InterruptedException {
        new PiMonteCarlo().calculaPi();
    }

    private void calculaPi() throws InterruptedException {
        List<CalculoPiParalelo> threads = new ArrayList<>();
        threads.add(new CalculoPiParalelo());
        threads.add(new CalculoPiParalelo());
        threads.add(new CalculoPiParalelo());
        for (CalculoPiParalelo calculoPiParalelo : threads) {
            new Thread(calculoPiParalelo).start();
        }
        long time = System.currentTimeMillis();
        while (true) {
            Thread.sleep(10000);
            long PTotal = 0;
            long PIn = 0;
            for (CalculoPiParalelo pi : threads) {
                PTotal += pi.PTotal;
                PIn += pi.PIn;
            }
            long interval = (System.currentTimeMillis() - time) / 1000;
            System.out.println("[" + interval + "] PI = " + (4 * ((double) PIn) / PTotal));
        }

    }

    private class CalculoPiParalelo implements Runnable {

        private long PTotal = 0;
        private long PIn = 0;

        @Override
        public void run() {
            while (true) {
                double x = rnd.nextDouble();
                double y = rnd.nextDouble();
                if (x * x + y * y <= 1) {
                    PIn++;
                }
                PTotal++;
            }
        }

        public long getPTotal() {
            return PTotal;
        }

        public long getPIn() {
            return PIn;
        }
    }

}

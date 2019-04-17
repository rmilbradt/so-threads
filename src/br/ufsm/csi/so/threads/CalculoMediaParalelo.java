package br.ufsm.csi.so.threads;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CalculoMediaParalelo {

    private List<Integer> numeros = new ArrayList<Integer>(TAMANHO);
    public static final int TAMANHO = 100000;
    public static final int NUM_THREADS = 5;

    public static void main(String[] args) throws InterruptedException {
        new CalculoMediaParalelo().calculaMedia();
    }

    private void calculaMedia() throws InterruptedException {
        //geração dos números aleatórios
        Random rnd = new Random();
        for (int i = 0; i < TAMANHO; i++) {
            numeros.add(Math.abs(rnd.nextInt(20)));
        }
        int soma = 0;
        Collection<ThreadCalculaSoma> threads = new ArrayList<>();
        for (int i = 0; i < NUM_THREADS; i++) {
            ThreadCalculaSoma t = new ThreadCalculaSoma(i);
            threads.add(t);
            new Thread(t).start();
        }
        Thread.sleep(1000);
        for (ThreadCalculaSoma t : threads) {
            while (!t.pronto) { }
            soma += t.subSoma;
        }
        System.out.println("Média = " + (soma / numeros.size()));
    }

    class ThreadCalculaSoma implements Runnable {

        private int numThread;
        private boolean pronto = false;
        private int subSoma = 0;

        public ThreadCalculaSoma(int numThread) {
            this.numThread = numThread;
        }

        @Override
        public void run() {
            for (int i = (numThread * TAMANHO / NUM_THREADS);
                 i < ((numThread + 1) * TAMANHO / NUM_THREADS);
                 i++) {
                int num = numeros.get(i);
                subSoma += num;
            }
            pronto = true;
        }
    }

}

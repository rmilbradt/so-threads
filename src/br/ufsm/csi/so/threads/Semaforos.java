package br.ufsm.csi.so.threads;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class Semaforos {

    private static int i;
    private static Set<Integer> setNumeros = new HashSet<>();
    private Semaphore mutex = new Semaphore(1);

    public static void main(String[] args) {
        new Semaforos().executaParalelo();
    }

    private void executaParalelo() {
        new Thread(new ThreadExecutavelA()).start();
        new Thread(new ThreadExecutavelB()).start();
    }

    class ThreadExecutavelA implements Runnable {

        @Override
        public void run() {
            while (true) {
                int numero;
                try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); return; }
                //System.out.println("[Thread A]: Acessou região crítica.");
                numero = ++i;
                //System.out.println("[Thread A]: Saiu da região crítica.");
                mutex.release();
                if (!setNumeros.add(numero)) {
                    System.out.println("[Thread A]: número " + numero + " repetido.");
                    break;
                }
                //try { Thread.sleep((int) (rnd.nextDouble() * 1000.0)); } catch (InterruptedException e) { }
            }
        }
    }

    class ThreadExecutavelB implements Runnable {

        private Random rnd = new Random();

        @Override
        public void run() {
            while (true) {
                int numero;
                try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); return; }
                //System.out.println("[Thread B]: Acessou região crítica.");
                numero = ++i;
                mutex.release();
                //System.out.println("[Thread B]: Saiu da região crítica.");
                if (!setNumeros.add(numero)) {
                    System.out.println("[Thread B]: número " + numero + " repetido.");
                    break;
                }
            //try { Thread.sleep((int) (rnd.nextDouble() * 1000.0)); } catch (InterruptedException e) { }
            }
        }
    }

}

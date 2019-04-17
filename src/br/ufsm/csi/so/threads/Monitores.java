package br.ufsm.csi.so.threads;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Monitores {

    private static int i;
    private static Set<Integer> setNumeros = new HashSet<>();
    private Object monitor = new Object();

    public static void main(String[] args) {
        new Monitores().executaParalelo();
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
                synchronized (Monitores.this) {
                    //System.out.println("[Thread A]: Acessou região crítica.");
                    numero = ++i;
                    //System.out.println("[Thread A]: Saiu da região crítica.");
                }
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
                synchronized (Monitores.this) {
                    //System.out.println("[Thread B]: Acessou região crítica.");
                    numero = ++i;
                    //System.out.println("[Thread B]: Saiu da região crítica.");
                }
                if (!setNumeros.add(numero)) {
                    System.out.println("[Thread B]: número " + numero + " repetido.");
                    break;
                }
                //try { Thread.sleep((int) (rnd.nextDouble() * 1000.0)); } catch (InterruptedException e) { }
            }
        }
    }

}

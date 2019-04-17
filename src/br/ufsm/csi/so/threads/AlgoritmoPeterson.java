package br.ufsm.csi.so.threads;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AlgoritmoPeterson {

    private static int i;
    private static Set<Integer> setNumeros = new HashSet<>();
    private boolean ca = false;
    private boolean cb = false;
    private char vez = 'A';

    public static void main(String[] args) {
        new AlgoritmoPeterson().executaParalelo();
    }

    private void executaParalelo() {
        new Thread(new ThreadExecutavelA()).start();
        new Thread(new ThreadExecutavelB()).start();
    }

    class ThreadExecutavelA implements Runnable {

        @Override
        public void run() {
            while (true) {
                ca = true;
                vez = 'B';
                while (cb && vez == 'B') { /*espera ocupada */ }
                System.out.println("[Thread A]: Acessou região crítica.");
                int numero = ++i;
                System.out.println("[Thread A]: Saiu da região crítica.");
                ca = false;
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
                cb = true;
                vez = 'A';
                while (ca && vez == 'A') { /*espera ocupada */ }
                System.out.println("[Thread B]: Acessou região crítica.");
                int numero = ++i;
                System.out.println("[Thread B]: Saiu da região crítica.");
                ca = false;
                if (!setNumeros.add(numero)) {
                    System.out.println("[Thread B]: número " + numero + " repetido.");
                    break;
                }
                //try { Thread.sleep((int) (rnd.nextDouble() * 1000.0)); } catch (InterruptedException e) { }
            }
        }
    }

}

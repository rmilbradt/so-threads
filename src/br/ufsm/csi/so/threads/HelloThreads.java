package br.ufsm.csi.so.threads;

import java.util.HashSet;
import java.util.Set;

public class HelloThreads {

    private static int i;
    private static Set<Integer> setNumeros = new HashSet<>();
    private char vez = 'A';

    public static void main(String[] args) {
        new HelloThreads().executaParalelo();
    }

    private void executaParalelo() {
        new Thread(new ThreadExecutavel('A')).start();
        new Thread(new ThreadExecutavel('B')).start();
    }

    class ThreadExecutavel implements Runnable {

        private char numeroThread;
        private char outroNumero;

        ThreadExecutavel(char numeroThread) {
            this.numeroThread = numeroThread;
            if (numeroThread == 'A') {
                outroNumero = 'B';
            } else {
                outroNumero = 'A';
            }
        }

        @Override
        public void run() {
            while (true) {
                while (vez == outroNumero) { /*espera ocupada */ }
                int numero = ++i;
                vez = outroNumero;
                //System.out.println("Thread " + numeroThread + ": i = " + numero);
                if (!setNumeros.add(numero)) {
                    System.out.println("Thread " + numeroThread + ": número " + numero + " repetido.");
                    break;
                }

            }
        }
    }

}

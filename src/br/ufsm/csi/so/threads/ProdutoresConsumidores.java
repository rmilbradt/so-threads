package br.ufsm.csi.so.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProdutoresConsumidores {

    private final int TAM_MAX_BUFFER = 10;
    private final Object cheio = new Object();
    private final Object vazio = new Object();
    private final List<Integer> buffer = new ArrayList<>(TAM_MAX_BUFFER);

    public static void main(String[] args) {
        new ProdutoresConsumidores().inicia();
    }

    private void inicia() {
        new Thread(new Produtor()).start();
        new Thread(new Consumidor()).start();
    }

    private class Produtor implements Runnable {

        private Random rnd = new Random();

        @Override
        public void run() {
            while (true) {

                if (buffer.size() == TAM_MAX_BUFFER) {
                    synchronized (cheio) {
                        System.out.println("[PROD] Buffer cheio, dormindo...");
                        try {
                            cheio.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                        System.out.println("[PROD] Acordou.");
                    }
                }

                int numero = rnd.nextInt();
                System.out.println("[PROD] Gerou número " + numero);
                synchronized (buffer) {
                    buffer.add(numero);
                }
                synchronized (vazio) {
                    if (buffer.size() == 1) {
                        System.out.println("[PROD] Acordando o consumidor...");
                        vazio.notify();
                    }
                }
            }
        }
    }

    private class Consumidor implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (vazio) {
                    if (buffer.size() == 0) {
                        System.out.println("[CONS] Buffer vazio, dormindo...");
                        try { vazio.wait(); } catch (InterruptedException e) { e.printStackTrace(); return; }
                        System.out.println("[CONS] Acordou...");
                    }
                }
                synchronized (buffer) {
                    int numero = buffer.remove(0);
                    System.out.println("[CONS] Consumiu número " + numero + ".");
                }

                if (buffer.size() == TAM_MAX_BUFFER - 1) {
                    synchronized (cheio) {
                        System.out.println("[CONS] Acordando o produtor...");
                        cheio.notify();
                    }
                }

            }
        }
    }




}

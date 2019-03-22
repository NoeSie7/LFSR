package lfsr;

import java.util.ArrayList;
import java.util.Random;

public class Lfsr {

    private ArrayList<Integer> lfsr1; // 19 bits de tamanio
    private ArrayList<Integer> lfsr2; // 22 bits
    private ArrayList<Integer> lfsr3; // 23 bits
    private ArrayList<Integer> aux;
    private Random rand;

    public Lfsr() {
        this.lfsr1 = null;
        this.lfsr2 = null;
        this.lfsr3 = null;
        this.rand = null;
    }

    public Lfsr(String cad_entrada) {
        this.rand = new Random();

        this.lfsr1 = new ArrayList<>();
        this.lfsr2 = new ArrayList<>();
        this.lfsr3 = new ArrayList<>();

        this.lfsr1.ensureCapacity(19);
        this.lfsr2.ensureCapacity(22);
        this.lfsr3.ensureCapacity(23);

        this.generarSemilla();
        this.xor();
        this.clockControl();
    }

    public void generarSemilla() {
        System.out.println("SIZE" + this.lfsr1.size());
        for (int i = 0; i < 19; ++i) {
            final int n = this.rand.nextInt(2);
            this.lfsr1.add(n);
        }
        System.out.println("SIZE" + this.lfsr1.size());
        for (int i = 0; i < 22; ++i) {
            final int n = this.rand.nextInt(2);
            this.lfsr2.add(n);
        }
        for (int i = 0; i < 23; ++i) {
            final int n = this.rand.nextInt(2);
            this.lfsr3.add(n);
        }
    }

    public void xor() {
        System.out.println("\n Xor \n");
        final int xorlfsr1 = this.lfsr1.get(0) ^ this.lfsr1.get(1) ^ this.lfsr1.get(2) ^ this.lfsr1.get(5);
        final int xorlfsr2 = this.lfsr2.get(0) ^ this.lfsr2.get(1) ^ this.lfsr2.get(2);
        final int xorlfsr3 = this.lfsr3.get(0) ^ this.lfsr3.get(1) ^ this.lfsr3.get(2) ^ this.lfsr3.get(15);
        System.out.println("XOR1:" + xorlfsr1);
        System.out.println("XOR2:" + xorlfsr2);
        System.out.println("XOR3:" + xorlfsr3);

    }

    public void clockControl() {
        this.aux = new ArrayList<>(3);
        this.aux.add(0, this.lfsr1.get(11 - 1));
        this.aux.add(1, this.lfsr2.get(12 - 1));
        this.aux.add(2, this.lfsr3.get(13 - 1));
        System.out.println("\nClockControl\n");
        for (int i = 0; i < this.aux.size(); ++i) {
            final int x = i + 11;
            System.out.println("Pos:" + "[" + x + "] ->" + this.aux.get(i));
        }

        if (((this.aux.get(0) == 1) && (this.aux.get(1) == 1) && (this.aux.get(2) == 1))
                || ((this.aux.get(0) == 0) && (this.aux.get(1) == 0) && (this.aux.get(2) == 0))) {

            if ((this.aux.get(0) == 1) && (this.aux.get(1) == 1) && (this.aux.get(2) == 1)) {
                this.aux.set(0, 0);
                this.aux.set(1, 0);
                this.aux.set(2, 0);
            } else {
                this.aux.set(0, 1);
                this.aux.set(1, 1);
                this.aux.set(2, 1);
            }
        } else if (((this.aux.get(0) == 1) && (this.aux.get(1) == 1) && (this.aux.get(2) == 0))
                || ((this.aux.get(0) == 0) && (this.aux.get(1) == 0) && (this.aux.get(2) == 1))) {

            if (((this.aux.get(0) == 1) && (this.aux.get(1) == 1) && (this.aux.get(2) == 0))) {
                this.aux.set(0, 0);
                this.aux.set(1, 0);
                this.aux.set(2, 1);
            } else {
                this.aux.set(0, 1);
                this.aux.set(1, 1);
                this.aux.set(2, 0);
            }

        } else if (((this.aux.get(0) == 0) && (this.aux.get(1) == 1) && (this.aux.get(2) == 1))
                || ((this.aux.get(0) == 1) && (this.aux.get(1) == 0) && (this.aux.get(2) == 0))) {

            if (((this.aux.get(0) == 0) && (this.aux.get(1) == 1) && (this.aux.get(2) == 1))) {
                this.aux.set(0, 1);
                this.aux.set(1, 0);
                this.aux.set(2, 0);
            } else {
                this.aux.set(0, 0);
                this.aux.set(1, 1);
                this.aux.set(2, 1);
            }

        } else if (((this.aux.get(0) == 1) && (this.aux.get(1) == 0) && (this.aux.get(2) == 1))
                || ((this.aux.get(0) == 0) && (this.aux.get(1) == 1) && (this.aux.get(2) == 0))) {

            if (((this.aux.get(0) == 1) && (this.aux.get(1) == 0) && (this.aux.get(2) == 1))) {
                this.aux.set(0, 0);
                this.aux.set(1, 1);
                this.aux.set(2, 0);
            } else {
                this.aux.set(0, 1);
                this.aux.set(1, 0);
                this.aux.set(2, 1);
            }
        }
        System.out.println("\nClockControlAfter\n");
        for (int i = 0; i < this.aux.size(); ++i) {
            final int x = i + 11;
            System.out.println("Pos:" + "[" + x + "] ->" + this.aux.get(i));
        }
    }

    public void mostrar() {
        System.out.println("\n Array1");
        for (int i = 0; i < 19; ++i) {
            System.out.println("Pos: [" + i + "] ->" + this.lfsr1.get(i));
        }
        System.out.println("\n Array2 \n");
        for (int i = 0; i < 22; ++i) {
            System.out.println("Pos: [" + i + "] ->" + this.lfsr2.get(i));
        }
        System.out.println(" \n Array3 \n");
        for (int i = 0; i < 23; ++i) {
            System.out.println("Pos: [" + i + "] ->" + this.lfsr3.get(i));
        }
    }

}

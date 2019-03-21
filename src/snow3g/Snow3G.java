package snow3g;

import java.util.ArrayList;
import java.util.Random;

public class Snow3G {

    private ArrayList<Integer> lfsr;
    private ArrayList<Integer> r1;
    private ArrayList<Integer> r2;
    private ArrayList<Integer> r3;
    private Random rand;

    Snow3G() {
        this.lfsr = null;
        this.rand = null;
        this.r1 = null;
        this.r2 = null;
        this.r3 = null;
    }

    Snow3G(String aux) {
        this.lfsr = new ArrayList<>();
        this.lfsr.ensureCapacity(16);
        this.r1 = new ArrayList<>();
        this.r1.ensureCapacity(32);
        this.r2 = new ArrayList<>();
        this.r2.ensureCapacity(32);
        this.r3 = new ArrayList<>();
        this.r3.ensureCapacity(32);
        this.generarSemilla();
    }

    public void generarSemilla() {
        for (int i = 0; i < 16; ++i) {
            final int n = this.rand.nextInt(2);
            this.lfsr.add(n);
        }
    }

    public void fsm() {
        // 3 registros de 32 bits

    }

    public void sBoxes() {

    }
}

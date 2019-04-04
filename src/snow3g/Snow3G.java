package snow3g;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Snow3G {

	private ArrayList<ArrayList<Integer>> lfsr1; // array con los 32 bits del LFSR
    private ArrayList<Integer> lfsr;// Longitud 16 del LFSR y cada elemento 32 bits
    private ArrayList<Integer> r1;
    private ArrayList<Integer> r2;
    private ArrayList<Integer> r3;
    private String[][] sbox;
   // private char SR[];
    final int SIZE_LFSR = 16;
    final int SIZE_R = 32;
    private Random rand;

    public Snow3G() {
        this.lfsr = null;
        this.lfsr1 = null;
        this.rand = null;
        this.r1 = null;
        this.r2 = null;
        this.r3 = null;
        this.sbox = null;
    }

    public Snow3G(String aux) {
    	
    	//Semilla aleatoria
        this.rand = new Random();
        
        //LFSR
        this.lfsr = new ArrayList<>();//32 bits
        this.lfsr1 = new ArrayList<ArrayList<Integer>>(16);// 16 bits
        //Generamos R Box
        this.r1 = new ArrayList<>();
        this.r2 = new ArrayList<>();
        this.r3 = new ArrayList<>();
        this.sbox = new String[16][17];
        
        // Generamos semillas de R y LFSR
        this.generarSemilla(this.lfsr1);
        this.generarSemillaR();
//        this.mostrarR(this.r1);
//        this.mostrarR(this.r2);
//        this.mostrarR(this.r3);

  
        final int n = this.xor();
        this.feedback(n);
        this.mostrarLFSR(this.lfsr1);
        this.readTxt();
        
        // Creamos SBoxes
//        this.sBoxes();
        
    }

    public void generarSemillaR(){
    	System.out.println("Entramos R");
    	for (int i = 0; i < SIZE_R ; i++) {
	      // final int n = this.rand.nextInt(2);
	      this.r1.add(numeroAleatorio());
	      this.r2.add(numeroAleatorio());
	      this.r3.add(numeroAleatorio());
      	}
    }
    
    public int numeroAleatorio() {
    	final int n = this.rand.nextInt(2);
    	return n;
    }
    
    public void generarSemilla(ArrayList<ArrayList<Integer>> p) {
    	System.out.println("LLEGA");
//        for (int i = 0; i < 32 ; i++) {
//            final int n = this.rand.nextInt(2);
//            this.lfsr.add(n);
//        }
       
       for (int i = 0; i < SIZE_LFSR ; i++) {				// Añadimos al LFSR
    	   this.lfsr = new ArrayList<Integer>();
    	   for (int j = 0; j < 32 ; j++) {                      // Generamos por cada posicion del lfsr un array de 32 bits
               final int n = this.rand.nextInt(2);
               this.lfsr.add(n);
           }
    	   p.add(this.lfsr);
       }
//       System.out.println("Primer elemento" + p.get(0).get(0) + p.get(1).get(1));
       System.out.println("Mostrando la semilla generada");
        this.mostrarLFSR(p);
    }

    public void generarFsm() {
        for (int i = 0; i < SIZE_R; i++) {
            this.r1.add(i);
        }
    }

    public int xor() {
        System.out.println("\n Xor \n");
        int xorlfsr = -1;
        // final int xorlfsr = this.lfsr.get(0) ^ this.lfsr.get(2) ^ this.lfsr.get(3) ^ this.lfsr.get(5);
        for (int i = 0; i < this.lfsr1.size(); i++) {
			xorlfsr = this.lfsr1.get(0).get(i) ^ this.lfsr1.get(2).get(i) ^ this.lfsr1.get(3).get(i) ^ this.lfsr1.get(5).get(i);
		}
        System.out.println("XOR:" + xorlfsr);
        return xorlfsr;
    }

    public void feedback(int num) {
        System.out.println("FEEDBACK" + num);
        int aux, aux1;
//        aux = this.lfsr.get(0);
//        for(int i = 0 ; i < this.lfsr.size() - 1; ++i) {
//        	aux1 = this.lfsr.get(i + 1);
//        	this.lfsr.set(i + 1, aux);
//        	// System.out.println("AUX: " + aux + " UAX1: "+ aux1);
//        	aux = aux1;
//        }
//        this.lfsr.set(0, num);
        aux = this.lfsr1.get(0).get(0);
        for (int i = 0; i < this.lfsr1.size(); i++) { 
            for (int j = 0; j < this.lfsr1.get(i).size() - 1; j++) { 
                aux1 = this.lfsr1.get(i).get(j + 1);
                this.lfsr1.get(i).set(j + 1, aux);
                aux = aux1;
            }  
        } 
        this.lfsr1.get(0).set(0, num);
    }

    public void fsm() {
        // 3 registros de 32 bits

    }

    public void mostrarLFSR(ArrayList <ArrayList<Integer>> vec) {
        System.out.println("\n Mosstrar \n");
//        for (int i = 0; i < vec.size(); ++i) {
//            System.out.println("Pos: [" + i + "] ->" + vec.get(i));
//        }
        for (int i = 0; i < vec.size(); i++) { 
            for (int j = 0; j < vec.get(i).size(); j++) { 
                System.out.print(vec.get(i).get(j) + " "); 
            } 
            System.out.println(); 
        } 

    }
    
    public void mostrarR(ArrayList<Integer> r){
    	System.out.println("\n Mostrar R \n");
    	for (int i = 0; i < r.size(); i++) {
			System.out.println("POS: [" + i + "] -> " + r.get(i));
		}
    	System.out.println("");
    }
    
    public void readTxt(){
        try {
            //ruta de tu archivo
            String ruta = "./src/txt/sbox.txt";
            BufferedReader br = getBuffered(ruta);
            //leemos la primera linea
            String linea =  br.readLine();
            //creamos la matriz vacia
            //contador
            int contador = 0;
            while(linea != null){
                String[] values = linea.split(",");
                // System.out.println(values[0]);
//                System.out.println("SIZE"+ values[0].length());
                //recorremos el arrar de string
                for (int i = 0; i < 17; i++) {
                    //se obtiene el primer caracter de el arreglo de strings
                    sbox[contador][i] = values[i];
                    		//values[i].charAt(0);
                }
                contador++;
                linea = br.readLine();
            }
            
            // Mostramos sbox
            System.out.println("SBOX \n");
            for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 17; j++) {
					System.out.print(sbox[i][j] + " ");
				}
				System.out.println("");
			}
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    
    public BufferedReader getBuffered(String link){

        FileReader lector  = null;
        BufferedReader br = null;
        try {
             File Arch=new File(link);
            if(!Arch.exists()){
               System.out.println("No existe el archivo");
            }else{
               lector = new FileReader(link);
               br = new BufferedReader(lector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return br;
    }
}

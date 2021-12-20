package sda.lab2;

import java.util.PriorityQueue;
import java.util.Scanner;

public class kuis2 {

    public static void main(String[] args){
        PriorityQueue<Mahasiswa> antrian = new PriorityQueue<Mahasiswa>();
        Scanner input = new Scanner(System.in);

//        System.out.print("Masukkan jumlah mahasiswa: ");
//        int n = input.nextInt();

//        for(int i=0;i<n;i++){
//            System.out.println("Mahasiswa "+ (i+1));
//            System.out.println("Masukkan Nama: ");
//            String nama = input.next();
//            System.out.println("Masukkan NPM: ");
//            String npm = input.nextLine();
//            System.out.println("Masukkan IPK: ");
//            double ipk = input.nextDouble();
//
//            antrian.add(new Mahasiswa(nama, npm, ipk));
//        }

        antrian.add(new Mahasiswa("Rohit","1236", 3.8));
        antrian.add(new Mahasiswa("sulthan","1234", 3.7));
        antrian.add(new Mahasiswa("Sulthan","1235", 3.7));
        antrian.add(new Mahasiswa("afif","1238", 3.8));
        antrian.add(new Mahasiswa("kapoor","4267", 3.9));
        antrian.add(new Mahasiswa("ruslan","2224", 3.2));


        while(!antrian.isEmpty()){
            System.out.println(antrian.poll());
        }

    }


}

class Mahasiswa implements Comparable<Mahasiswa> {
    String nama;
    String npm;
    double ipk;

    public Mahasiswa(String nama, String npm, double ipk){
        this.nama = nama;
        this.npm = npm;
        this.ipk = ipk;
    }

    @Override
    public int compareTo(Mahasiswa o) {
        if(this.ipk==o.ipk){
            if(this.nama.equals(o.nama)){
                return this.npm.compareTo(o.npm);
            }
            return this.nama.compareToIgnoreCase(o.nama);
        }
        return this.ipk > o.ipk? -1 : 1;
    }


    @Override
    public String toString() {
        return this.nama + " - " + this.npm + " - " + this.ipk;
    }
}

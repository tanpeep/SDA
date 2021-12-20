package sda.lab4;

import java.util.Arrays;

public class kuis4 {

    public static void main(String[] args) {
        int[] arr1 = { 4, 5, 9 };
        int[] arr2 = { 1, 3, 7, 8 };
        int[] arr3 = { 2, 6 };
        int[] result = merge3(arr1, arr2, arr3);
        System.out.println(Arrays.toString(result));
    }

    /**
     * method ini akan melakukan operasi sebanyak x kali dengan n<x<2n
     * sehingga kompleksitasnya hanya O(n)
     * @param arr1
     * @param arr2
     * @param arr3
     * @return
     */
    static int[] merge3(int[] arr1, int[] arr2, int[] arr3) {
        int[] res = new int[arr1.length + arr2.length+ arr3.length];
        int[] arr12 = new int[arr1.length+arr2.length];

// -------------------------- Menggabungkan array 1 dan 2 -----------------------
        int i=0,j=0,k=0;
        while(i<arr1.length && j<arr2.length){
            if(arr1[i]<arr2[j]) {
                arr12[k] = arr1[i];
                i++;
            }
            else {
                arr12[k] = arr2[j];
                j++;
            }
            k++;
        }

        // dua while dibawah untuk menambahkan sisa diantara dua matriks yang digabungkan
        // pada while sebelumnya
        while(i<arr1.length){
            arr12[k] = arr1[i];
            i++;
            k++;
        }
        while(j<arr2.length){
            arr12[k] = arr2[j];
            j++;
            k++;
        }

// --------------------------- Menggabungkan array sebelumnya dengan array 3 ------------------
        i=0;j=0;k=0;

        while(i<arr12.length && j<arr3.length){
            if(arr12[i]<arr3[j]) {
                res[k] = arr12[i];
                i++;
            }
            else {
                res[k] = arr3[j];
                j++;
            }
            k++;
        }

        while(i<arr12.length){
            res[k] = arr12[i];
            i++;
            k++;
        }
        while(j<arr3.length) {
            res[k] = arr3[j];
            j++;
            k++;
        }
// ------------------------------------------------------------------

        return res;
    }
}

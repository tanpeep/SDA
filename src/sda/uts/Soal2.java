/* Tuliskan Nama, NPM, dan Kelasmu di sini:
 ** Nama Lengkap: Sulthan Afif Althaf
 ** NPM         : 2006473863
 ** Kelas       : D
 */

//package sda.uts;

import java.util.Scanner;
import java.util.Stack;

public class Soal2{

    /*TO DO*/
    /*Letakkan ketiga baris ini pada main method atau pada constructor,
     *sehingga program dapat berjalan (lolos test cases level 1)*/
    public static void main(String[] args) {
        Scanner ss = new Scanner(System.in);
        while(ss.hasNext())
            processThis(ss.nextLine());
    }

    /*TO DO
     *
     * Lanjutkan implementasi method-method berikut ini
     * (boleh memodifikasi lebih dari 1 method)
     */
    public static void processThis(String stringinput) {
        String[] tokens = stringinput.split(" ");
        Stack<String> s = new Stack<String>();
        s.push("");
        for (String t : tokens) {
            if (t.equals("(") || t.equals("^") || ((isKurung(t) || isOperator(t)) && !s.isEmpty() && isHigher(t,s.peek())))
                s.push(t);
            else if (isOperator(t)) {
                while (!isHigher(t, s.peek())) {
                    String v = s.pop();
                    System.out.print(v);
                }
                s.push(t);
            }
            else if(t.equals(")")){
                while (!s.isEmpty() && !s.peek().equals("(")){
                    System.out.print(s.pop());
                }
                if(!s.isEmpty()) s.pop();
            }
            else System.out.print(t);
        }
        while (!s.isEmpty())
            if(s.peek().equals("(")) s.pop();
            else System.out.print(s.pop());
        System.out.println();
    }

    public static boolean isOperator(String t) {
        return t.equals("+") || t.equals("*") || t.equals("-") || t.equals("/") || t.equals("^");
    }

    public static boolean isKurung(String t){
        return t.equals("(") || t.equals(")");
    }

    public static boolean isHigher(String x, String y) {
        if (y.equals("")) return true;
        if (orderOf(x) > orderOf(y)) return true;
        return false;
    }

    public static int orderOf(String op) {
        int ret = switch(op){
            case "+" -> 1;
            case "-" -> 1;
            case "*" -> 2;
            case "/" -> 2;
            case "^" -> 3;
            default -> 0;
        };
        return ret;
    }


}

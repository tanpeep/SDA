/**
 * @Author : Sulthan Afif Althaf - 2006473863
 */

//package sda.lab4;

import org.w3c.dom.Node;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

// TODO - class untuk Lantai
class Lantai {

    String tingkat;
    Lantai next;
    Lantai bef;

    public Lantai(String lt, Lantai n){
        tingkat = lt;
        next = n;
    }

    public String getValue(){
        return tingkat;
    }

}


// TODO - class untuk Gedung
class Gedung {
    Lantai head,now;

    public Gedung() {
        head = null;
        now = head;
    }

    public void bangun(String input){
        // TODO - handle BANGUN
        if(head==null) {
            head = new Lantai(input, null);
            now = head;
            head.bef = null;
        }
        else {
            Lantai next = null;
            if(now.next!=null) {
                next = now.next;
                now.next = new Lantai(input, next);
                now.next.next.bef = now.next;
            }
            else
                now.next = new Lantai(input,null);
            Lantai temp = now;
            now = now.next;
            now.bef = temp;
        }
    }

    public String lift(String kemana){

        if(now==null) return "";

        if(kemana.equals("BAWAH") && now.bef!=null){
            now = now.bef;
        }
        else if(kemana.equals("ATAS") && now.next!=null)
            now = now.next;


        return now.getValue();
    }

    public String  hancurkan(){
        // TODO - handle HANCURKAN

        if(now==null) return "";


        String ret = now.getValue();

        if(now.bef!=null){
            now.bef.next = now.next;
            if(now.next!=null)
                now.next.bef = now.bef;
            now = now.bef;
        }
        else if (now.next!=null){
            now = now.next;
            head = now;
            now.bef = null;
        }
        else {
            now = null;
            head = null;
        }

        return ret;
    }

    public void gabung(Lantai baru){
        if(now==null){
            now = baru;
            head = baru;
            if(baru!=null) baru.bef = null;
        }
        else {
            Lantai temp = now;
            while(temp.next!=null)
                temp = temp.next;
            temp.next = baru;
            if(baru!=null) baru.bef = temp;
        }
    }


}

public class Lab4 {
    private static InputReader in;
    public static PrintWriter out;

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        Map<String,Gedung> namaGedung = new HashMap<>();

        // N operations
        int N = in.nextInt();
        String cmd;

        // TODO - handle inputs
        for (int zz = 0; zz < N; zz++) {

            cmd = in.next();


            if(cmd.equals("FONDASI")){
                String A = in.next();
                namaGedung.put(A,new Gedung());
            }
            else if(cmd.equals("BANGUN")){
                String A = in.next();
                String X = in.next();
                Gedung gedung = namaGedung.get(A);
                gedung.bangun(X);
            }
            else if(cmd.equals("LIFT")){
                String A = in.next();
                String X = in.next();
                Gedung gedung = namaGedung.get(A);
                out.println(gedung.lift(X));
            }
            else if(cmd.equals("SKETSA")){
                String A = in.next();
                Gedung gedung = namaGedung.get(A);
                Lantai temp = gedung.head;
                while(temp!=null){
                    out.print(temp.getValue());
                    temp = temp.next;
                }
                out.println();
            }
            else if(cmd.equals("TIMPA")){
                String A = in.next();
                String B = in.next();
                Gedung gedungA = namaGedung.get(A);
                Gedung gedungB = namaGedung.get(B);
                gedungA.gabung(gedungB.head);
                namaGedung.remove(B);
            }
            else if(cmd.equals("HANCURKAN")){
                String A = in.next();
                Gedung gedung = namaGedung.get(A);
                out.println(gedung.hancurkan());
            }
        }

        // don't forget to close/flush the output
        out.close();
    }

    // taken from https://codeforces.com/submissions/Petr
    // together with PrintWriter, these input-output (IO) is much faster than the usual Scanner(System.in) and System.out
    // please use these classes to avoid your fast algorithm gets Time Limit Exceeded caused by slow input-output (IO)
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}
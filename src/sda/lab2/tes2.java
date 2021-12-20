package sda.lab2;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class tes2 {

    private static InputReader in;
    private static PrintWriter out;
    private static Deque<geng> antrian = new ArrayDeque<geng>();
    private static Map<String,Integer> namaGeng = new HashMap<String,Integer>();
    private static long antri = 0;
    private static int jumlahGeng = 0;

    // TODO
    static private void handleDatang(String Gi, long Xi) {
        if(!namaGeng.containsKey(Gi)){
            jumlahGeng++;
            namaGeng.put(Gi,jumlahGeng);
        }
        geng gengGeng = new geng(Gi,Xi);
        antri += Xi;
        antrian.addLast(gengGeng);
    }

    // TODO
    static private String handleLayani(long Yi) {
        return "";
    }

    // TODO
    static private int handleTotal(String Gi) {
        return -1;
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("DATANG")) {
                String Gi = in.next();
                long Xi = in.nextInt();
                handleDatang(Gi, Xi);
                out.println(antri);
            } else if(event.equals("LAYANI")) {
                long Yi = in.nextInt();

                out.println(handleLayani(Yi));
            } else {
                String Gi = in.next();

                out.println(handleTotal(Gi));
            }
        }

        out.flush();
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

class geng {
    public String nama;
    public long jumlah;

    public geng(String nama, long jumlah){
        this.nama = nama;
        this.jumlah = jumlah;
    }
}


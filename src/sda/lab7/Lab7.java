/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

//package sda.lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;


class jalan implements Comparable<jalan> {
    int tujuan;
    boolean isTol = false;

    jalan (int tujuan, int isTol){
        this.tujuan = tujuan;
        if(isTol==1)
            this.isTol = true;
    }


    @Override
    public int compareTo(jalan o) {
        return 0;
    }
}

class tol {
    int awal,akhir;

    tol(int awal, int akhir){
        this.akhir = akhir;
        this.awal = awal;
    }
}

public class Lab7 {
    private static InputReader in;
    private static PrintWriter out;
//    private static boolean[] visited = new boolean[110];
    private static ArrayList<jalan>[] rute;
    private static ArrayList<tol> toll;
//    private static int[][] rute;
    private static boolean isCek = false;
    private static int N;
    private static int[] par;
    private static int cnt = 0;
    private static Map<Integer,Integer> partol = new HashMap<>();


    public static int findPar(int x){
        if(par[x]==x)
            return x;

        par[x] = findPar(par[x]);
        return par[x];
    }

    // TODO: method to initialize graph
    public static void createGraph() {
        rute = new ArrayList[N+10];
//        rute = new int[N+10][N+10];
        par = new int[N+10];

//        for(int i=1;i<=N;i++)
//            for(int j=1;j<=N;j++)
//                rute[i][j] = 100000;
        for(int i=1;i<=N;i++)
            par[i] = i;
    }

    // TODO: method to create an edge with type==T that connects vertex U and vertex V in a graph
    public static void addEdge(int U, int V, int T) {
//        rute[U].add(new jalan(V,T));
//        rute[V].add(new jalan(U,T));
//        rute[U][V] = T;
//        rute[V][U] = T;
        if(T==0) {
            int parA = findPar(U);
            int parB = findPar(V);
            if (parA != parB)
                par[parA] = parB;
        }
        else {
            cnt++;
            toll.add(new tol(U,V));
        }
    }

    // TODO: Handle teman X Y K
    public static int canMudik(int X, int Y, int K) {
//        for(int i = 1;i<=110;i++)
//            visited[i] = false;

        if(!isCek){
//            for(int i=1;i<=N;i++)
//                for(int j = 1;j<=N;j++)
//                    for(int k=1;k<=N;k++)
//                        rute[j][k] = Math.min(rute[j][k],(rute[j][i] + rute[i][k]));
//
            isCek = true;
        }

        if(findPar(X)==findPar(Y))
            return 1;
        



        return 0;
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        N = in.nextInt();
        int M = in.nextInt();
        int Q = in.nextInt();
        createGraph();

        for (int i=0;i < M;i++) {
            int U = in.nextInt();
            int V = in.nextInt();
            int T = in.nextInt();
            addEdge(U, V, T);
        }
        while(Q-- > 0) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int K = in.nextInt();
            out.println(canMudik(X, Y, K));
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

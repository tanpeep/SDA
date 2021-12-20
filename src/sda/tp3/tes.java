package sda.tp3;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

class karyawann {
    int index;
    int rank;
    boolean isResign;
    boolean isRentan;
    int bigger;
    int secBig;
    int teman;

    public karyawann(int index, int rank){
        this.index = index;
        this.rank = rank;
        isResign = false;
        isRentan = false;
        bigger = 0;
        secBig = 0;
        teman = 0;
    }
}

class binnett {
    karyawann[] daun;
    int size;

    binnett(){
        daun = new karyawann[200010];
        size = 0;
    }

    int parent (int i){
        return (i-1)/2;
    }

    int left(int i){
        return 2*i + 1;
    }

    int right(int i){
        return 2 * (i+1);
    }

    void insert(karyawann now){
        daun[size] = now;

        int cur = size;
        size++;

        while(daun[cur].rank<daun[parent(cur)].rank){
            if(cur==0)
                break;
//           System.out.println(daun[cur].val + " " + daun[parent(cur)].val);
//           System.out.println("cur:" + cur + " " + parent(cur));
            karyawann temp = daun[cur];
            daun[cur] = daun[parent(cur)];
            daun[parent(cur)] = temp;
            cur = parent(cur);
        }
    }

    void delete(){
        karyawann now = daun[0];
        daun[0] = daun[size--];
        percodown(0);
    }

    void percoup(int index){
        if(index<=0)
            return;

        int cur = index;
        while(daun[cur].rank<daun[parent(cur)].rank){
//           System.out.println(daun[cur].val + " " + daun[parent(cur)].val);
//           System.out.println(cur);
            karyawann temp = daun[cur];
            daun[cur] = daun[parent(cur)];
            daun[parent(cur)] = temp;
            cur = parent(cur);
        }
    }


    void percodown(int index){
        if(index> (size/2) && index<size)
            return;

        if(index>=size)
            return;

//        System.out.println(index + " " + size);

        if(daun[index]==null || daun[index].rank<daun[left(index)].rank ||
                daun[index].rank<daun[right(index)].rank){

            if(daun[left(index)]!=null && daun[right(index)]!=null
                    && daun[left(index)].rank > daun[right(index)].rank ){
//                System.out.println("KIRI");
                karyawann temp = daun[index];
                daun[index] = daun[left(index)];
                daun[left(index)] = temp;
                percodown(left(index));
            }
            else if(daun[right(index)]!=null){
//                System.out.println("KANAN");
                karyawann temp = daun[index];
                daun[index] = daun[right(index)];
                daun[right(index)] = temp;
                percodown(right(index));
            }
            else if(daun[left(index)]!=null){
                karyawann temp = daun[index];
                daun[index] = daun[left(index)];
                daun[left(index)] = temp;
                percodown(left(index));
            }
        }
    }

    void cetak(){
        for(int i=0;i<size;i++){
            System.out.println(daun[i].rank + " " + daun[i].index);
        }
        System.out.println();
    }


}

public class tes {
    private static InputReader in;
    private static PrintWriter out;
    private static int[] sorted = new int[100010];
    private static int[] par = new int[100010];
    private static ArrayList<karyawann> renk = new ArrayList<>();

    public static int find(int x){
        if(x==par[x])
            return x;
        return par[x] = find(par[x]);
    }

    public static void main(String args[]) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);
        ArrayList<ArrayList<Integer>> adjlist = new ArrayList<>();
        int simulasi = 0;

        int N,M,Q;

        N = in.nextInt();
        M = in.nextInt();
        Q = in.nextInt();

        karyawann defa = new karyawann(0,-1);
        renk.add(defa);

        for(int i=1;i<=N;i++){
            par[i]= i;
            sorted[i-1] = i;
            int rank = in.nextInt();
            adjlist.add(new ArrayList<>());
            karyawann now = new karyawann(i, rank);
            renk.add(now);
        }

        for(int i=0;i<M;i++){
            int a,b;
            a = in.nextInt();
            b = in.nextInt();
            karyawann u = renk.get(a);
            karyawann v = renk.get(b);
            adjlist.get(a).add(b);
            adjlist.get(b).add(a);
            u.teman++;
            v.teman++;


            // gabungin parent untuk networking + boss-----
            int parA = find(a);
            int parB = find(b);

            karyawann uu = renk.get(parA);
            karyawann vv = renk.get(parB);

            if(uu.rank>vv.rank){
                par[parB] = parA;
                karyawann ww = renk.get(uu.secBig);
                if(vv.rank> ww.rank)
                    uu.secBig = parB;
            }
            else {
                par[parA] = parB;
                karyawann ww = renk.get(vv.secBig);
                if(uu.rank> ww.rank)
                    vv.secBig = parA;
            }
            // -------------------------------------------

            // hitung kerentanan -------------------------
            if(u.rank >= v.rank){
                v.bigger++;
                if(v.bigger>0 && !v.isRentan) {
                    v.isRentan = true;
                    simulasi++;
                }
            }

            if(v.rank >= u.rank){
                u.bigger++;
                if(u.bigger>0 && !u.isRentan){
                    u.isRentan = true;
                    simulasi++;
                }
            }
            // -----------------------------------------
        }

        for(int i=0;i<Q;i++){
            int C;

            C = in.nextInt();

            if(C==1){
                int a,b;
                a = in.nextInt();
                b = in.nextInt();

                karyawann u = renk.get(a);
                karyawann v = renk.get(b);
                adjlist.get(a).add(b);
                adjlist.get(b).add(a);
                u.teman++;
                v.teman++;


                // gabungin parent untuk networking + boss-----
                int parA = find(a);
                int parB = find(b);

                karyawann uu = renk.get(parA);
                karyawann vv = renk.get(parB);

                if(uu.rank>vv.rank){
                    par[parB] = parA;
                    karyawann ww = renk.get(uu.secBig);
                    if(vv.rank> ww.rank)
                        uu.secBig = parB;
                }
                else {
                    par[parA] = parB;
                    karyawann ww = renk.get(vv.secBig);
                    if(uu.rank> ww.rank)
                        vv.secBig = parA;
                }
                // -------------------------------------------

                // hitung kerentanan -------------------------
                if(u.rank >= v.rank){
                    v.bigger++;
                    if(v.bigger>0 && !v.isRentan) {
                        v.isRentan = true;
                        simulasi++;
                    }
                }

                if(v.rank >= u.rank){
                    u.bigger++;
                    if(u.bigger>0 && !u.isRentan){
                        u.isRentan = true;
                        simulasi++;
                    }
                }
                // -----------------------------------------
            }
            else if(C==2){
                int a = in.nextInt();

                karyawann u = renk.get(a);
                u.isResign = true;

//                for(int j=0;j<)
            }
        }

    }

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

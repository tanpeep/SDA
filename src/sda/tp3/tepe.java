/**
 * @Author : Sulthan Afif Althaf - 2006473863
 */

//package sda.tp3;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * class untuk object karyawan,
 * berisi atribut yg mendukung optimisasi dan kemudahan
 * dalam menyusun algoritma
 */
class karyawan {
    int index;
    int rank;
    int teman;
    int secBig;
    int bigger;
    boolean isRentan;
    boolean isResign;

    public karyawan(int index, int rank){
        this.index = index;
        this.rank = rank;
        teman = 0;
        isResign = false;
        secBig = 0;
        isRentan = false;
        bigger = 0;
    }
}

/**
 * class khusus object dalam queue untuk bfs
 */
class per {
    int pos;
    int val;

    public per(int pos, int val){
        this.pos = pos;
        this.val = val;
    }
}

/**
 * class untuk binary heap
 * terinspirasi dari Lab 6 & GeeksForGeeks Max Heap
 */
class binnet {
    ArrayList<karyawan> daun = new ArrayList<>();
    int size;

    public binnet(){
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

    void insert(karyawan now){
        if(daun.size()==size){
            daun.add(now);
        }
        else {
            daun.set(size,now);
        }
        int cur = size;
        size++;

        while(daun.get(cur).rank>daun.get(parent(cur)).rank){
//            System.out.println("HORE");
            if(cur==0)
                break;

            karyawan temp = daun.get(cur);
            daun.set(cur, daun.get(parent(cur)));
            daun.set(parent(cur),temp);
            cur = parent(cur);
        }
    }

    void remove(){
        size--;
        daun.set(0, daun.get(size));
        percodown(0);
    }

    void percodown(int index) {
        if (index > (size / 2) && index < size)
            return;

        if (index >= size)
            return;

//        System.out.println(index + " " + size);

        if(right(index)<size && (daun.get(index).rank<daun.get(left(index)).rank
                || daun.get(index).rank<daun.get(right(index)).rank)){

            if(daun.get(left(index)).rank > daun.get(right(index)).rank){
//                System.out.println("KIRI");
                karyawan temp = daun.get(index);
                daun.set(index, daun.get(left(index)));
                daun.set(left(index), temp);
                percodown(left(index));
            }
            else{
//                System.out.println("KANAN");
                karyawan temp = daun.get(index);
                daun.set(index, daun.get(right(index)));
                daun.set(right(index), temp);
                percodown(right(index));
            }
        }
        else if(left(index)<size && daun.get(index).rank<daun.get(left(index)).rank){
            karyawan temp = daun.get(index);
            daun.set(index, daun.get(left(index)));
            daun.set(left(index), temp);
            percodown(left(index));
        }
    }

    void cetak(){
        for(int i=0;i<size;i++){
            System.out.println(daun.get(i).rank + " " + daun.get(i).index);
        }
        System.out.println();
    }
}

/**
 * class khusus untuk pengurutan mst
 */
class triple{
    int a,b,val;

    public triple(int a, int b, int val){
        this.a = a;
        this.b = b;
        this.val = val;
    }
}

public class tepe {
    private static InputReader in;
    private static PrintWriter out;
    private static int[] par = new int[100010];
    private static ArrayList<karyawan> renk = new ArrayList<>();

    // function khusus untuk menghandle pengurutan mst menggunakan algo mergesort
    public static void mertrip (triple a[],int l, int r){
        if(l<r){
            int mid = (l+r) / 2;
            mertrip(a,l,mid);
            mertrip(a, mid+1,r);

            mer(a,l,r);
        }
    }

    // mengurutkan mst
    public static void mer(triple a[], int l, int r) {
        int mid = (l + r) / 2;
        int lenL = mid - l + 1;
        int lenR = r - mid;
        triple[] ll = new triple[lenL];
        triple[] rr = new triple[lenR];

        for (int i = 0; i < lenL; i++) {
            ll[i] = a[i + l];
        }

        for (int i = 0; i < lenR; i++) {
            rr[i] = a[i + mid + 1];
        }

        int i = 0;
        int j = 0;
        int k = l;

        while (i < lenL && j < lenR) {
            if(ll[i].val<rr[j].val){
                a[k] = ll[i];
                i++;
            }
            else {
                a[k] = rr[j];
                j++;
            }
            k++;
        }

        while (i < lenL) {
            a[k] = ll[i];
            i++;
            k++;
        }

        while (j < lenR) {
            a[k] = rr[j];
            j++;
            k++;
        }
    }

    // function untuk menghandle pengurutan berdasarkan pangkat seorang
    public static void mergeRec (int a[],int l, int r){
        if(l<r){
            int mid = (l+r) / 2;
            mergeRec(a,l,mid);
            mergeRec(a, mid+1,r);

            merge(a,l,r);
        }
    }

    // mengurutkan berdasarkan pangkat
    public static void merge(int sorted[], int l, int r) {
        int mid = (l + r) / 2;
        int lenL = mid - l + 1;
        int lenR = r - mid;
        int[] ll = new int[lenL];
        int[] rr = new int[lenR];

        for (int i = 0; i < lenL; i++) {
            ll[i] = sorted[i + l];
        }

        for (int i = 0; i < lenR; i++) {
            rr[i] = sorted[i + mid + 1];
        }

        int i = 0;
        int j = 0;
        int k = l;

        while (i < lenL && j < lenR) {
            if (renk.get(ll[i]).rank < renk.get(rr[j]).rank ||
                    (renk.get(ll[i]).rank==renk.get(rr[j]).rank &&
                            find(renk.get(ll[i]).index)<find(renk.get(rr[j]).index))) {
                sorted[k] = ll[i];
                i++;
            } else {
                sorted[k] = rr[j];
                j++;
            }
            k++;
        }

        while (i < lenL) {
            sorted[k] = ll[i];
            i++;
            k++;
        }

        while (j < lenR) {
            sorted[k] = rr[j];
            j++;
            k++;
        }
    }

    // function untuk mencari parent dari suatu karyawan dalam suatu disjoint set
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
        ArrayList<ArrayList<Integer>> adjNilai = new ArrayList<>();
        ArrayList<binnet> binheap = new ArrayList<>();
        int simulasi = 0;


        int N,M,Q,NN;
        N = in.nextInt();
        M = in.nextInt();
        Q = in.nextInt();
        NN = N;

        for(int i=0;i<=N;i++)
            adjNilai.add(new ArrayList<>());

        // set default untuk kebutuhan berbagai query
        karyawan zero = new karyawan(0,-1);
        binheap.add(new binnet());
        renk.add(zero);

        // set pangkat setiap karyawan
        for(int i=1;i<=N;i++){
            par[i] = i;
            int pangkat = in.nextInt();
            karyawan now = new karyawan(i, pangkat);
            adjNilai.get(pangkat).add(i);
            binheap.add(new binnet());
            renk.add(now);
        }

        // set pertemanan awal
        for(int i=0;i<M;i++){
            int a,b;
            a = in.nextInt();
            b = in.nextInt();
            karyawan u = renk.get(a);
            karyawan v = renk.get(b);
            binheap.get(a).insert(v);
            binheap.get(b).insert(u);
            u.teman++;
            v.teman++;

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
            // urusin parent -------------- find
            int parA = find(a);
            int parB = find(b);
            if(parA==parB)
                continue;

            karyawan uu = renk.get(parA);
            karyawan vv = renk.get(parB);

            if(uu.rank>vv.rank){
                par[parB] = parA;
                karyawan ww = renk.get(uu.secBig);
                if(vv.rank> ww.rank)
                    uu.secBig = parB;
            }
            else {
                par[parA] = parB;
                karyawan ww = renk.get(vv.secBig);
                if(uu.rank> ww.rank)
                    vv.secBig = parA;
            }
            // ----------------------------------------

        }


        // query
        for(int i=0;i<Q;i++){
            int C = in.nextInt();

            //query tambah
            if(C==1){
                int a,b;
                a = in.nextInt();
                b = in.nextInt();
                karyawan u = renk.get(a);
                karyawan v = renk.get(b);
                binheap.get(a).insert(v);
                binheap.get(b).insert(u);
                u.teman++;
                v.teman++;

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
                // urusin parent -------------- find
                int parA = find(a);
                int parB = find(b);

                if(parA==parB)
                    continue;

                par[parA] = parB;
                // ----------------------------------------
            }

            // query resign
            else if(C==2){
                int a = in.nextInt();
                karyawan u = renk.get(a);
                u.isResign = true;
                NN--;
                if(u.isRentan)
                    simulasi--;

                // mengurangi nilai teman untuk setiap teman u dan mengupdate kerentanan teman u
                for(int j=0;j<binheap.get(a).size;j++){
                    karyawan v = binheap.get(a).daun.get(j);
                    if(v.isResign)
                        continue;
                    v.teman--;
                    if(u.rank >= v.rank){
                        v.bigger = Math.max(0, v.bigger-1);
                        if(v.bigger<1 && v.isRentan) {
                            v.isRentan = false;
                            simulasi--;
                        }
                    }
                }
            }

            // query carry
            else if(C==3){
                int a = in.nextInt();
                karyawan u = renk.get(a);

                if(u.teman==0)
                    out.println(0);
                else {
                    // menghapus karyawan yg tidak valid pada top of binheap
                    while(binheap.get(a).daun.get(0).isResign)
                        binheap.get(a).remove();

                    out.println(binheap.get(a).daun.get(0).rank);
                }
            }

            // query boss
            else if (C==4){
                int a = in.nextInt();
                int parA = find(a);
                karyawan u = renk.get(parA);

                // menghitung pangkat terbesar dari suatu join set, jika pangkat terbesar dari
                // suatu disjoint set merupakan dirinya sendiri, akan digunakan pangkat terbesar
                // kedua yang sudah disimpan
                if(a==parA){
                    if(u.secBig==0)
                        out.println(0);
                    else
                        out.println(renk.get(u.secBig).rank);
                }
                else {
                    out.println(u.rank);
                }
            }

            // query sebar
            else if(C==5){
                int a,b;
                a = in.nextInt();
                b = in.nextInt();
                boolean cek = false;

                // kasus u dan v sama atau pangkat u dan v sama
                if(a==b || renk.get(a).rank==renk.get(b).rank){
                    out.println(0);
                    continue;
                }

                // kasus yg tidak mungkin ada jalan
                if(renk.get(a).teman==0 && renk.get(b).teman==0 &&
                        adjNilai.get(a).size()==1 && adjNilai.get(b).size()==1 && a!=b){
                    out.println(-1);
                    continue;
                }

                Queue<per> bfs = new ArrayDeque<>();

                boolean[] vis = new boolean[N+5];
                boolean[] visNilai = new boolean[N+5];

                vis[a] = true;
                bfs.add(new per(a,0));

                // penelusuran secara bfs
                while(bfs.size()>0 && !cek){
                    int cur = bfs.peek().pos;
                    int dist = bfs.peek().val;
                    int rank = renk.get(cur).rank;
                    bfs.poll();

                    if(renk.get(cur).isResign)
                        continue;

                    // penelurusan node tetangga
                    for(int j=0;j<binheap.get(cur).size && renk.get(cur).teman>0 && !cek;j++){
                        int next = binheap.get(cur).daun.get(j).index;
                        if(!renk.get(next).isResign && !vis[next]){
                            bfs.add(new per(next, dist+1));
                            vis[next] = true;
                            if(next==b){
                                out.println(dist);
                                cek = true;
                                break;
                            }
                        }
                    }

                    if(visNilai[rank])
                        continue;

                    visNilai[rank] = true;

                    // penelusuran pangkat tetangga
                    for(int j=0;j<adjNilai.get(rank).size() && !cek;j++){
                        int next = adjNilai.get(rank).get(j);
                        if(!renk.get(next).isResign && !vis[next]){
                            bfs.add(new per(next, dist+1));
                            vis[next] = true;
                            if(next==b){
                                out.println(dist);
                                cek = true;
                                break;
                            }
                        }
                    }
                }

                if(!cek)
                    out.println(-1);
            }

            // query simulasi
            else if(C==6){
                // mengurangi jumlah karyawan yg tersedia dengan jumlah
                // karyawan rentan yang sudah diupdate sebelumnya di tambah dan resign
                out.println(NN - simulasi);
            }

            // query network
            else if(C==7){
                int[] sorted = new int[N+5];
                triple[] mst = new triple[N+5];
                for(int j=0;j<N;j++){
                    sorted[j] = j+1;
                }
                long ans = 0;
                int sz = 0;
                mergeRec(sorted,0,N-1); // mengurutkan karyawan berdasarkan pangkat

                // untuk setiap karyawan yg berurutan setelah disort dan bernilai pangkat sama
                // dan belum tergabung, akan digabung
                for(int j=0;j<N-1;j++){
                    if(renk.get(sorted[j]).rank == renk.get(sorted[j+1]).rank && find(sorted[j])!=find(sorted[j+1])){
                        int parA = find(sorted[j]);
                        int parB = find(sorted[j+1]);
                        par[parA] = parB;
                    }
                }

                // untuk setiap karyawan berurutan yg belum terhubung, akan dibuat edge baru
                // yang disimpan dalam array mst
                for(int j=0;j<N-1;j++){
                    if(find(sorted[j])!=find(sorted[j+1])){
                        int val = Math.abs(renk.get(sorted[j]).rank - renk.get(sorted[j+1]).rank);
                        mst[sz] = new triple(sorted[j],sorted[j+1],val);
                        sz++;
                    }
                }

                mertrip(mst,0,sz-1); // pengurutan array mst

                // penggabungan menggunakan algo kruskal ( dari edge terkecil )
                for(int j=0;j<sz;j++){
                    if(find(mst[j].a)!=find(mst[j].b)){
                        int parA = find(mst[j].a);
                        int parB = find(mst[j].b);
                        par[parA] = parB;
                        ans += mst[j].val;
                    }
                }

                out.println(ans);
            }
        }

        out.flush();
    }

    /**
     * Fast I-O
     */
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
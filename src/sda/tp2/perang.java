/**
 * @author: Sulthan Afif Althaf - 2006473863
 */
package sda.tp2;


import java.io.*;
import java.util.*;

class daratan {

    noda here;
    daratan next;
    daratan bef;
    boolean isKuil;

    public daratan(noda here, daratan n){
        this.here = here;
        next = n;
        isKuil = false;
    }

}


// TODO - class untuk Gedung
class pulau {
    String nama;
    daratan kuil,pepulau,akhir;
    boolean isPulau;
    int cnt;
    int tot=0;
    pulau next,bef;
    avl tree;

    public pulau(daratan kuil, String nama) {
        this.kuil = kuil;
        this.nama = nama;
        isPulau = true;
        this.kuil.bef = null;
        pepulau = this.kuil;
        next = null;
        bef = null;
        akhir = pepulau;

    }

    public void add(daratan baru) {
        pepulau.next = baru;
        baru.bef = pepulau;
        pepulau = pepulau.next;
        akhir = pepulau;
    }

}

public class perang {
    private static InputReader in;
    private static PrintWriter out;



    public static void main(String args[]) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N,Q;
        Map<String,pulau> namaKuil = new HashMap<>();
        daratan raiden;
        pulau pulauraiden;

        N = in.nextInt();

        for(int i=0;i<N;i++){
            String P;
            int D;

            P = in.next();
            D = in.nextInt();
            long[] A = new long[D+3];

            A[0] = in.nextInt();
            noda node = new noda(A[0]);
            daratan awal = new daratan(node,null);
            awal.isKuil = true;

            pulau baru = new pulau(awal,P);
            baru.cnt = D;
            baru.tot = D;
            baru.tree = new avl();
            baru.tree.root = baru.tree.insert(baru.tree.root,node);

            for(int j=1;j<D;j++) {
                A[j] = in.nextInt();
                node = new noda(A[j]);
                daratan now = new daratan(node,null);
                baru.add(now);
                baru.tree.root = baru.tree.insert(baru.tree.root,node);
            }

            namaKuil.put(P,baru);
        }

        String R = in.next();
        int E = in.nextInt();

        pulau uv = namaKuil.get(R);
        raiden = uv.kuil;
        pulauraiden = uv;

        int mv = 1;
        while(mv<E){
            raiden = raiden.next;
            mv++;
        }

//        for(String nama : namaKuil.keySet()){
//            System.out.println(nama + " " + namaKuil.get(nama).tree.root.sz);
//        }


        Q = in.nextInt();

        for(int i=0;i<Q;i++) {
//            System.out.println(i);
            String C;

            C = in.next();

            if(C.equals("UNIFIKASI")) {
                String U,V;

                U = in.next();
                V = in.next();

                pulau u = namaKuil.get(U);
                pulau v = namaKuil.get(V);


               pulau temp = u;

               int cnt=0;
               cnt += u.cnt;

               while(temp.next!=null){
                   temp = temp.next;
                   cnt += temp.cnt;
               }

               temp.next = v;
               v.bef = temp;

               temp = v;

               cnt += v.cnt;
               while(temp.next!=null){
                   temp = temp.next;
                   cnt += temp.cnt;
               }

                v.isPulau = false;

                out.println(cnt);
            }
            else if(C.equals("PISAH")) {
                String U;

                U = in.next();

                pulau u = namaKuil.get(U);

                pulau temp = u;

                int cntA = 0;
                while(temp.bef!=null){
                    temp = temp.bef;
                    cntA += temp.cnt;
                }

                pulau temp2 = u;
                int cntB=0;
                cntB += u.cnt;
                while(temp2.next!=null){
                    temp2 = temp2.next;
                    cntB += temp2.cnt;
                }

                u.isPulau = true;
                u.bef.next = null;
                u.bef = null;

                out.println(cntA + " " + cntB);
            }
            else if (C.equals("GERAK")) {
                String L;
                int S;

                L = in.next();
                S = in.nextInt();
                mv = 0;
                if(L.equals("KIRI")){
                    while(mv<S){
                        if(raiden.bef!=null){
                            raiden = raiden.bef;
                        }
                        else {
                            pulau temp = pulauraiden;
                            pulauraiden = pulauraiden.bef;
                            if(pulauraiden!=null) raiden = pulauraiden.akhir;
                            else{
                                pulauraiden = temp;
                                break;
                            }
                        }
                        mv++;
                    }
                    
                }
                else{
                    while(mv<S){
                        if(raiden.next!=null){
                            raiden = raiden.next;
                        }
                        else {
//                            System.out.println("WOLO");
                            pulau temp = pulauraiden;
                            pulauraiden = pulauraiden.next;
                            if(pulauraiden!=null) {
//                                out.println("NOPE");
                                raiden = pulauraiden.kuil;
                            }
                            else {
//                                System.out.println("YOI");
                                pulauraiden = temp;
                                break;
                            }
                        }
                        mv++;
                    }
                }
                out.println(raiden.here.val);
            }
            else if (C.equals("TEBAS")) {
                String L;
                int S,cnt=0;

                L = in.next();
                S = in.nextInt();
                daratan temp = raiden;
                pulau tempz = pulauraiden;
                boolean change = false;

                if(L.equals("KIRI")){
                    while(temp!=null && tempz!=null && cnt<S){
                        if(temp.here.val == raiden.here.val && temp!=raiden){
                            change = true;
                            raiden = temp;
                            pulauraiden = tempz;
                            cnt++;
                        }
                        if(temp.bef!=null){
                            temp = temp.bef;
                        }
                        else {
                            tempz = tempz.bef;
                            while(tempz!=null){
                                long banyak = tempz.tree.find(tempz.tree.root,raiden.here.val);
                                if(banyak>0){
                                    pulauraiden = tempz;
                                    cnt += banyak;
                                    if(cnt>S) {
                                        cnt -= banyak;
                                        temp = pulauraiden.akhir;
                                        break;
                                    }
                                }
                            }
                            if(tempz!=null) {
//                                long banyak = tempz.
                                temp = tempz.akhir;
                            }
                        }
                    }
                }
                else{
                    while((temp!=null && tempz!=null) && cnt<S){
                        if(temp.here.val == raiden.here.val && temp!=raiden){
                            change = true;
                            raiden = temp;
                            pulauraiden = tempz;
                            cnt++;
                        }
                        if(temp.next!=null){
                            temp = temp.next;
                        }
                        else {
                            tempz = tempz.next;
                            if(tempz!=null) temp = tempz.kuil;
                        }
                    }
                }
                if(change){
                    if(L.equals("KIRI")) {
                        if (raiden.next != null)
                            out.println(raiden.next.here.val);
                        else
                            out.println(pulauraiden.next.kuil.here.val);
                    }
                    else {
                        if (raiden.bef != null)
                            out.println(raiden.bef.here.val);
                        else
                            out.println(pulauraiden.bef.akhir.here.val);
                    }
                }
                else
                    out.println(0);
            }
            else if (C.equals("TELEPORTASI")) {
                String V;

                V = in.next();

                pulau u = namaKuil.get(V);
                raiden = u.kuil;
                pulauraiden = u;

                out.println(raiden.here.val);
            }
            else if (C.equals("RISE")) {
                String U;
                long H,X,ans = 0;

                U = in.next();
                H = in.nextInt();
                X = in.nextInt();

                pulau u = namaKuil.get(U);

                while(u!=null){
                    ans += u.tree.rise(u.tree.root,H,X);
                    u = u.next;
                }
                out.println(ans);
            }
            else if (C.equals("QUAKE")) {
//                System.out.println("NATE");
                String U;
                long H,X,ans=0;

                U = in.next();
                H = in.nextInt();
                X = in.nextInt();

                pulau u = namaKuil.get(U);

                while(u!=null){
                    ans += u.tree.quake(u.tree.root, H, X);
                    u = u.next;
                }

                out.println(ans);
            }
            else if (C.equals("CRUMBLE")) {
                if(raiden.isKuil)
                    out.println(0);
                else {
                    long val = raiden.here.val;
                    raiden.bef.next = raiden.next;
                    if(raiden.next!=null)
                        raiden.next.bef = raiden.bef;
                    else
                        pulauraiden.akhir = raiden.bef;
                    raiden = raiden.bef;

                    pulauraiden.tree.root = pulauraiden.tree.delete(pulauraiden.tree.root, val,1);

                    out.println(val);
                    pulauraiden.cnt--;
                }
            }
            else if(C.equals("STABILIZE")) {
                if(raiden.isKuil)
                    out.println(0);
                else {
                    long val = Math.min(raiden.here.val, raiden.bef.here.val);
                    noda newNod = new noda(val);
                    daratan newDar = new daratan(newNod,null);

                    if(raiden.next!=null){
                        raiden.next.bef = newDar;
                        newDar.next = raiden.next;
                        newDar.bef = raiden;
                        raiden.next = newDar;
                    }
                    else {
                        raiden.next = newDar;
                        newDar.bef = raiden;
                        pulauraiden.akhir = raiden.next;
                    }

                    pulauraiden.tree.root = pulauraiden.tree.insert(pulauraiden.tree.root,newNod);
                    out.println(val);
                    pulauraiden.cnt++;
                }
            }
            else if(C.equals("SWEEPING")) {
                String U;
                long L,ans=0;

                U = in.next();
                L = in.nextInt();

                pulau u = namaKuil.get(U);

//                System.out.println("BATAS SUCI:");

                while(u!=null) {
                    ans += u.tree.sweep(u.tree.root, L);
                    u = u.next;
                }
                out.println(ans);
            }

//            for(String nama : namaKuil.keySet()){
//                System.out.println(nama + " " + namaKuil.get(nama).tree.root.sz);
//            }

        }


        out.flush();
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

class noda {
    long val,berubah;
    int cnt, height, sz;
    noda left,right;

    noda (long val){
        this.val = val;
        cnt = 1;
        height = 1;
        left = null;
        right = null;
        berubah = 0;
        sz = 1;
    }
}

class avl {
    noda root;

    noda insert(noda node, noda now){
        if(node==null)
            return now;

        node.sz++;
        if(now.val<node.val) {
            node.left = insert(node.left, now);
        }
        else if(now.val>node.val) {
            node.right = insert(node.right, now);
        }
        else {
            node.cnt++;
            now = node;
            return node;
        }

        return node;
    }

    noda delete(noda node, long val, int cnt){
        if(node==null)
            return node;

        node.sz -= cnt;
        if(val<node.val) {
            node.left = delete(node.left, val, cnt);
        }
        else if(val>node.val) {
            node.right = delete(node.right, val, cnt);
        }
        else {

            node.cnt -= cnt;
            if(node.cnt>0)
                return node;

            noda temp;

            if(node.left==null){
                return node.right;
            }
            if(node.right==null)
                return node.left;

            temp = minValue(node.right);

            node.right = delete(node.right, temp.val, temp.cnt);

            noda temp2 = node;

            node = temp;
            node.left = temp2.left;
            node.right = temp2.right;
            node.sz = node.cnt + getSz(node.left) + getSz(node.right);
        }

        return node;
    }

    noda minValue(noda node){
        noda cur = node;
        while(cur.left!=null)
            cur = cur.left;

        return cur;
    }

    long find(noda node, long val){
        if(node==null)
            return -1;

        if(node.berubah!=0)
            updateVal(node);

        if(node.val==val)
            return node.cnt;

        if(val<node.val)
            return find(node.left, val);

        return find(node.right, val);
    }

    long rise(noda node,long H, long X){
        if(node==null)
            return 0;

        long cnt = 0;

        if(node.val>H){
            cnt += node.cnt;
            cnt += rise(node.left, H, X);
            cnt += rise(node.right, H, X);
            node.val += X;
        }
        else {
            cnt += rise(node.right, H, X);
        }

        return cnt;
    }

    long quake(noda node, long H, long X){
        if(node==null){
            return 0;
        }

        long cnt = 0;


        if(node.val<H){
            cnt += node.cnt;
            cnt += quake(node.left, H , X);
            cnt += quake(node.right, H, X);
            node.val -= X;
        }
        else
            cnt += quake(node.left, H, X);

        return  cnt;
    }

    long sweep(noda node, long H){
        if(node==null)
            return 0;

        if(node.berubah!=0)
            updateVal(node);



        long cnt = 0;
        if(node.val>=H){
            cnt = sweep(node.left,H);
        }
        else {
            cnt += node.sz;
            cnt -= getSz(node.right);
            cnt += sweep(node.right, H);
        }

        return cnt;
    }

    void updateVal(noda node){
        node.val -= node.berubah;

        if(node.left!=null)
            node.left.berubah += node.berubah;

        if(node.right!=null)
            node.right.berubah += node.berubah;

        node.berubah = 0;
    }

    int getSz(noda node){
        if(node==null)
            return 0;

        else return node.sz;
    }
}

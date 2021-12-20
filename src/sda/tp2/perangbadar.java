/**
 * @author: Sulthan Afif Althaf - 2006473863
 */
package sda.tp2;


import java.io.*;
import java.util.*;

class land {

    long asli;
    land next;
    land bef;
    boolean isKuil;

    public land(long asli, land n){
        this.asli = asli;
        next = n;
        isKuil = false;
    }

}


// TODO - class untuk Gedung
class island {
    String nama;
    land kuil,peisland,akhir;
    boolean isisland;
    int cnt;
    int tot=0;
    island next,bef;
    avll tree;

    public island(land kuil, String nama) {
        this.kuil = kuil;
        this.nama = nama;
        isisland = true;
        this.kuil.bef = null;
        peisland = this.kuil;
        next = null;
        bef = null;
        akhir = peisland;

    }

    public void add(land baru) {
        peisland.next = baru;
        baru.bef = peisland;
        peisland = peisland.next;
        akhir = peisland;
    }

}

public class perangbadar {
    private static InputReader in;
    private static PrintWriter out;

    public static void mergeRec (long a[], int l, int r){
        if(l<r){
            int mid = (l+r) / 2;
            mergeRec(a,l,mid);
            mergeRec(a,mid+1,r);

            merge(a,l,r);
        }
    }

    public static void merge(long a[], int l, int r){
        int mid = (l+r)/2;
        int lenL = mid-l+1;
        int lenR = r - mid;
        long[] ll = new long[lenL];
        String[] namaL = new String[lenL];
        long[] rr = new long[lenR];
        String[] namaR = new String[lenR];

        for(int i=0;i<lenL;i++)
            ll[i] = a[i+l];

        for(int i=0;i<lenR;i++)
            rr[i] = a[i+mid+1];

        int i=0; int j=0; int k = l;

        while(i<lenL && j<lenR){
            if(ll[i]<rr[j]){
                a[k] = ll[i];
                i++;
            }
            else {
                a[k] = rr[j];
                j++;
            }
            k++;
        }

        while(i<lenL){
            a[k] = ll[i];
            i++;
            k++;
        }

        while(j<lenR){
            a[k] = rr[j];
            j++;
            k++;
        }
    }

    public static void masukin(long a[], island pulau, int l, int r){
        if(l>r)
            return;

        int mid = (l+r)/2;
//        System.out.println(a[mid]);
        pulau.tree.root = pulau.tree.insert(pulau.tree.root, a[mid]);
        masukin(a, pulau, l, mid-1);
        masukin(a, pulau, mid+1, r);
    }


    public static void main(String args[]) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int N,Q;
        Map<String,island> namaKuil = new HashMap<>();
        land raiden;
        island islandraiden;

        N = in.nextInt();

        for(int i=0;i<N;i++){
            String P;
            int D;

            P = in.next();
            D = in.nextInt();
            long[] A = new long[D+3];

            A[0] = in.nextInt();
            land awal = new land(A[0],null);
            awal.isKuil = true;

            island baru = new island(awal,P);
            baru.cnt = D;
            baru.tot = D;
            baru.tree = new avll();


            for(int j=1;j<D;j++) {
                A[j] = in.nextInt();
                land now = new land(A[j],null);
                baru.add(now);
            }

            mergeRec(A,0,D-1);
            masukin(A,baru,0,D-1);

//            for(int j=1;j<D;j++) {
//                System.out.print(A[j]);
//            }

            namaKuil.put(P,baru);
        }

        String R = in.next();
        int E = in.nextInt();

        island uv = namaKuil.get(R);
        raiden = uv.kuil;
        islandraiden = uv;

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

                island u = namaKuil.get(U);
                island v = namaKuil.get(V);


                island temp = u;

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

                v.isisland = false;

                out.println(cnt);
            }
            else if(C.equals("PISAH")) {
                String U;

                U = in.next();

                island u = namaKuil.get(U);

                island temp = u;

                int cntA = 0;
                while(temp.bef!=null){
                    temp = temp.bef;
                    cntA += temp.cnt;
                }

                island temp2 = u;
                int cntB=0;
                cntB += u.cnt;
                while(temp2.next!=null){
                    temp2 = temp2.next;
                    cntB += temp2.cnt;
                }

                u.isisland = true;
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
                            island temp = islandraiden;
                            islandraiden = islandraiden.bef;
                            if(islandraiden!=null) raiden = islandraiden.akhir;
                            else{
                                islandraiden = temp;
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
                            island temp = islandraiden;
                            islandraiden = islandraiden.next;
                            if(islandraiden!=null) {
//                                out.println("NOPE");
                                raiden = islandraiden.kuil;
                            }
                            else {
//                                System.out.println("YOI");
                                islandraiden = temp;
                                break;
                            }
                        }
                        mv++;
                    }
                }
                out.println(islandraiden.tree.cekVal(islandraiden.tree.root,raiden.asli));
            }
            else if (C.equals("TEBAS")) {
                String L;
                int S,cnt=0;

                L = in.next();
                S = in.nextInt();
                land temp = raiden;
                long val = islandraiden.tree.cekVal(islandraiden.tree.root, raiden.asli);
                island tempz = islandraiden;
                boolean change = false;

                if(L.equals("KIRI")){
                    while(temp!=null && tempz!=null && cnt<S){
                        if(tempz.tree.cekVal(tempz.tree.root,temp.asli)
                                == val && temp!=raiden){
                            change = true;
                            raiden = temp;
                            islandraiden = tempz;
                            cnt++;
                        }
                        if(temp.bef!=null){
                            temp = temp.bef;
                        }
                        else {
                            tempz = tempz.bef;
//                            while(tempz!=null){
//                                long banyak = tempz.tree.find(tempz.tree.root,raiden.here.val);
//                                if(banyak>0){
//                                    islandraiden = tempz;
//                                    cnt += banyak;
//                                    if(cnt>S) {
//                                        cnt -= banyak;
//                                        temp = islandraiden.akhir;
//                                        break;
//                                    }
//                                }
//                            }
                            if(tempz!=null) {
                                temp = tempz.akhir;
                            }
                            else
                                break;
                        }
                    }
                }
                else{
                    while((temp!=null && tempz!=null) && cnt<S){
                        if(tempz.tree.cekVal(tempz.tree.root,temp.asli)
                                == val && temp!=raiden){
//                            System.out.println("haha");
                            change = true;
                            raiden = temp;
                            islandraiden = tempz;
                            cnt++;
                        }
                        if(temp.next!=null){
//                            System.out.println("hihi");
                            temp = temp.next;
                        }
                        else {
//                            System.out.println("huhu");
                            tempz = tempz.next;
                            if(tempz!=null) temp = tempz.kuil;
                            else
                                break;
                        }
                    }
                }
                if(change){
                    if(L.equals("KIRI")) {
                        if (raiden.next != null)
                            out.println(islandraiden.tree.cekVal(islandraiden.tree.root, raiden.next.asli));
                        else if(islandraiden.next!=null)
                            out.println(islandraiden.next.tree.cekVal(islandraiden.next.tree.root,islandraiden.next.kuil.asli));
                    }
                    else {
                        if (raiden.bef != null)
                            out.println(islandraiden.tree.cekVal(islandraiden.tree.root, raiden.bef.asli));
                        else if(islandraiden.bef!=null)
                            out.println(islandraiden.bef.tree.cekVal(islandraiden.bef.tree.root,islandraiden.bef.akhir.asli));
                    }
                }
                else
                    out.println(0);
            }
            else if (C.equals("TELEPORTASI")) {
                String V;

                V = in.next();

                island u = namaKuil.get(V);
                raiden = u.kuil;
                islandraiden = u;

//                System.out.println(raiden.asli);
                out.println(islandraiden.tree.cekVal(islandraiden.tree.root,raiden.asli));
            }
            else if (C.equals("RISE")) {
                String U;
                long H,X,ans = 0;

                U = in.next();
                H = in.nextInt();
                X = in.nextInt();

                island u = namaKuil.get(U);

//                System.out.println(U);
                while(u!=null){
                    ans += u.tree.rise(u.tree.root,H,X);
//                    u.tree.preOrder(u.tree.root);
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

                island u = namaKuil.get(U);
//                System.out.println(U);

                while(u!=null){
                    ans += u.tree.quake(u.tree.root, H, X);
//                    u.tree.preOrder(u.tree.root);
                    u = u.next;
                }

                out.println(ans);
            }
            else if (C.equals("CRUMBLE")) {
                if(raiden.isKuil)
                    out.println(0);
                else {
                    long val = islandraiden.tree.cekVal(islandraiden.tree.root,raiden.asli);
                    raiden.bef.next = raiden.next;
                    if(raiden.next!=null)
                        raiden.next.bef = raiden.bef;
                    else
                        islandraiden.akhir = raiden.bef;

                    islandraiden.tree.root = islandraiden.tree.delete(islandraiden.tree.root, raiden.asli,1);
                    raiden = raiden.bef;

                    out.println(val);
                    islandraiden.cnt--;
                }
            }
            else if(C.equals("STABILIZE")) {
                if(raiden.isKuil)
                    out.println(0);
                else {
                    long asli;
                    if(islandraiden.tree.cekVal(islandraiden.tree.root, raiden.asli)>
                            islandraiden.tree.cekVal(islandraiden.tree.root, raiden.bef.asli))
                        asli = raiden.bef.asli;
                    else
                        asli = raiden.asli;

                    land newDar = new land(asli,null);

                    if(raiden.next!=null){
                        raiden.next.bef = newDar;
                        newDar.next = raiden.next;
                        newDar.bef = raiden;
                        raiden.next = newDar;
                    }
                    else {
                        raiden.next = newDar;
                        newDar.bef = raiden;
                        islandraiden.akhir = raiden.next;
                    }

                    islandraiden.tree.root = islandraiden.tree.insert(islandraiden.tree.root,asli);
                    out.println(islandraiden.tree.cekVal(islandraiden.tree.root, asli));
                    islandraiden.cnt++;
                }
            }
            else if(C.equals("SWEEPING")) {
                String U;
                long L,ans=0;

                U = in.next();
                L = in.nextInt();

                island u = namaKuil.get(U);

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

class nod {
    long asli,val,berubah;
    int cnt, height, sz;
    nod left,right;

    nod (long val){
        asli = val;
        this.val = val;
        cnt = 1;
        height = 1;
        left = null;
        right = null;
        berubah = 0;
        sz = 1;
    }
}

class avll {
    nod root;

    nod insert(nod node, long val){
        if(node==null)
            return (new nod(val));

        if(node.berubah!=0)
            updateVal(node);

        node.sz++;
        if(val<node.asli) {
            node.left = insert(node.left, val);
        }
        else if(val>node.asli) {
            node.right = insert(node.right, val);
        }
        else {
            node.cnt++;
            return node;
        }

        return node;
    }

    nod delete(nod node, long val, int cnt){
        if(node==null)
            return node;

        node.sz -= cnt;
        if(val<node.asli) {
            node.left = delete(node.left, val, cnt);
        }
        else if(val>node.asli) {
            node.right = delete(node.right, val, cnt);
        }
        else {

            node.cnt -= cnt;
            if(node.cnt>0)
                return node;

            nod temp;

            if(node.left==null){
                return node.right;
            }
            if(node.right==null)
                return node.left;

            temp = minValue(node.right);

            node.right = delete(node.right, temp.asli, temp.cnt);

            nod temp2 = node;

            node = temp;
            node.left = temp2.left;
            node.right = temp2.right;
            node.sz = node.cnt + getSz(node.left) + getSz(node.right);
        }

        return node;
    }

    nod minValue(nod node){
        nod cur = node;
        while(cur.left!=null)
            cur = cur.left;

        return cur;
    }

    long find(nod node, long val){
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

    long rise(nod node,long H, long X){
        if(node==null)
            return 0;

        long cnt = 0;

        if(node.berubah!=0)
            updateVal(node);

        if(node.val>H){
            cnt += node.sz;
            cnt -= getSz(node.left);
            node.val += X;
            if(node.right!=null){
                node.right.berubah += X;
            }

            cnt += rise(node.left, H, X);
        }
        else {
            cnt += rise(node.right, H, X);
        }

        return cnt;
    }

    long quake(nod node, long H, long X){
        if(node==null){
            return 0;
        }

        long cnt = 0;

        if(node.berubah!=0)
            updateVal(node);


        if(node.val<H){
            cnt += node.sz;
            cnt -= getSz(node.right);
            node.val -= X;
            if(node.left!=null)
                node.left.berubah -= X;

            cnt += quake(node.right, H, X);
        }
        else
            cnt += quake(node.left, H, X);

        return cnt;
    }

    long sweep(nod node, long H){
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

    void updateVal(nod node){
        node.val += node.berubah;

        if(node.left!=null)
            node.left.berubah += node.berubah;

        if(node.right!=null)
            node.right.berubah += node.berubah;

        node.berubah = 0;
    }

    long cekVal(nod node, long val){
        if(node==null){
            return 0;
        }
        if(node.berubah!=0)
            updateVal(node);

        if(node.asli==val)
            return node.val;
        else if(node.asli<val)
            return cekVal(node.right, val);

        return cekVal(node.left, val);
    }

    int getSz(nod node){
        if(node==null)
            return 0;

        else return node.sz;
    }
    void preOrder(nod node)
    {
        if (node != null)
        {
            if(node.berubah!=0)
                updateVal(node);
            System.out.print(node.val + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

}

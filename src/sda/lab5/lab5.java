/**
 * @author : Sulthan Afif Althaf - 2006473863
 */
//package sda.lab5;

import java.io.*;
import java.util.*;


public class lab5 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static avltre tree = new avltre();
    private static long typedif = 0;
    private static Map<String,nud> mep = new HashMap<>();
    private static Map<Long,avltre> typetree = new HashMap<>();
    private static Map<String,Long> harge = new HashMap<>();

    public static void main(String[] args) {

        //Menginisialisasi kotak sebanyak N
        int N = in.nextInt();
        for(int i = 0; i < N; i++){
            String nama = in.next();
            long harga = in.nextInt();
            long tipe = in.nextInt();
            handleStock(nama, harga, tipe);
        }

        //Query
        //(method dan argumennya boleh diatur sendiri, sesuai kebutuhan)
        int NQ = in.nextInt();
        for(int i = 0; i < NQ; i++){
            String Q = in.next();
            if (Q.equals("BELI")){
                long L = in.nextInt();
                long R = in.nextInt();
                handleBeli(L, R);

            }else if(Q.equals("STOCK")){
                String nama = in.next();
                long harga = in.nextInt();
                long tipe = in.nextInt();
                handleStock(nama, harga, tipe);

            }else{ //SOLD_OUT
                String nama = in.next();
                handleSoldOut(nama);

            }
        }

        out.flush();
    }

    //TODO
    static void handleBeli(long L, long R){
        nud ansA = new nud(1000000001,0);
        nud A = tree.cariMin(tree.root,L,ansA);
        nud ansB = new nud(0,0);
        nud B = tree.cariMax(tree.root,R,ansB);
        nud X = new nud(0,0);
        nud Y = new nud(0,0);
        long dif = 0;

//        System.out.println(A.harga + " " + B.harga);

        if(A.harga>B.harga){
            nud temp = A;
            A = B;
            B = temp;
        }


        if(A.harga>=L && A.harga<=R && B.harga>=L && B.harga<=R){
            if(A.tipe != B.tipe){
                out.println(A.harga + " " + B.harga);
            }
            else {
                if(A.fro!=null || A.bef!=null || B.bef!=null || B.fro!=null){
                    out.println(A.harga + " " + B.harga);
                }
                else if(typedif>1){

                    boolean cek = false;
                    for(avltre treeA : typetree.values()){
                        if(treeA.root==null)
                            continue;

                        treeA.preOrder(treeA.root);
                        System.out.println();

                        ansA = new nud(1000000001,0);
                        nud AA = treeA.cariMin(treeA.root,L,ansA);
                        ansB = new nud(0,0);
                        nud BB = treeA.cariMax(treeA.root,R,ansB);

                        for(avltre treeB : typetree.values()){
                            if(treeB.root==null || treeB.root.tipe==treeA.root.tipe)
                                continue;

                            nud ansAA = new nud(1000000001,0);
                            nud AAA = treeB.cariMin(treeB.root, L,ansAA);
                            nud ansBB = new nud(0,0);
                            nud BBB = treeB.cariMax(treeB.root, R,ansBB);

                            if(AA.harga>=L && AA.harga<=R && BBB.harga>=L && BBB.harga<=R){
                                if(Math.abs(AA.harga - BBB.harga)==dif && AA.harga+BBB.harga>X.harga + Y.harga){
                                    X = AA;
                                    Y = BBB;
                                    cek = true;
                                }
                                else if(Math.abs(AA.harga-BBB.harga)>dif){
                                    X = AA;
                                    Y = BBB;
                                    dif = Math.abs(AA.harga-BBB.harga);
                                    cek = true;
                                }
                            }

                            if(AAA.harga>=L && AAA.harga<=R && BB.harga>=L && BB.harga<=R){
                                if(Math.abs(AAA.harga - BB.harga)==dif && AAA.harga+BB.harga>X.harga + Y.harga){
                                    X = AAA;
                                    Y = BB;
                                    cek = true;
                                }
                                else if(Math.abs(AAA.harga-BB.harga)>dif){
                                    X = AAA;
                                    Y = BB;
                                    dif = Math.abs(AAA.harga-BB.harga);
                                    cek = true;
                                }
                            }
                        }
                    }
                    if(!cek)
                        out.println("-1 -1");
                    else {
                        if(X.harga>Y.harga)
                            out.println(Y.harga + " "+X.harga);
                        else
                            out.println(X.harga+ " " + Y.harga);
                    }

                }
                else {
                    out.println("-1 -1");
                }
            }
        }
        else {
            out.println("-1 -1");
        }
    }


    //TODO
    static void handleStock(String nama, long harga, long tipe){
        nud baru = new nud(harga,tipe);
        mep.put(nama,baru);
        tree.root = tree.insert(tree.root,baru);
        harge.put(nama,harga);
        nud barulagi = new nud(harga,tipe);
        avltre type = new avltre();
        if (typetree.containsKey(tipe)) {
            type = typetree.get(tipe);
//                System.out.println(thisRoot.harga + "harga ni bos");
            type.root = type.insertTipe(type.root,barulagi);
        } else {
            typetree.put(tipe, type);
//                System.out.println(newRoot.harga + "harga ni bos");
            type.root = type.insertTipe(type.root,barulagi);
            typedif++;
        }
//            type.preOrder(type.root);
    }

    //TODO
    static void handleSoldOut(String nama){
        nud now = mep.get(nama);
        tree.root = tree.deletenude(tree.root, now);
        long harga = harge.get(nama);
        avltre type = typetree.get(now.tipe);
//        System.out.println("isi:");
//        type.preOrder(type.root);
//        System.out.println();
//        System.out.println(type.root.harga);
//        System.out.println(harga);
        type.root = type.deleteTipe(type.root, harga);
        if(type.root==null){
//            System.out.println(now.tipe + "null");
            typetree.remove(now.tipe);
            typedif--;
        }

    }


    // taken from https://codeforces.com/submissions/Petr
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

class nud {
    long harga, tipe;
    nud left, right,bef,fro;
    int height;
    int count = 0;

    public nud (long harga, long tipe){
        this.harga = harga;
        this.tipe = tipe;
        height = 1;
        count = 1;
    }
}

// implementasi avl tree dari geeksforgeeks
// https://www.geeksforgeeks.org/avl-tree-set-2-deletion/?ref=lbp
class avltre {
    nud root;

    nud insert(nud nude, nud now) {
        if (nude == null)
            return now;

        if (now.harga < nude.harga)
            nude.left = insert(nude.left, now);
        else if (now.harga > nude.harga)
            nude.right = insert(nude.right, now);
        else {
            if(nude.tipe == now.tipe){
                nude.count++;
            }
            else if(nude.tipe<now.tipe){
                nud temp = nude;
                while(temp.tipe<now.tipe && temp.fro!=null)
                    temp = temp.fro;

                if(temp.tipe<now.tipe){
                    temp.fro = now;
                    now.bef = temp;
                }
                else if(temp.tipe==now.tipe)
                    temp.count++;
                else {
                    temp.bef.fro = now;
                    now.fro = temp;
                    now.bef = temp.bef;
                    temp.bef = now;
                }
            }
            else {
                nud temp = nude;
                while(temp.tipe>now.tipe && temp.bef!=null)
                    temp = temp.bef;

                if(temp.tipe>now.tipe){
                    temp.bef = now;
                    now.fro = temp;
                }
                else if(temp.tipe==now.tipe)
                    temp.count++;
                else {
                    temp.fro.bef = now;
                    now.bef = temp;
                    now.fro = temp.fro;
                    temp.fro = now;
                }
            }
            return nude;
        }

        nude.height = 1 + max(tinggi(nude.left),
                tinggi(nude.right));

        int balance = getBalance(nude);


        if (balance > 1 && now.harga < nude.left.harga)
            return kanan(nude);

        if (balance < -1 && now.harga > nude.right.harga)
            return kiri(nude);

        if (balance > 1 && now.harga > nude.left.harga) {
            nude.left = kiri(nude.left);
            return kanan(nude);
        }

        if (balance < -1 && now.harga < nude.right.harga) {
            nude.right = kanan(nude.right);
            return kiri(nude);
        }

        return nude;
    }

    nud insertTipe(nud nude, nud now) {
        if (nude == null)
            return now;

        if (now.harga < nude.harga)
            nude.left = insertTipe(nude.left, now);
        else if (now.harga > nude.harga)
            nude.right = insertTipe(nude.right, now);
        else {
            nude.count++;
            return nude;
        }

        nude.height = 1 + max(tinggi(nude.left),
                tinggi(nude.right));

        int balance = getBalance(nude);


        if (balance > 1 && now.harga < nude.left.harga)
            return kanan(nude);

        if (balance < -1 && now.harga > nude.right.harga)
            return kiri(nude);

        if (balance > 1 && now.harga > nude.left.harga) {
            nude.left = kiri(nude.left);
            return kanan(nude);
        }

        if (balance < -1 && now.harga < nude.right.harga) {
            nude.right = kanan(nude.right);
            return kiri(nude);
        }

        return nude;
    }


    nud minValuenude(nud nude) {
        nud current = nude;

        while (current.left != null)
            current = current.left;

        return current;
    }

    nud deletenude(nud root, nud now) {
        if (root == null)
            return root;

        if (now.harga < root.harga)
            root.left = deletenude(root.left, now);

        else if (now.harga > root.harga)
            root.right = deletenude(root.right, now);

        else {
            nud temp = root;
            boolean cek = false;
            while (temp.tipe < now.tipe && temp.fro!=null)
                temp = temp.fro;

            while(temp.tipe>now.tipe && temp.bef!=null)
                temp = temp.bef;

            temp.count -= 1;

            if(temp.count>0)
                return root;

            if(temp==root)
                cek = true;

            if(temp.fro!=null || temp.bef!=null){
                if (temp.fro != null) {
                    temp = temp.fro;
                    temp.bef = temp.bef.bef;
                    if(temp.bef!=null) temp.bef.fro = temp;
                } else {
                    temp = temp.bef;
                    temp.fro = temp.fro.fro;
                    if(temp.fro!=null) temp.fro.bef = temp;
                }

                if(cek){
                    temp.left = root.left;
                    temp.right = root.right;
                    temp.height = root.height;
                    root = temp;
                }
                return root;
            }

            if ((root.left == null) || (root.right == null)) {
                temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                temp = minValuenude(root.right);

                root.harga = temp.harga;

                root.right = deletenude(root.right, temp);
            }
        }

        if (root == null)
            return root;

        root.height = max(tinggi(root.left), tinggi(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return kanan(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = kiri(root.left);
            return kanan(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return kiri(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = kanan(root.right);
            return kiri(root);
        }

        return root;
    }

    nud deleteTipe(nud root, long harga) {
        if (root == null)
            return root;

        if (harga < root.harga)
            root.left = deleteTipe(root.left, harga);

        else if (harga > root.harga)
            root.right = deleteTipe(root.right, harga);

        else {
            root.count--;

//            System.out.println(root.count);

            if(root.count>0)
                return root;


            if ((root.left == null) || (root.right == null)) {
                nud temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                nud temp = minValuenude(root.right);

                root.harga = temp.harga;

                root.right = deleteTipe(root.right, temp.harga);
            }
        }

        if (root == null)
            return root;

        root.height = max(tinggi(root.left), tinggi(root.right)) + 1;

        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0)
            return kanan(root);

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = kiri(root.left);
            return kanan(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0)
            return kiri(root);

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = kanan(root.right);
            return kiri(root);
        }

        return root;
    }


    nud kanan(nud y) {
        nud x = y.left;
        nud T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(tinggi(y.left), tinggi(y.right)) + 1;
        x.height = max(tinggi(x.left), tinggi(x.right)) + 1;

        return x;
    }

    nud kiri(nud x) {
        nud y = x.right;
        nud T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(tinggi(x.left), tinggi(x.right)) + 1;
        y.height = max(tinggi(y.left), tinggi(y.right)) + 1;

        return y;
    }

    int getBalance(nud N) {
        if (N == null)
            return 0;
        return tinggi(N.left) - tinggi(N.right);
    }

    nud cariMin(nud now, long l,nud ans){
        if(now==null)
            return ans;

        if(now.harga==l)
            return now;

        if(now.harga>l){
            if(ans.harga>=now.harga) ans = now;
            return cariMin(now.left,l,ans);
        }
        else if(now.harga<l){
            return cariMin(now.right,l,ans);
        }

        return null;
    }

    nud cariMax(nud now, long r, nud ans){
        if(now==null)
            return ans;
        if(now.harga==r)
            return now;

//        System.out.println(now.harga);

        if(now.harga<r){
            if(ans.harga<=now.harga) ans = now;
            return cariMax(now.right,r,ans);
        }
        else if(now.harga>r){
            return cariMax(now.left,r,ans);
        }

        return null;
    }

    int tinggi(nud now) {
        if (now == null)
            return 0;
        return now.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    void preOrder(nud nude)
    {
        if (nude != null)
        {
            System.out.print(nude.harga + " ");
            preOrder(nude.left);
            preOrder(nude.right);
        }
    }

}


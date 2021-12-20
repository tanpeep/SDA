/**
 * @author : Sulthan Afif Althaf - 2006473863
 */
package sda.lab5;

import java.io.*;
import java.util.*;


public class lab5ver2 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);
    private static avltree tree = new avltree();
    private static Map<String,Long> mep = new HashMap<>();
    private static Map<Long, Long> price = new HashMap<>();

    public static void main(String[] args) {

        //Menginisialisasi kotak sebanyak N
        long N = in.nextInt();
        for(long i = 0; i < N; i++){
            String nama = in.next();
            long harga = in.nextInt();
            long tipe = in.nextInt();
            handleStock(nama, harga, tipe);
        }

        //Query
        //(method dan argumennya boleh diatur sendiri, sesuai kebutuhan)
        long NQ = in.nextInt();
        for(long i = 0; i < NQ; i++){
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
        long ansA = 1000000001;
        long A = tree.cariMin(tree.root,L,ansA);
        long ansB = 0;
        long B = tree.cariMax(tree.root,R,ansB);


        if(A>B){
            long temp = A;
            A = B;
            B = temp;
        }


        if(A!=ansA && B!=ansB && A!=ansB && B!=ansA && A>=L && A<=R && B>=L && B<=R){
            long val = price.get(A);
            if(A!=B || val>1){
                out.println(A+" "+B);
            }
            else
                out.println("-1 -1");
        }
        else{
            out.println("-1 -1");
        }
    }

    //TODO
    static void handleStock(String nama, long harga, long tipe){
        noda baru = new noda(harga);
        mep.put(nama,harga);

        if(price.containsKey(harga)){
            long val = price.get(harga);
            val += 1;
            price.put(harga, val);
        }
        else {
            price.put(harga, (long) 1);
            tree.root = tree.insert(tree.root, harga);
        }
    }

    //TODO
    static void handleSoldOut(String nama){
        long now = mep.get(nama);
        long val = price.get(now);
        if(val>1){
            val -= 1;
            price.put(now,val);
        }
        else {
            price.remove(now);
            tree.root = tree.deleteNode(tree.root, now);
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

        public long nextInt() {
            return Integer.parseInt(next());
        }
    }
}



class noda
{
    long harga;
    int height;
    noda left, right;

    noda(long d)
    {
        harga = d;
        height = 1;
    }
}

// implementasi avl tree dari geeksforgeeks
// https://www.geeksforgeeks.org/avl-tree-set-2-deletion/?ref=lbp
class avltree {
    noda root;

    noda insert(noda node, long harga) {
        if (node == null)
            return (new noda(harga));

        if (harga < node.harga)
            node.left = insert(node.left, harga);
        else if (harga > node.harga)
            node.right = insert(node.right, harga);
        else
            return node;

        node.height = 1 + max(tinggi(node.left),
                tinggi(node.right));

        int balance = getBalance(node);


        if (balance > 1 && harga < node.left.harga)
            return kanan(node);

        if (balance < -1 && harga > node.right.harga)
            return kiri(node);

        if (balance > 1 && harga > node.left.harga) {
            node.left = kiri(node.left);
            return kanan(node);
        }

        if (balance < -1 && harga < node.right.harga) {
            node.right = kanan(node.right);
            return kiri(node);
        }

        return node;
    }


    noda minValueNode(noda node) {
        noda current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    noda deleteNode(noda root, long harga) {
        if (root == null)
            return root;

        if (harga < root.harga)
            root.left = deleteNode(root.left, harga);

        else if (harga > root.harga)
            root.right = deleteNode(root.right, harga);

        else {

            if ((root.left == null) || (root.right == null)) {
                noda temp = null;
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
                noda temp = minValueNode(root.right);

                root.harga = temp.harga;

                root.right = deleteNode(root.right, temp.harga);
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


    noda kanan(noda y) {
        noda x = y.left;
        noda T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(tinggi(y.left), tinggi(y.right)) + 1;
        x.height = max(tinggi(x.left), tinggi(x.right)) + 1;

        return x;
    }

    noda kiri(noda x) {
        noda y = x.right;
        noda T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(tinggi(x.left), tinggi(x.right)) + 1;
        y.height = max(tinggi(y.left), tinggi(y.right)) + 1;

        return y;
    }

    int getBalance(noda N) {
        if (N == null)
            return 0;
        return tinggi(N.left) - tinggi(N.right);
    }

    long cariMin(noda now, long l,long ans){
        if(now==null)
            return ans;

        if(now.harga==l)
            return l;

        if(now.harga>l){
            if(ans>=now.harga) ans = now.harga;
            return cariMin(now.left,l,ans);
        }
        else if(now.harga<l){
            return cariMin(now.right,l,ans);
        }

        return -1;
    }

    long cariMax(noda now, long r, long ans){
        if(now==null)
            return ans;
        if(now.harga==r)
            return r;

        if(now.harga<r){
            if(ans<=now.harga) ans = now.harga;
            return cariMax(now.right,r,ans);
        }
        else if(now.harga>r){
            return cariMax(now.left,r,ans);
        }

        return -1;
    }

    int tinggi(noda now) {
        if (now == null)
            return 0;
        return now.height;
    }

    int max(int a, int b) {
        return (a > b) ? a : b;
    }

}


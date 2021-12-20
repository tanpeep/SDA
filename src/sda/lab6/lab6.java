/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

//package sda.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class node{
    long val;
    int index,pos;

    node(long val, int index){
        this.val = val;
        this.index = index;
    }
}

/**
 * implementasi terinspirasi dari GeeksForGeeks dan slide SDA Binary Heap
 * https://www.geeksforgeeks.org/max-heap-in-java/?ref=leftbar-rightbar
 */
class binheap {
    node[] daun;
    int size;

    binheap(){
        daun = new node[200010];
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

   void insert(node now){
       now.pos = size;
       daun[size] = now;

       int cur = size;
       size++;

       while(daun[cur].val<daun[parent(cur)].val ||
               (daun[cur].val==daun[parent(cur)].val && daun[cur].index<daun[parent(cur)].index)){
           if(cur==0)
               break;
//           System.out.println(daun[cur].val + " " + daun[parent(cur)].val);
//           System.out.println("cur:" + cur + " " + parent(cur));
           node temp = daun[cur];
           daun[cur] = daun[parent(cur)];
           daun[parent(cur)] = temp;
           daun[cur].pos = cur;
           daun[parent(cur)].pos = parent(cur);
           cur = parent(cur);
       }
   }

   void delete(long newVal){
        node now = daun[0];
        now.val = newVal;
        daun[0] = daun[size--];
        percodown(0);
   }

   void percoup(int index){
       if(index<=0)
           return;

       int cur = index;
       while(daun[cur].val<daun[parent(cur)].val ||
               (daun[cur].val==daun[parent(cur)].val && daun[cur].index<daun[parent(cur)].index)){
//           System.out.println(daun[cur].val + " " + daun[parent(cur)].val);
//           System.out.println(cur);
           node temp = daun[cur];
           daun[cur] = daun[parent(cur)];
           daun[parent(cur)] = temp;
           daun[cur].pos = cur;
           daun[parent(cur)].pos = parent(cur);
           cur = parent(cur);
       }
   }


   void percodown(int index){
        if(index> (size/2) && index<size)
            return;

        if(index>=size)
            return;

//        System.out.println(index + " " + size);

        if(daun[index]==null || (daun[left(index)]!=null && (daun[index].val>daun[left(index)].val ||
                (daun[index].val==daun[left(index)].val && daun[index].index>daun[left(index)].index))) ||
                (daun[right(index)]!=null && (daun[index].val>daun[right(index)].val ||
                (daun[index].val==daun[right(index)].val && daun[index].index>daun[right(index)].index)))){

            if(daun[left(index)]==null || (daun[right(index)]!=null && daun[right(index)].val<daun[left(index)].val)
                    || (daun[right(index)]!= null && daun[right(index)].val==daun[left(index)].val
                            && daun[right(index)].index<daun[left(index)].index)){
//                System.out.println("KIRI");
                node temp = daun[index];
                daun[index] = daun[right(index)];
                daun[right(index)] = temp;
                daun[index].pos = index;
                daun[right(index)].pos = right(index);
                percodown(right(index));
            }
            else if(daun[right(index)]==null || daun[left(index)]!=null){
//                System.out.println("KANAN");
                node temp = daun[index];
                daun[index] = daun[left(index)];
                daun[left(index)] = temp;
                daun[index].pos = index;
                daun[left(index)].pos = left(index);
                percodown(left(index));
            }
        }
   }

   void cetak(){
        for(int i=0;i<size;i++){
            System.out.println(daun[i].val + " " + daun[i].index);
        }
        System.out.println();
   }


}


public class lab6 {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        List<node> tanah = new ArrayList<>();
        binheap arheap = new binheap();

        int N = in.nextInt();
        for (int i = 0; i < N; i++) {
            long height = in.nextInt();
            node baru = new node(height, i);
            tanah.add(baru);
            arheap.insert(baru);
        }

        int Q = in.nextInt();
        while(Q-- > 0) {
            String query = in.next();
            if (query.equals("A")) {
                long y = in.nextInt();

                node baru = new node(y, N);
                tanah.add(baru);
                N++;
                arheap.insert(baru);
            } else if (query.equals("U")) {
                int x = in.nextInt();
                long y = in.nextInt();
                node update =tanah.get(x);

                if(update.val<y){
                    update.val = y;
                    arheap.percodown(update.pos);
                }
                else if(update.val>y){
//                    System.out.println(update.val);
//                    System.out.println(update.pos);
                    update.val = y;
                    arheap.percoup(update.pos);
                }
            } else {
                // TODO: Handle query R
                int index = arheap.daun[0].index;
                long newVal;
                if(index==0){
                    newVal = tanah.get(index).val;
                    if(N>1)
                        newVal = Math.max(tanah.get(index).val,tanah.get(index+1).val);

                    if(N>1) {
                        tanah.get(index).val = newVal;
                        tanah.get(index+1).val = newVal;
//                        arheap.percoup(tanah.get(index).pos);
//                        arheap.percoup(tanah.get(index+1).pos);
                        arheap.percodown(tanah.get(index+1).pos);
                        arheap.percodown(tanah.get(index).pos);
                    }
                }
                else if(index==N-1){
                    newVal = Math.max(tanah.get(index).val,tanah.get(index-1).val);

                    tanah.get(index).val = newVal;
                    tanah.get(index-1).val = newVal;
//                    arheap.percoup(tanah.get(index).pos);
//                    arheap.percoup(tanah.get(index-1).pos);
                    arheap.percodown(tanah.get(index-1).pos);
                    arheap.percodown(tanah.get(index).pos);
                }
                else {
                    newVal = Math.max(tanah.get(index).val,Math.max(tanah.get(index-1).val,tanah.get(index+1).val));

                    tanah.get(index).val = newVal;
                    tanah.get(index+1).val = newVal;
                    tanah.get(index-1).val = newVal;
//                    arheap.percoup(tanah.get(index).pos);
//                    arheap.percoup(tanah.get(index+1).pos);
//                    arheap.percoup(tanah.get(index-1).pos);
                    if(tanah.get(index+1).pos>tanah.get(index-1).pos){
                        arheap.percodown(tanah.get(index+1).pos);
                        arheap.percodown(tanah.get(index-1).pos);
                    }
                    else {
                        arheap.percodown(tanah.get(index-1).pos);
                        arheap.percodown(tanah.get(index+1).pos);
                    }
                    arheap.percodown(tanah.get(index).pos);

                }

                out.println(newVal + " " + index);
//                int cnt = 0;
//                while(true){
//                    System.out.println(cnt);
//                    cnt++;
//                    if(arheap.daun[0].val== tanah.get(arheap.daun[0].index)){
//                        int index = arheap.daun[0].index;
////                        System.out.println(index);
//                        long newVal;
//                        if(index==0){
//                            newVal = tanah.get(index);
//                            if(N>1)newVal = Math.max(tanah.get(index),tanah.get(index+1));
//
//                            if(N>1) {
//                                tanah.set(index, newVal);
//                                tanah.set(index + 1, newVal);
//                            }
//                        }
//                        else if(index==N-1){
//                            newVal = Math.max(tanah.get(index),tanah.get(index-1));
//
//                            tanah.set(index, newVal);
//                            tanah.set(index-1, newVal);
//                        }
//                        else {
//                            newVal = Math.max(tanah.get(index),Math.max(tanah.get(index-1),tanah.get(index+1)));
//
//                            tanah.set(index, newVal);
//                            tanah.set(index-1,newVal);
//                            tanah.set(index+1,newVal);
//                        }
//
//                        out.println(newVal + " " + index);
//                        break;
//                    }
//                    else {
//                        long newVal = tanah.get(arheap.daun[0].index);
//                        arheap.delete(newVal);
//                    }
//                }
            }

//            arheap.cetak();
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
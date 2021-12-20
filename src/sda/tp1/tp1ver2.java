/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

package sda.tp1;


import java.io.*;
import java.util.*;

public class tp1ver2 {
    private static InputReader in;
    private static PrintWriter out;



    public static void main(String args[]) throws IOException{
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int tc;

        tc = in.nextInt();

        for(int i=0;i<tc;i++){
            int n,p,e,cntEval=0;
            Map<String, namaKode> orang = new HashMap<>();
            PriorityQueue<namaKode> rank = new PriorityQueue<>();

            n = in.nextInt();
            cntEval = n;

            ArrayList<String> kode = new ArrayList<>();
            long terkecil = 0; long terbesar = n-1;

            for(int j=0;j<n;j++){
                String spesialisasi="";
                String nama = in.next();
                kode.add(nama);
                spesialisasi = in.next();
                orang.put(nama, new namaKode(nama,(long) j,spesialisasi.charAt(0),false,j));
            }


            e = in.nextInt();

            for(int j=0;j<e;j++){
                p = in.nextInt();

                for(int k =0;k<p;k++){
                    String u;
                    int v;

                    u = in.next();
                    v = in.nextInt();

                    //System.out.println(index);
                    namaKode orangNow = orang.get(u);
                    if(v==0){
                        terkecil--;
                        orangNow.nilai = terkecil;
                    }
                    else {
                        terbesar++;
                        orangNow.nilai = terbesar;
                    }


                    orangNow.tunjuk++;
                }


                for(int k=0;k<n;k++){
                    rank.add(orang.get(kode.get(k)));
                }

                int cnt = 0;
                while(rank.size()>0){
                    namaKode depan = rank.poll();
                    if(j==e-1)
                        kode.set(cnt,depan.nama);
                    depan.nilai = (long) cnt;
                    if(!depan.eval){
                        if(depan.before>cnt){
                            depan.eval = true;
                            cntEval--;
                        }
                    }
                    out.print(depan.nama + " ");
                    depan.before = cnt;
                    cnt++;
                }

                out.println();

            }

            String evaluasi = in.next();

            if(evaluasi.equals("PANUTAN")){
                int Q = in.nextInt();
                int baso=0,somay=0;

                for(int j=0;j<Q;j++){
                    char special = orang.get(kode.get(j)).skill;
                    if(special == 'B')
                        baso++;
                    else
                        somay++;
                }
                out.println(baso + " " + somay);
            }
            else if(evaluasi.equals("KOMPETITIF")){
                String namaKompetitif = "";
                int nilaiKompetitif=0;

                for(int j=0;j<n;j++){
                    int now = orang.get(kode.get(j)).tunjuk;
                    if (now > nilaiKompetitif) {
                        nilaiKompetitif = now;
                        namaKompetitif = kode.get(j);
                    }
                }
                out.println(namaKompetitif + " " + nilaiKompetitif);
            }
            else if(evaluasi.equals("EVALUASI")){
                if(cntEval>0) {
                    for (int j = 0; j < n; j++) {
                        if (!orang.get(kode.get(j)).eval) {
                            out.print(kode.get(j) + " ");
                        }
                    }
                }
                else out.print("TIDAK ADA");
                out.println();
            }
            else if(evaluasi.equals("DUO")){
                Queue<String> baso = new ArrayDeque<>();
                Queue<String> somay = new ArrayDeque<>();

                for(int j=0;j<n;j++){
                    if(orang.get(kode.get(j)).skill=='B'){
                        baso.add(kode.get(j));
                    }
                    else
                        somay.add(kode.get(j));
                }

                while(baso.size()>0 && somay.size()>0){
                    out.println(baso.poll() + " " + somay.poll());
                }

                if(baso.size()>0 || somay.size()>0){
                    out.print("TIDAK DAPAT: ");

                    while(baso.size()>0){
                        out.print(baso.poll()+" ");
                    }

                    while(somay.size()>0){
                        out.print(somay.poll()+" ");
                    }

                    out.println();
                }
            }
            else {
                int q = in.nextInt();
                long[][][] dp = new long[q+2][3][n+5];

                if(orang.get(kode.get(0)).skill =='B')
                    dp[0][0][1] = 1;
                else
                    dp[0][1][1] = 1;

                for(int j=1;j<=q;j++){
                    int sumB = 0;
                    int sumS = 0;
//                    for(int k=0;k<2;k++){
//                        long sum = 0;
                    for(int l=j;l<=n;l++){
                        if(orang.get(kode.get(l-1)).skill=='B'){
                            if(l<n && orang.get(kode.get(l)).skill=='B'){
                                dp[j][0][l+1] += sumB;
                                dp[j][0][l+1] %= 1000000007;
                            }
                            else {
                                dp[j][1][l + 1] += sumB;
                                dp[j][1][l + 1] %= 1000000007;
                            }
                        }
                        else {
                            if(l<n && orang.get(kode.get(l)).skill=='B'){
                                dp[j][0][l+1] += sumS;
                                dp[j][0][l+1] %= 1000000007;
                            }
                            else {
                                dp[j][1][l + 1] += sumS;
                                dp[j][1][l+1] %= 1000000007;
                            }
                        }
                        sumB += dp[j-1][0][l];
                        sumB %= 1000000007;
                        sumS += dp[j-1][1][l];
                        sumS %= 1000000007;
                    }
//                    }
                }
                out.println(dp[q][0][n+1] + dp[q][1][n+1]);
            }
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

class namaKode implements Comparable<namaKode>{
    String nama;
    long nilai;
    char skill;
    boolean eval;
    int before;
    int tunjuk = 0;

    public namaKode(String nama, long nilai, char skill, boolean eval, int before){
        this.nama = nama;
        this.nilai = nilai;
        this.skill  = skill;
        this.eval = eval;
        this.before = before;
    }


    @Override
    public int compareTo(namaKode o) {
        return this.nilai<o.nilai? -1 : 1;
    }
}

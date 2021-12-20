/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

package sda.tp1;


import java.io.*;
import java.util.*;

public class tp1 {
    private static InputReader in;
    private static PrintWriter out;
    private static Map<String,Integer> urutan = new HashMap<>();
    private static Map<String,Character> skill = new HashMap<>();


    /**
     * ini adalah implementasi rekursif dengan base case
     * @param a
     * @param nama
     * @param l
     * @param r
     */
    public static void mergeRec (long a[], String nama[], int l, int r){
        if(l<r){
            int mid = (l+r) / 2;
            mergeRec(a,nama,l,mid);
            mergeRec(a,nama,mid+1,r);

            merge(a,nama,l,r);
        }

//        System.out.println("L: " +l+ " " + r);
//        for(int k=l;k<=r;k++){
//            System.out.print(a[k] + " ");
//        }
    }

    public static void merge(long a[], String nama[], int l, int r){
        int mid = (l+r)/2;
        int lenL = mid-l+1;
        int lenR = r - mid;
        long[] ll = new long[lenL];
        String[] namaL = new String[lenL];
        long[] rr = new long[lenR];
        String[] namaR = new String[lenR];

        for(int i=0;i<lenL;i++){
            ll[i] = a[i+l];
            namaL[i] = nama[i+l];
        }

        for(int i=0;i<lenR;i++){
            rr[i] = a[i+mid+1];
            namaR[i] = nama[i+mid+1];
        }

        int i=0; int j=0; int k = l;

        while(i<lenL && j<lenR){
            if(ll[i]>rr[j]){
                a[k] = ll[i];
                nama[k] = namaL[i];
                i++;
            }
            else {
                a[k] = rr[j];
                nama[k] = namaR[j];
                j++;
            }
            urutan.put(nama[k],k+1);
            k++;
        }

        while(i<lenL){
            a[k] = ll[i];
            nama[k] = namaL[i];
            urutan.put(nama[k],k+1);
            i++;
            k++;
        }

        while(j<lenR){
            a[k] = rr[j];
            nama[k] = namaR[j];
            urutan.put(nama[k],k+1);
            j++;
            k++;
        }
    }

    public static void main(String args[]) throws IOException{
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int tc;

        tc = in.nextInt();

        for(int i=0;i<tc;i++){
            int n,p,e;
            Map<String,Integer> tunjuk = new HashMap<>();
            Map<String,Boolean> eval = new HashMap<>();
            Map <String,Integer> before = new HashMap<>();
            urutan = new HashMap<>();
            skill = new HashMap<>();

            n = in.nextInt();

            String[] kode = new String[n+10];
            long[] nilai = new long[n+10];
            long terkecil = 0; long terbesar = n-1;

            for(int j=0;j<n;j++){
                String spesialisasi="";
                kode[j] = in.next();
                spesialisasi = in.next();
                nilai[j] = n - 1 - j;
                urutan.put(kode[j],j+1);
                skill.put(kode[j],spesialisasi.charAt(0) );
                before.put(kode[j],j+1);
                eval.put(kode[j], Boolean.FALSE);
            }


            e = in.nextInt();

            for(int j=0;j<e;j++){
                p = in.nextInt();

                for(int k =0;k<p;k++){
                    String u;
                    int v;

                    u = in.next();
                    v = in.nextInt();

                    int index = urutan.get(u);
                    //System.out.println(index);
                    if(v==0){
                        terbesar++;
                        nilai[index-1] = terbesar;
                    }
                    else {
                        terkecil--;
                        nilai[index-1] = terkecil;
                    }


                    if(tunjuk.containsKey(u))
                        tunjuk.put(u,tunjuk.get(u)+1);
                    else
                        tunjuk.put(u,1);
                }


                mergeRec(nilai,kode,0, n-1);

                for(int k=0;k<n;k++){
                    if(!eval.get(kode[k])){
                        if(before.get(kode[k])>urutan.get(kode[k])){
                            eval.put(kode[k],Boolean.TRUE);
                        }
                    }
                    out.print(kode[k] + " ");
                    before.put(kode[k], k+1);
                }

                out.println();

            }

            String evaluasi = in.next();

            if(evaluasi.equals("PANUTAN")){
                int Q = in.nextInt();
                int baso=0,somay=0;

                for(int j=0;j<Q;j++){
                    Character special = skill.get(kode[j]);
                    if(special.equals('B'))
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
                    if(tunjuk.containsKey(kode[j])) {
                        int now = tunjuk.get(kode[j]);
                        if (now > nilaiKompetitif) {
                            nilaiKompetitif = now;
                            namaKompetitif = kode[j];
                        }
                    }
                }
                out.println(namaKompetitif + " " + nilaiKompetitif);
            }
            else if(evaluasi.equals("EVALUASI")){
                for(int j=0;j<n;j++){
                    if(!eval.get(kode[j])){
                        out.print(kode[j] + " ");
                    }
                }
                out.println();
            }
            else if(evaluasi.equals("DUO")){
                Queue<String> baso = new ArrayDeque<>();
                Queue<String> somay = new ArrayDeque<>();

                for(int j=0;j<n;j++){
                    if(skill.get(kode[j]).equals('B')){
                        baso.add(kode[j]);
                    }
                    else
                        somay.add(kode[j]);
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

                if(skill.get(kode[0]).equals('B'))
                    dp[0][0][1] = 1;
                else
                    dp[0][1][1] = 1;

                for(int j=1;j<=q;j++){
                    int sumB = 0;
                    int sumS = 0;
//                    for(int k=0;k<2;k++){
//                        long sum = 0;
                    for(int l=j;l<=n;l++){
                        if(skill.get(kode[l-1]).equals('B')){
                            if(l<n && skill.get(kode[l]).equals('B')){
                                dp[j][0][l+1] += sumB;
                                dp[j][0][l+1] %= 1000000007;
                            }
                            else {
                                dp[j][1][l + 1] += sumB;
                                dp[j][1][l + 1] %= 1000000007;
                            }
                        }
                        else {
                            if(l<n && skill.get(kode[l]).equals('B')){
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

/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

package sda.lab3;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class Lab3 {

    private static InputReader in;
    private static PrintWriter out;
    private static long ans = 0;
    private static int gali = 0;
    private static int N;
    private static ArrayList<Long> S = new ArrayList<>();
    private static ArrayList<Long> M = new ArrayList<>();
    private static ArrayList<Long> B = new ArrayList<>();

    // TODO
    static private void findMaxBerlian() {
        long[][][] dp = new long[1010][1010][5]; // now, cnt, before

        for(int i = 0;i<=N;i++)
            for(int j = 0;j<=i;j++)
                for(int k=0;k<3;k++)
                    dp[i][j][k] = 0;

        for(int i = 1;i<=N;i++){
            for(int j = 1; j <= i;j++){
                dp[i][j][0] = max(dp[i][j][0],dp[i-1][j][1]);
                dp[i][j][0] = max(dp[i][j][0],dp[i-1][j][2]);
                dp[i][j][0] = max(dp[i][j][0],dp[i-1][j][0]);
                dp[i][j][1] = max(dp[i][j][1],dp[i-1][j-1][2] + S.get(i-1));
                dp[i][j][1] = max(dp[i][j][1],dp[i-1][j-1][0] + S.get(i-1));
                dp[i][j][2] = max(dp[i][j][2],dp[i-1][j-1][1] + M.get(i-1));
                dp[i][j][2] = max(dp[i][j][2],dp[i-1][j-1][0] + M.get(i-1));
            }
        }
        


        for(int i=0;i<=N;i++){
            for(int j=0;j<3;j++){
                if(i>0)
                    dp[N][i][j] += B.get(i-1);
                if(dp[N][i][j]>ans){
                    ans = dp[N][i][j];
                    gali = i;
                }
                else if(dp[N][i][j]==0){
                    gali = min(gali,i);
                }
            }
        }
    }


    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);


        N = in.nextInt();

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            S.add((long) tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            M.add((long) tmp);
        }

        for(int i=0;i<N;i++) {
            int tmp = in.nextInt();
            B.add((long) tmp);
        }

        findMaxBerlian();

        out.print(ans + " " + gali);

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

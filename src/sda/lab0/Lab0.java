/**
 * @author : Sulthan Afif Althaf - 2006473863
 */

package sda.lab0;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Lab0 {
    private static InputReader in;
    private static PrintWriter out;


    static long multiplyMod(int N, long P, long[] a) {
        long ans=1;

        /**
         * dilakukan iterasi untuk setiap index dari a,
         * akan di mod kemudian dikali dengan variabel ans
         * kemudian di mod lagi untuk menghindari overflow
         */
        for(int i=0;i<N;i++){
            a[i] = a[i]%P;
            ans = ans*a[i];
            ans = ans%P;
        }
        return ans;
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        // Read value of N and P
        int N = in.nextInt();

        long P = in.nextInt();

        // Read value of a
        long[] a = new long[N];
        for (int i = 0; i < N; ++i) {
            a[i] = in.nextInt();
        }

        // TODO: implement method multiplyMod(int, int, int[]) to get the answer
        long ans = multiplyMod(N, P, a);
        out.println(ans);

        // don't forget to close/flush the output
        out.close();
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


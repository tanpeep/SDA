/**
 * @author : Sulthan Afif Althaf - 2006473863
 *
 * Kolaborator - Mika S. Rahwono
 */

package sda.lab1;

import java.io.*;
import java.util.*;

public class Lab1 {
    private static InputReader in = new InputReader(System.in);
    private static PrintWriter out = new PrintWriter(System.out);

    /**
     * The main method that reads input, calls the function
     * for each question's query, and output the results.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args) {

        int N = in.nextInt();   // banyak bintang
        int M = in.nextInt();   // panjang sequence

        List<String> sequence = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            String temp = in.next();
            sequence.add(temp);
        }

        long maxMoney = getMaxMoney(N, M, sequence);
        out.println(maxMoney);
        out.close();
    }

    public static long getMaxMoney(int N, int M, List<String> sequence) {
        long result = 0;
        long lastSection = 0;
        long thisSection = 0;
        boolean firstStar = false;

        for(int i=1;i<M;i++){
            if(sequence.get(i).equals("*")){
                if(!firstStar) {
                    result = thisSection;
                    firstStar = true;
                }
                else {
                    if (lastSection + thisSection > thisSection) {
                        thisSection += lastSection;
                    }
                    result = Math.max(result, thisSection);
                }
                lastSection = thisSection;
                thisSection = 0;
            }
            else
                thisSection += Long.parseLong(sequence.get(i));
        }

        return result;
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

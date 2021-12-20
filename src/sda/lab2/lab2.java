/**
 * @author = Sulthan Afif Althaf - 2006473863
 */

package sda.lab2;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import static java.lang.Math.min;
import static java.lang.Math.max;


public class lab2 {

    private static InputReader in;
    private static PrintWriter out;
    private static Map<String,Integer> geng = new HashMap<String,Integer>();
    private static Queue<String> antrian = new ArrayDeque<String>();
    private static Deque<Long> nilaiAntrian = new ArrayDeque<Long>();
    private static long[] sudah = new long[200010];
    private static int jumlahGeng = 0;
    private static long jumlahAntrian = 0;

    // TODO
    static private void handleDatang(String Gi, long Xi) {
        if(!geng.containsKey(Gi)){
            jumlahGeng++;
            geng.put(Gi,jumlahGeng);
        }

        antrian.add(Gi);
        nilaiAntrian.addLast(Xi);
        jumlahAntrian += Xi;
    }

    // TODO
    static private String handleLayani(long Yi) {
        String ret = "";
        jumlahAntrian -= Yi;
        while(Yi>0){
            int index = geng.get(antrian.peek());
            long top = nilaiAntrian.pollFirst();
            ret = antrian.peek();
            if(Yi>top){
                sudah[index] += top;
                Yi -= top;
                antrian.poll();
            }
            else {
                sudah[index] += Yi;
                top -= Yi;
                Yi = 0;
                if(top==0) antrian.poll();
                else nilaiAntrian.addFirst(top);
            }
        }
        return ret;
    }

    // TODO
    static private long handleTotal(String Gi) {
        if(!geng.containsKey(Gi)) return 0;
        int index = geng.get(Gi);
        return sudah[index];
    }

    public static void main(String args[]) throws IOException {

        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        for(int i=1;i<=200000;i++) {
            sudah[i] = 0;
        }

        int N;

        N = in.nextInt();

        for(int tmp=0;tmp<N;tmp++) {
            String event = in.next();

            if(event.equals("DATANG")) {
                String Gi = in.next();
                long Xi = in.nextInt();
                handleDatang(Gi, Xi);
                out.println(jumlahAntrian);
            } else if(event.equals("LAYANI")) {
                long Yi = in.nextInt();

                out.println(handleLayani(Yi));
            } else {
                String Gi = in.next();

                out.println(handleTotal(Gi));
            }
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
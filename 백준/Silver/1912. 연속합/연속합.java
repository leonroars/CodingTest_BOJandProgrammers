import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] sequence = new int[N];
        String seq = br.readLine();
        StringTokenizer st = new StringTokenizer(seq);
        for (int i = 0; i < N; i++) {
            sequence[i] = Integer.parseInt(st.nextToken());
        }

        int maxSoFar = -Integer.MAX_VALUE, maxEndingHere = 0;
        for (int i = 0; i < N; i++) {
            maxEndingHere = maxEndingHere + sequence[i];
            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
            }
            if (maxEndingHere < 0) {
                maxEndingHere = 0;
            }
        }

        System.out.print(maxSoFar);
    }
}
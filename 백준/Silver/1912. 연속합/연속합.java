import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] memo = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        int maxAccum = 0;
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(st.nextToken());
            if(i == 0){memo[i] = current; maxAccum = current;}
            else{
                memo[i] = Math.max(memo[i-1] + current, current);
                maxAccum = Math.max(maxAccum, memo[i]);
            }
        }

        System.out.print(maxAccum);
    }
}
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int N; // 전체 수열의 길이
    static int K; // 목표 구간의 길이
    static int max = -Integer.MAX_VALUE; // 최대 구간합.
    static int[] seq;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        seq = new int[N];
        
        st = new StringTokenizer(br.readLine());
        int accum = 0;
        
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(st.nextToken());
            if(i < K){
                accum += current;
                if(i == K - 1){max = accum;}
            }
            else {
                accum = accum - (seq[i - K]) + current;
                if(max < accum){max = accum;}
            }
            seq[i] = current;
        }
        
        System.out.print(max);
        
    }
}
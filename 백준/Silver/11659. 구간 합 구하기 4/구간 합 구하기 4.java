import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 풀이 및 접근
// 우선 첫째 수부터 k번째 수 까지의 누적합을 dp[k]에 저장한다.
// 그렇다면 구간합(a, b) = 구간합(1, b) - 구간합(1, a-1)로 알 수 있다.

public class Main{
    static int[] seq;
    static int[] dp;
    static int N;

    static int partialSum(int start, int end){
        return dp[end] - dp[start - 1];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        seq = new int[N + 1];
        dp = new int[N + 1];
        int M = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int accum = 0;
        for(int i = 1; i < N + 1; i++){
            int current = Integer.parseInt(st.nextToken());
            seq[i] = current;
            accum += current;
            dp[i] = accum;
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            sb.append(partialSum(start, end));
            if(i != M - 1){sb.append("\n");}
        }

        System.out.print(sb);
    }
}
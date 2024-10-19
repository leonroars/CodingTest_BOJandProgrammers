import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main{
    static int N;
    static int[] wine;
    static int[][] dp; // dp[i][0] : i번째 포도주를 마실 때 최대 섭취량 / dp[i][1] : i 번째 포도주 스킵할 때 최대 섭취량

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        wine = new int[N + 1];
        dp = new int[N + 1][2];

        // 0. 입력
        for(int i = 1; i < N + 1; i++){
            wine[i] = Integer.parseInt(br.readLine());
        }

        System.out.print(drink());

    }

    private static int drink(){
        for(int i = 1; i < N + 1; i++){
            if(i == 1){dp[i][0] = wine[i];}
            else{
                // i 번째 포도주를 마시는 경우 최대 섭취량 : i-2를 마시고 왔거나 i-2 건너 뛰고 i- 1 마시고 온 경우 중 최대.
                dp[i][0] = Math.max(dp[i - 2][0] + wine[i], dp[i-2][1] + wine[i - 1] + wine[i]);
                // i 번째 포도주 안 마시는 경우 최대 섭취량 : i-1 마시고 온 경우와 i-1 안 마시고 온 경우 중 최대.
                dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            }
        }

        return Math.max(dp[N][0], dp[N][1]);
    }
}
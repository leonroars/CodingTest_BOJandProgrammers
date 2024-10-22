import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    static int N;
    static int[] stair;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        stair = new int[N + 1];
        dp = new int[N + 1][2]; // dp[i][0] : i번째 계단 밟을 때 해당 위치 점수 최대 합 / dp[i][1] : 안 밟을 때.

        // 0. 입력 - O(N)
        for(int i = 1; i < N + 1; i++){
            stair[i] = Integer.parseInt(br.readLine());
        }

        // 1. Bottom Up DP
        for(int k = 1; k < N + 1; k++){
            // Case I : 첫 번째 계단
            if(k == 1){dp[k][0] = stair[1];}
            // Case II : 두 번째 계단 **
            else if(k == 2){
                dp[k][0] = dp[k - 1][0] + stair[k];
                dp[k][1] = dp[k - 1][0];
            }
            // Case III : 세 번째 계단
            else if(k == 3){
                dp[k][0] = stair[k] + Math.max(dp[k - 2][0], dp[k - 2][1] + stair[k - 1]); // = max(stair[k - 2], stair[k - 1])
                dp[k][1] = stair[k - 2] + stair[k - 1];
            }
            // Case IV : 네 번째 이상의 계단을 밟거나 / 밟지 않을 때 취득 가능한 최대 점수.
            else {
                // 1) 밟는 경우 : (k - 2) 번째 계단을 밟고 왔거나 / (k - 3) 밟고 (k - 2) 건너 뛰고 (k - 1) 밟고 온 경우 중 더 큰 값.
                dp[k][0] = stair[k] + Math.max(dp[k - 2][0], (dp[k - 3][0] + stair[k - 1]));
                // 2) 밟지 않는 경우 : (k - 2)번째 계단을 반드시 밟아야 한다.
                dp[k][1] = dp[k - 1][0];
            }

        }

        System.out.print(dp[N][0]);
    }
}

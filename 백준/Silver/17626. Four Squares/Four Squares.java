import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    static int N; // 최대 50,000
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1];

        fourSquares();

        System.out.print(dp[N]);

    }

    // Bottom Up DP. 시간 제한이 타이트한 경우 유효하다.
    private static void fourSquares(){
        for(int i = 1; i < N + 1; i++){
            // Base Case
            if(i < 4){dp[i] = i;}
            else {
                for(int j = 1; j * j <= i; j++){
                    if(j * j == i){dp[i] = 1;}
                    else {
                        int current = 1 + dp[i - (j * j)];
                        if(dp[i] == 0 || current < dp[i]){dp[i] = current;} // 갱신
                    }
                }
            }
        }
    }
}
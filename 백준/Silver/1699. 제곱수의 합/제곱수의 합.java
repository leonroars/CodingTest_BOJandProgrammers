import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main{
    static int N; // 1 <= N <= 100,000
    static int[] dp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1];

        System.out.print(squareSum());
    }

    private static int squareSum(){

        for(int i = 0; i < N + 1; i++){
            if(i < 4){dp[i] = i;}
            else{
                for(int j = 1; j * j <= i; j++){
                    if(j * j == i){dp[i] = 1;}
                    else {
                        int current = dp[i - (j * j)] + dp[j * j];
                        if(dp[i] == 0 || dp[i] > current){
                            dp[i] = current;
                        }
                    }
                }
            }
        }

        return dp[N];
    }
}
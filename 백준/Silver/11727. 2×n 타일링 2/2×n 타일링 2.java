import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] dp = new int[N + 1];
    
        for(int i = 1; i < N + 1; i++){
            if(i == 1){dp[1] = 1;}
            else if(i == 2){dp[2] = 3;}
            else {dp[i] = (((dp[i - 1]) % 10007) + ((2 * dp[i - 2]) % 10007)) % 10007;}
        }
    
        System.out.print(dp[N]);
    }
}


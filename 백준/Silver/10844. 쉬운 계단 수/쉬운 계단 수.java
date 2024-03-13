import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int[][] dp; // dp[i][k] = i번째 수가 k인 길이 i 계단 수의 갯수. 십억으로 나눈 나머지를 저장하므로 int 적절.
    static int N; // 계단 수의 길이

    static int stairNum(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < 10; j++){
                // Case I : 첫 번째 수를 결정하는 경우의 수
                if(i == 0){
                    if(j == 0){dp[i][j] = 0;} // Edge Case I : 첫 수가 0인 계단 수는 존재하지 않는다.
                    else{dp[i][j] = 1;} // 그 외에 첫 번째 수가 j인 경우의 수는 각각 하나씩 존재한다.
                }
                // Case II : 이후의 자리수를 결정하는 경우의 수
                else {
                    if(j == 0){dp[i][j] = dp[i-1][j+1] % 1000000000;}
                    else if(j == 9){dp[i][j] = dp[i-1][j-1] % 1000000000;}
                    else{dp[i][j] = (int)((long)(dp[i-1][j-1] + dp[i-1][j+1]) % 1000000000);}
                }
            }
        }
        int accum = 0;
        for(int k = 0; k < 10; k++){
            accum = (int)((long)(accum + dp[N-1][k]) % 1000000000);
        }
        return accum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N][10];

        System.out.print(stairNum());
    }
}
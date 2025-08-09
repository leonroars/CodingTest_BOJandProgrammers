import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()); // Weight Limit
        
        int[] weight = new int[N];
        int[] value = new int[N];
        
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            weight[i] = Integer.parseInt(st.nextToken());
            value[i] = Integer.parseInt(st.nextToken());
        }
        
        int[][] dp = new int[N][K + 1]; // dp[i][k] : 최대 무게가 k로 제한될때, 0~i 번째 물건으로 만들 수 있는 최대 가치합.
        
        for(int i = 0; i < N; i++){
            for(int w = 0; w < K + 1; w++){
                if(i == 0){
                    if(w >= weight[i]){dp[i][w] = value[i];}
                }
                else {
                    // i 번째 물건을 담아도 무게 제한 이하인 경우 : 담을 지 말지 고민
                    if(w >= weight[i]){
                        dp[i][w] = Math.max(dp[i-1][w], dp[i-1][w - weight[i]] + value[i]);
                    }
                    else {
                        dp[i][w] = dp[i-1][w];
                    }
                }
            }
        }
        
        System.out.print(dp[N-1][K]);
    }
}
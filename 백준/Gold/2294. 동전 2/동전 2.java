import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        int[] dp = new int[K + 1]; // dp[p] : p 원을 만들기 위해 필요한 동전의 최소 갯수.
        
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(br.readLine()); // 동전의 가치
            if(current <= K){dp[current] = 1;}
        }
        
        // 1원~k원까지 총 만 개의 금액에 대해, 최소 몇 개의 동전이 필요한지 파악하기.
        for(int targetPrice = 1; targetPrice <= K; targetPrice++){
            if(dp[targetPrice] != 1){
                int min = 10001;
                for(int low = 1; low <= targetPrice / 2; low++){
                    // 유의미한 조합이 나오기 위해선 둘 다 0이 아니어야 한다!
                    int left = dp[low];
                    int right = dp[targetPrice - low];
                    
                    if(left != 0 && right != 0){
                        int currentMin = left + right;
                        min = Math.min(min, currentMin);
                    }
                }
                // 만들 수 있는 조합이 있을 때에만 갱신.
                dp[targetPrice] = (min == 10001) ? 0 : min;
            }
        }
        
        // 출력
        int answer = (dp[K] == 0) ? -1 : dp[K];
        System.out.print(answer);
    }
}
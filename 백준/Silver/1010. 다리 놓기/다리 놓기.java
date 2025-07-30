import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            
            dp = new int[N][M];
            int result = makeBridge(N, M);
            answer.append(result);
            if(tc < TC - 1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
        
    }
    
    private static int makeBridge(int N, int M){
        // Case I : 동과 서의 사이트 수가 동일할 때 가능한 다리 놓기 경우의 수는 1.
        if(N == M){return 1;}
        
        // Case II : N < M 일 때 -> Bottom Up DP.
        for(int w = N - 1; w >= 0; w--){
            
            // Base Case : w == N - 1
            if(w == N - 1){
                for(int e1 = N - 1; e1 < M; e1++){
                    dp[w][e1] = 1;
                }
            }
            
            else {
                // General Case : 0 <= w < N - 1
                for(int e = w; e < w + (M - N + 1); e++){
                    int accum = 0;
                    int limit = ((e + 1) + (M - N + 1)) > M - 1 ? M : (e + 1) + (M - N + 1);
                    for(int e2 = e + 1; e2 < limit; e2++){
                        accum += dp[w + 1][e2];
                    }
                    dp[w][e] = accum;
                }
            }
        }
        
        int result = 0;
        for(int e3 = 0; e3 < (M - N + 1); e3++){
            result += dp[0][e3];
        }
        
        return result;
    }
    
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] dp; // dp[i][j] = 가장 왼쪽의 카드가 i번째, 가장 오른쪽의 카드가 j번째 카드일 때 플레이어가 취할 수 있는 최대 점수.
    static int[] cards;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            int N = Integer.parseInt(br.readLine());
            dp = new int[N][N];
            cards = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            // 카드 입력 받기
            for(int i = 0; i < N; i++){
                cards[i] = Integer.parseInt(st.nextToken());
            }
            
            // Bottom Up
            for(int l = 1; l < N + 1; l++){
                for(int start = 0; start < N - l + 1; start++){
                    int end = start + l - 1;
                    
                    // 근우 차례인 경우 : 자신이 가질 수 있는 카드 총합이 최대가 되도록 하는 선택을 한다.
                    if(isATurn(N, l)){
                        if(l == 1){dp[start][end] = cards[start];}
                        else if(l == 2){dp[start][end] = Math.max(cards[start], cards[end]);}
                        else {
                            int pickLeft = cards[start] + dp[start + 1][end];
                            int pickRight = dp[start][end - 1] + cards[end];
                            
                            dp[start][end] = Math.max(pickLeft, pickRight);
                        }
                    }
                    
                    // 명우 차례인 경우 : dp[start][end] 가 가장 작도록 하는 선택을 한다.
                    else {
                        if(l == 1){dp[start][end] = cards[start];}
                        else if(l == 2){dp[start][end] = Math.min(cards[start], cards[end]);}
                        else {
                            dp[start][end] = Math.min(dp[start + 1][end], dp[start][end - 1]);
                        }
                    }
                }
            }
            
            result.append(dp[0][N - 1]);
            if(tc < TC - 1){result.append("\n");}
        }
        
        System.out.print(result.toString());
    }
    
    private static boolean isATurn(int N, int cardCnt){
        return N % 2 == cardCnt % 2;
    }
}
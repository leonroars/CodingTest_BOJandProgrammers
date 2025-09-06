import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            int N = Integer.parseInt(br.readLine());
            int[] pages = new int[N];
            int[] sum = new int[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            for(int i = 0; i < N; i++){
                pages[i] = Integer.parseInt(st.nextToken());
                if(i > 0){sum[i] = sum[i - 1] + pages[i];}
                else{sum[i] = pages[i];}
            }
            
            answer.append(findMinCost(pages, sum));
            if(tc < TC-1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    public static int findMinCost(int[] pages, int[] sum){
        int N = pages.length;
        int[][] dp = new int[N][N]; // N*N 배열 : 250,000_ dp[i][j] = i번째 파트부터 j번째 파트까지 합치는데 필요한 최소비용.
        
        for(int i = N - 1; i >= 0; i--){
            for(int j = i; j < N; j++){
                if(i == j){dp[i][j] = 0;}
                else if(i == j - 1){dp[i][j] = pages[i] + pages[j];}
                else{
                    for(int k = i + 1; k < j; k++){
                        int currentCost = Math.min(dp[i][k-1] + dp[k][j] + getPartialSum(i, j, sum)
                                                   , dp[i][k] + dp[k + 1][j] + getPartialSum(i, j, sum));
                        if(dp[i][j] == 0 || dp[i][j] > currentCost){dp[i][j] = currentCost;}
                    }
                }
            }
        }
        
        return dp[0][N-1];
    }
    
    private static int getPartialSum(int i, int j, int[] sum){
        return (i == 0) ? (sum[j]) : (sum[j] - sum[i - 1]);
    }
}
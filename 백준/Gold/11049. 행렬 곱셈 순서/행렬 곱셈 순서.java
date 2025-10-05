// 설계 30분

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    
    static int[][] dp; // dp[i][j] : i번째 행렬부터 j번째 행렬까지 곱셈할 때 필요한 최소 곱셉 연산 수
    static int N; // 행렬 수
    static int[][] matrices; // 행렬들
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        dp = new int[N][N];
        matrices = new int[N][2];
        
        // 1. 행렬 입력 받기
        for(int n = 0; n < N; n++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            matrices[n][0] = r;
            matrices[n][1] = c;
        }
        
        // 2. solution 실행
        // (1, N) 범위 내에 존재하는 임의의 l 길이의,
        for(int l = 1; l < N + 1; l++){
            // (0, N-l) 범위 내에 존재하는 s를 시작점으로 하는 구간에 대해, 해당 구간의 행렬을 곱하는 최소 곱셈횟수 찾기.
            for(int s = 0; s < N - l + 1; s++){
                calculateDP(s, s + l - 1);
            }
        }
        
        // 3. 출력
        System.out.print(dp[0][N-1]);
        
    }
    
    // dp[i][j] 계산 / afterMult[i][j] 갱신
    public static void calculateDP(int i, int j){
        if(i == j){
            dp[i][j] = 0;
        }
        else if(i == j - 1){
            dp[i][j] = multiplyMatrices(
                matrices[i][0], matrices[i][1], matrices[j][0], matrices[j][1]
            );
        }
        else {
            int minByFarForIAndJ = Integer.MAX_VALUE;
            for(int k = i + 1; k < j; k++){
                int currentMin = Math.min(dp[i][k-1] + dp[k][j] + multiplyMatrices(matrices[i][0], matrices[k-1][1], matrices[k][0], matrices[j][1])
                                          , dp[i][k] + dp[k + 1][j] + multiplyMatrices(matrices[i][0], matrices[k][1], matrices[k+1][0], matrices[j][1]));
                
                minByFarForIAndJ = Math.min(minByFarForIAndJ, currentMin);
            }
            dp[i][j] = minByFarForIAndJ;
        }
    }
    
    // 두 행렬 곱셈에 필요한 곱셈 연산 수 계산
    public static int multiplyMatrices(int r1, int c1, int r2, int c2){return r1 * c1 * c2;}
    
}
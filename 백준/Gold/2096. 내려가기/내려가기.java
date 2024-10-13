import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 0. 제출 풀이 시간 복잡도 : O(N)
 1. 문제의 제약조건
    - 시간 제한 : 1초
    - 메모리 제한 : 256MB(Java 11)
    - 입력 크기 : 최소 1, 최대 100,000
    
    * 입력 크기와 시간 제한을 고려하였을 때, O(N log N) 이하의 시간 복잡도를 갖는 풀이를 고민해야한다.
    * Greedy Choice Property X. 당장의 최선이 전체 해의 최선으로 귀결되지 않음.
        - ex. 1     1    1
              100   0    0
              0     0   999
              
    * 하지만 Optimal Substructure 성질 & Overlapping Subproblem을 만족한다.
    
      1) Optimal Substructure
         - K < N-1인 K 행 그리고 0 이상 2 이하인 J열에 대하여,
            (K, J) 선택 시 취득 가능한 최대 점수 합은
             그로부터 이동 가능한 (K + 1, J), (K+1, J + 1) 선택 시 취득 가능한 최대 합 중 더 큰 것이다.
         - K == N - 1인 경우 (K, J) 위치 선택시 취득 가능한 최대 점수는 해당 위치에 할당된 점수 그 자체이다.
      2) Overlapping Subproblem
         - 부분문제 : "어떤 위치 (K, J) 선택시 취득 가능한 최대 합 / 최소 합"
         - 열 위치 별 다음 행에서 선택 가능한 위치가 겹치는 곳이 있다.
           ex. (p, 0) (p, 1) 위치에서 취득 가능한 최대/최소 합을 구하는 문제를 해결할 때,
               두 부분문제는 "(p + 1, 1) 위치에서 취득 가능한 최대/최소합" 이라는 부분문제를 공유한다.
               따라서 DP를 통해 반복 문제 해결을 피할 수 있는 것이다.

2. 풀이 설계
   - 시간보다 공간이 상대적으로 많이 주어졌다는 점에 주목하여,
     각 위치별 취득 가능한 최대/최소 위치를 저장하기 위해 배열을 별도로 배정하는 대신
     반복문 한 번(즉 3 * N 개의 입력에 대한 탐색)을 통해 최대/최소를 동시에 구하도록 한다.
     그렇게 한다면 선형 시간 복잡도를 갖는 풀이를 설계할 수 있다!
 */


public class Main {
    static int N;
    static int[][] board;
    static int[][] max;
    static int[][] min;
    static StringBuilder answer = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        board = new int[N][3]; // N 행 3 열
        max = new int[N][3]; // 최대
        min = new int[N][3]; // 최소
        
        // 1. 입력
        for(int row = 0; row < N; row ++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int col = 0; col < 3; col++){
                board[row][col] = Integer.parseInt(st.nextToken());
                
                // 점수가 0점일 수 있기 때문에 -1로 초기화.
                max[row][col] = -1; 
                min[row][col] = -1;
            }
        }
        
        // 2. 실행.
        maxAndMin();
        
        int totalMax = Math.max((Math.max(max[0][0], max[0][1])), max[0][2]);
        int totalMin = Math.min((Math.min(min[0][0], min[0][1])), min[0][2]);
        
        answer.append(totalMax);
        answer.append(" ");
        answer.append(totalMin);
        
        System.out.print(answer.toString());
    }
    
    private static void maxAndMin(){
        for(int row = N-1; row >= 0; row--){
            for(int col = 0; col < 3; col++){
                // Base Case
                if(row == N - 1){
                    max[row][col] = min[row][col] = board[row][col];
                }
                else {
                    if(col == 0){
                        max[row][col] = board[row][col] + Math.max(max[row + 1][col], max[row + 1][col + 1]);
                        min[row][col] = board[row][col] + Math.min(min[row + 1][col], min[row + 1][col + 1]);
                    }
                    else if(col == 1){
                        max[row][col]
                            = board[row][col] + Math.max(Math.max(max[row + 1][col - 1], max[row + 1][col]), max[row + 1][col + 1]);
                        min[row][col]
                            = board[row][col] + Math.min(Math.min(min[row + 1][col - 1], min[row + 1][col]), min[row + 1][col + 1]);
                        
                    }
                    else {
                        max[row][col] = board[row][col] + Math.max(max[row + 1][col], max[row + 1][col - 1]);
                        min[row][col] = board[row][col] + Math.min(min[row + 1][col], min[row + 1][col - 1]);                        
                    }
                }
            }
        }
    }
}

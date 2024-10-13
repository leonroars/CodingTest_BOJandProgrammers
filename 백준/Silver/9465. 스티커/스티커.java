import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/*
 1. 문제 풀이 알고리즘 : DP
 2. 핵심 아이디어 : 어떤 스티커([i][j] 위치)를 떼었을 때 그 다음에 취할 수 있는 선택지는 아래의 두 가지 입니다.
                 1) 꼭짓점을 공유하는 대각선 위치의 스티커를 선택하여 뗀다.
                 2) 바로 대각선이 아닌 그 옆의 스티커를 선택하여 뗀다.
                 
                 '최선 같아 보이는 것을 덥석 무는 것이 최선이 아닐 수 있다'는 점에서 포도주 시식 문제와 유사합니다.
 3. 왜 DP 문제인가?
    - 이 문제는 DP의 두 가지 대표적인 속성을 지니고 있습니다.
    1) Optimal Substructure : 어떤 위치 (i, j) 위치의 스티커를 선택했을 때 취할 수 있는 최대 점수는,
                                해당 위치의 스티커를 선택했다고 가정했을 때
                                그 다음에 선택 가능한 스티커 (p, q)를 선택했을 때 취할 수 있는 최대 점수를 포함합니다.
    2) Overlapping Subproblem
        다음과 같은 예를 생각해봅시다.
        
        case) n = 5
           0    1    2   3     4
        --------------------------
     0  | X  |    |    |    |    |
        --------------------------
     1  | X  |    | ?  |    |    |
        --------------------------
        
        위와 같은 예시에서, '?'가 표시된 위치인 (1, 2)를 선택 가능한 이전의 스티커 위치는
        (0, 0) 과 (1, 0) 두 개가 있습니다. - (0, 1)의 경우 (1, 0)을 선택하는 최선의 경우에 포함되므로 표시하지 않았습니다. -
        해당 문제는 Optimal Substructure 성질을 만족하기 때문에,
        (0, 0)과 (1, 0) 각 위치의 스티커를 선택했을 때 각각의 최적해는 (1, 2)를 선택했을 때 취득가능한 최적해를 포함합니다.
        따라서 이 문제는 부분문제가 중복되는 Overlapping Subproblem 성질을 만족합니다.
 */

public class Main {

    static int T; // The number of test cases.
    static int n; // Width
    static int[][] board; // Input
    static int[][] dp;
    static int maxAccum; // 각 테스트케이스 별 최대 점수 담을 변수.
    static StringBuilder answer = new StringBuilder(); // 정답으로 제출할 예정.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        T = Integer.parseInt(br.readLine());
        // T 회 실시
        for(int i = 0; i < T; i++){
            maxAccum = -Integer.MAX_VALUE; // 최댓값 담을 변수 초기화.
            n = Integer.parseInt(br.readLine());
            board = new int[2][n]; // 2 행 N 열의 board
            dp = new int[2][n];

            // 입력 받기
            for(int row = 0; row < 2; row++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                for(int col = 0; col < n; col++){
                    board[row][col] = Integer.parseInt(st.nextToken());
                    dp[row][col] = -1; // 점수가 0부터 시작하므로 -1로 채워주기!
                }
            }

            maxAccum = Math.max(solution(0, 0), solution(1, 0));
            answer.append(maxAccum);
            answer.append("\n");
        }

        System.out.print(answer.toString().trim());

    }

    // 재귀, DP
    private static int solution(int currentRow, int currentCol){
        // Base Case A : (n - 2) 위치에서의 최댓값 = 꼭짓점을 공유하는 스티커의 값까지 포함할 때가 최댓값.
        if(currentCol == n - 2){
            if(currentRow == 0){
                return dp[currentRow][currentCol]
                    = board[currentRow][currentCol] + board[currentRow + 1][currentCol + 1];
            }
            else {
                return dp[currentRow][currentCol] = board[currentRow][currentCol] + board[currentRow - 1][currentCol + 1];
            }
        }

        // Base Case B : (n - 3) 위치에서의 최댓값 =  max(dp[꼭짓점 공유하는 대각선 위치에서의 최댓값], dp[그것을 선택하지 않고 그 옆의 것을 선택하는 경우 최댓값]);
        if(currentCol == n - 3){
            if(currentRow == 0){
                return dp[currentRow][currentCol]
                        = Math.max(board[currentRow][currentCol] + board[currentRow + 1][currentCol + 1] + board[currentRow][currentCol + 2],
                        board[currentRow][currentCol] + board[currentRow + 1][currentCol + 2]);
            }
            else {
                return dp[currentRow][currentCol]
                        = Math.max(board[currentRow][currentCol] + board[currentRow - 1][currentCol + 1] + board[currentRow][currentCol + 2],
                        board[currentRow][currentCol] + board[currentRow - 1][currentCol + 2]);
            }

        }
        // Base Case C : (n-1)위치에서의 최댓값.
        if(currentCol == n - 1){return board[currentRow][currentCol];}

        // Base Case C : 이미 계산된 값이 있는 경우 반환.
        if(dp[currentRow][currentCol] != -1){return dp[currentRow][currentCol];}

        if(currentRow == 0){
            return dp[currentRow][currentCol]
                    = Math.max(board[currentRow][currentCol] + solution(currentRow + 1, currentCol + 1),
                    board[currentRow][currentCol] + solution(currentRow + 1, currentCol + 2));
        }
        return dp[currentRow][currentCol]
                = Math.max(
                        board[currentRow][currentCol] + solution(currentRow - 1, currentCol + 1),
                board[currentRow][currentCol] + solution(currentRow - 1, currentCol + 2));
    }
}
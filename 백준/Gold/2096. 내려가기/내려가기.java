import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

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
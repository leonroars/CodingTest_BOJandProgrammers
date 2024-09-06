import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 [풀이 아이디어] : 각 행을 별도의 수열로 생각하자!
 */


public class Main {
    // static int[][] board;
    static int[][] tab; // DP
    static StringBuilder answer = new StringBuilder();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()); // N * N board.
        int M = Integer.parseInt(st.nextToken()); // M queries.
        
        // Board Initialization.
        // board = new int[N+1][N+1]; // 좌표 접근의 용이성 위해 0~N 인덱스를 갖도록 초기화.
        tab = new int[N+1][N+1];
        for(int row = 1; row < N + 1; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 1; col < N + 1; col ++){
                // board[row][col] = Integer.parseInt(st.nextToken());
                if(col == 1){
                    tab[row][col] = Integer.parseInt(st.nextToken());
                }
                else{
                    tab[row][col] = tab[row][col - 1] + Integer.parseInt(st.nextToken());
                }
            }
        }
        
        // Getting two coordinates per each iteration for M-times.
        for(int q = 0; q < M; q++){
            st = new StringTokenizer(br.readLine());
            
            int r1 = Integer.parseInt(st.nextToken()); // row1
            int c1 = Integer.parseInt(st.nextToken()); // col1
            
            int r2 = Integer.parseInt(st.nextToken()); // row2
            int c2 = Integer.parseInt(st.nextToken()); // col2
            
            solver(r1, c1, r2, c2);
            if(q < M-1){answer.append("\n");}
        }
        
        System.out.println(answer.toString().trim());
        
    }
    
    static void solver(int row1, int col1, int row2, int col2){
        int accum = 0; // 누적합 담을 변수
        
        for(int rowLoc = row1; rowLoc <= row2; rowLoc++){
            // tab[N+1][N+1]이므로, 범위 열이 가장 첫 열인 1 인덱스이더라도 ArrayIndexOutOfBound 피할 수 있음.
            accum += tab[rowLoc][col2] - tab[rowLoc][col1 - 1];
        }
        answer.append(accum);
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    static int N; // Rows
    static int M; // Cols
    static int max = -Integer.MAX_VALUE;
    static int[] candidateRow = new int[]{0, 1, -1, 0};
    static int[] candidateCol = new int[]{1, 0, 0, -1};
    static int[][] board;
    static boolean[][] visited;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        board = new int[N][M];
        visited = new boolean[N][M];
        
        // 0. Board Initialization
        for(int r = 0; r < N; r++){
            st = new StringTokenizer(br.readLine());
            for(int c = 0; c < M; c++){
                board[r][c] = Integer.parseInt(st.nextToken());
            }
        }
        
        // 1. DFS 
        findMaxSum();
        
        // 2. Print answer.
        System.out.print(max);
    }
    
    public static void findMaxSum(){
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                ArrayList<int[]> blocks = new ArrayList<>();
                blocks.add(new int[]{r, c});
                
                visited[r][c] = true;
                createTetromino(blocks, 1);
                visited[r][c] = false;
            }
        }
    }
    
    // (row, col) 을 기준으로 우향/하향/상향 테트로미노 만들기.
    private static void createTetromino(ArrayList<int[]> blocks, int depth){
        // Base Case : depth == 4에 도달한 경우 -> 테트로미노 합산 및 필요 시 갱신.
        if(depth == 4){
            int currentTetrominoSum = calculateSum(blocks);
            max = Math.max(max, currentTetrominoSum);
            return;
        }
        
        // General Case : Depth 4 에 이르기까지 현재 블럭들의 오른쪽 변/아래쪽 변 을 검토하며 새로운 블럭 붙이기.
        for(int[] block : blocks){
            for(int d = 0; d < 4; d++){
                int[] candidateBlock = new int[]{block[0] + candidateRow[d], block[1] + candidateCol[d]};
                // 다음 블럭을 붙일 수 있는 변을 찾은 경우!
                if(isAvailable(candidateBlock[0], candidateBlock[1]) 
                   && !visited[candidateBlock[0]][candidateBlock[1]]){
                    visited[candidateBlock[0]][candidateBlock[1]] = true;
                    ArrayList<int[]> newBlocks = new ArrayList<>(blocks);
                    newBlocks.add(candidateBlock);
                    createTetromino(newBlocks, depth + 1);
                    visited[candidateBlock[0]][candidateBlock[1]] = false;
                }
            }
        }
    }
    
    private static int calculateSum(ArrayList<int[]> blocks){
        int sum = 0;
        for(int[] block : blocks){
            sum += board[block[0]][block[1]];
        }
        return sum;
    }
    
    private static boolean isAvailable(int row, int col){
        return row >= 0 && row < N && col >= 0 && col < M;
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.ArrayList;

public class Main {
    static char[][] board;
    static int R; // 전체 행 수;
    static int C; // 전체 열 수;
    static int maxLen = -Integer.MAX_VALUE;
    static HashSet<Character> used = new HashSet<>();
    
    static int[] dRow = new int[]{-1, 1, 0, 0};
    static int[] dCol = new int[]{0, 0, -1, 1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        board = new char[R][C];
        
        for(int r = 0; r < R; r++){
            String row = br.readLine();
            for(int c = 0; c < row.length(); c++){
                board[r][c] = row.charAt(c);
            }
        }
        
        move(0, 0, 1);
        
        System.out.print(maxLen);
    }
    
    private static void move(int row, int col, int moveCnt){
        
        // 0. 현 문자 방문 처리. (최초 정점을 위해서 필요. 나머지는 이미 확인 후 promising 할 때만 재귀호출하므로 불필요.)
        used.add(board[row][col]);
        
        ArrayList<int[]> adjs = new ArrayList<>();
        
        for(int d = 0; d < 4; d++){
            int nextRow = row + dRow[d];
            int nextCol = col + dCol[d];
            
            if(isAvailable(nextRow, nextCol) && !isUsed(board[nextRow][nextCol])){
                adjs.add(new int[]{nextRow, nextCol});
            }
        }
        
        // Base Case : 탐색 종료 지점 도달.
        if(adjs.size() == 0){
            maxLen = Math.max(maxLen, moveCnt);
        }
        else {
            for(int[] adj : adjs){
                move(adj[0], adj[1], moveCnt + 1);
            }
        }
        
        // 현 문자 방문 처리 해제하기.
        used.remove(board[row][col]);
    }
    
    private static boolean isAvailable(int row, int col){
        return row >= 0 && row < R && col >= 0 && col < C;
    }
    
    private static boolean isUsed(char given){
        return used.contains(given);
    }
}
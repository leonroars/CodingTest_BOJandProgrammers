import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int sourceRow = Integer.parseInt(st.nextToken());
            int sourceCol = Integer.parseInt(st.nextToken());
            
            st = new StringTokenizer(br.readLine());
            int targetRow = Integer.parseInt(st.nextToken());
            int targetCol = Integer.parseInt(st.nextToken());
            
            answer.append(getMinMove(N, sourceRow, sourceCol, targetRow, targetCol));
            if(tc < TC - 1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    private static int getMinMove(int N, int sourceRow, int sourceCol, int targetRow, int targetCol){
        int[] dRow = new int[]{-2, -1, 1, 2, 2, 1, -1, -2};
        int[] dCol = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
        
        boolean[][] visited = new boolean[N][N];
        int[][] cnt = new int[N][N];
        for(int row = 0; row < N; row++){
            Arrays.fill(cnt[row], Integer.MAX_VALUE);
        }
        
        ArrayDeque<Position> q = new ArrayDeque<>();
        
        Position source = new Position(sourceRow, sourceCol);
        visited[sourceRow][sourceCol] = true;
        cnt[sourceRow][sourceCol] = 0;
        q.addLast(source);
        
        while(!q.isEmpty()){
            Position current = q.removeFirst();
            
            for(int i = 0; i < 8; i++){
                int nextRow = current.currentRow + dRow[i];
                int nextCol = current.currentCol + dCol[i];
                
                if(isAvailable(N, nextRow, nextCol)){
                    int nextCnt = cnt[current.currentRow][current.currentCol] + 1;
                    
                    if(!visited[nextRow][nextCol] 
                       || (visited[nextRow][nextCol] && cnt[nextRow][nextCol] > nextCnt)){
                        q.addLast(new Position(nextRow, nextCol));
                        cnt[nextRow][nextCol] = nextCnt;
                        if(!visited[nextRow][nextCol]){visited[nextRow][nextCol] = true;}
                    }
                }
            }
        }
        
        return cnt[targetRow][targetCol];
    }
    
    private static boolean isAvailable(int N, int r, int c){
        return r >= 0 && r < N && c >= 0 && c < N;
    }
    
    static class Position {
        int currentRow;
        int currentCol;
        
        public Position(int r, int c){
            this.currentRow = r;
            this.currentCol = c;
        }
    }
}
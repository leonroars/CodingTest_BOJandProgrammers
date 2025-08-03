import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main{
    public static void main(String[] args) throws IOException {
        StringBuilder answer = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int W = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        
        while(W > 0 && H > 0){
            boolean[][] map = new boolean[H][W];
            for(int row = 0; row < H; row++){
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < W; col++){
                    int valueAtCurrent = Integer.parseInt(st.nextToken());
                    if(valueAtCurrent == 1){map[row][col] = true;}
                }
            }
            int cnt = numberOfIsland(map, W, H);
            answer.append(cnt);
            answer.append("\n");
            
            st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
        }
        
        System.out.print(answer.toString().trim());
    }
    
    private static int numberOfIsland(boolean[][] map, int W, int H){
        boolean[][] visited = new boolean[H][W];
        int islandCnt = 0;
        for(int row = 0; row < H; row++){
            for(int col = 0; col < W; col++){
                if(map[row][col] && !visited[row][col]){
                    BFS(map, visited, row, col);
                    islandCnt++;
                }
            }
        }
        
        return islandCnt;
    }
    
    private static void BFS(boolean[][] map, boolean[][] visited, int startRow, int startCol){
        // 0. 이동방향 정의 : 0, 45, 90, 135, 180, 225, 270, 315
        int[] dRow = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
        int[] dCol = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        visited[startRow][startCol] = true;
        q.addFirst(new int[]{startRow, startCol});
        
        while(!q.isEmpty()){
            int[] current = q.removeLast();
            for(int direction = 0; direction < 8; direction++){
                int[] next = new int[]{current[0] + dRow[direction], current[1] + dCol[direction]};
                // 범위 내 인덱스 && 해당 위치가 육지 && 아직 미방문 상태인 경우 -> 방문 처리 및 큐에 추가.
                if(isAvailable(map.length, map[0].length, next[0], next[1])
                   && map[next[0]][next[1]] && !visited[next[0]][next[1]]){
                    visited[next[0]][next[1]] = true;
                    q.addFirst(next);
                }
            }
        }
    }
    
    private static boolean isAvailable(int H, int W, int row, int col){
        return row >= 0 && row < H && col >= 0 && col < W;
    }
}
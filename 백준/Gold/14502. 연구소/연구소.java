import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class Main {
    static int N; // 행 수
    static int M; // 열 수
    static int W; // 입력으로 주어진 벽의 갯수.
    static int maxSafeZone = -Integer.MAX_VALUE;
    static int[][] lab; // 연구소
    static ArrayList<int[]> virusList; // 바이러스의 위치.
    
    static int[] dRow = new int[]{-1, 1, 0, 0};
    static int[] dCol = new int[]{0, 0, -1, 1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        lab = new int[N][M];
        virusList = new ArrayList<>();
        W = 0;
        
        // 0. 입력 받기.
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < M; col++){
                int current = Integer.parseInt(st.nextToken());
                
                if(current == 2){
                    virusList.add(new int[]{row, col});
                }
                else {
                    if(current == 1){
                        W++;
                    }
                }
                
                lab[row][col] = current;
            }
        }
        
        // 1. 벽을 세울 수 있는 모든 가능성 탐색하며 각각의 경우에서의 안전구역 넓이 파악.
        setWall(0);
        
        // 2. 가장 넓은 안전구역 넓이 출력.
        System.out.print(maxSafeZone);
    }
    
    /*** 재귀적으로 벽을 세울 수 있는 모든 경우의 수 탐색 ***/
    private static void setWall(int depth){
        // Base Case : 이미 세 개의 벽을 세운 경우. -> 바이러스 퍼뜨린 후 안전지대 넓이 파악.
        if(depth == 3){
            int V = spreadVirus();
            int currentMaxSafeZone = (N * M) - (W + 3) - (V);
            if(currentMaxSafeZone > maxSafeZone){maxSafeZone = currentMaxSafeZone;}
            
            return;
        }
        
        for(int row = 0; row < N; row++){
            for(int col = 0; col < M; col++){
                // 빈 칸을 찾아 벽을 세운 후 다음 벽 세우기 위해 메서드 재귀 호출.
                if(lab[row][col] == 0){
                    lab[row][col] = 1;
                    setWall(depth + 1);
                    lab[row][col] = 0; // 원상복귀.
                }
            }
        }
    }
    
    /*** 너비우선탐색 방식으로 바이러스를 퍼뜨린다. 이후 현재 연구소 내 바이러스의 위치 수를 반환. ***/
    private static int spreadVirus(){
        int V = 0;
        boolean[][] visited = new boolean[N][M];
        ArrayDeque<int[]> q = new ArrayDeque<>(virusList);
        
        for(int[] virus : virusList){
            visited[virus[0]][virus[1]] = true;
            V++;
        }
        
        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            for(int i = 0; i < 4; i++){
                int nextRow = current[0] + dRow[i];
                int nextCol = current[1] + dCol[i];
                int[] next = new int[]{nextRow, nextCol};
                
                // 방문 가능한 위치임과 동시에 아직 미방문상태이고 빈칸인 경우 -> 바이러스 전파 가능.
                if(isPossible(next) && isAvailable(next) && !visited[nextRow][nextCol]){
                    q.addLast(next);
                    visited[nextRow][nextCol] = true;
                    V++;
                }
            }
        }
        
        return V;
    }
    
    private static boolean isPossible(int[] pos){
        return pos[0] >= 0 && pos[0] < N && pos[1] >= 0 && pos[1] < M;
    }
    
    private static boolean isAvailable(int[] pos){
        return lab[pos[0]][pos[1]] == 0;
    }
    
    
}
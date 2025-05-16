import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

/*
 <풀이 방식> 1번의 BFS
 <새로 배우게 된 풀이 아이디어 및 해결 스키마>
  - BFS를 Level-order 와 같은 기초적인 개념이 아니라, 'OS 의 멀티태스킹'과 유사하게 작동하는 알고리즘으로 이해할 수 있다.
    '벽을 뚫는다면 / 벽을 뚫지 않는다면' 과 같은 분기에 대해, 깊이우선탐색과 다르게
    각 분기를 별도의 프로세스(태스크)로 분리하여 저장하고 이를 재방문해 해당 분기로부터 작업을 이어나가는 방식으로 작동한다는 것이다.
  - 위와 같은 아이디어를 통해, 어떤 지점 (x, y)에 존재하는 벽을 뚫었을 때 / 뚫지 않았을 때를 모두 동일하게
    분리된 프로세스로 취급하여 큐에 모두 저장하고, 이를 순차적으로 새로 꺼내어 마지막 작업 지점으로부터 이어 작업하는 것이다.
    대신, 큐에서 꺼내어 해당 작업 지점으로부터 작업(이동)을 규칙에 따라 진행할 수 있도록,
    해당 프로세스의 상태를 알려주는 어떤 정보(ex. 해당 지점 이전에 벽을 이미 하나 뚫었는가 아닌가 등)을 함께 저장해야 하는 것이다.
 */

public class Main {
    static int[][][] dist; // dist[x][y][0] : (1, 1)~(x, y) 까지 벽을 뚫지 않고 도달한 최단 거리
    static int[][] map; // map[x][y] == 1 => 벽
    static int N;
    static int M;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N + 1][M + 1];
        dist = new int[N + 1][M + 1][2];
        
        dist[1][1][0] = dist[1][1][1] = 0;
        dist[N][M][0] = dist[N][M][1] = -1;
        
        for(int n = 1; n < N + 1; n++){
            String wholeRow = br.readLine();
            for(int m = 0; m < M; m++){
                map[n][m + 1] = Character.getNumericValue(wholeRow.charAt(m));
            }
        }
        
        BFS();
        
        int answer;
        if(dist[N][M][0] != -1 && dist[N][M][1] != -1){
            answer = Math.min(dist[N][M][0], dist[N][M][1]) + 1;
        }
        else {
            if(dist[N][M][0] == -1 && dist[N][M][1] != -1){answer = dist[N][M][1] + 1;}
            else if(dist[N][M][0] != -1 && dist[N][M][1] == -1){answer = dist[N][M][0] + 1;}
            else{answer = -1;}
        }
        
        System.out.print(answer);
    }
    
    private static void BFS(){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[N + 1][M + 1];
        int[] dRow = new int[]{-1, 1, 0, 0};
        int[] dCol = new int[]{0, 0, -1, 1};
        
        q.addLast(new int[]{1, 1, 0, 0}); // (row, col, hasBroken, distByFar)
        
        while(!q.isEmpty()){
            boolean isPromising = false;
            int[] current = q.removeFirst();
            int currentRow = current[0];
            int currentCol = current[1];
            boolean hasBrokenWall = (current[2] == 1) ? true : false;
            int distByFar = current[3];
            
            // 큐에서 꺼낸 위치에 대한 평가 및 갱신 작업.
            if(!visited[currentRow][currentCol]){
                if(hasBrokenWall){dist[currentRow][currentCol][1] = distByFar;}
                else{dist[currentRow][currentCol][0] = distByFar;}
                
                isPromising = true;
                visited[currentRow][currentCol] = true;
            }
            else {
                if(hasBrokenWall){
                   if(dist[currentRow][currentCol][1] == 0 ||
                     dist[currentRow][currentCol][1] > distByFar){
                       dist[currentRow][currentCol][1] = distByFar;
                       isPromising = true;
                   }
                }
                else {
                    if(dist[currentRow][currentCol][0] == 0 ||
                       dist[currentRow][currentCol][0] > distByFar){
                        dist[currentRow][currentCol][0] = distByFar;
                        isPromising = true;
                    }
                }
            }
            
            // 어떤 지점으로부터 탐색을 재개하는 것이 최단거리 찾기에 유망한 경우에만 큐에 추가.
            if(isPromising){
                for(int i = 0; i < 4; i++){
                    int nextRow = currentRow + dRow[i];
                    int nextCol = currentCol + dCol[i];
                    if(isAvailable(nextRow, nextCol)){
                        if(isWall(nextRow, nextCol)){
                            if(!hasBrokenWall){
                                q.addLast(new int[]{nextRow, nextCol, 1, distByFar + 1});
                            }
                        }
                        else{
                            q.addLast(new int[]{nextRow, nextCol, current[2], distByFar + 1});
                        }
                    }
                }
            }
        }
        
    }
    
    private static boolean isWall(int row, int col){
        return map[row][col] == 1;
    }
    
    private static boolean isAvailable(int row, int col){
        return row > 0 && row < N + 1 && col > 0 && col < M + 1;
    }
}
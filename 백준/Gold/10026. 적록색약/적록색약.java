import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayDeque;

/*
 0. 풀이 아이디어 : 몇 가지 조건이 달린 BFS/DFS 이용한 연결 요소 개수 구하기.
 1. 조건
     - 적록이 인접한 경우, 색약자는 하나의 구역으로 보지만 그렇지 않은 사람은 두 개의 개별 구역으로 인지한다.
     - 이를 고려해 색약자의 인지 구역 탐색 알고리즘과 비색약자의 인지 구역 탐색 두 가지를 설계 후 활용하여 탐색한다.
 */

public class Main{
    static int N;
    static char[][] picture;
    static boolean[][] visited;
    
    static int forColorWeakness = 0;
    static int theOthers = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        picture = new char[N][N];
        visited = new boolean[N][N];
        
        // 0. 입력
        for(int row = 0; row < N; row++){
            String currentRow = br.readLine();
            for(int col = 0; col < N; col++){
                picture[row][col] = currentRow.charAt(col);
            }
        }
        
        // 1. 색약자 시점 탐색
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                if(!visited[row][col]){
                    BFS(row, col, true);
                    forColorWeakness++;
                }
            }
        }
        
        visited = new boolean[N][N]; // 방문 기록 초기화.
        
        // 2. 비색약자 시점 탐색
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                if(!visited[row][col]){
                    BFS(row, col, false);
                    theOthers++;
                }
            }
        }
        
        System.out.print(theOthers + " " + forColorWeakness);
        
        
        
        
        
    }
    
    private static void BFS(int row, int col, boolean isColorWeakness){
        char target = picture[row][col]; // 목표 색
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{row, col});
        visited[row][col] = true;
        
        int[] dRow = new int[]{1, -1, 0, 0};
        int[] dCol = new int[]{0, 0, -1, 1};
        
        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            int cRow = current[0];
            int cCol = current[1];
            
            for(int i = 0; i < 4; i++){
                int[] next = new int[]{cRow + dRow[i], cCol + dCol[i]};
                int nextRow = next[0];
                int nextCol = next[1];
                
                if(isAvailable(next) && !visited[nextRow][nextCol]){
                    // Case I : 색약자 시점 탐색인 경우
                    if(isColorWeakness && (target == 'R' || target == 'G')
                      && (picture[nextRow][nextCol] == 'R' || picture[nextRow][nextCol] == 'G')){
                        visited[nextRow][nextCol] = true;
                        q.addLast(next);
                    }
                    else{
                        if(target == picture[nextRow][nextCol]){
                            visited[nextRow][nextCol] = true;
                            q.addLast(next);
                        }
                    }
                }
            }
        }
        
        
    }
    
    private static boolean isAvailable(int[] pos){
        return pos[0] >= 0 && pos[0] < N && pos[1] >= 0 && pos[1] < N;
    }
}
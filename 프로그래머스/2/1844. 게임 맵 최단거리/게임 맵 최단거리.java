import java.util.Arrays;
import java.util.ArrayDeque;

class Solution {
    
    int[][] visited;
    int N;
    int M;
    
    public int solution(int[][] maps) {
        N = maps.length; // 행
        M = maps[0].length; // 열
        visited = new int[N][M];
        
        // 0. BFS 실시.
        BFS(maps);
        
        // 1. 경로 최솟값 출력
        return (visited[N - 1][M - 1] == 0) ? (-1) : (visited[N-1][M-1]);
        
    }
    
    
    private void BFS(int[][] maps){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{0, 0}); // 처음 캐릭터 위치 추가.
        visited[0][0] = 1;
        int[] dRow = new int[]{-1, 1, 0, 0};
        int[] dCol = new int[]{0, 0, -1, 1};
        
        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            
            for(int i = 0; i < 4; i++){
                int[] next = new int[]{current[0] + dRow[i], current[1] + dCol[i]};
                // 벽이 아니고 이동 가능한 위치에 대하여,
                if(isPossible(next) && maps[next[0]][next[1]] != 0){
                    // 미방문 위치에 대해서만 갱신 후 큐에 추가.
                    if(visited[next[0]][next[1]] == 0){
                        visited[next[0]][next[1]] = visited[current[0]][current[1]] + 1;
                        q.addLast(next);
                    }
                }
            }
            
        }
    }
    
    private boolean isPossible(int[] pos){
        return pos[0] >= 0 && pos[0] < N && pos[1] >= 0 && pos[1] < M;
    }
}
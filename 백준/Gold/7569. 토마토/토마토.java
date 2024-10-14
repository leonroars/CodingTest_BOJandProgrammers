import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;

/*
 1. 문제의 제약 조건
     - 시간 : 1초
     - 메모리 : 256MB
     - 입력
       * 2 <= 가로(M), 세로(N) <= 100
       * 1 <= 높이(H) <= 100
       즉, 최대 입력 주어질 시 필요 탐색 배열 위치 수는 백 만 개.
       충분히 1초 안에 처리할 수 있다.

 */


public class Main {
    
    static int M; // 가로
    static int N; // 세로
    static int H; // 높이
    static int days = -1; // 다 익기까지 걸리는 시간
    static int[][][] box;
    static int[][][] tab;
    static int unripe = 0; // 안 익은 토마토 갯수.
    // 시작 시점에 이미 익은 토마토들 위치. : [0] = h, [1] = row, [2] = col.
    static ArrayList<int[]> starts = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 0. 토마토 상자 가로 * 세로 * 높이 받기.
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        
        box = new int[H][N][M];
        tab = new int[H][N][M];
        
        // 1. 입력 받기 : O(N * M * H)
        for(int h = 0; h < H; h++){
            for(int row = 0; row < N; row ++){
                st = new StringTokenizer(br.readLine());
                for(int col = 0; col < M; col++){
                    int current = Integer.parseInt(st.nextToken());
                    if(current == 0){unripe++;} // 안 익은 경우 추가.
   
                    // 현 토마토가 시작부터 익은 토마토인 경우 : 탐색 시작점
                    if(current == 1){
                        starts.add(new int[]{h, row, col});
                        tab[h][row][col] = 0;
                    }
                    // 아닌 경우
                    else {
                        tab[h][row][col] = -1; // 미방문/미탐색 상태로 설정.
                    }
                    
                    box[h][row][col] = current;
                }
            }
        }
        // Case A : 안 익은 토마토가 적어도 하나 존재하는 경우. -> 탐색
        if(unripe != 0){
            BFS();
            // Case A' : 탐색했으나 익지 못하는 토마토가 존재하는 경우.
            if(unripe != 0){days = -1;}
        }
        // Case B : 안 익은 토마토가 하나도 없는 경우.
        else {
            days = 0;
        }
        
        System.out.print(days);
        
    }
    
    private static void BFS(){
        // 0. 입력 주어질 때 이미 익은 토마토들이 시작점이므로 큐에 추가.
        ArrayDeque<int[]> q = new ArrayDeque<>(starts);
        
        // 탐색순서 : 앞뒤상하좌우
        int[] dH = new int[]{-1, 1, 0, 0, 0, 0};
        int[] dRow = new int[]{0, 0, -1, 1, 0, 0};
        int[] dCol = new int[]{0, 0, 0, 0, -1, 1};
        
        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            
            for(int i = 0; i < 6; i++){
                int[] next 
                    = new int[]{current[0] + dH[i], current[1] + dRow[i], current[2] + dCol[i]};
                // 방문 가능함 && 아직 미방문한 위치에 대하여,
                if(isAvail(next) && tab[next[0]][next[1]][next[2]] == -1){
                    // 안 익은 토마토가 존재하는 경우에만 갱신 후 큐에 추가.
                    // 이미 익은 토마토를 만난 경우, 다른 위치의 토마토에 의해
                    // 벌써 익었음을 의미하므로, 갱신하거나 큐에 추가하지 않는다!
                    if(box[next[0]][next[1]][next[2]] == 0){
                        tab[next[0]][next[1]][next[2]]
                            = tab[current[0]][current[1]][current[2]] + 1;
                        // 전체 일 수 갱신
                        if(tab[next[0]][next[1]][next[2]] > days){
                            days = tab[next[0]][next[1]][next[2]];
                        }
                        unripe--; // 안 익은 토마토 수 차감.
                        q.addLast(next);
                    }
                }
            }
        }
    }
    
    /* 주어진 위치 pos에 대해,
        a) 인덱스 범위 내에 존재하는가?
        b) 비어있는 공간인가?
       검증 후 결과 반환하는 메서드.
    */
    private static boolean isAvail(int[] pos){
        int cH = pos[0];
        int cR = pos[1];
        int cC = pos[2];
        
        return cH >= 0 && cH <= H - 1
            && cR >= 0 && cR <= N - 1
            && cC >= 0 && cC <= M - 1
            && box[cH][cR][cC] != -1;
    }
}
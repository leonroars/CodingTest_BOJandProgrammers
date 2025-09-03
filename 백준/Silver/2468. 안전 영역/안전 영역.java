import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main {
    static int N;
    static int[][] map;
    static int max;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 0. 맵 크기 입력 & 맵 입력 받기
        N = Integer.parseInt(br.readLine());
        map = new int[N][N];
        max = 1;
        
        int lowest = 101;
        int highest = 0;
        
        for(int r = 0; r < N; r++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int c = 0; c < N; c++){
                int current = Integer.parseInt(st.nextToken());
                map[r][c] = current;
                
                lowest = Math.min(lowest, current);
                highest = Math.max(highest, current);
            }
        }
        
        // 1. 시뮬레이션 실행
        simulate(lowest, highest);
        
        // 2. 결과 출력
        System.out.print(max);
        
    }
    
    public static void simulate(int lo, int hi){
        // 0. [lo, hi] 범위 내의 수 k만큼 비가 올 때,
        for(int rain = lo; rain < hi + 1; rain++){
            // 잠긴 구역 표시
            boolean[][] drowned = new boolean[N][N];
            drowned = drown(drowned, rain);
            
            // 안전지대 합산
            int currentSafeArea = countSafeArea(drowned);
            
            // 갱신 필요 시 갱신
            max = Math.max(max, currentSafeArea);
        }
    }
    
    // 비가 rainAmount 만큼 내릴 때, rainAmount 이하 높이 지대가 모두 잠겼음을 표시함.
    private static boolean[][] drown(boolean[][] drowned, int rainAmount){
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                if(map[r][c] <= rainAmount){drowned[r][c] = true;}
            }
        }
        
        return drowned;
    }
    
    // 잠긴 지역 지도를 바탕으로 안전 지대 수 헤아리기
    private static int countSafeArea(boolean[][] drowned){
        int currentCnt = 0;
        int[] dRow = new int[]{-1, 1, 0, 0};
        int[] dCol = new int[]{0, 0, -1, 1};
        boolean[][] visited = new boolean[N][N];
        
        for(int r= 0; r < N; r++){
            for(int c = 0; c < N; c++){
                // 잠기지 않은 지역에 대해서 BFS
                if(!drowned[r][c] && !visited[r][c]){
                    currentCnt++;
                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    q.addLast(new int[]{r, c});
                    visited[r][c] = true;
                    
                    while(!q.isEmpty()){
                        int[] current = q.removeFirst();
                        for(int d = 0; d < 4; d++){
                            int nextRow = current[0] + dRow[d];
                            int nextCol = current[1] + dCol[d];
                            if(isAvailable(nextRow, nextCol)
                               && !drowned[nextRow][nextCol]
                               && !visited[nextRow][nextCol]){
                                visited[nextRow][nextCol] = true;
                                q.addLast(new int[]{nextRow, nextCol});
                            }
                        }
                    }
                }
            }
        }
        
        return currentCnt;
    }
    
    private static boolean isAvailable(int r, int c){
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
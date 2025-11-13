import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        
        // 1. 맵 입력 받기
        int totalEnemiesCnt = 0;
        for(int r = 0; r < N; r++){
            st = new StringTokenizer(br.readLine());
            for(int c = 0; c < M; c++){
                map[r][c] = Integer.parseInt(st.nextToken());
                if(map[r][c] == 1){totalEnemiesCnt++;}
            }
        }
        
        WarGame warGame = new WarGame(N, M, D, map, totalEnemiesCnt);
        
        // 2. 문제 풀기
        int maxElimination = warGame.calculateMaxElimination();
        
        // 3. 출력
        System.out.print(maxElimination);
    }
    
    
    static class WarGame {
        int N;
        int M;
        int D;
        int[][] map;
        int totalEnemiesCnt;
        int maxElimination = -Integer.MAX_VALUE;
        
        public WarGame(int N, int M, int D, int[][] map, int totalEnemiesCnt){
            this.N = N;
            this.M = M;
            this.D = D;
            this.map = map;
            this.totalEnemiesCnt = totalEnemiesCnt;
        }
        
        
        public int calculateMaxElimination(){
            for(int firstArcherCol = 0; firstArcherCol < M - 2; firstArcherCol++){
                for(int secondArcherCol = firstArcherCol + 1; secondArcherCol < M - 1; secondArcherCol++){
                    for(int thirdArcherCol = secondArcherCol + 1; thirdArcherCol < M; thirdArcherCol++){
                        
                        int[][] currentMap = copyMap();
                        int enemiesLeftCnt = totalEnemiesCnt;
                        int eliminatedEnemies = 0;
                        
                        // 게임 종료(적 완전 소멸) 되기 전까지 '적 공격 및 제거 수 파악'-'적 이동'-'종료 여부확인' 반복.
                        while(enemiesLeftCnt > 0){
                            boolean[][] eliminated = simulateAttack(currentMap, firstArcherCol, secondArcherCol, thirdArcherCol);
                            int eliminatedByArcher = 0;
                            
                            for(int r = 0; r < N; r++){
                                for(int c = 0; c < M; c++){
                                    if(eliminated[r][c]){
                                        currentMap[r][c] = 0;
                                        eliminatedByArcher++;
                                    }
                                }
                            }
                            
                            int removedFromMap = moveEnemies(currentMap);
                            
                            enemiesLeftCnt -= (eliminatedByArcher + removedFromMap);
                            eliminatedEnemies += eliminatedByArcher;
                        }
                        
                        // 현 배치 기준 최종 제거 적 수 갱신
                        maxElimination = Math.max(maxElimination, eliminatedEnemies);
                    }
                }
            }
            
            return maxElimination;
        }
        
        /* 해당 위치 궁수로부터 공격 시뮬레이션 */
        private boolean[][] simulateAttack(int[][] currentMap, int firstArcherCol, int secondArcherCol, int thirdArcherCol){
            boolean[][] eliminated = new boolean[N][M];
            
            for(int i = 0; i < 3; i++){
                int currentArcherCol;
                if(i == 0){currentArcherCol = firstArcherCol;}
                else if(i == 1){currentArcherCol = secondArcherCol;}
                else{currentArcherCol = thirdArcherCol;}
                
                int enemyRow = -1;
                int enemyCol = -1;
                int minDist = Integer.MAX_VALUE;
                
                for(int r = N - 1; r >= 0; r--){
                    for(int c = 0; c < M; c++){
                        int distFromArcher = checkDist(N, currentArcherCol, r, c);
                        if(currentMap[r][c] == 1 && distFromArcher <= D){
                            if(distFromArcher < minDist){
                                enemyRow = r;
                                enemyCol = c;
                                minDist = distFromArcher;
                            }
                            else if(distFromArcher == minDist){
                                if(c < enemyCol){
                                    enemyRow = r;
                                    enemyCol = c;
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                }
                
                if(minDist != Integer.MAX_VALUE){
                    eliminated[enemyRow][enemyCol] = true;
                }
            }
            
            return eliminated;
        }
        
        /* 남은 적 이동 : 열 단위로 작업 진행. 가장 아래 행부터 아래로 하나씩 옮긴다. */
        private int moveEnemies(int[][] currentMap){
            int removedFromMapCnt = 0;
            for(int c = 0; c < M; c++){
                for(int r = N - 1; r >= 0; r--){
                    if(currentMap[r][c] == 1){
                        if(r == N - 1){
                            currentMap[r][c] = 0;
                            removedFromMapCnt++;
                        }
                        else {
                            currentMap[r][c] = 0;
                            currentMap[r + 1][c] = 1;
                        }
                    }
                }
            }
            return removedFromMapCnt;
        }
        
        /* 보드 복사 : 궁수 배치 후 복사 필요. */
        private int[][] copyMap(){
            int[][] newMap = new int[N][M];
            for(int r = 0; r < N; r++){
                for(int c = 0; c < M; c++){
                    newMap[r][c] = map[r][c];
                }
            }
            return newMap;
        }
        
        /*궁수 좌표로부터 to 좌표까지의 거리 계산*/
        private int checkDist(int archerRow, int archerCol, int targetRow, int targetCol){
            return Math.abs(archerRow - targetRow) + Math.abs(archerCol - targetCol);
        }
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.PriorityQueue;

/*

1. 문제에서 요구하는 것 : "섬 집합 I 의 임의의 원소 a, b 간 이동이 가능하도록 하기 위해 필요한 도로들의 최소 길이 합"

2. 문제로부터 유도할 수 있는 조건들
 1) 설치했을 때 Cycle이 생기도록 하는 도로는 문제에서 요구하는 최적해를 이루는 도로집합에 포함될 수 없다.
    <귀류법을 활용한 증명>
    섬의 집합 중 부분집합 P가 있고, P의 원소를 {A, B, C}라고 해보자.
    그리고 C와 A를 잇는 도로 k 가 있고 이를 설치했을 때 Cycle이 생긴다고 가정한다.
    그렇다면 A->B->C->A 와 같은 Cycle 이 생성될 것이다.
    이 도로 k가 '모든 섬 간 연결되도록 하는 도로 길이 최소 합'에 포함된다고 가정해보자.
    
    A->B->C 경로의 길이를 X, A->B->C->A 경로 길이를 Y라고 정의해보자.
    문제의 조건에 따라, 도로의 길이는 최소 1이다.
    
    최초 가정에 따르면 X > Y + 1 가 성립하는데, X와 Y 모두 양의 정수이므로 부등식이 성립하지 않는다.
    이를 통해 가정이 모순임을 보일 수 있다.
    
    따라서, Cycle이 생기도록 하는 도로는 '섬 간 이동이 가능하도록 하는 도로 길이 최소합을 이루는 도로 집합'에 포함되지 않는다.
    <Q.E.D>
    
 2) 모든 섬이 서로 연결되어 있기 위해선, 모든 섬이 최소 하나의 다리와 연결되어 있어야 한다.
    
 3) 1), 2)에 따라 섬의 갯수가 N일 때 문제가 요구하는 최적해를 이루는 도로집합의 최소 크기는 N-1이다.
    즉, 모든 N 개의 섬이 연결되기 위해서는 최소 N-1개의 도로가 필요하다는 것이다.
    따라서, 우리가 찾는 '모든 섬을 연결하는 최단 경로'를 구성하는 다리의 수는 'N-1개' 이다.
    
 4) 또한, 도로의 길이가 최소 1인 양의 정수이므로,
    전체 문제의 최적해는 부분문제의 최적해로 구성되는 Optimal Substructure 성질을 만족한다.
    
    전체 문제를 '전체 섬 집합 P의 임의의 원소 A, B 간 연결되도록 하는 도로 길이 최소합'이라고 정의한다면,
    부분 문제는 '전체 섬 집합 P의 부분집합 Q의 임의의 원소 i, j 간 연결되도록 하는 도로 길이 최소합'이라고 정의할 수 있다.
    이때, '연결'의 특성 상 참여 주체가 2개 이상이어야 하므로, 부분집합 Q의 조건은 '원소가 2개 이상인 부분집합'으로 정의할 수 있다.
    
    조금 바꾸어 이야기하면,
    어떤 정점으로부터 다리를 놓을 수 있는 여러 정점 중, '가장 거리가 가까운 섬'에 다리를 놓는 것이 해당 부분 문제의 최적해라는 것이다.
    

3. 위의 성질을 고려한 풀이 설계

 1) 섬 파악하기 :  O(N * M <= 100)
    - 수, 각 섬을 구별케하는 식별자 할당까지 하나의 BFS로 수행.
 2) 각 섬 간 최단 거리 파악하기  : O(N * M)
    - 수평과 수직방향으로 줄을 그어보며 서로 다른 두 섬 간 최단 직선 거리 계산()
    - 크기가 2인 부분집합에 대한 부분문제 최적해를 구하는 과정이다.
 3) 

 */ 


public class Main {
    static int N; // 세로 크기
    static int M; // 가로 크기
    
    static int[][] islandMap; // 1~6번까지 섬 id 로 표현. ex) islandMap[i][j] == 1 -> (i,j)는 1번 섬 소속.
    static int[][] minDist; // minDist[i][j] = i번 섬-j번 섬 간 최단 직선 거리.
    static int islandCnt = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        int[][] map = new int[N][M];
        islandMap = new int[N][M];
        
        ArrayList<int[]> lands = new ArrayList<>();
        
        // 0. Map 입력 받기
        for(int n = 0; n < N; n++){
            st = new StringTokenizer(br.readLine());
            for(int m = 0; m < M; m++){
                map[n][m] = Integer.parseInt(st.nextToken());
                if(map[n][m] == 1){lands.add(new int[]{n, m});}
            }
        }
        
        // 1. 섬 정보 파악하기. V
        identifyIslands(map, lands);
        
        // 2. 모든 두 섬 간 최단 거리 파악하기. V
        calculateMinDistBetweenTwoIslands();
        
        // 3. 임의의 정점으로부터(1번) Greedy 하게 가장 가까운 다른 정점을 찾아 추가하며 트리 구성하기.
        int minLen = findMinLenSum();
        
        // 4. 출력
        System.out.print(minLen);
        
    }
    
    /* 육지 위치와 땅/바다 정보를 토대로 1) 섬을 파악하고, 2) 각 섬에 ID를 부여하여 맵에 표기. */
    public static void identifyIslands(int[][] map, ArrayList<int[]> lands){
        int[] dRow = new int[]{-1, 1, 0, 0}; // 상하좌우
        int[] dCol = new int[]{0, 0, -1, 1};
        
        boolean[][] visited = new boolean[N][M];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        int islandCounter = 1;
        
        for(int[] land : lands){
            // 아직 방문하지 않은 땅으로부터 인접 땅으로 출발. : 해당 섬 ID = islandCounter
            if(!visited[land[0]][land[1]]){
                islandMap[land[0]][land[1]] = islandCounter;
                q.offer(land);
                visited[land[0]][land[1]] = true;
                
                while(!q.isEmpty()){
                    int[] current = q.poll();
                    for(int d = 0; d < 4; d++){
                        int adjRow = current[0] + dRow[d];
                        int adjCol = current[1] + dCol[d];
                        // In-bound && 미방문 && 바다 아니고 땅일 경우 -> 같은 섬에 속한 인접한 땅.
                        if(isAvailable(adjRow, adjCol)
                           && map[adjRow][adjCol] == 1
                           && !visited[adjRow][adjCol])
                        {
                            islandMap[adjRow][adjCol] = islandCounter;
                            visited[adjRow][adjCol] = true;
                            q.offer(new int[]{adjRow, adjCol});
                        }
                    }
                }
                
                // 어떤 땅으로부터 인접한 모든 땅을 탐색함 -> 하나의 섬 탐색 종료했으므로 cnt++.
                islandCounter++;
            }
        }
        islandCnt = islandCounter - 1;
    }
    
    /* 10*10 맵 내에 존재하는 모든 땅의 위치로부터 상,하,좌,우 직선 방향 탐색하며 다른 섬과의 최단 거리 찾기. */
    public static void calculateMinDistBetweenTwoIslands(){
        minDist = new int[islandCnt + 1][islandCnt + 1];
        for(int r = 0; r < N; r++){
            for(int c = 0; c < M; c++){
                // 1. 해당 위치가 어떤 섬에 포함된 경우 -> 해당 위치로부터 상하좌우 직선 방향 끝까지 탐색.
                if(islandMap[r][c] != 0){
                    calculateMinDistFrom(r, c);
                }
            }
        }
    }
    
    private static void calculateMinDistFrom(int row, int col){
        int currentIsland = islandMap[row][col];
        int[] dRow = new int[]{-1, 1, 0, 0};
        int[] dCol = new int[]{0, 0, -1, 1};
        
        for(int d = 0; d < 4; d++){
            int currentRow = row + dRow[d];
            int currentCol = col + dCol[d];
            int bridgeLen = 0;
            
            // 1) 바다만 지나서 도달할 수 있는 섬 찾을 때까지 이동.
            while(isAvailable(currentRow, currentCol) && islandMap[currentRow][currentCol] == 0){
                bridgeLen++;
                currentRow += dRow[d];
                currentCol += dCol[d];
            }
            
            // 2) 여기 도달한 경우, 다른 섬을 찾았거나 or 맵의 끝에 도달한 것
            if(isAvailable(currentRow, currentCol)
                  && islandMap[currentRow][currentCol] != 0
                  && bridgeLen >= 2){
                int otherIsland = islandMap[currentRow][currentCol];
                if(minDist[currentIsland][otherIsland] == 0 
                   || minDist[currentIsland][otherIsland] > bridgeLen){
                    minDist[currentIsland][otherIsland] = bridgeLen;
                }
            }
        }
    }
    
    public static int findMinLenSum(){
        // 0. 도로의 길이를 보관하는 Min-Heap 초기화.
        PriorityQueue<Road> pq = new PriorityQueue<>();
        boolean[] isInTree = new boolean[minDist.length + 1]; // 어떤 섬이 이미 트리를 구성하는 정점인가?
        int cnt = 0; // 트리를 구성하는 섬의 수 추적용.
        int minLenSum = 0;
        
        pq.add(new Road(1, 0)); // 출발 정점 추가.
        
        while(!pq.isEmpty()){
            Road current = pq.poll();
            if(!isInTree[current.to]){
                isInTree[current.to] = true;
                cnt++;
                minLenSum += current.dist;
                
                for(int i = 1; i < islandCnt + 1; i++){
                    // 자기 자신 아니고 && 이동 가능한 인접 섬이라면 pq에 추가.
                    if(i != current.to && minDist[current.to][i] != 0){
                        pq.add(new Road(i, minDist[current.to][i]));
                    }
                }
            }
        }
        
        // 결과 확인 : cnt != islandCnt => -1 반환.
        if(cnt != islandCnt){return -1;}
        return minLenSum;
    }
    
    private static boolean isAvailable(int row, int col){
        return row >= 0 && row < N && col >= 0 && col < M;
    }
    
    static class Road implements Comparable<Road>{
        int to; // 해당 도로의 도착 섬 번호.
        int dist; // 거리
        
        public Road(int to, int dist){
            this.to = to;
            this.dist = dist;
        }
        
        @Override
        public int compareTo(Road otherRoad){
            return Integer.compare(this.dist, otherRoad.dist);
        }
    }
}
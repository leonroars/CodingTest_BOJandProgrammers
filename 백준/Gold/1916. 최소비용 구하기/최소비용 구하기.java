import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.Arrays;

/*
   1. 제약 조건
    - 시간 제한 : 0.5초 (약 50만 번 ~ 100만 번 연산 사이)
    - 메모리 제한 : 128MB(크기 10억인 배열이 512MB 초과였으므로 대략 2.5억)
    - 1 <= 도시의 수 N(정점 수 V) <= 1,000
    - 1 <= 버스의 수 M(간선 수 E) <= 100,000
      ** DFS와 BFS 모두 시간복잡도가 O(V + E) 이므로, 시간 제한 상으로는 두 가지 탐색 모두 가능.
         하지만 메모리 제한을 고려했을 때 재귀적 탐색은 불가.
    - 정답으로 반환할 '최소 비용' int or long?
      : 도시 수가 최대 입력인 1000 / 출발지와 목적지가 0, 999
      / 각 비용 100,000의 버스가 999대가 있으며 / 모든 버스를 다 타야만 출발지-목적지 완주 가능하다면?
      그런 경우 최소 비용 = 1억에 근사.
      즉 int형으로 표현 가능한 범위이므로 int형으로 선언한다.

  2. 풀이
   - 문제의 제약조건만 잘 파악한다면 BFS 탐색의 원칙을 크게 벗어나지 않는다.
   - 우선 BFS로 진행해보려한다. 만약 통과 안되면 Dijkstra 알고리즘을 활용해야 한다.
   -
 */


public class Main{

    static int N;
    static int M;
    static ArrayList<Integer>[] graph; // graph[i] : i로부터 갈 수 있는 도시 목록.
    static int[][] cost; // cost[i][j] : i-j 가는 버스 비용
    static int[] minCost; // minCost[k] : start - k 까지의 최소 비용.
    static int start; // 시작 도시
    static int end; // 도착 도시

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());
        graph = new ArrayList[N + 1];
        cost = new int[N + 1][N + 1];
        minCost = new int[N + 1];
        Arrays.fill(minCost, -1); // 시작점으로부터 각 정점 이동 최소 비용 -1로 초기화. (비용이 0 이상이므로.)

        // 0. 그래프 초기화
        for(int j = 1; j < N + 1; j++){
            graph[j] = new ArrayList<>();
            Arrays.fill(cost[j], -1);
        }

        // 1. 입력
        for(int i = 0; i < M; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int currentCost = Integer.parseInt(st.nextToken());

            if(cost[from][to] == -1 || currentCost < cost[from][to]){cost[from][to] = currentCost;}
            if(!graph[from].contains(to)){graph[from].add(to);}
        }

        StringTokenizer st = new StringTokenizer(br.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        // 2. BFS 시행
        BFS();

        // 3. 출력
        System.out.println(minCost[end]);

    }

    private static void BFS(){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(start);
        minCost[start] = 0;

        while(!q.isEmpty()){
            int current = q.removeFirst();
            ArrayList<Integer> adjacents = graph[current];
            // 이동 가능한 도시에 대해,
            for(int adj : adjacents){
                // 미방문 지점이거나 | 갱신이 유효한 경우 -> 큐에 추가 및 갱신
                if(minCost[adj] == -1 || minCost[adj] > minCost[current] + cost[current][adj]){
                    minCost[adj] = minCost[current] + cost[current][adj];
                    q.addLast(adj);
                }
            }
        }
    }
}
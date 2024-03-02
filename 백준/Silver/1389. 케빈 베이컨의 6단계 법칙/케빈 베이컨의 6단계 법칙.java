import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayDeque;
import java.util.Arrays;

// 풀이 및 접근
//
// - 해당 문제는 사람과 정점을, 친구 관계를 간선에 대응 시켜 그래프로 표현할 수 있는 문제이다.
// - 이때 각 출발 정점에서 다른 정점으로 도달 가능한 최단 거리를 합산한 결과를 케빈 베이컨 수라고 한다.
// - 문제는 케빈 베이컨 수가 가장 작은 사람의 번호를 출력할 것을 요구한다.
// - 가장 작은 사람이 여러 명일 경우 가장 작은 번호를 출력한다.
//
// 문제의 명세를 간단하게 정리해보면 다음과 같다.
// - 친구가 없는 사람은 없다 : 연결 그래프
// - 중복된 친구 관계가 입력될 수 있다 : 인접 리스트를 BitSet[] 으로 구현함으로써, 매 입력 시 기존에 입력된 관계인지 확인.
// - 친구 이외의 관계가 없다: 각 정점 간 간선의 종류가 하나, 즉 가중치가 없는 무가중치 그래프이다.
// - 1과 2가 친구면 2와 1도 친구다 : 무방향 그래프.
//
// 문제의 정점(사람)간 간선(친구관계)는 무방향, 무가중치로 정의된다.
// 무방향 무가중치 그래프에서 BFS는 출발정점으로부터 다른 모든 정점으로의 최단 경로 탐색을 보장한다.
// 따라서 BFS를 이용한 풀이를 사용하기로 한다.
// 개략적인 절차는 다음과 같다.
// 
// 1. 모든 정점에 대하여 각각 다음을 수행한다.
//    1) 시작 정점을 지정한다.
//    2) 시작 정점으로부터 다른 정점으로의 거리를 보관하기 위한 dist[] 배열을 초기화한다.
//    3) 자기 자신으로의 거리를 0으로 설정한다. - dist[시작 정점] = 0;
//    4) 해당 정점과 dist[]를 인자로 BFS()를 호출한다. - BFS(시작정점, dist)
//       a) 큐를 초기화한다.
//       b) 방문 여부를 확인하기 위한 boolean[] visited를 초기화한다.
//       c) 큐에 인자로 받은 정점(시작 정점)을 삽입한다.
//       d) 큐가 비어있지 않은 동안,
//         - 큐에서 정점을 추출한다.
//         - 해당 정점이 미방문인 경우,
//            * visited[방금 뽑은 정점] = true로 설정한다.
//            * 방금 뽑은 정점과 인접한 정점 중 '아직 거리가 갱신되지 않은 정점들'의 거리를 갱신한다.
//               (dist[거리 미갱신 + 인접 정점] = dist[방금 뽑은 정점] + 1)
//            * 거리를 갱신한 정점들을 다시 큐에 삽입한다.
//    5) BFS가 변경한 dist[] 배열을 순회하며, 시작정점을 제외한 나머지 정점들로의 거리를 합산하여
//       해당 출발 정점의 케빈 베이컨 수를 계산한다.
//    6) 계산된 케빈 베이컨 수가 지금까지의 케빈 베이컨 수 최솟값(minKevBac)보다 적으면 갱신하고,
//       누구의 케빈 베이컨 수인지를 나중에 확인하기 위해, 변수(who)에 해당 케빈 베이컨 수를 가진 정점의 번호를 저장한다.

public class Main{
    static class Graph{
        private int vertices;
        private BitSet[] adj; // 친구 관계가 중복되어 입력될 수 있으므로 BitSet을 이용한 인접 리스트를 구현한다.
        private int minKevBac; // 가장 적은 케빈 베이컨 수
        private int who; // 케빈 베이컨 수가 가장 적은 학생의 번호. 케빈 베이컨 수의 최소값 갱신 시 함께 갱신된다.

        public Graph(int vertices, int edges){
            this.vertices = vertices;
            adj = (BitSet[]) new BitSet[vertices + 1]; // Initializing Adjacent List
            for(int i = 1; i < adj.length; i++){
                adj[i] = new BitSet(vertices + 1);
            }
            minKevBac = Integer.MAX_VALUE; // 최솟값 갱신 위해 정수 표현 가능 최댓값으로 초기화.
        }

        //
        public void addEdge(int v, int w){
            adj[v].set(w);
            adj[w].set(v);
        }

        public int KevinBacon(int startVertex){
            for(int i = 1; i < vertices + 1; i++){
                int[] dist = new int[vertices + 1];
                Arrays.fill(dist, -1);
                dist[i] = 0;
                BFS(i, dist);
                int Kev = 0;
                for(int j = 1; j < vertices + 1; j++){
                    Kev += dist[j];
                }
                if(Kev < minKevBac){minKevBac = Kev; who = i;}
                if(Kev == minKevBac && who > i){who = i;}
            }
            return who;
        }

        private void BFS(int vertex, int[] dist){
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            boolean[] visited = new boolean[vertices + 1]; // 방문/미방문 여부 체크하고 확인하기 위한 boolean[].
            queue.addLast(vertex);
            while(!queue.isEmpty()){
                int current = queue.removeFirst();
                if(!visited[current]){
                    visited[current] = true;
                    for(int i = 1; i < vertices + 1; i++){
                        // 기존의 잘못된 코드 : adj[currnet].get(i) && !visited[i]
                        // 현재 방문 여부 확인과 이에 따른 방문은 Lazy 하게 시행하도록 구현한 상태이다. (큐에서 뽑을 때 이루어진다.)
                        // 그렇기 때문에 '거리 미갱신 여부'가 아니라 '미방문 여부'로 조건을 설정 시,
                        // 여러 정점과 인접한 정점이
                        // 이미 발견했지만(이전의 정점에서 더 빠른 경로로 방문 가능) 방문하지 않아
                        // 큐에 다시 삽입된다. 
                        // 
                        if(adj[current].get(i) && dist[i] == -1){
                            queue.addLast(i);
                            dist[i] = dist[current] + 1;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());
        int startVertex  = -1;
        Graph graph = new Graph(vertices, edges);
        for(int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.addEdge(v, w);
            if(startVertex == -1) {startVertex = v;}
        }

        System.out.println(graph.KevinBacon(startVertex));
    }
}

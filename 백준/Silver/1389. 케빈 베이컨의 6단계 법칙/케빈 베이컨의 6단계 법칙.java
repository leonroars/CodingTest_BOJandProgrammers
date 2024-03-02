import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayDeque;

public class Main{
    static class Graph{
        private int vertices;
        private BitSet[] adj; // 친구 관계가 중복되어 입력될 수 있으므로 BitSet을 이용한 인접 리스트를 구현한다.
        private int minKevBac;
        private int who; // 케빈 베이컨 수가 가장 적은 학생의 번호.

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
                        if(adj[current].get(i) && dist[i] == 0){
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

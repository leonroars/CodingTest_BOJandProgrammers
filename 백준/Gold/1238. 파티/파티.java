import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        
        Graph graph = new Graph(N, M, X);
        
        // 도로 입력 받기.
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            graph.addEdge(u, v, w);
        }
        
        graph.calculateShortestPath();
        
        int[] toXFrom = graph.getSPFromAllToX();
        int[] fromXTo = graph.getSPFromXToAll();
        
        int maxDuration = -Integer.MAX_VALUE;
            
        for(int town = 1; town < N + 1; town++){
            int requiredDurationFromTownToX = toXFrom[town] + fromXTo[town];
            maxDuration = Math.max(maxDuration, requiredDurationFromTownToX);
        }
            
        System.out.print(maxDuration);
    }
    
    static class Graph {
        static final int INF = Integer.MAX_VALUE / 4;
        int V;
        int E;
        int X; // 파티가 열리는 마을 번호
        ArrayList<Edge>[] adj;
        int[] distToXFrom; // distToXFrom[k] : k 번 마을 -> X 번 마을로 가는 최단 거리
        int[] distFromXTo; // distFromXTo[k] : X번 마을 -> k 번 마을로 가는 최단 거리
        
        public Graph(int V, int E, int X){
            this.V = V;
            this.E = E;
            this.X = X;
            adj = new ArrayList[V + 1]; // 마을 번호가 1번부터 시작하므로.
            distToXFrom = new int[V + 1];
            distFromXTo = new int[V + 1];
            
            for(int v = 1; v < V + 1; v++){
                adj[v] = new ArrayList<Edge>();
            }
        }
        
        /* 단방향 도로 삽입 */
        public void addEdge(int u, int v, int w){
            adj[u].add(new Edge(u, v, w));
        }
        
        public void calculateShortestPath() {
            // 1~N 번 마을로부터 다른 마을로의 SSSP 실행 : 최악의 경우 약 1억 번 가량의 연산 필요. 1초 내에 가능.
            for(int source = 1; source < V + 1; source++){
                /* Dijkstra 실행 */
                int[] sp = new int[V + 1];
                
                for(int v = 1; v < V + 1; v++){
                    if(v == source){sp[v] = 0;}
                    else{sp[v] = INF;}
                }
                
                PriorityQueue<Node> pq = new PriorityQueue<>();
                pq.offer(new Node(source, 0));
                
                while(!pq.isEmpty()){
                    Node current = pq.poll();
                    
                    if(current.distFromSource > sp[current.number]){continue;}
                    
                    for(Edge edge : adj[current.number]){
                        int next = edge.to;
                        int nextDistFromSource = sp[current.number] + edge.dist;
                        
                        if(nextDistFromSource < sp[next]){
                            sp[next] = nextDistFromSource;
                            pq.offer(new Node(next, sp[next]));
                        }
                    }
                }
                
                // Case I : source == X -> distFromXTo 변수에 sp 에 대한 참조 할당.
                if(source == X){distFromXTo = sp;}
                // Case II : Else -> sp[v] 만 distToXFrom[source]에 저장.
                else {distToXFrom[source] = sp[X];}
            }
        }
        
        public int[] getSPFromAllToX(){return this.distToXFrom;}
        
        public int[] getSPFromXToAll(){return this.distFromXTo;}
        
    }
    
    static class Edge {
        int from;
        int to;
        int dist;
        
        public Edge(int u, int v, int w){
            this.from = u;
            this.to = v;
            this.dist = w;
        }
    }
    
    static class Node implements Comparable<Node> {
        int number;
        int distFromSource;
        
        public Node(int num, int d){
            this.number = num;
            this.distFromSource = d;
        }
        
        @Override
        public int compareTo(Node thatNode){
            if(this.distFromSource == thatNode.distFromSource){return Integer.compare(this.number, thatNode.number);}
            return Integer.compare(this.distFromSource, thatNode.distFromSource);
        }
    }
}
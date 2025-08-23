import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());
        
        DirectedGraph g = new DirectedGraph(V, E);
        
        int source = Integer.parseInt(br.readLine());
        
        for(int e = 0; e < E; e++){
            st = new StringTokenizer(br.readLine());
            
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            g.addEdge(u, v, w);
        }
        
        g.runDijkstra(source);
        
        StringBuilder answer = new StringBuilder();
        
        for(int v = 1; v < V + 1; v++){
            answer.append(g.getShortestDistTo(v));
            if(v < V){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    static class DirectedGraph {
        static ArrayList<DirectedEdge>[] graph;
        static int V;
        static int E;
        static int INF = Integer.MAX_VALUE;
        static int[] distFromSource;
        
        public DirectedGraph(int numberOfVertex, int numberOfEdge){
            this.V = numberOfVertex;
            this.E = numberOfEdge;
            this.distFromSource = new int[numberOfVertex + 1];
            graph = new ArrayList[numberOfVertex + 1];
           
            Arrays.fill(distFromSource, this.INF);
            for(int i = 1; i < V + 1; i++){
                graph[i] = new ArrayList<DirectedEdge>();
            }
        }
        
        public static void addEdge(int u, int v, int w){
            DirectedEdge edge = new DirectedEdge(u, v, w);
            graph[u].add(edge);
        }
        
        public static void runDijkstra(int source){
            PriorityQueue<Node> pq = new PriorityQueue<>();
            
            pq.add(new Node(source, 0));
            
            while(!pq.isEmpty()){
                // PQ 에서 Greedy 하게 뽑은 것은 최단거리가 확정됨이 증명 가능.
                Node current = pq.poll();
                
                // 아직 확정되지 않은 경우에만 갱신.
                // Robert Sedgewick이 제안한 Indexed PQ 를 활용하는 경우엔 중복 여부를 Eager 하게 확인 및 갱신 가능.
                // But, Java Collection PQ 사용 시, 불가하므로 Lazy 하게 갱신을 하게 된다.
                // 따라서, 'PQ에서 꺼낸 가장 최단거리의 정점만이 최단거리임이 확정 가능하다'는 부분에 맞게 작성해야함.
                // 그래야 중복을 피할 수 있음.
                if(distFromSource[current.num] == INF){
                    distFromSource[current.num] = current.dist;
                    // 인접 정점들까지의 거리를 계산해 PQ에 삽입
                    
                    for(DirectedEdge edge : graph[current.num]){
                        int adj = edge.to;
                        int adjDist = distFromSource[current.num] + edge.weight;
                        pq.add(new Node(adj, adjDist));
                    }
                }
            }
        }
        
        public static String getShortestDistTo(int vertexNum){
            if(distFromSource[vertexNum] == Integer.MAX_VALUE){
                return "INF";
            }
            return Integer.toString(distFromSource[vertexNum]);
        }
        
        static class DirectedEdge {
            int from;
            int to;
            int weight;
            
            public DirectedEdge(int u, int v, int w){
                this.from = u;
                this.to = v;
                this.weight = w;
            }
        }
        
        static class Node implements Comparable<Node> {
            int num;
            int dist;
            
            public Node(int vertexNum, int distFromSource){
                this.num = vertexNum;
                this.dist = distFromSource;
            }
            
            @Override
            public int compareTo(Node thatNode){
                return Integer.compare(this.dist, thatNode.dist);
            }
        }
    }
}
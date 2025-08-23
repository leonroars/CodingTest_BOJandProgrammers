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
        
        Graph graph = new Graph(V, E);
        
        for(int e = 0; e < E; e++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            graph.addEdge(u, v, w);
        }
        
        st = new StringTokenizer(br.readLine());
        
        int k = Integer.parseInt(st.nextToken());
        int j = Integer.parseInt(st.nextToken());
        
        System.out.print(graph.shortestPathIncluding(k, j));
    }
    
    static class Graph {
        static int INF = Integer.MAX_VALUE; // 간선 가중치 최대 1,000 * 200,000 < Integer.MAX_VALUE
        static int V;
        static int E;
        static ArrayList<Edge>[] adjOf;
        
        public Graph(int numberOfVertex, int numberOfEdge){
            this.V = numberOfVertex;
            this.E = numberOfEdge;
            adjOf = new ArrayList[V + 1];
            
            for(int v = 1; v < V + 1; v++){
                adjOf[v] = new ArrayList<Edge>();
            }
        }
        
        public static void addEdge(int u, int v, int w){
            adjOf[u].add(new Edge(u, v, w));
            adjOf[v].add(new Edge(v, u, w));
        }
        
        
        // k, j 번 정점을 포함하는 1~N 으로의 최단 경로 계산
        public static String shortestPathIncluding(int k, int j){
            int[] shortestPathFromOneTo = shortestPathFrom(1);
            int[] shortestPathFromKTo = shortestPathFrom(k);
            int[] shortestPathFromJTo = shortestPathFrom(j);
            
            // (1, k)-(k, j)-(j, N) v.s (1, j)-(j, k)-(k, N) 둘 중 더 짧은 것 구하기.
            long answer 
                = Math.min(
                ((long)shortestPathFromOneTo[k] + (long)shortestPathFromKTo[j] + (long)shortestPathFromJTo[V]),
                (long)(shortestPathFromOneTo[j] + (long)shortestPathFromJTo[k] + (long)shortestPathFromKTo[V])
            );
            
            return (answer >= INF) ? ("-1") : (Long.toString(answer));
        }
        
        private static int[] shortestPathFrom(int source){
            int[] distFromSource = new int[V + 1];
            Arrays.fill(distFromSource, INF);
            PriorityQueue<Node> pq = new PriorityQueue<>();
            
            pq.add(new Node(source, 0));
            
            while(!pq.isEmpty()){
                Node current = pq.poll();
                
                if(distFromSource[current.num] == INF){
                    distFromSource[current.num] = current.dist;
                    
                    for(Edge edge : adjOf[current.num]){
                        int adj = edge.v;
                        int adjDist = distFromSource[current.num] + edge.w;
                        pq.add(new Node(adj, adjDist));
                    }
                }
            }
            
            return distFromSource;
        }
        
        
        static class Edge {
            int u;
            int v;
            int w;
            
            public Edge(int thisEnd, int thatEnd, int weight){
                this.u = thisEnd;
                this.v = thatEnd;
                this.w = weight;
            }
        }
        
        static class Node implements Comparable<Node>{
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int V = Integer.parseInt(br.readLine());
        int E = Integer.parseInt(br.readLine());
        
        Graph graph = new Graph(V, E);
        
        for(int e = 0; e < E; e++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            graph.addEdge(u, v, w);
        }
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int source = Integer.parseInt(st.nextToken());
        int dest = Integer.parseInt(st.nextToken());
        
        // Source로부터 SSSP 실행
        graph.findShortestPath(source);
        
        // Shortest Dist 회수
        long minCostFromSourceToDest = graph.getMinCostFromSource(dest);
        
        // 경로 회수
        String path = graph.getShortestPathFromSource(dest);
        
        // 경로 길이 회수
        int pathLength = graph.getShortestPathLength(dest);
        
        System.out.println(minCostFromSourceToDest);
        System.out.println(pathLength);
        System.out.print(path);
    }
    
    static class Graph {
        int V; // 도시 수
        int E; // 버스 수
        ArrayList<Edge>[] adj; // Adjacency List
        int[][] prev; // For Path Construction _ prev[k][0] : 바로 이전 정점 번호 / prev[k][1] : source-k까지 경로 길이
        long[] sp; // Shortest Path's Distances From Source
        
        public Graph(int V, int E){
            this.V = V;
            this.E = E;
            adj = new ArrayList[V + 1]; // 도시 번호가 1부터 시작.
            prev = new int[V + 1][2];
            sp = new long[V + 1];
            
            for(int v = 1; v < V + 1; v++){
                adj[v] = new ArrayList<Edge>();
                prev[v][0] = 0; // 0번 도시가 존재하지 않으므로 경로 종료 식별 가능하도록 0으로 설정.
                prev[v][1] = 1; // 자신 자체만으로도 경로 길이가 1인 것으로 간주.
                sp[v] = Long.MAX_VALUE / 4; // MAX_VALUE / 4로 할당함으로써, 발생 가능한 가장 긴 경로 길이 저장이 가능하도록 함과 동시에 overflow 방지.
            }
        }
        
        public void addEdge(int from, int to, int cost){
            adj[from].add(new Edge(from, to, cost));
        }
        
        public void findShortestPath(int source) {
            PriorityQueue<Node> pq = new PriorityQueue<>();
            sp[source] = 0;
            pq.offer(new Node(source, 0));
            
            while(!pq.isEmpty()){
                Node current = pq.poll();
                
                // Prunning.
                if(current.distFromSource > sp[current.number]){continue;}
                
                for(Edge adj : adj[current.number]){
                    int next = adj.to;
                    long nextDistFromSource = sp[current.number] + (long)adj.cost;
                    
                    // Edge Relaxation : 이때 Path Construct.
                    if(sp[next] > nextDistFromSource){
                        sp[next] = nextDistFromSource;
                        prev[next][0] = current.number;
                        prev[next][1] = prev[current.number][1] + 1;
                        pq.offer(new Node(next, nextDistFromSource));
                    }
                }
            }
        }
        
        public long getMinCostFromSource(int to){
            return sp[to];
        }
        
        public String getShortestPathFromSource(int to){
            ArrayDeque<Integer> stack = new ArrayDeque<>();
            int cursor = to;
            
            while(cursor != 0){
                stack.push(cursor);
                cursor = prev[cursor][0];
            }
            
            StringBuilder path = new StringBuilder();
            
            while(!stack.isEmpty()){
                path.append(stack.pop()).append(" ");
            }
            
            return path.toString().trim();
        }
        
        public int getShortestPathLength(int to){
            return prev[to][1];
        }
    }
    
    static class Edge {
        int from;
        int to;
        int cost;
        
        public Edge(int u, int v, int w){
            this.from = u;
            this.to = v;
            this.cost = w;
        }
    }
    
    static class Node implements Comparable<Node> {
        int number;
        long distFromSource;
        
        public Node(int num, long dist){
            this.number = num;
            this.distFromSource = dist;
        }
        
        @Override
        public int compareTo(Node thatNode){
            if(this.distFromSource == thatNode.distFromSource){
                return Integer.compare(this.number, thatNode.number);
            }
            return Long.compare(this.distFromSource, thatNode.distFromSource);
        }
    }
}
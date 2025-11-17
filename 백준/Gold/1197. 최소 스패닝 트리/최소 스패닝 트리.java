import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;

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
        
        System.out.print(graph.calculateMSTLength());
    }
    
    static class Graph {
        int V;
        int E;
        PriorityQueue<Edge> edges;
        UnionFind uf;
        
        public Graph(int V, int E){
            this.V = V;
            this.E = E;
            edges = new PriorityQueue<>();
            uf = new UnionFind(V);
        }
        
        public void addEdge(int u, int v, int w){
            edges.add(new Edge(u, v, w));
        }
        
        public long calculateMSTLength(){
            long accumPathLen = 0;
            int pathCnt = 0;
            
            // Cycle 필터링을 위해 V-1 길이의 MST를 찾아야함.
            while(!edges.isEmpty() && pathCnt < V){
                Edge current = edges.poll();
                
                // 최초엔 모두 각자의 서로소 집합을 가짐.
                // 따라서, MST를 이루는 정점과 간선을 하나씩 Greedy하게 확장해나간다.
                if(!uf.isConnected(current.u, current.v)){
                    uf.union(current.u, current.v);
                    pathCnt++;
                    accumPathLen += current.w;
                }
            }
            
            return accumPathLen; // MST가 존재하는 경우만 입력으로 주어짐.
        }
    }
    
    static  class UnionFind {
        int N;
        int[] parent;
        int[] size;
        
        public UnionFind(int N){
            this.N = N;
            // 이후 1부터 시작하는 정점 번호로 Query 할 예정이므로, 크기 N+1로 초기화.
            parent = new int[N + 1];
            size = new int[N + 1];
            
            for(int p = 1; p < N + 1; p++){
                parent[p] = p;
                size[p] = 1;
            }
        }
        
        public int root(int p){
            while(p != parent[p]){
                parent[p] = parent[parent[p]]; // Path Compression by halving the distance.
                p = parent[p];
            }
            
            return p;
        }
        
        public boolean isConnected(int p, int q){
            return root(p) == root(q);
        }
        
        public void union(int p, int q){
            int rootOfP = root(p);
            int rootOfQ = root(q);
            
            if(rootOfP == rootOfQ){return;}
            
            // 작은 쪽을 큰 쪽에 합치기.
            if(size[rootOfP] > size[rootOfQ]){
                parent[rootOfQ] = rootOfP;
                size[rootOfP] += size[rootOfQ];
            }
            else {
                parent[rootOfP] = rootOfQ;
                size[rootOfQ] += size[rootOfP];
            }
        }
    }
    
    static class Edge implements Comparable<Edge>{
        int u;
        int v;
        int w;
        
        public Edge(int u, int v, int w){
            this.u = u;
            this.v = v;
            this.w = w;
        }
        
        @Override
        public int compareTo(Edge thatEdge){
            return Integer.compare(this.w, thatEdge.w);
        }
    }
}
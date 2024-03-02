import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayList;

// 풀이 및 접근
// '바이러스는 1번 컴퓨터에서 시작되어 1번과 어떤 방식으로던 연결되어있는 모든 컴퓨터로 전염된다'가 문제의 설정이다.
// 또한 컴퓨터와 그들 간의 연결관계는 네트워크를 이룬다.
// 이는 그래프와 정확하게 일치하는 구조이다. - 그래프는 정점과 그들간의 관계로 이루어져있다 - .
// 위의 문제를 그래프 구조로 표현해보면,
// 바이러스에 감염되는 컴퓨터는 1번으로부터 시작해 연결 관계를 타고 도달 가능한(Reachable) 모든 컴퓨터이다.
// 전형적인 DFS 문제이다.
// 근원지인 1번으로부터 시작해 DFS 탐색을 함으로써, 1번으로부터 도달가능한 모든 컴퓨터를 파악할 수 있다.
// DFS는 이미 방문한 정점(컴퓨터)를 다시 방문하지 않기 위해 방문한 정점에 표시를 하기 때문이다.(visited)
// 1번으로부터 시작해 DFS를 실시하고, 탐색이 끝나면 visited를 확인해 어떤 컴퓨터를 방문했는지 확인하면 바로 알 수 있다.

public class Main{
    // Main Program
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int vertices = Integer.parseInt(br.readLine());
        int edges = Integer.parseInt(br.readLine());
        Graph graph = new Graph(vertices, edges);
        
        for(int i = 0; i < edges; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph.addEdge(v, w);
        }
        
        System.out.print(graph.infectedComputers());
        
    }
    
    // Graph Implementation
    static class Graph {
        private ArrayList<Integer>[] adj;
        private int vertices;
        private int edges;
        private BitSet visited;
        
        public Graph(int numOfVertices, int numOfEdges){
            vertices = numOfVertices;
            edges = numOfEdges;
            visited = new BitSet(vertices + 1);
            adj = (ArrayList<Integer>[]) new ArrayList[vertices + 1];
            for(int i = 1; i < vertices + 1; i++){
                adj[i] = new ArrayList<Integer>();
            }
        }
        
        public void addEdge(int v, int w){
            adj[v].add(w);
            adj[w].add(v);
        }
        
        public int infectedComputers(){
            int count = 0;
            DFS(1);
            for(int i = 0; i < vertices + 1; i++){
                if(visited.get(i)){count++;}
            }
            
            return count - 1; // 바이러스의 근원인 1은 제외하고 센다.
        }
        
        private void DFS(int startVertex){
            visited.set(startVertex);
            for(int w : adj[startVertex]){
                if(!visited.get(w)){
                    DFS(w);
                }
            }
        }
    }
}
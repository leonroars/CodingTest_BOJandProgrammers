import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayList;
import java.io.IOException;


public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());
        Graph g = new Graph(vertices);

        for(int i = 0; i < edges; i++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            g.addEdge(v, w);
        }

        System.out.println(g.connectedComponent());

    }

    static class Graph{
        private int vertices;
        private BitSet visited;
        private ArrayList<Integer>[] adj;

        public Graph(int vertices){
            this.vertices = vertices;
            this.visited = new BitSet(vertices + 1);
            adj = (ArrayList<Integer>[]) new ArrayList[vertices + 1];

            for(int i = 1; i < vertices + 1; i++){
                adj[i] = new ArrayList<>();
            }
        }

        public void addEdge(int v, int w){
            adj[v].add(w);
            adj[w].add(v);
        }

        public int connectedComponent(){
            int ccNum = 0;
            for(int vertex = 1; vertex < adj.length; vertex++){
                if(!visited.get(vertex)){
                    DFS(vertex);
                    ccNum++;
                }
            }
            return ccNum;
        }

        private void DFS(int vertex){
            visited.set(vertex);
            for(int adj : adj[vertex]){
                if(!visited.get(adj)){
                    DFS(adj);
                }
            }
        }
    }
}
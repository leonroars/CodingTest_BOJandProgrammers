import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Stack;
import java.util.BitSet;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());

        Graph graph = new Graph(vertices, edges);

        for(int i = 0; i < edges; i++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            graph.addEdge(v, w);
        }

        Stack<Integer> sorted = graph.topologicalSort();
        StringBuilder sb = new StringBuilder();
        while(!sorted.isEmpty()){
            sb.append(sorted.pop());
            sb.append(" ");
        }

        System.out.println(sb.toString().trim());


    }

    static class Graph {

        private int vertices;
        private int edges;

        private static ArrayList<Integer>[] adj;
        private static BitSet hasEdgeFrom;
        private static BitSet visited;

        public Graph(int vertices, int edges){
            this.vertices = vertices;
            this.edges = edges;

            adj = (ArrayList<Integer>[]) new ArrayList[vertices + 1];
            for(int i = 0; i < adj.length; i++){adj[i] = new ArrayList<>();}

            hasEdgeFrom = new BitSet(vertices + 1);
            visited = new BitSet(vertices + 1);
        }

        public void addEdge(int v, int w){
            adj[v].add(w);
            hasEdgeFrom.set(w);
        }

        public static Stack<Integer> topologicalSort(){
            Stack<Integer> stack = new Stack<>();
            for(int i = 1; i < adj.length; i++){
                if(!adj[i].isEmpty() && !visited.get(i)){dfs(adj, stack, i);}
                else{
                    if(!hasEdgeFrom.get(i)){
                        stack.push(i);
                        visited.set(i);
                    }
                }
            }

            return stack;
        }

        private static void dfs(ArrayList<Integer>[] graph, Stack<Integer> stack, int start){
            visited.set(start);
            for(int adj : graph[start]){
                if(!visited.get(adj)){
                    dfs(graph, stack, adj);
                }
            }
            stack.push(start);
        }

    }


}

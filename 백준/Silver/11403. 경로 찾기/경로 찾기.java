import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;

public class Main{
    static class Graph{
        private int vertices;
        private boolean[][] graph;
        private BitSet visited;
        private StringBuilder reachable;

        public Graph(int vertexNum){
            vertices = vertexNum;
            graph = new boolean[vertices][vertices];
            reachable = new StringBuilder();
        }

        public void addEdge(String row, int col){
            StringTokenizer st = new StringTokenizer(row);
            for(int i = 0; i < vertices; i++){
                int v = Integer.parseInt(st.nextToken());
                if(v == 1){graph[col][i] = true;}
            }
        }

        public String reachable(){
            for(int i = 0; i < vertices; i++){
                visited = new BitSet(vertices);
                dfs(i, visited);
                for(int j = 0; j < vertices; j++){
                    if(!visited.get(j)){reachable.append(0);}
                    if(visited.get(j)){reachable.append(1);}

                    if(j != vertices - 1){reachable.append(" ");}
                }
                reachable.append("\n");
            }

            return reachable.toString();
        }

        private void dfs(int start, BitSet visited){
            for(int i = 0; i < vertices; i++){
                if(graph[start][i] && !visited.get(i)){
                    visited.set(i);
                    dfs(i, visited);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int vertices = Integer.parseInt(br.readLine());
        Graph graph = new Graph(vertices);

        for(int i = 0; i < vertices; i++){
            String row = br.readLine();
            graph.addEdge(row, i);
        }

        System.out.print(graph.reachable());
    }
}
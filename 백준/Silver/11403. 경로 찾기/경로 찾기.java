import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;

// 풀이 및 접근
// 방향 그래프에서의 reachability를 확인할 수 있는 알고리즘 구현을 요구하는 문제이다.
// 방향 그래프에서 어떤 정점에서 다른 모든 정점들로의 도달 가능성은 해당 정점으로부터 시작해
// 깊이우선탐색을 시행함으로써 파악가능하다.
// 아래의 풀이는 입력을 받아 인접행렬로 표현된 그래프를 생성하고
// 0부터 시작해 각 정점을 시작 정점 삼아 순차적으로 DFS를 수행한다.
// 이때 한 정점으로부터의 DFS가 완료될 경우, 해당 정점으로부터 도달 가능한 정점을
// visited를 순차탐색하며 확인 후 정답으로 반환할 StringBuilder에 추가해준다.

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
            // 이때 중요한 점은 방향그래프이기 때문에 자기 자신에 대한 방문 여부를
            // 문제에서 어떻게 제시하는지 잘 살펴봐야한다는 점이다.
            // 어떤 문제에서는 자기자신으로의 방문을 전제하는 반면
            // 그렇지 않은 문제도 있기 때문이다.
            // 이번 문제는 자기 자신으로의 방문을 전제하지 않기 때문에
            // 시작 정점도 다른 정점들과 마찬가지로 간선이 존재할때에만 방문 표시를 하도록 해야한다.
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
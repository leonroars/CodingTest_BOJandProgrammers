import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V = Integer.parseInt(br.readLine());
        int E = Integer.parseInt(br.readLine());
        
        Graph graph = new Graph(V, E);
        
        for(int e = 0; e < E; e++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int fee = Integer.parseInt(st.nextToken());
            graph.addBus(from, to , fee);
        }
        
        int[][] allPairsShortestPaths = graph.calculateAllPairsShortestPaths();
        
        StringBuilder answer = new StringBuilder();
        
        for(int i = 1; i < V + 1; i++){
            for(int j = 1; j < V + 1; j++){
                int cur = (allPairsShortestPaths[i][j] == Integer.MAX_VALUE / 4) ? 0 : allPairsShortestPaths[i][j];
                answer.append(cur);
                if(j < V){answer.append(" ");}
            }
            if(i < V){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    static class Graph {
        int V;
        int E;
        int[][] matrix; // matrix[i][j] : Min Cost from i to j.
        
        public Graph(int v, int e){
            this.V = v;
            this.E = e;
            matrix = new int[V + 1][V + 1];
            for(int i = 1; i < V + 1; i++){
                for(int j = 1;j < V + 1; j++){
                    if(i == j){matrix[i][j] = 0;}
                    else {matrix[i][j] = Integer.MAX_VALUE / 4;} // Overflow 방지 및 발생 가능 최대값 고려 INF
                }
            }
        }
        
        public void addBus(int from, int to, int fee){
            // A->B 로 가는 복수의 버스가 있음
            // 하지만, 최단 경로에 전혀 필요하지 않으므로, 입력 시에 대체하기.
            if(matrix[from][to] > fee){matrix[from][to] = fee;}
        }
        
        public int[][] calculateAllPairsShortestPaths(){
            for(int k = 1; k < V + 1; k++){
                for(int i = 1; i < V + 1; i++){
                    for(int j = 1; j < V + 1; j++){
                        if(matrix[i][j] > matrix[i][k] + matrix[k][j]){
                            matrix[i][j] = matrix[i][k] + matrix[k][j];
                        }
                    }
                }
            }
            
            return matrix;
        }
    }
}
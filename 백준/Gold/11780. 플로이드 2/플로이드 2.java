import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

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
            int w = Integer.parseInt(st.nextToken());
            
            graph.addEdge(from, to, w);
        }
        
        int[][] sp = graph.calculateSP();
        
        for(int i = 1; i < V + 1; i++){
            StringBuilder spStr = new StringBuilder();
            for(int j = 1; j < V + 1; j++){
                int currentSP = (sp[i][j] == Integer.MAX_VALUE / 4) ? 0 : sp[i][j];
                spStr.append(currentSP);
                if(j < V){spStr.append(" ");}
            }
            System.out.println(spStr.toString());
        }
        
        for(int a = 1; a < V + 1; a++){
            for(int b = 1; b < V + 1; b++){
                System.out.println(graph.getPath(a, b));
            }
        }
    }
    
    static class Graph {
        int V;
        int E;
        int[][] distMatrix;
        // prev[i][j] = i~j 간 최단 거리 경로를 구성하는 정점 중, j 바로 전에 위치한 정점 정보.
        // 즉, prev[i][j] == Penultimate vertex on the shortest path from i to j.
        int[][] prev;
        
        public Graph(int v, int e){
            this.V = v;
            this.E = e;
            distMatrix = new int[V + 1][V + 1];
            prev = new int[V + 1][V + 1];
            
            for(int i = 1; i < V + 1; i++){
                for(int j = 1; j < V + 1; j++){
                    distMatrix[i][j] = (i == j) ? 0 : Integer.MAX_VALUE / 4;
                    prev[i][j] = -1; // 초기값.
                }
            }
        }
        
        public void addEdge(int from, int to, int w){
            if(distMatrix[from][to] > w){
                distMatrix[from][to] = w;
                prev[from][to] = from;
            }
        }
        
        public int[][] calculateSP(){
            for(int k = 1; k < V + 1; k++){
                for(int i = 1; i < V + 1; i++){
                    for(int j = 1; j < V + 1; j++){
                        // 갱신되는 경우 -> distMatrix, prev 모두 갱신 필요.
                        if(distMatrix[i][j] > distMatrix[i][k] + distMatrix[k][j]){
                            distMatrix[i][j] = distMatrix[i][k] + distMatrix[k][j];
                            prev[i][j] = prev[k][j];
                        }
                    }
                }
            }
            return distMatrix;
        }
        
        public String getPath(int i, int j){
            if(i != j && distMatrix[i][j] != Integer.MAX_VALUE / 4){
                StringBuilder path = new StringBuilder();
                
                ArrayDeque<Integer> stack = new ArrayDeque<>();
                int intermediateCnt = 0;
                
                int current = j;
                while(current != i){
                    stack.push(current);
                    current = prev[i][current];
                    intermediateCnt++;
                }
                
                stack.push(i);
                intermediateCnt++;
                
                path.append(intermediateCnt).append(" ");
                
                while(!stack.isEmpty()){
                    path.append(stack.pop()).append(" ");
                }
                
                return path.toString();
            }
            return "0";
        }
        
    }
}
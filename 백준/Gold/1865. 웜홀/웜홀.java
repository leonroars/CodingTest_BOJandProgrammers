import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            ArrayList<Edge> edges = new ArrayList<>();
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            
            for(int m = 0; m < M; m++){
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                
                edges.add(new Edge(u, v, w));
                edges.add(new Edge(v, u, w));
            }
            
            for(int w = 0; w < W; w++){
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int duration = -Integer.parseInt(st.nextToken());
                edges.add(new Edge(from, to, duration));
            }
            
            boolean currentResult = doesNegativeCycleExist(N, edges);
            String currentAnswer = (currentResult) ? "YES" : "NO";
            answer.append(currentAnswer).append("\n");
        }
        
        System.out.print(answer.toString().trim());
    }
    
    public static boolean doesNegativeCycleExist(int V, ArrayList<Edge> edges){
        boolean[] visited = new boolean[V + 1];
        boolean isNegativeCycleFound = false;
        for(int source = 1; source < V + 1; source++){
            // Check Fast
            if(isNegativeCycleFound){break;}
            
            if(!visited[source]){
                // 1) SP 배열 초기화
                int[] sp = new int[V + 1];
                for(int i = 1; i < V + 1; i++){
                    if(i != source){sp[i] = Integer.MAX_VALUE / 4;}
                }
                
                // 2) V-1 Relaxation
                for(int turn = 1; turn < V; turn++){
                    for(Edge edge : edges){
                        // Edge Relax
                        if(sp[edge.thatEnd] > sp[edge.thisEnd] + edge.w){
                            sp[edge.thatEnd] = sp[edge.thisEnd] + edge.w;
                        }
                        
                        visited[edge.thisEnd] = true;
                        visited[edge.thatEnd] = true;
                    }
                }
                
                // 3) V Relaxation : Negative Cycle Detection
                for(Edge edge : edges){
                    if(sp[edge.thatEnd] > sp[edge.thisEnd] + edge.w){
                        isNegativeCycleFound = true;
                        break;
                    }
                }
                if(isNegativeCycleFound){break;}
            }
        }
        return isNegativeCycleFound;
    }
    
    static class Edge {
        int thisEnd;
        int thatEnd;
        int w;
        
        public Edge(int u, int v, int w){
            this.thisEnd = u;
            this.thatEnd = v;
            this.w = w;
        }
    }
}
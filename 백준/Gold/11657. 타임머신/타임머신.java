import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static long INF = Long.MAX_VALUE / 4; // 간선 가중치가 최대 10,000 이므로 더하기 과정에서 Overflow 예방.
    static ArrayList<Edge> edges = new ArrayList<>();
    static int[][] weights;
    static int V;
    
    static class Edge {
        public int u;
        public int v;
        public int w;
        
        public Edge(int start, int end, int weight){
            this.u = start;
            this.v = end;
            this.w = weight;
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        V = N;
        
        // 간선 정보 입력 받기
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edges.add(new Edge(u, v, w));
        }
        
        // 벨만포드 알고리즘 호출 후 반환값 출력
        System.out.print(bellmanFord());
    }
    
    private static String bellmanFord(){
        long[] dist = new long[V + 1]; // dist[v] : 1~v 까지의 최단거리
        boolean negativeCycleDetected = false;
        
        // dist[] 초기화
        Arrays.fill(dist, INF);
        dist[1] = 0;
        
        // Relaxation Process
        for(int relaxationCnt = 0; relaxationCnt < V + 1; relaxationCnt++){
            for(Edge edge : edges){
                int u = edge.u;
                int v = edge.v;
                if(dist[u] == INF){continue;}
                if(dist[v] > dist[u] + edge.w){
                    if(relaxationCnt == V){negativeCycleDetected = true; break;}
                    dist[v] = dist[u] + edge.w;
                }
            }
            if(negativeCycleDetected){break;}
        }
        StringBuilder answer = new StringBuilder();
        
        if(negativeCycleDetected){answer.append(-1);}
        else {
            for(int j = 2; j < V + 1; j++){
                answer.append(dist[j] == INF ? -1 : dist[j]);
                if(j < V){answer.append("\n");}
            }
        }
        
        return answer.toString();
    }
}
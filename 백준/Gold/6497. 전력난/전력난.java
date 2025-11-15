import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.PriorityQueue;

/*

 입력으로 주어지는 집과 도로를 각각 정점 및 간선으로 사상해볼 때, 크기 상 Dense Graph 에 가까운 형태가 나온다.
 따라서 간선을 모두 PQ에 담아 Greedy 하게 MST 를 만들어가는 Kruskal 보단 Prim 알고리즘이 더 유용하다.

 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean isEnd = false;
        StringBuilder answer = new StringBuilder();
        
        while(!isEnd){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numberOfHouses = Integer.parseInt(st.nextToken());
            int numberOfRoads = Integer.parseInt(st.nextToken());
            
            if(numberOfHouses == 0 && numberOfRoads == 0){isEnd = true;}
            else {
                Graph graph = new Graph(numberOfHouses, numberOfRoads);
                
                for(int r = 0; r < numberOfRoads; r++){
                    st = new StringTokenizer(br.readLine());
                    
                    int u = Integer.parseInt(st.nextToken());
                    int v = Integer.parseInt(st.nextToken());
                    int w = Integer.parseInt(st.nextToken());
                    
                    graph.addRoad(u, v, w);
                }
                
                int possibleCostReduction = graph.calculateCostReduction();
                answer.append(possibleCostReduction).append("\n");
            }
        }
        
        System.out.print(answer.toString().trim());
    }
    
    static class Graph {
        int V;
        int E;
        ArrayList<Edge>[] adj;
        int currentCostSum; // 도로 전체에 달린 조명 전기세 합 -> 절약과 비교될 기준.
        
        public Graph(int V, int E){
            this.V = V;
            this.E = E;
            adj = new ArrayList[V]; // 0-based Index.
            for(int i = 0; i < V; i++){adj[i] = new ArrayList<Edge>();}
        }
        
        public void addRoad(int thisEnd, int thatEnd, int dist){
            adj[thisEnd].add(new Edge(thisEnd, thatEnd, dist));
            adj[thatEnd].add(new Edge(thatEnd, thisEnd, dist));
            currentCostSum += dist; // 현행 전기세 누계 갱신.
        }
        
        public int calculateCostReduction(){
            int possibleMinCost = calculateMinCost();
            return currentCostSum - possibleMinCost; // 최소 0.
        }
        
        
        private int calculateMinCost(){
            boolean[] visited = new boolean[V];
            int minCost = 0;
            
            PriorityQueue<Edge> pq = new PriorityQueue<>();
            for(Edge firstAdjEdge : adj[0]){
                pq.add(firstAdjEdge);
            }
            
            while(!pq.isEmpty()){
                Edge current = pq.poll();
                
                // Cycle Filter
                if(visited[current.thisEnd] && visited[current.thatEnd]){continue;}
                
                // Cycle 을 구성하는 간선 아님이 확정 -> MST 에 포함 확정.
                minCost += current.w;
                
                // 간선을 구성하는 두 정점 중, 방금 처음 방문한 정점의 인접 간선 모두 pq에 추가.
                if(!visited[current.thisEnd]){
                    visited[current.thisEnd] = true;
                    for(Edge adjEdge : adj[current.thisEnd]){
                        if(!visited[adjEdge.thatEnd]){
                            pq.add(adjEdge);
                        }
                    }
                }
                
                if(!visited[current.thatEnd]){
                    visited[current.thatEnd] = true;
                    for(Edge adjEdge : adj[current.thatEnd]){
                        if(!visited[adjEdge.thatEnd]){
                            pq.add(adjEdge);
                        }
                    }
                }
            }
            
            return minCost;
        }
    }
    
    static class Edge implements Comparable<Edge> {
        int thisEnd;
        int thatEnd;
        int w; // 거리 == 전기세.
        
        public Edge(int u, int v, int w){
            this.thisEnd = u;
            this.thatEnd = v;
            this.w = w;
        }
        
        @Override
        public int compareTo(Edge thatEdge){
            return Integer.compare(this.w, thatEdge.w);
        }
    }
}
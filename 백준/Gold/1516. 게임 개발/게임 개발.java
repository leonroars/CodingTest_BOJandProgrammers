import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int V = Integer.parseInt(br.readLine());
        
        Graph graph = new Graph(V);
        
        for(int i = 1; i < V + 1; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int numberOfTokens = st.countTokens();
            
            for(int t = 0; t < numberOfTokens - 1; t++){
                int currentNum = Integer.parseInt(st.nextToken());
                if(t == 0){
                    graph.setDuration(i, currentNum);
                }
                else {
                    graph.addDependency(i, currentNum);
                }
            }
        }
        
        graph.calculateMinDuration();
        
        for(int j = 1; j < V + 1; j++){
            System.out.println(graph.getMinDurationOf(j));
        }
    }
    
    static class Graph {
        int V;
        ArrayList<Integer>[] g;
        int[] durationCost; // durationCost[k] : k 건축에 걸리는 시간.
        int[] indegree; // indegree[k] : k를 짓기 직전에 반드시 건축 완료해야하는 건물 수.
        int[] minDuration; // minDuration[k] : k를 짓기 까지 걸리는 최소 시간.
        
        public Graph(int V){
            this.V = V;
            g = new ArrayList[V + 1];
            minDuration = new int[V + 1];
            
            for(int i = 1; i < V + 1; i++){g[i] = new ArrayList<Integer>();}
            durationCost = new int[V + 1];
            indegree = new int[V + 1];
        }
        
        /* u를 짓기 위해 v가 필요. */
        public void addDependency(int u, int v) {
            g[v].add(u);
            indegree[u]++;
        }
        
        public void setDuration(int v, int t){
            durationCost[v] = t;
        }
        
        public void calculateMinDuration(){
            // 1. 바로 지을 수 있는 건물 목록 찾기
            ArrayList<Integer> fundamentals = new ArrayList<>();
            for(int v = 1; v < V + 1; v++){
                if(indegree[v] == 0){fundamentals.add(v);}
            }
            
            PriorityQueue<Building> pq = new PriorityQueue<>();
            
            // 2. PQ에 삽입.
            fundamentals.forEach(v -> pq.add(new Building(v, durationCost[v])));
            
            while(!pq.isEmpty()){
                Building current = pq.poll();
                minDuration[current.number] = current.duration;
                
                for(Integer next : g[current.number]){
                    indegree[next]--;
                    if(indegree[next] == 0){
                        pq.add(new Building(next, minDuration[current.number] + durationCost[next]));
                    }
                }
            }
        }
        
        public int getMinDurationOf(int v){
            return minDuration[v];
        }
        
    }
    
    static class Building implements Comparable<Building>{
        int number;
        int duration; // 이 건물 다 짓기까지 필요한 시간.
        
        public Building(int n, int d){
            this.number = n;
            this.duration = d;
        }
        
        @Override
        public int compareTo(Building thatBuilding){
            return Integer.compare(this.duration, thatBuilding.duration);
        }
    }
}
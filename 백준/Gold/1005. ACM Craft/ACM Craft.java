import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            
            Graph graph = new Graph(N);
            
            // 건물별 건축 소요 시간 입력 받기.
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i < N + 1; i++){
                graph.setDuration(i, Integer.parseInt(st.nextToken()));
            }
            
            // 건물 간 의존 관계 입력 받기.
            for(int j = 0; j < K; j++){
                st = new StringTokenizer(br.readLine());
                int before = Integer.parseInt(st.nextToken());
                int after = Integer.parseInt(st.nextToken());
                graph.addDependency(before, after);
            }
            
            // 목표 건물 번호 받기.
            int W = Integer.parseInt(br.readLine());
            int requiredTimeForWToBeDone = graph.calculateMinTimeFor(W);
            
            answer.append(requiredTimeForWToBeDone);
            answer.append("\n");
        }
        
        System.out.print(answer.toString().trim());
    }
    
    static class Graph {
        int N; // 건물의 수
        ArrayList<Integer>[] g;
        int[] indegree; // indegree[k] : k번 건물을 짓기 전에, 반드시 지어졌음을 확인해야 하는 건물 수.
        int[] duration; // duration[k] : k번 건물 건축에 소요되는 시간.
        
        public Graph(int N){
            this.N = N;
            g = new ArrayList[N + 1];
            for(int i = 1; i < N + 1; i++){g[i] = new ArrayList<Integer>();}
            indegree = new int[N + 1];
            duration = new int[N + 1];
        }
        
        public void addDependency(int before, int after){
            g[before].add(after);
            indegree[after]++;
        }
        
        public void setDuration(int k, int w){
            duration[k] = w;
        }
        
        public PriorityQueue<Building> getFirstBuildings(){
            PriorityQueue<Building> firstBuildings = new PriorityQueue<>(); // 가장 먼저 지어야하는 건물들
            for(int i = 1; i < N + 1; i++){
                if(indegree[i] == 0){
                    firstBuildings.add(new Building(i, duration[i]));
                }
            }
            
            return firstBuildings;
        }
        
        public int calculateMinTimeFor(int target){
            int[] dp = new int[N + 1]; // dp[k] = k번 건물 건축을 완료하기 위해 필요한 최소 시간.
            PriorityQueue<Building> pq = getFirstBuildings();
            
            while(!pq.isEmpty()){
                Building currentCompletedBuilding = pq.poll();
                dp[currentCompletedBuilding.buildingNumber] = currentCompletedBuilding.requiredTimeSpan;
                for(Integer next : g[currentCompletedBuilding.buildingNumber]){
                    indegree[next]--;
                    if(indegree[next] == 0){
                        pq.add(new Building(next, dp[currentCompletedBuilding.buildingNumber] + duration[next]));
                    }
                }
            }
            
            return dp[target];
        }
        
    }
    
    static class Building implements Comparable<Building>{
        int buildingNumber;
        int requiredTimeSpan; // 해당 건물 짓기까지 필요한 시간
        
        public Building(int number, int timeSpan){
            this.buildingNumber = number;
            this.requiredTimeSpan = timeSpan;
        }
        
        @Override
        public int compareTo(Building thatBuilding){
            if(this.requiredTimeSpan == thatBuilding.requiredTimeSpan){
                return Integer.compare(this.buildingNumber, thatBuilding.buildingNumber);
            }
            return Integer.compare(this.requiredTimeSpan, thatBuilding.requiredTimeSpan);
        }
    }
}
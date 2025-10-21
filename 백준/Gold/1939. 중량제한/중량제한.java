import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class Main {
    static int N; // 섬의 수
    static int M; // 다리 수
    static ArrayList<Edge>[] graph; // 섬-도로-섬 을 그래프로 모델링
    static long maxWOfRoad = 0; // 최대 도로 하중 -> Binary Search 시작 시의 범위 지정 위함.
    
    static int P; // 공장 A 위치
    static int Q; // 공장 B 위치.
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        graph = new ArrayList[N + 1]; // 섬의 번호 범위 : (1, N)
        for(int i = 1; i < N + 1; i++){graph[i] = new ArrayList<Edge>();}
        
        // 1. 도로 정보 입력 받기. - Self-cycle 은 문제 해결과 무관하므로 제외한다.
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            long w = Long.parseLong(st.nextToken());
            maxWOfRoad = Math.max(maxWOfRoad, w); 
            if(u != v){
                graph[u].add(new Edge(v, w));
                graph[v].add(new Edge(u, w));
            }
            
        }
        
        // 2. 공장 위치 두 개 받기 : 둘 중 하나를 출발점, 남은 하나를 도착점으로 설정한다. 어느 것을 무엇으로 하던 상관 X
        st = new StringTokenizer(br.readLine());
        
        P = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());
        
        // 3. 'P~Q 간 이동 가능한 최대 중량' 찾는 메서드 호출
        long maxWeight = findMaxWeightFromPToQ();
        
        // 4. 정답 출력 : 항상 정답이 존재한다 했으므로.
        System.out.print(maxWeight);
        
    }
    
    public static Long findMaxWeightFromPToQ(){
        long lo = 1;
        long hi = maxWOfRoad;
        long answer = -1;
        
        // 결정 문제의 결과가 T 가 나오는 값/F가 나오는 값 모두 다음 탐색 범위에 포함.
        // 이를 통해 결국 마지막엔 lo==hi 또는 lo = hi - 1 두 가지 중 하나가 나온다.
        while(lo < hi - 1){
            // General Cases
            long mid = ((lo + hi) / 2);
            
            boolean result = isTransportable(mid);
            
            if(result){lo = mid;}
            else{hi = mid;}
        }
        
        answer = (isTransportable(hi)) ? hi : lo;
        
        return answer;
    }
    
    /*
        - P~Q 간 k 무게 운송이 가능한지 검증하는 메서드 
        - k무게가 운송가능하도록 하는 도로로 연결된 섬만 방문하는 BFS 수행.
    */
    public static boolean isTransportable(long k){
        
        // 갈 수 있냐, 없냐만 판단하면 되기 때문에 각 정점까지의 비용(한계 하중) 기록이 불필요하다.
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[N + 1];
        boolean isReachable = false; // 조기 종료를 위한 Flag.
        
        q.offer(P); // P를 시작점으로.
        visited[P] = true;
        
        while(!q.isEmpty() && !isReachable){
            int current = q.poll();
            
            for(Edge edge : graph[current]){
                // k 하중 운송이 가능한 도로이고 && 방문한 적 없는 섬이라면 => 큐에 추가.
                if(edge.weight >= k && !visited[edge.to]){
                    // Case A : 벌써 Q 찾음! -> 조기 종료
                    if(edge.to == Q){isReachable = true;}
                    // Case B : 아니라면 Q에 추가.
                    else{q.offer(edge.to);}
                    
                    // 방문 처리.
                    visited[edge.to] = true; 
                }
            }
        }
        
        return isReachable;
    }
    
    static class Edge {
        int to;
        long weight;
        
        public Edge(int t, long w){
            this.to = t;
            this.weight = w;
        }
    }
}
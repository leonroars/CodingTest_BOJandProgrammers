import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        Graph graph = new Graph(N);
        
        // 1. 선후관계 입력받기.
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int before = Integer.parseInt(st.nextToken());
            int after = Integer.parseInt(st.nextToken());
            graph.addPrecedence(before, after);
        }
        
        // 2. Topological Order 구하기.
        String topologicalOrder = graph.getTopologicalOrder();
        
        // 3. 출력
        System.out.print(topologicalOrder);
        
        
    }
    
    static class Graph {
        int N;
        int[] indegree; // ** 이 문제의 핵심. Topological Order 의 Transitive Dependency 를 활용해야함.
        ArrayList<Integer>[] g;
        boolean[] hasPrecedent; // hasPrecedent[j] == true => j 이전에 풀면 좋은 문제가 존재한다.
        
        public Graph(int N){
            this.N = N;
            indegree = new int[N + 1];
            g = new ArrayList[N + 1]; // 문제의 번호가 1부터 시작하므로 N+1.
            for(int i = 1; i < N + 1; i++){g[i] = new ArrayList<Integer>();}
            hasPrecedent = new boolean[N + 1];
        }
        
        public void addPrecedence(int before, int after){
            g[before].add(after);
            hasPrecedent[after] = true;
            indegree[after]++;
        }
        
        public ArrayList<Integer> getNexts(int before){
            return g[before];
        }
        
        /* 가장 먼저 풀어야하는 문제들 목록 반환 */
        public PriorityQueue<Integer> getFirsts(){
            PriorityQueue<Integer> firsts = new PriorityQueue<>();
            
            for(int i = 1; i < N + 1; i++){
                if(!hasPrecedent[i]){firsts.add(i);}
            }
            
            return firsts;
        }
        
        public String getTopologicalOrder(){
            StringBuilder topOrder = new StringBuilder();
            boolean[] visited = new boolean[N + 1];
            
            PriorityQueue<Integer> pq = getFirsts();
            ArrayDeque<Integer> q = new ArrayDeque<>();
            
            while(!pq.isEmpty()){
                int currentProblem = pq.poll();
                
                // 해당 문제를 풀고 나서 바로 다음으로 풀 수 있는 문제들(직접 의존성 존재하는 문제들) -> 진입 차수 깎아주기.
                for(Integer next : getNexts(currentProblem)){
                    indegree[next]--;
                    if(indegree[next] == 0){pq.add(next);}
                }
                
                topOrder.append(currentProblem).append(" ");
            }
            
            return topOrder.toString().trim();
        }
    }
}
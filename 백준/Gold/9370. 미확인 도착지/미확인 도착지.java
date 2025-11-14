import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;

/*

I. 문제 해결을 위한 알고리즘 모델링
 - 교차로를 정점, 두 교차로를 잇는 도로를 간선으로 생각하여 'Undirected Weighted Graph' 로 모델링할 수 있다.
 - 양방향 도로이므로, 간선 두 개를 별도로 생성하여 등록하면 'Weighted Digraph' 로 모델링할 수도 있다.
 - 이때, 두 교차로 간 최단 거리를 구하는 알고리즘을 shortestPath(u, v) 가 있다고 가정하면,
   문제가 요구하는 '목적지 후보 x'를 다음과 같이 정의할 수 있다.
 
 "
  Set(x) = Set of all x that,
      - x is element of Set(t) which is given set of all destination candidate.
      - x such that,
        * shortestPath(s, x) = shortestPath(s, g) + edgeWeight(g, h) + shortestPath(h, x);
        OR
        * shortestPath(s, x) = shortestPath(s, h) + edgeWeight(g, h) + shortestPath(g, x);
 "
 이를 위해 두 가지 최단 경로 알고리즘을 떠올릴 수 있다.
  A) Single execution of Floyd-Warshall
  B) Multiple execution of Dijkstra : 총 두 번 실행 필요 - Dijkstra(s, all) + Dijkstra(h, all)
 
 시간 제약이 3초이지만, 테스트케이스의 수가 최대 100이다.
 즉, 최악의 경우, 한 테스트 케이스에 대해 총 0.03초 내에 결과를 반환해야 한다는 것이다.
 
 최대 교차로 수가 2,000임을 감안했을 때, Floyd-Warshall 은 시간 복잡도 관점과 더불어 불필요한 계산을 고려했을 때 적절하지 않다.
 따라서, Dijkstra 를 두 번 실행하는 방식으로 문제의 답을 도출하고자 한다.
 
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            
            Graph graph = new Graph(n, m, t);
            
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            
            graph.setDefinitePath(g, h);
            
            for(int i = 0; i < m; i++){
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                graph.addEdge(u, v, w);
            }
            
            for(int j = 0; j < t; j++){
                graph.addCandidate(Integer.parseInt(br.readLine()));
            }
            
            String tcAnswer = graph.calculateCandidates(s);
            answer.append(tcAnswer);
            answer.append("\n");
        }
        
        System.out.print(answer.toString().trim());
    }
    
    static class Graph {
        int V;
        int E;
        int t; // The number of candidates.
        ArrayList<Edge>[] g; // Adjacency List
        
        int passedU; // 지나갔음이 확실한 교차로 1
        int passedV; // 지나갔음이 확실한 교차로 2
        int distBetweenPassedUAndPassedV; // 교차로 1, 2간 거리.
        
        ArrayList<Integer> candidates; // 후보 교차로 목록
        HashMap<Integer, int[]> shortestDists; // <SourceNum, int[] shortestDistToAll>
        
        /* Graph Constructor */
        public Graph(int n, int m, int t){
            this.V = n;
            this.E = m;
            this.t = t;
            candidates = new ArrayList<Integer>();
            
            g = new ArrayList[V + 1];
            for(int i = 1; i < V + 1; i++){g[i] = new ArrayList<Edge>();}
            shortestDists = new HashMap<>();
        }
        
        /* 지나갔음이 확실한 두 교차로 설정 */
        public void setDefinitePath(int G, int H){
            this.passedU = G;
            this.passedV = H;
        }
        
        public void addEdge(int u, int v, int w){
            g[u].add(new Edge(u, v, w));
            g[v].add(new Edge(v, u, w));
            
            // 지나갔음이 확실한 두 교차로를 연결하는 도로일 경우, 도로 거리 설정.
            if((passedU == u && passedV == v) || (passedU == v && passedV == u)){distBetweenPassedUAndPassedV = w;}
        }
        
        public void addCandidate(int v){
            candidates.add(v);
        }
        
        public String calculateCandidates(int s){
            // 1. Dijkstra 로 s->all, h->all 구하기.
            dijkstra(s);
            dijkstra(passedV);
            dijkstra(passedU);
            
            // 2. Candidates 중 조건 만족하는 후보 추리기.
            int[] distFromS = shortestDists.get(s);
            int[] distFromH = shortestDists.get(passedV);
            int[] distFromG = shortestDists.get(passedU);
            PriorityQueue<Integer> filteredCandidates = new PriorityQueue<>();
            
            for(Integer candidate : candidates){
                if((distFromS[candidate] == distFromS[passedU] + distBetweenPassedUAndPassedV + distFromH[candidate]) 
                || (distFromS[candidate] == distFromS[passedV] + distBetweenPassedUAndPassedV + distFromG[candidate])){
                    filteredCandidates.add(candidate);
                }
            }
            
            StringBuilder result = new StringBuilder();
            while(!filteredCandidates.isEmpty()){
                result.append(filteredCandidates.poll());
                result.append(" ");
            }
            
            return result.toString().trim();
        }
        
        private void dijkstra(int source){
            int[] distFromSource = new int[V + 1];
            PriorityQueue<Intersection> pq = new PriorityQueue<>();
            
            for(int v = 1; v < V + 1; v++){
                if(v == source){distFromSource[v] = 0;}
                // Overflow 방지 및 최악의 경우에서 어떤 경로의 최장 길이(1,000 * 50,000) 고려 상한 설정.
                else {distFromSource[v] = Integer.MAX_VALUE / 4;}
            }
            
            pq.add(new Intersection(source, distFromSource[source]));
            
            while(!pq.isEmpty()){
                Intersection current = pq.poll();
                if(distFromSource[current.number] < current.distFromS){continue;}
                
                for(Edge adjEdge : g[current.number]){
                    int adj = adjEdge.thatEnd;
                    int adjDistFromSource = distFromSource[current.number] + adjEdge.w;
                    
                    if(adjDistFromSource < distFromSource[adj]){
                        distFromSource[adj] = adjDistFromSource;
                        pq.add(new Intersection(adj, adjDistFromSource));
                    }
                }
            }
            
            shortestDists.put(source, distFromSource);
        }
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
    
    static class Intersection implements Comparable<Intersection>{
        int number;
        int distFromS;
        
        public Intersection(int number, int dist){
            this.number = number;
            this.distFromS = dist;
        }
        
        @Override
        public int compareTo(Intersection thatIntersection){
            return Integer.compare(this.distFromS, thatIntersection.distFromS);
        }
    }
}
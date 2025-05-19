import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static int maxRange; // 최대 수색 가능 거리
    static int n; // 지점 수
    static int r; // 도로 수
    static ArrayList<Road>[] map;
    static HashMap<Integer, Integer> itemStatus;
    static int maxItemCnt = -Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        maxRange = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());
        map = (ArrayList<Road>[])new ArrayList[n + 1];
        itemStatus = new HashMap<>();
        
        st = new StringTokenizer(br.readLine());
        for(int i = 1; i < n + 1; i++){
            int numOfItem = Integer.parseInt(st.nextToken());
            itemStatus.put(i, numOfItem);
            map[i] = new ArrayList<Road>();
        }
        
        for(int j = 0; j < r; j++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            
            Road road = new Road(x, y, w);
            Road revRoad = new Road(y, x, w);
            
            map[x].add(road);
            map[y].add(revRoad);
        }
        
        
        // 각 정점으로부터 BFS 수행하며 탐색 가능 범위 내 지점까지 탐색 후 아이템 카운트.
        for(int source = 1; source < n + 1; source++){
            int itemCntFromCertainSource = BFS(source);
            if(itemCntFromCertainSource > maxItemCnt){maxItemCnt = itemCntFromCertainSource;}
        }
        
        System.out.print(maxItemCnt);
        
    }
    
    public static int BFS(int source){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        boolean[] visited = new boolean[n + 1];
        // ArrayList<Integer> reachable = new ArrayList<>();
        
        // [source, distByFar]
        q.addLast(new int[]{source, 0});
        visited[source] = true;
        
        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            int currentVertex = current[0];
            int distTillCurrent = current[1];
            
            for(Road road : map[currentVertex]){
                int adj = road.to;
                int distOfRoad = road.dist;
                int distToAdj = distTillCurrent + distOfRoad;
                
                if(distToAdj <= maxRange){
                    q.addLast(new int[]{adj, distToAdj});
                    if(!visited[adj]){visited[adj] = true;}
                }
            }
        }
        
        // 방문 가능한 지점의 아이템 수 헤아리기.
        int itemAccumCnt = 0;
        for(int i = 1; i < n + 1; i++){
            if(visited[i]){itemAccumCnt += itemStatus.get(i);}
        }
        
        return itemAccumCnt;
    }
    
    static class Road {
        int from;
        int to;
        int dist;
        
        public Road(int x, int y, int w){
            this.from = x;
            this.to = y;
            this.dist = w;
        }
    }
}
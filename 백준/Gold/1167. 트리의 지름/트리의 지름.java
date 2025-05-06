import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Main {
    static int V;
    static HashMap<Integer, Integer>[] tree;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 1. Takes inputs & Initialization phase.
        V = Integer.parseInt(br.readLine());
        tree = new HashMap[V + 1];
        for(int i = 1; i < V + 1; i++){tree[i] = new HashMap<>();}
        
        for(int v = 0; v < V; v++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            
             // 정점 간 부모-자식 관계로 인해, 간선이 총 두 번 등장하므로 to가 처음부터 -1인 경우 존재 X.
            while(to != -1){
                int dist = Integer.parseInt(st.nextToken()); // to != -1 이라면 dist 또한 -1이 아님.
                tree[from].put(to, dist);
                to = Integer.parseInt(st.nextToken());
            }
        }
        
        // 2. Call BFS twice to find diameter of given tree.
        int[] resultFromOne = BFS(1);
        int[] resultedDiameter = BFS(resultFromOne[0]);
        
        System.out.print(resultedDiameter[1]);
    }
    
    private static int[] BFS(int source){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[V + 1];
        int[] result = new int[2];
        int[] distFromSource = new int[V + 1];
        
        q.addLast(source);
        visited[source] = true;
        distFromSource[source] = 0;
        
        while(!q.isEmpty()){
            int current = q.removeFirst();
            for(Integer adj : tree[current].keySet()){
                if(!visited[adj]){
                    int distToAdj = distFromSource[current] + tree[current].get(adj);
                    if(distToAdj > result[1]){
                        result[0] = adj;
                        result[1] = distToAdj;
                    }
                    distFromSource[adj] = distToAdj;
                    visited[adj] = true;
                    q.addLast(adj);
                }
            }
        }
        
        return result;
    }
}

import java.util.ArrayDeque;
import java.util.HashSet;

class Solution {
    public int solution(int n, int[][] edge) {
        /*
         - Must remove self-referenced nodes and redundant one.
         - BFS gurantees the shortest path from a node to another.
        */ 
        
        int maxDist = 0;
        int maxDistHitCnt = 1;
        int[] distFromOne = new int[n + 1];
        HashSet<Integer> discovered = new HashSet<>(); // A set of discovered vertices
        HashSet<Integer>[] graph = new HashSet[n + 1]; // adjacency list represented graph
        
        // 1. Initialize graph.
        for(int i = 0; i < graph.length; i++){graph[i] = new HashSet<>();}
        
        // 2. Generates.
        for(int j = 0; j < edge.length; j++){
            int vertexA = edge[j][0];
            int vertexB = edge[j][1];
            
            // Remove self-reference & redundant adjacent vertices.
            if(vertexA != vertexB){graph[vertexA].add(vertexB); graph[vertexB].add(vertexA);}
        }
        
        // BFS
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(1);
        discovered.add(1);
        
        while(!q.isEmpty()){
            int current = q.removeFirst();
            for(int adj : graph[current]){
                if(!discovered.contains(adj)){
                    distFromOne[adj] = distFromOne[current] + 1;
                    if(distFromOne[adj] > maxDist){maxDist = distFromOne[adj]; maxDistHitCnt = 1;}
                    if(distFromOne[adj] == maxDist){maxDistHitCnt++;}
                    
                    q.addLast(adj);
                    discovered.add(adj);
                }
            }
        }
        
        return maxDistHitCnt - 1;
    }
}
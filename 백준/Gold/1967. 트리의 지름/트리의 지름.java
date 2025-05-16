// Online Java Compiler
// Use this editor to write, compile and run your Java code online

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    static HashMap<Integer, ArrayList<Edge>> tree; // ArrayList<Edge> adjOfX = tree.get(x);
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine()); // N : 정점 수
        tree = new HashMap<>();
        
        // 1. 트리 간선 정보 입력 받기. 정점 수가 1개인 경우 아래 입력은 수행되지 않는다.
        for(int n = 0; n < N - 1; n++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            Edge e = new Edge(x, y, w);
            Edge reverseE = new Edge(y, x, w);
            
            if(tree.containsKey(x)){
                // 별도의 put 없이 이렇게 fetch 후 변경만 해도 저장이 될까?
                tree.get(x).add(e);
            }
            else{
                ArrayList<Edge> adj = new ArrayList<>();
                adj.add(e);
                tree.put(x, adj);
            }
            
            if(tree.containsKey(y)){
                tree.get(y).add(reverseE);
            }
            else {
                ArrayList<Edge> adjRev = new ArrayList<>();
                adjRev.add(reverseE);
                tree.put(y, adjRev);
            }
        }
        
        // 2. 정점 수가 1개 이상인 경우 BFS 두 번 호출을 통해 지름을 구성하는 정점 및 거리 계산.
        //    정점 수가 1개인 경우 지름은 0이다.
        int diameter;
        
        if(N > 1){
            int[] a = BFS(1);
            int[] b = BFS(a[0]);
            diameter = b[1];
        }
        else {
            diameter = 0;
        }
        
        System.out.print(diameter);
        
    }
    
    public static int[] BFS(int source){
        int[] result = new int[2]; // result[0] : source 로 부터 가장 먼 거리의 정점 / result[1] : 거리
        result[1] = -Integer.MAX_VALUE;
        
        ArrayDeque<int[]> q = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        q.addLast(new int[]{source, 0});
        visited.add(source);
        
        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            int currentVertex = current[0];
            int distFromSource = current[1];
            
            ArrayList<Edge> adjEdges = tree.get(currentVertex);
            for(Edge edge : adjEdges){
                int adjVertex = edge.to;
                int weight = edge.weight;
                if(!visited.contains(adjVertex)){
                    int distByFarFromSource = distFromSource + weight;
                    if(distByFarFromSource > result[1]){
                        result[0] = adjVertex; result[1] = distByFarFromSource;
                    }
                    q.addLast(new int[]{adjVertex, distByFarFromSource});
                    visited.add(adjVertex);
                }
            }
        }
        
        return result;
    }
    
    
    static class Edge {
        int from;
        int to;
        int weight;
        
        public Edge(int x, int y, int w){
            this.from = x;
            this.to = y;
            this.weight = w;
        }
    }
}
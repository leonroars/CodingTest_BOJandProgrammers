import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Collections;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertices = Integer.parseInt(st.nextToken()); // 정점 개수
        int edges = Integer.parseInt(st.nextToken()); // 간선 개수
        int start = Integer.parseInt(st.nextToken()); // 시작 정점
        BitSet visited = new BitSet(vertices + 1);

        // 인접리스트 그래프 구현.
        PriorityQueue<Integer>[] graph = (PriorityQueue<Integer>[]) new PriorityQueue[vertices + 1];
        for(int i = 1; i < vertices + 1; i++){
            graph[i] = new PriorityQueue<>();
        }

        // 간선 입력 받아 인접리스트에 삽입. 간선의 입력은 대칭이 보장되지 않으므로, 대칭삽입 필수
        for(int j = 1; j < edges + 1; j++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[v].add(w);
            graph[w].add(v);
        }
        br.close();

        int[] traversed = new int[vertices + 1]; // 조우한 정점의 순서대로 해당 정점과 일치하는 인덱스에 순서를 기입.

        int nth = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        while(!queue.isEmpty()){
            int current = queue.poll();
            if(!visited.get(current)){visited.set(current); traversed[current] = nth; nth++;}
            while(!graph[current].isEmpty()){
                int adj = graph[current].poll();
                if(!visited.get(adj)){queue.add(adj);}
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < traversed.length; i++){
            sb.append(traversed[i]);
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
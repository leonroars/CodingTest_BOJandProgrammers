import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Collections;

// 풀이 접근
// - 매 케이스마다 순회 시작 노드가 주어지므로, 시작 노드를 따로 저장해둘 int 변수가 필요하다.
// - 그래프는 인접 리스트로 구현한다.
// - 절차 별 설명은 주석으로 달아두었다.
// - 다만 고민되는 점은, 오름차순 DFS를 위한 인접리스트의 정렬 부분이다.
// - 정렬을 하지 않고 우선순위 큐와 같은 방식으로 인접 정점 리스트를 구현한다면 조금 더 효율적인 구현이 가능할 것 같다는
//   직관적 생각이 든다.
public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int vertices = Integer.parseInt(st.nextToken()); // 정점 개수
        int edges = Integer.parseInt(st.nextToken()); // 간선 개수
        int start = Integer.parseInt(st.nextToken()); // 시작 정점
        BitSet visited = new BitSet(vertices + 1);

        // 인접리스트 형태의 그래프 구현.
        ArrayList<Integer>[] graph = (ArrayList<Integer>[]) new ArrayList[vertices + 1];
        for(int i = 1; i < vertices + 1; i++){
            graph[i] = new ArrayList<>();
        }

        // 간선 입력 받아 인접리스트에 삽입. 간선의 입력은 대칭이 보장되지 않으므로, 해당 로직 구현 필수.
        for(int j = 1; j < edges + 1; j++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            graph[v].add(w);
            graph[w].add(v);
        }
        br.close();

        // 인접리스트를 돌며, 각 정점 별 인접 정접들을 담은 ArrayList를 내림차순 정렬. - TimSort : O(n log n)
        // 후에 스택을 활용한 DFS 구현 시 문제의 요구조건인 '인접 정점 오름차순 방문'을 고려하였다.
        for (int k = 1; k < graph.length; k++){
            graph[k].sort(Comparator.reverseOrder());
        }

        int[] traversed = new int[vertices + 1]; // 조우한 정점의 순서대로 해당 정점과 일치하는 인덱스에 순서를 기입.
        Arrays.fill(traversed, 0);
        int nth = 1;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        while(!stack.isEmpty()){
            int current = stack.pop();
            if(!visited.get(current)){visited.set(current); traversed[current] = nth; nth++;}
            for(int adj : graph[current]){
                if(!visited.get(adj)){stack.push(adj);}
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
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayList;
import java.util.ArrayDeque;

// 풀이 접근
// 앞서 풀었던 BOJ 24479 & 24444를 통해 인접 정점 방문 순서가 정점의 대소관계에 근거에 결정되는 경우,
// 우선순위 큐를 사용하는 것이 처리 시간을 상당히 개선해준다는 것을 알게 되었다.
// 하지만 우선순위 큐의 효율적 최대/최소값 반환을 사용하기 위해선 매번 최대/최소값을 삭제하는 연산을 사용해야한다.
// 그렇기 때문에 DFS와 BFS를 모두 우선순위 큐를 이용해 구현한 인접리스트를 공유하도록 하는 방법이 성립하지 않는다.
// 메모리 제한이 128MB로 상당히 타이트하기 때문에 각각의 탐색 알고리즘을 위한 인접리스트를 따로 만드는 것도 어려울 것이다.
// 따라서 다음과 같은 방법을 생각해본다.
//
// 1. 하나의 인접 리스트를 공유한다. 인접리스트는 배열 기반으로 하며, 각 배열의 인덱스는 노드의 번호와 일치한다.
// 2. 배열의 각 인덱스에는 해당하는 정접의 인접 정점들이 ArrayList<Integer>가 들어가있다.
// 3. 출발 정점에 따라 방문하지 않는 정점과 간선이 존재할 수 있다. 그러므로 정렬이 필요하다면 방문 시에 한다.
// 4. 인접 정점은 오름차순으로 방문하는데, 만약 DFS와 BFS 모두 반복으로 구현할 경우를 생각해 정렬을 두 번 한다면
//    시간 초과가 발생할 것으로 예상된다.
//    따라서 다음과 같은 방법을 생각해본다.
//    a) 오름차순 정렬을 한 후, DFS는 int DFScnt = ArrayList.size() - 1로부터 시작하여 역순으로 접근 후
//       스택에 삽입하여 로직을 진행하도록 한다.
//    b) 메모리 사용량도 아낄 겸 DFS는 재귀로 진행하고 BFS만 반복으로 구현한다.
//    이번 풀이에선 b)로 가보도록 하겠다.
// 5. 또한 BFS, DFS를 모두 static method로 구현하고
//    FS/BFS 공유가 불가능한 것들도 로컬 변수로 생성 후 사용하도록 하여 GC가 되도록 하여 메모리 부하를 덜어준다.
//    단, BitSet visited는 DFS와 BFS를 따로 관리한다.
//    멀티스레딩을 하지 않는 한, 두 탐색은 순차적으로 이루어지는데 이때 둘 중 어떤 쪽이 먼저 처리될지 정한다.
//    (이번 풀이에서는 DFS를 먼저 처리하기로 한다.) 그렇다면 BFS 시, DFS의 방문 노드를 확인하여
//    방문하려는 정점이 DFS가 이미 방문을 한 정점일 경우
//    이미 DFS 처리 시 해당 노드의 인접 정점들이 정렬이 됐을 것이므로 새로 정렬하지 않고 그대로 이용할 수 있도록 한다.
// 6. 또한 이번에는 방문하지 않은 노드를 0으로 출력할 필요가 없으므로 dfs & bfs 진행시 방문 순서대로 정점 기록 후 출력하면 된다.


public class Main{

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int vertices = Integer.parseInt(st.nextToken());
        int edges = Integer.parseInt(st.nextToken());
        int start = Integer.parseInt(st.nextToken());
        BitSet DFSvisited = new BitSet(vertices);
        BitSet BFSvisited = new BitSet(vertices);

        // 인접 리스트 초기화. 마찬가지로 입력 정점의 번호로 접근이 용이하도록 정점의 개수보다 하나 더 크게 초기화한 후
        // 인덱스는 1부터 끝까지만 사용한다.
        ArrayList<Integer>[] adj = (ArrayList<Integer>[]) new ArrayList[vertices + 1];
        for(int j = 0; j < adj.length; j++){
            adj[j] = new ArrayList<>();
        }

        // 양방향(무방향)그래프이므로 간선이 대칭이 되도록 삽입한다.
        for(int i = 0; i < edges; i++){
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            adj[v].add(w);
            adj[w].add(v);
        }

        System.out.println(dfs(adj, DFSvisited, start));
        System.out.println(bfs(adj, BFSvisited, DFSvisited, start));



    }

    static String dfs(ArrayList<Integer>[] adj, BitSet DFSvisited, int start){
        StringBuilder sb = new StringBuilder();
        dfs(adj, DFSvisited, start, sb);
        return sb.toString().strip();
    }
    private static void dfs(ArrayList<Integer>[] adj, BitSet DFSvisited, int start, StringBuilder sb) {
        if (!DFSvisited.get(start)) {
            DFSvisited.set(start);
            adj[start].sort(Comparator.naturalOrder());
            sb.append(start);
            sb.append(" ");
        }
        for (int w : adj[start]) {
            if (!DFSvisited.get(w)) {
                dfs(adj, DFSvisited, w, sb);
            }
        }
    }

    static String bfs(ArrayList<Integer>[] adj, BitSet BFSvisited, BitSet DFSvisited, int start){
        StringBuilder sb = new StringBuilder();
        bfs(adj, BFSvisited, DFSvisited, start, sb);
        return sb.toString().strip();
    }

    private static void bfs(ArrayList<Integer>[] adj, BitSet BFSvisited, BitSet DFSvisited, int start, StringBuilder sb){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(start);
        while(!q.isEmpty()){
            int current = q.removeFirst();
            if(!BFSvisited.get(current)){
                BFSvisited.set(current);
                sb.append(current);
                sb.append(" ");
            }
            if(!DFSvisited.get(current)){adj[current].sort(Comparator.naturalOrder());}
            for(int w : adj[current]){
                if(!BFSvisited.get(w)){q.addLast(w);}
            }
        }

    }
}
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.ArrayDeque;
public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        BitSet visited = new BitSet(N);
        int[] parent = new int[N + 1];

        // Taking input for N-1 times.
        ArrayList<Integer>[] tree = (ArrayList<Integer>[])new ArrayList[N + 1];
        for(int i = 1; i < N + 1; i++){
            tree[i] = new ArrayList<Integer>();
        }

        // Constructing Adjacency List representation of tree.
        for(int i = 0; i < N - 1; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            tree[v].add(w);
            tree[w].add(v);
        }
        br.close();

        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.addLast(1);
        // Traverse given tree in iterative-BFS manner. Starting from node '1'.
        while(!queue.isEmpty()) {
            int current = queue.removeFirst();
            if (!visited.get(current)){
                visited.set(current);
                for (int adj : tree[current]){
                    if(!visited.get(adj) && !tree[current].isEmpty()){
                        queue.addLast(adj);
                        parent[adj] = current;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int j = 2; j < N + 1; j++){
            sb.append(parent[j]);
            sb.append("\n");
        }

        System.out.println(sb);




    }
}
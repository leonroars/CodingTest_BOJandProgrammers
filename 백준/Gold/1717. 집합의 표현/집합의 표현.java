import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 Robert Sedgewick & Kevin Wayne 의 Algorithms 4/E "Union-Find" 부분을 참고하였습니다.
 */

public class Main {
    static int N;
    static int M;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 0. Union-Find 자료구조 초기화.
        UnionFind uf = new UnionFind(N + 1);
        StringBuilder answer = new StringBuilder();

        // 1. M 번의 연산 수행.
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int opCode = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            if(opCode == 0){
                uf.union(p, q);
            }
            else{
                boolean isConnected = uf.connected(p, q);
                if(isConnected){answer.append("YES");}
                else{answer.append("NO");}

                if(m < M - 1){answer.append("\n");}
            }
        }

        System.out.print(answer.toString());

    }

    /*** WQUPC (Weighted Quick-Union with Path Compression)***/
    static class UnionFind {
        private int[] id; // id[i] = Parent of i.
        private int[] size; // size[i] = The size of the tree which 'i' belong to.

        public UnionFind(int N){
            id = new int[N];
            size = new int[N];

            for(int i = 0; i < N; i++){
                id[i] = i;
                size[i] = 1;
            }
        }
        /*
           root()
               - Finding given node's root.
               - Worst-case Time Complexity : O(log N)
        */
        private int root(int p){
            while(p != id[p]){
                id[p] = id[id[p]]; // Compressing the path length from given p to its root to half.
                p = id[p];
            }
            return p;
        }

        /*
            Find Operation
                - Returns true if given parameters are connected
                - Worst-case Time Complexity : O(log N)
        */
        public boolean connected(int p, int q){return root(p) == root(q);}

        /*
            Union Operation
                - Connect given parameters in weighted manner.
                - Change smaller tree's root to point bigger one's root.
                - Maintaining managable depth of every tree. -> Leads to efficiency.
                - Worst-case Time Complexity : O(log N)
         */
        public void union(int p, int q){
            int i = root(p); // Root node of the tree which p belongs to.
            int j = root(q); // Root node of the tree which q belongs to.

            // Case A : They're already connected.
            if(i == j){return;}
            else{
                // Attach smaller tree to bigger tree.
                if(size[i] < size[j]){
                    id[i] = j;
                    size[j] += size[i];
                }
                else {
                    id[j] = i;
                    size[i] += size[j];
                }
            }
        }
    }
}
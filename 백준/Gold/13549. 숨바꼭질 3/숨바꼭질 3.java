import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.Arrays;

/*
 0. 풀이 방식 : 0-1 BFS (Shortest Path Algorithm for Binary Edge-weight Graph)
             - 간선의 가중치가 0 혹은 1이기 때문에,
               다음에 방문할 위치를 담은 FIFO Queue에 가중치가 0인 움직임(2 * X), 그 다음에 가중치가 1인 움직임을 추가했다.
               간선의 가중치가 binary 인 경우 PQ 를 활용한 Dijkstra를 활용하지 않아도 BFS 로도 최단거리 탐색이 가능.
               단, 후에 더 나은 경로를 찾은 경우를 위해, 만약 어떤 위치의 tab[] 값을 업데이트한 경우 큐에 다시 추가해야한다.
               그래야만 해당 위치로부터 이어지는 다른 위치로의 도달 최단 시간도 갱신이 가능하기 때문이다.
 1. 시간 복잡도 : O(K = 100,001) 이다. 기본적으로 해당 알고리즘은 출발지점으로부터 도달가능한 지점을 모두 탐색하기 때문이다.
 */


public class Main{
    static int N; // 수빈 위치.
    static int K; // 동생 위치.
    static int[] tab = new int[100001]; // tab[K] = 수빈의 위치로부터 K까지 가는 최단 시간. 수빈과 동생의 위치 모두 10만이 최대.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 순간이동 시 0초 소요하므로, 어떤 위치까지 0초만에 도달하는 것이 가능하다.
        // 따라서 -1로 초기화.
        Arrays.fill(tab, -1);

        BFS();

        System.out.print(tab[K]); // 수빈의 위치로부터 동생의 위치까지 가는 최단 시간.


    }

    private static void BFS(){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        tab[N] = 0;
        q.addLast(N);

        while(!q.isEmpty()){
            int current = q.removeFirst();
            if(current == K){
                break;
            }
            else if(current < K){
                if(current > 0 && isAvail(current * 2)
                        && (tab[current * 2] == -1 || tab[current * 2] > tab[current])){
                    q.addLast(current * 2);
                    tab[current * 2] = tab[current];
                }
                if(isAvail(current + 1)
                        && (tab[current + 1] == -1
                        || tab[current + 1] > tab[current] + 1)){
                    tab[current + 1] = tab[current] + 1;
                    q.addLast(current + 1);
                }
                if(isAvail(current - 1)
                        && (tab[current - 1] == -1
                        || tab[current - 1] > tab[current] + 1)){
                    tab[current - 1] = tab[current] + 1;
                    q.addLast(current - 1);
                }

            }
            else {
                if(isAvail(current - 1)
                        && (tab[current - 1] == -1
                        || tab[current - 1] > tab[current] + 1)){
                    tab[current - 1] = tab[current] + 1;
                    q.addLast(current - 1);
                }
            }
        }

    }

    // 해당 위치가 인덱스 범위 내에 존재할 경우 true
    private static boolean isAvail(int pos){
        return pos >= 0 && pos <= 100000;
    }
}

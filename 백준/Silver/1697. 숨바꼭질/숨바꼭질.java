import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main {

    static int[] tab = new int[100001]; // 1 <= K, N <= 100,000
    static int K;
    static int N;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        /* 배열 초기화가 가장 중요! */
        Arrays.fill(tab, -1); // 미방문 위치 == -1이 되도록 초기화. 그렇지 않으면, 이미 방문한 지점을 미방문으로 생각하고 갱신해버림.
        // 출발점이 0일 때가 반례.
        // 출발점이 0이므로 tab[0] == 0 이어야한다.
        // 하지만 만약 (tab[k] == 0) 을 미방문 상태로 인식하도록 프로그램을 작성할 경우,
        // tab[(0 * 2) == 0] 방문시 이를 미방문으로 인식하고 tab[0] = 1로 갱신하게 된다!!
        // 그래서 계속 0 100000 입력 시 23이 나왔던 것!

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // Call the BFS function to populate tab[K] with the correct time
        move();

        // Print the minimum time required to reach K
        System.out.print(tab[K]);
    }

    // BFS
    private static void move(){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(N); // 시작점 추가.
        tab[N] = 0; // 시작점 시간 초기화

        while(!q.isEmpty()){
            int current = q.removeFirst();

            if(current == K){
                break;
            }
            else {
                if(current < K){
                    if(current * 2 <= 100000 && (tab[current * 2] == -1 || tab[current * 2] > tab[current] + 1)){
                        tab[current * 2] = tab[current] + 1;
                        q.addLast(current * 2);
                    }
                    if(current + 1 <= 100000 && (tab[current + 1] == -1 || tab[current + 1] > tab[current] + 1)){
                        tab[current + 1] = tab[current] + 1;
                        q.addLast(current + 1);
                    }
                }

                if(current > 0 && (tab[current - 1] == -1 || tab[current - 1] > tab[current] + 1)){
                    tab[current - 1] = tab[current] + 1;
                    q.addLast(current - 1);
                }
            }
        }
    }
}


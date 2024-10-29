import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayDeque;


/*
   0. Snake의 존재로 인해 Cycle이 존재할 수 있다.
 */

public class Main {
    static HashMap<Integer, Integer> lAndS = new HashMap<>(); // Ladders & Snakes
    static boolean[][] visited = new boolean[101][2]; // visited[i][0] == true -> 발견만 하고 방문하지 않은 상태. / [1] => 발견 및 탐색도 한 상태.
    static int[] cnt = new int[101]; // cnt[i] : i 도달 시점 최소 주사위 던지기 횟수.
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // 1. 사다리 입력 받기
        for(int i = 0;  i < N; i++){
            st = new StringTokenizer(br.readLine());
            lAndS.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        // 2. 뱀 입력 받기
        for(int j = 0; j < M; j++){
            st = new StringTokenizer(br.readLine());
            lAndS.put(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }

        BFS(1, 0); // 1부터 시작

        System.out.print(cnt[100]);
    }

    public static void BFS(int pos, int diceCount){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(pos);
        visited[pos][0] = true;
        cnt[pos] = 0;

        while(!q.isEmpty()){
            int current = q.removeFirst();
            visited[current][1] = true;

            // // Base Case : 100에 도달하는 최소 횟수 발견.
            // if(current == 100){break;} // BFS의 Level-order 순회 특성 상 처음 발견 시점이 가장 이른 시점.

            // Case I : 사다리 혹은 뱀 존재하는 경우
            if(lAndS.containsKey(current)){
                int dest = lAndS.get(current);
                // 미발견 상태 위치 발견한 경우 -> 갱신
                if(!visited[dest][0]){
                    q.addLast(dest);
                    visited[dest][0] = true;
                    cnt[dest] = cnt[current];
                }
                // 이미 발견한 지점에 대한 갱신이 필요할 때
                else {
                    if(cnt[dest] > diceCount){
                        cnt[dest] = cnt[current];
                        // 탐색 및 방문까지 한 경우에만 큐에 다시 추가. 이때만 재검토가 필요하다.
                        if(visited[dest][1]){q.addLast(dest);}
                    }
                }
            }

            // Case II : 주사위 던져야 하는 경우
            else {
                for(int i = 6; i > 0; i--){
                    int next = current + i;
                    // 방문 가능한 위치에 대해 다음을 검토한다.
                    if(isAvailable(next)){
                        // 처음 발견한 위치의 경우 -> 갱신 및 큐에 추가.
                        if(!visited[next][0]){
                            q.addLast(next);
                            cnt[next] = cnt[current] + 1;
                            visited[next][0] = true;
                        }
                        // 이미 발견 및 탐색도 하였으나 새로운 최단 경로 발견한 경우 -> 큐에 새로 추가해서 재검토
                        else if(visited[next][1] && cnt[next] > cnt[current] + 1){
                            q.addLast(next);
                            cnt[next] = cnt[current] + 1;
                        }
                        // 발견만 한(현재 큐 어딘가에 박혀있는) 위치에 대해 더 효율적인 도달 경로를 발견한 경우 -> 갱신만!
                        else {
                            if(cnt[next] > cnt[current] + 1){cnt[next] = cnt[current] + 1;}
                        }
                    }
                }
            }
        }

    }

    private static boolean isAvailable(int pos){
        return pos > 0 && pos < 101; // 1 ~ 100 사이 숫자일 경우 True.
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main{

    static int N; // 숫자 풀의 크기.
    static int M; // 만들어질 수열의 길이.
    static boolean[] visited = new boolean[10001]; // 입력으로 주어지는 수가 10,000보다 작거나 같은 자연수.
    static ArrayList<Integer> pool = new ArrayList<>(); // 제공되는 숫자 풀 (중복 X)
    static int[] available = new int[10001]; // 각 숫자 별 풀 내 갯수(ex. 1이 두 개면 aval[1] = 2).
    static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        // 1. 입력
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(st.nextToken());
            // 이미 존재하는 경우 -> 갯수 추가.
            if(available[current] != 0){
                available[current] += 1;
            }
            else {
                available[current] = 1;
                pool.add(current);
            }
        }

        pool.sort((a1, a2) -> {return a1-a2;});

        // 2. 프로그램 실행.
        sequence(0, "");

        System.out.print(answer.toString().trim());
    }

    private static void sequence(int currentDepth, String prev){
        if(currentDepth == M){
            answer.append(prev.trim());
            answer.append("\n");
            return;
        }

        for(int currentPick : pool){
            // 아직 방문하지 않은 숫자이거나, 방문했지만 아직 사용 가능한 갯수가 남은 경우.
            if(!visited[currentPick]){
                int beforeCnt = available[currentPick];
                String current = prev + currentPick + " ";

                if(beforeCnt > 0){
                    available[currentPick] -= 1;
                }
                if(available[currentPick] == 0){visited[currentPick] = true;}
                // 재귀 호출
                sequence(currentDepth + 1, current);
                // 원상 복귀
                if(available[currentPick] == 0){visited[currentPick] = false;}
                available[currentPick] = beforeCnt;
            }

        }
    }
}
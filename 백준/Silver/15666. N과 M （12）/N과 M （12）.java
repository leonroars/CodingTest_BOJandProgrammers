import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main{
    static int N; // 숫자 풀의 크기
    static int M; // 만들어질 수열의 길이
    static int prevPick = 0; // 바로 직전에 고른 숫자. 주어지는 수가 자연수이므로 0 초기화.
    static boolean[] isAlready = new boolean[10001]; // 효율적인 숫자 중복 체크를 위한 boolean
    static ArrayList<Integer> pool = new ArrayList<>(); // 수열 생성에 사용될 수 목록
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
            // 중복이 아닌 경우에만 추가.
            if(!isAlready[current]){
                isAlready[current] = true;
                pool.add(current);
            }
        }

        // 2. 비내림차순정렬 (Monotonically Increasing)
        pool.sort((a1, a2) -> {return a1 - a2;});

        // 3. 실행
        sequence(0, "");

        // 4. 출력
        System.out.print(answer.toString().trim());


    }

    private static void sequence(int currentDepth, String prev){
        if(currentDepth == M){
            answer.append(prev.trim());
            answer.append("\n");
            return;
        }
        for(int pick : pool){
            // 비내림차순에 맞게 다음 숫자 고르기.
            if(pick >= prevPick){
                int before = prevPick;
                prevPick = pick;
                String current =  prev + pick + " ";
                sequence(currentDepth + 1, current);
                prevPick = before;
            }

        }
    }
}
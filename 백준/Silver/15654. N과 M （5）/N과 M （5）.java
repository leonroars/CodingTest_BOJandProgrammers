import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

/*
 - 시간 제한 : 1초
 - 메모리 제한 : 512MB
 - 1 <= M <= N <= 8

 만약 M과 N이 모두 8일 경우 8!(40,320) 가지의 의 수열이 가능하다.
 재귀적 백트래킹을 활용했을 때 주어진 시간 및 메모리 제한 내에 만들 수 있는 경우의 수.
 다만 이때 중요한 점은, 수열을 다 만들고 순서를 정렬하는 것이 아니라!
 미리 N 개의 수를 정렬해두고 이에 대해 백트래킹을 실시함으로써 생성되는 수열의 순서를 강제해야한다는 것.
 따로 정렬을 하는 경우 풀이의 시간복잡도가 불필요하게 높아진다.

 */

public class Main {

    static int N;
    static int M;
    static ArrayList<String> nums = new ArrayList<>();
    static boolean[] visited;
    static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new boolean[N]; // N 개의 수.

        st = new StringTokenizer(br.readLine());

        // 1. 입력 받기 : 상수 시간복잡도
        for(int i = 0; i < N; i++){
            nums.add(st.nextToken()); // 숫자 풀에 입력 숫자 하나씩 추가.
        }

        // 2. 입력 정렬 : 상수 시간복잡도
        nums.sort((a1, a2) -> {return Integer.parseInt(a1) - Integer.parseInt(a2);});

        // 3. 백트래킹 메서드 호출.
        sequence(0, "");

        System.out.print(answer.toString().trim()); // 불필요한 마지막 공백 트리밍.
    }

    private static void sequence(int currentDepth, String current){
        // Base Case : 최대 깊이 도달한 경우. (최대 깊이 == M)
        if(currentDepth == M){
            answer.append(current.trim()); // 지금까지의 경로로부터 만들어진 수열 추가.
            answer.append("\n"); // 개행
            return;
        }

        for(int i = 0; i < nums.size(); i++){
            // 앞의 호출로부터 채택되지 않은 숫자만 골라 생성.
            if(!visited[i]){
                visited[i] = true;
                sequence(currentDepth + 1, current + nums.get(i) + " ");
                visited[i] = false;
            }
        }
    }
}
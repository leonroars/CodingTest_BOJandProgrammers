import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap; // int[]로 만들 시 사용하지도 않을 숫자에 대한 공간이 생성되기 때문!

/*
 - 시간 제한 : 2초
 - 메모리 제한 : 512MB
 - 1 <= A < B <= 10억

 * 문제의 조건 상 A, B 모두 int 범위 내에서 표현 가능하다. 따라서 int형으로 선언한다.

 현재의 숫자로부터 새로운 숫자 두 개를 만들 수 있다. 이를 이동으로 생각하자.
 중요한 점은 A로부터 만든 숫자가 B를 넘을 경우 다시 감소시키는 방법이 주어지지 않았기 때문에,
 "탐색 실패 지점 == 생성된 숫자가 B를 넘은 경우" 로 생각할 수 있다.
 이러한 점을 고려하여 (이미 방문한 위치(숫자), 그 숫자를 만들기 위해 시행된 연산의 수)를 저장하기 위한
 HashMap<Integer, Integer> tab 을 활용한다!
 "정답 = 최소 연산수 + 1"이기 때문에 이를 반드시 유념할 것!

 또한 A로부터 새로운 숫자를 만드는 두 가지 방법이 비용 균등하므로 BFS는 목표지점까지 최단 경로 탐색을 제공한다.
 따라서 문제의 조건에 맞게 BFS를 잘 시행하면 된다.
 */

public class Main {

    static int A;
    static int B;
    static HashMap<Integer, Integer> tab; // Tabular that prevents program from visiting pre-visited location.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        tab = new HashMap<>();

        BFS();

        int answer = (tab.containsKey(B)) ? (tab.get(B) + 1) : (-1);
        System.out.print(answer);

    }

    private static void BFS(){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(A); // 출발점 : A
        tab.put(A, 0);

        while(!q.isEmpty()){
            int current = q.removeFirst();
            if(current == B){return;}

            ArrayList<Integer> adjacent = new ArrayList<>();

            Long twice = current * 2L;
            Long longer = Long.parseLong((Integer.toString(current) + "1"));

            // 방문 가능한 위치만 인접 위치 목록에 추가.
            if(isAvailable(twice)){adjacent.add(current * 2);}
            if(isAvailable(longer)){adjacent.add(Integer.parseInt(Integer.toString(current) + "1"));}

            // 인접 위치 중,
            for(int next : adjacent){
                // 이전에 방문한 적 없는 위치에 대하여,
                if(!tab.containsKey(next)){
                    tab.put(next, tab.get(current) + 1);
                    q.addLast(next);
                }
            }
        }

    }

    private static boolean isAvailable(Long location){
        return location <= 1000000000 && location <= B;
    }
}
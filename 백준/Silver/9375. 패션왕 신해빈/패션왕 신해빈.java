import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayList;

/*
 1. 풀이의 핵심 : 각 카테고리 별 옷의 개수에 '고르지 않음' 명목으로 1을 추가해준다.
    왜?
     - 그렇게 하지 않으면 내가 처음에 생각했던 방법인 '전체 카테고리 별 1개 고르는 방법,, 2개 고르는 방법...' 이 짓을 해야한다.
       하지만 '고르지 않음' 명목의 1을 추가해준다면?
       문제가 '전체 카테고리에 걸쳐, 각 카테고리 별로 옷을 하나씩 골라 입는 경우의 수'가 된다!
       단순 곱셈 문제가 되는 것이다!
       그리고 중요한 것은 '옷을 반드시 하나는 입어야 한다'이므로, 계산 결과에서 1을 빼준다!
 */

public class Main {
    static int T;
    static int N; // 테스트케이스 별 전체 의상 수
    static HashMap<String, Integer> map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = Integer.parseInt(br.readLine());
        String[] current = new String[2];
        StringBuilder sb = new StringBuilder();

        while(T > 0){
            N = Integer.parseInt(br.readLine());
            map = new HashMap<>();
            // 1. 테스트케이스별 입력 받기
            for(int i = 0; i < N; i++){
                current = br.readLine().split(" ");
                String category = current[1];

                // Case I : 이미 해당 카테고리의 옷이 존재하는 경우 -> 갯수 갱신
                if(map.containsKey(category)){
                    int cCnt = map.get(category) + 1;
                    map.put(category, cCnt);
                }
                // Case II : 존재하지 않는 경우 -> 1로 저장 & 카테고리 목록에 저장..
                else {
                    map.put(category, 1);
                }
            }
            // 2. 경우의 수 구하기.
            int answer = 1;
            for(int cPerC : map.values()){
                answer *= (cPerC + 1);
            }

            sb.append(answer - 1);
            if(T > 1){sb.append("\n");}
            T--;
        }
        
        System.out.print(sb.toString());
    }
}
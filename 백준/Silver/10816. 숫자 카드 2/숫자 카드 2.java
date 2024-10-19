import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashMap;

/*
 이 풀이는 풀이 시간 및 메모리 제한 상 허용되는 연산 수를 확인해보기 위한 풀이입니다.
 */

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        HashMap<Integer, Integer> map = new HashMap<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 1. 입력 : O(N) _ 1 <= N <= 500,000
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(st.nextToken());
            if(map.containsKey(current)){
                int prev = map.get(current);
                map.put(current, prev + 1);
            }
            else {
                map.put(current, 1);
            }
        }
        StringBuilder sb = new StringBuilder();
        
        // 2. 입력
        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int j = 0; j < M; j++){
            int query = Integer.parseInt(st.nextToken());
            if(map.containsKey(query)){
                sb.append(map.get(query));
            }
            else {
                sb.append(0);
            }
            sb.append(" ");
        }
        
        System.out.print(sb.toString().trim());
        
    }
}
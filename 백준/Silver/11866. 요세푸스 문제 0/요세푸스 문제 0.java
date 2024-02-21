import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

// 풀이 및 접근
// 문제가 원형 큐에서 삽입 및 삭제 위치 지정을 위해 인덱스를 '회전'시키는 논리를 요구하고 있다고 생각하였다.
// 예를 들어 Underlying array의 길이가 M인 원형 큐에서 i위치로부터 N만큼 떨어진 위치의 아이템의 위치 참조 하기 위해
// (i + N)이 아닌 (i + N) % M을 한다.


public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> arr = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        int idx = K - 1; // 시작 인덱스 설정. 
        
        for(int i = 1; i < N + 1; i++){
            arr.add(i);
        }
        
        while(!arr.isEmpty()){
            int removed = arr.remove(idx);
            sb.append(removed);
            if(!arr.isEmpty()){
                sb.append(", ");
                // K가 아닌 K-1을 하는 것은 없는 값 참조를 피하기 위해 하나씩 제거해나가는 방식을 사용했기 때문이다.
                idx = (idx + K - 1) % arr.size(); 
            }
        }
        
        sb.append(">");
        
        System.out.println(sb);
        
        
    }
}
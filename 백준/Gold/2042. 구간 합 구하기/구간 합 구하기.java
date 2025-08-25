import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int N; // 1 이상 백만 이하.
    static int M; // 수열 변경 횟수(1 이상 10,000 이하)
    static int K; // 구간 합 쿼리 요청 수(1 이상 10,000 이하)
    
    static long[] sumTill; // sumTill[i] : 1번째 수 ~ i번째 수까지의 합.
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        sumTill = new long[N + 1];
        
        // 1. 수열 입력 받기. - O(N)
        for(int i = 1; i < N + 1; i++){
            long num = Long.parseLong(br.readLine());
            sumTill[i] = sumTill[i - 1] + num;
        }
        
        // 2. 명령어 입력 받기
        StringBuilder answer = new StringBuilder();
        
        for(int j = 0; j < M + K; j++){
            st = new StringTokenizer(br.readLine());
            int op = Integer.parseInt(st.nextToken());
            
            if(op == 1){
                int targetIdx = Integer.parseInt(st.nextToken());
                long targetNum = Long.parseLong(st.nextToken());
                changeNum(targetIdx, targetNum);
            }
            else {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                answer.append(querySum(a, b));
                answer.append("\n");
            }
        }
        
        // 3. 출력
        System.out.print(answer.toString().trim());
    }
    
    // a 번째 수를 b로 변경 : O(N)
    public static void changeNum(int a, long b){
        long diff = b - (sumTill[a] - sumTill[a - 1]);
        // 변경 전파
        for(int idx = a; idx < N + 1; idx++){
            sumTill[idx] += diff;
        }
    }
    
    // 구간 합 쿼리 처리 : 문제의 조건 상 a <= b 임이 보장됨.
    public static String querySum(int a, int b){
        return Long.toString(sumTill[b] - sumTill[a - 1]);
    }
}
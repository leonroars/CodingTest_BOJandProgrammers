import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    static int[] dp; // dp[i] = The length of LIS starting from 'i'
    static int[] seq; // Sequence
    static int N; // Sequence Size

    // dp[startIdx] = startIdx에서 시작하는 LIS의 길이를 반환.
    // 끝에서부터 처리되어 거꾸로 올라오는 구조. 재귀적 bottom up
    static int func(int startIdx){
        // Base Case : 수열의 끝 숫자로 만들 수 있는 LIS 길이는 1.
        if(startIdx == N-1){return dp[startIdx] = 1;}
        // Base Case : dp[startIdx] == 0일때. 아직 구하지 않았다는 뜻이므로 구한다.
        // 아닐 경우 메모이제이션을 활용할 수 있으므로 아래의 절차를 활용하지 않고 바로 스킵.
        if(dp[startIdx] == 0){
            // seq[startIdx] 다음에 올 수 있는 수로부터 시작하는 LIS 중 가장 긴 것.
            // 만약 startIdx 이후의 수열의 모든 숫자가 전부 seq[startIdx] 보다 작을 경우 0 그대로 반환. LIS가 startIdx에서 끝이라는 뜻이기 때문.
            int nextLIS = 0;
            for(int i = startIdx + 1; i < N; i++){
                int current = 0; // seq[i]로 시작하는 LIS 길이
                if(seq[i] > seq[startIdx]){
                    current = func(i);
                    // startIdx 다음으로 올 수 있는 가능한 모든 수로부터 시작하는 LIS 중 최댓값 갱신하기.
                    if(current > nextLIS){nextLIS = current;}
                }
            }
            dp[startIdx] = nextLIS + 1;
        }
        return dp[startIdx];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N]; // 메모이제이션 배열 초기화
        seq = new int[N]; // 입력 수열을 담을 배열 초기화
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            seq[i] = Integer.parseInt(st.nextToken());
        }

        int max = 0;
        for(int j = 0; j < N; j++){
            int LISfromCurrent = func(j);
            if(LISfromCurrent > max){max = LISfromCurrent;}
        }

        System.out.print(max);
    }
}
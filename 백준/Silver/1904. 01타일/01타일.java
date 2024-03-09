import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// 풀이 및 접근
// 내가 가장 취약한 동적 계획법 문제이다.
// 만약 내가 이 문제를 처음 풀 때부터 정석적으로 '반복되는 부분문제'를 파악하고 점화식을 세울 수 있었다면 가장 좋았겠지만
// 아직 익숙하지 않아 N == 6 일때까지 가능한 모든 수를 나열해서 적어본 후에야 규칙을 찾아 점화식을 세울 수 있었다.
// 내가 구한 점화식은 다음과 같다.
//

public class Main{
    static long tile(int N){
        long[] memo = new long[1000001];
        memo[1] = 1;
        memo[2] = 2;
        if(N == 1){return 1;}
        if(N == 2){return 2;}
        for(int i = 3; i < N + 1; i++){
            memo[i] = (memo[i - 1] + memo[i - 2]) % 15746;
        }

        return memo[N];

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        System.out.print(tile(N));
    }
}
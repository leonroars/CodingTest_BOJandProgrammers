import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    static int N; // 1 <= 동전 종류 수 N <= 10
    static int K; // 1 <= 목표 금액 K <= 1억
    static int[] coins; // coins[0] : 가장 단위 금액이 큰 동전 / coins[N - 1] = 단위 가장 작은 동전

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        coins = new int[N];

        // 1. 입력 받기.
        for(int i = N - 1; i > -1; i--){
            coins[i] = Integer.parseInt(br.readLine());
        }

        int answer = coinSelection(0, 0, K);

        System.out.print(answer);
    }

    private static int coinSelection(int idx, int accumCount, int left){
        // Base Case : 목표 금액 달성한 경우.
        if(left == 0){return accumCount;}

        // Base Case : 만들 수 없는 경우
        //             (하지만 현 문제에서는 만들 수 없는 경우에 어떤 답안을 출력할지 언급이 없으므로, 이런 경우 없다고 생각하는 게 맞다.)
        if((idx == N && (left % coins[0]) != 0)){return -1;}

        int share = left / coins[idx];

        // Case I : 현재 단위 동전 > left -> 다음 동전 조사.
        if(share == 0){return coinSelection(idx + 1, accumCount, left);}

        // Case II : 나누어지는 경우 -> 나머지 토스!
        return coinSelection(idx + 1, accumCount + share, left % coins[idx]);

    }
}
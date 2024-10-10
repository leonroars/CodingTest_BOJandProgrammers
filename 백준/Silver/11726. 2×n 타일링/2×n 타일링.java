import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

/*
 1. 풀이 아이디어 : Optimal Substructure + Overlapping Subproblem

    2 * 1 : 1가지
    2 * 2 : 2가지(1*2 두 개 혹은 2*1 두개)
    2 * 3 : 가장 처음에 무엇을 두는 지에 따라 달라진다는 점에 주목.
            1) 처음에 1*2를 둔다 : 남은 공간은 ((2 - 1) * 2), (2 * (3 - 2))가 된다.
               어느 위치에 1*2를 두던 
            2) 처음에 2*1을 둔다 : 남은 공간은 ((2 - 2) * 1), (2 * (3 - 1))가 된다.
    일반화하여 생각해보자.
    세로 길이가 2로 정해져있기 때문에
    2*K 직사각형에 대해 처음에 1*2를 놓을 경우
    남은 공간은 1*2 바로 아래의 공간(1*2 크기)와 남은 (2 * K-2) 공간이다.
    2*1을 놓을 경우 남은 공간은 (2 * K - 1) 공간이다.
    즉, 전체 해가 부분해로 구성되는 것이다.

    이를 점화식으로 세우면 다음과 같이 표현할 수 있다.
    2*N 크기의 직사각형이 주어질 때 이를 1*2, 2*1 타일로 채우는 방법의 수를 dp[N]이라고 하자.
    dp[N] = 1 (N == 1)
          = 2 (N == 2)
          = dp[N - 1] + dp[N - 2] (N >= 3)


 */


public class Main{

    static int N;
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N + 1];

        fill(N);

        System.out.print(dp[N]);
    }

    private static int fill(int width){
        if(width == 1){return dp[width] = 1;}
        if(width == 2){return dp[width] = 2;}

        if(dp[width] != 0){return dp[width];}
        return dp[width] = (fill(width - 1) + fill(width - 2)) % 10007;
    }
}
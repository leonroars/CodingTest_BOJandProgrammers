import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/*
   - 시작해로부터 끝나는 해까지의 연도 수 : M, N의 최소 공배수.
   - 즉, 전체 햇수가 M, N의 최소공배수에 도달하기 이전에,
     M으로 나누었을 때 x, N으로 나누었을 때 y인 K번째 해를 찾는 것.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();

        while(TC > 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int M = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            answer.append(cain(M, N, x, y));
            if(TC > 1){answer.append("\n");}
            TC--;
        }

        System.out.print(answer.toString());

    }

    private static int cain(int M, int N, int x, int y){
        int gcd = GCD(M, N);
        int end = (M * N) / gcd; // 몇 번째 해에 종말인가?
        int cX = -1;

        for(int k = 0; k * M + x <= end; k++){
            cX = k * M + x;
            if(((cX % N == 0) && (y == N)) || cX % N == y){ // 나머지 y == N 인 경우를 생각해보자! 거의 다 온 듯?
                return cX;
            }
        }
        return -1;
    }

    /* 최대공약수 */
    private static int GCD(int M, int N){
        int remainder = M % N;
        int dividend = N;
        int divisor = M;

        while(remainder != 0){
            divisor = dividend; // 이전의 피제수가 새로운 제수가 된다.
            dividend = remainder; // 이전 계산의 나머지가 새로운 피제수가 된다.
            remainder = divisor % dividend;
        }

        return dividend;
    }
}
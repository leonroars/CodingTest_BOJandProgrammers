import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// 풀이 접근
// 규칙을 찾는 것이 가장 유효했다.
// 1보다 큰 n번 째 피보나치 수열의 fib(0) & fib(1) 호출 수는, 각각 n-2, n-1 번째 피보나치 수와 일치한다.
// 제한 시간이 0.25초로 주어지는 만큼, 일반적인 재귀 함수 작성이 아닌 배열을 이용한 Memoization을 활용하는
// 동적 계획법적 접근으로 풀이한다.
// 또한 피보나치 객체를 따로 만들어두고 처음에 인스턴스화시 최대 입력값인 40을 인자로 주어
// 매 입력마다 피보나치 수열을 불필요하게 반복적으로 재계산할 필요가 없도록 하였다.


public class Main{
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Fibonacci fib = new Fibonacci(40);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            sb.append(fib.callNum(Integer.parseInt(br.readLine())));
            sb.append("\n");
        }
        br.close();
        System.out.println(sb.toString());
    }

    static class Fibonacci{
        private static int f0;
        private static int f1;

        private static int[] memo;

        public Fibonacci(int maxSize){
            memo = new int[maxSize + 1];
            memo[0] = 1;
            memo[1] = 1;
            memo[2] = 2;
            for(int i = 3; i < maxSize + 1; i++){
                memo[i] = memo[i - 2] + memo[i - 1];
            }
        }

        public StringBuilder callNum(int x){
            StringBuilder sb = new StringBuilder();
            if(x == 0 | x == 1){
                if(x == 0){sb.append(1); sb.append(" "); sb.append(0);}
                if(x == 1){sb.append(0); sb.append(" "); sb.append(1);}
            }
            else {
                sb.append(memo[x - 2]);
                sb.append(" ");
                sb.append(memo[x - 1]);
            }
            return sb;
        }
    }
}
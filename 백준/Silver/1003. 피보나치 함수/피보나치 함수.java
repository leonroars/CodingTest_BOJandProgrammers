import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
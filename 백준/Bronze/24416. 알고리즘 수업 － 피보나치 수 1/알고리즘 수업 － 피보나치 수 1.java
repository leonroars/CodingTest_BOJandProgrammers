import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Fibonacci fibo = new Fibonacci(N);
        System.out.print(fibo);
    }
    static class Fibonacci{
        private int recursionCount;
        private int dpCount;
        private int[] tab; // Bottom-up Tabulation.
        
        public Fibonacci(int N){
            recursionCount = 0;
            dpCount = 0;
            tab = new int[N + 1];
            fib(N);
            fibonacci(N);
        }
        
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(recursionCount);
            sb.append(" ");
            sb.append(dpCount);
            
            return sb.toString();
        }
        
        private int fib(int N){
            if(N == 1 || N == 2){recursionCount++; return 1;}
            return fib(N - 1) + fib(N - 2);
        }
        
        private int fibonacci(int N){
            tab[1] = 1;
            tab[2] = 1;
            for(int i = 3; i <= N; i++){
                tab[N] = tab[N-1] + tab[N-2];
                dpCount++;
            }
            return tab[N];
        }
    }
}
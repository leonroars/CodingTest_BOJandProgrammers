import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// 브루트포스로도 가능하지만, 동적계획법 연습 삼아 동적계획법으로 구현해본다.
public class Main{

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        Binomial b = new Binomial(N, r);
        System.out.println(b.calc(N, r));

    }
    static class Binomial{
        private static int[][] memo;

        public Binomial(int N, int r){
            memo = new int[N + 1][r + 1];
        }

        public int calc(int N, int r){
            if(N == r || r == 0){return 1;}
            if(memo[N][r] == 0){
                return memo[N][r] = calc(N-1, r-1) + calc(N-1, r);
            }
            else{return memo[N][r];}
        }
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());
        
        System.out.print(primeNumberBetween(M, N));
    }
    
    private static String primeNumberBetween(int M, int N){
        boolean[] table = new boolean[N + 1];
        Arrays.fill(table, true);
        table[0] = table[1] = false;
        
        // 1. 에라토스테네스의 체 : 2부터 (int)sqrt(N) 까지의 수의 배수는 모두 소수가 아니므로 표시해주기.
        for(int k = 2; k <= (int)Math.sqrt(N); k++){
            if(table[k]){
                for(int j = k * k; j <= N; j += k){
                    table[j] = false;
                }
            }
        }
        
        // 2. 결과 담아주기.
        StringBuilder result = new StringBuilder();
        for(int i = M; i < N + 1; i++){
            if(table[i]){
                result.append(i);
                result.append("\n");
            }
        }
        
        // 3. 결과 반환
        return result.toString().trim();
    }
}
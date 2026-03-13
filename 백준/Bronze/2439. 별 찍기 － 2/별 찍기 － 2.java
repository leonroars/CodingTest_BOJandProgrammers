import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();
        
        for(int i = 1; i <= N; i++){
            result.append(" ".repeat(N-i) + "*".repeat(i));
            if(i < N){result.append("\n");}
        }
        
        System.out.print(result.toString());
    }
}
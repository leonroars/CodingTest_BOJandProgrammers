import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for(int i = N-1; i >= 0; i--){
            for(int left = 0; left < i; left++){
                sb.append(" ");
            }
            for(int star = 0; star < (2*N-1) - (2*i); star++){
                sb.append("*");
            }
            sb.append("\n");
        }

        for(int i = 1; i < N; i++){
            for(int left = 0; left < i; left++){
                sb.append(" ");
            }
            for(int star = (2*N - 1) - (2*i); star > 0; star--){
                sb.append("*");
            }
            sb.append("\n");
        }

        System.out.print(sb.toString().stripTrailing());
    }
}
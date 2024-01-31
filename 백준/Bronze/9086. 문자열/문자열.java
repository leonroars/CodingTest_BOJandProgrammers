import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < N; i++){
            String s = br.readLine();
            sb.append(s.charAt(0));
            sb.append(s.charAt(s.length() - 1));
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
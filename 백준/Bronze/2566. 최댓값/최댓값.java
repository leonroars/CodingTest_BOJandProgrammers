import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int max = 0;
        int[] maxLoc = new int[]{1, 1}; // 전부 0일 경우 처음 위치가 출력되도록 한다.
        for(int i = 1; i < 10; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j = 1; j < 10; j++){
                int num = Integer.parseInt(st.nextToken());
                if(num > max){max = num; maxLoc[0] = i; maxLoc[1] = j;}
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(max);
        sb.append("\n");
        sb.append(maxLoc[0]);
        sb.append(" ");
        sb.append(maxLoc[1]);
        System.out.println(sb);
    }
}
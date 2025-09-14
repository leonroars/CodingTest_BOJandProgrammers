import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[][] pos = new int[N][2];
        
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            pos[i][0] = Integer.parseInt(st.nextToken());
            pos[i][1] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(pos, (o1, o2) -> {
            if(o1[1] == o2[1]){return o1[0] - o2[0];}
            return o1[1] - o2[1];
        });
        
        StringBuilder answer = new StringBuilder();
        
        for(int j = 0; j < N; j++){
            answer.append(pos[j][0]);
            answer.append(" ");
            answer.append(pos[j][1]);
            if(j < N - 1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
}
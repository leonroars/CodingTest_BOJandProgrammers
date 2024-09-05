import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashMap;

public class Main{
    public static void main(String[] args) throws IOException {
        HashMap<Integer, Boolean> map = new HashMap<>();
        StringBuilder answer = new StringBuilder();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++){
            int existingNum = Integer.parseInt(st1.nextToken());
            map.put(existingNum, true);
        }
        
        int M = Integer.parseInt(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        for(int j = 0; j < M; j++){
            int queryNum = Integer.parseInt(st2.nextToken());
            if(map.containsKey(queryNum)){
                answer.append("1").append("\n");
            }
            else {
                answer.append("0").append("\n");
            }
        }
        
        System.out.println(answer.toString().trim());
    }
}
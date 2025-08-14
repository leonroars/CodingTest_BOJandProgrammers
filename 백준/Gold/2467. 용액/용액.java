import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        int l = 0;
        int r = N-1;
        int[] answer = new int[2];
        int closestByFar = Integer.MAX_VALUE;
        
        while(l < r){
            int left = arr[l];
            int right = arr[r];
            int sum = left + right;
            if(Math.abs(sum) < closestByFar){
                answer[0] = left;
                answer[1] = right;
                closestByFar = Math.abs(sum);
            }
            
            if(sum < 0){l++;}
            else if(sum == 0){break;}
            else{r--;}
        }
        
        StringBuilder ans = new StringBuilder();
        ans.append(answer[0]);
        ans.append(" ");
        ans.append(answer[1]);
        
        System.out.print(ans.toString());
    }
}
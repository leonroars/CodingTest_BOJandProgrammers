import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());
        int[] arr = new int[N];
        
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        int currentSum = 0;
        for (int i = 0; i < X; i++) {
            currentSum += arr[i];
        }
        int max = currentSum;
        int maxCnt = 1;

        for (int tail = X; tail < N; tail++) {
            currentSum += arr[tail] - arr[tail - X];
            if (currentSum > max) {
                max = currentSum;
                maxCnt = 1;
            } else if (currentSum == max) {
                maxCnt++;
            }
        }    
        StringBuilder answer = new StringBuilder();
        
        if(max == 0){answer.append("SAD");}
        else {
            answer.append(max);
            answer.append("\n");
            answer.append(maxCnt);
        }
        
        System.out.print(answer.toString());
    }
}
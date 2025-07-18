import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(st.nextToken());
        int minLen = Integer.MAX_VALUE;
        
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        
        for(int i = 0; i < N; i++){arr[i] = Integer.parseInt(st.nextToken());}
        
        int left = 0;
        int right = 0;
        int currentSum = arr[left];
        boolean promising = true;
        
        while(promising){
            if(currentSum < S){
                if(right == N-1){promising = false;}
                else {currentSum += arr[++right];}
            }
            else {
                int currentLen = right - left + 1;
                if(currentLen < minLen){minLen = currentLen;}
                
                if(left == N-1){promising = false;}
                else {currentSum -= arr[left++];}
            }
        }
        if(minLen == Integer.MAX_VALUE){minLen = 0;}
        System.out.print(minLen);
    }
}
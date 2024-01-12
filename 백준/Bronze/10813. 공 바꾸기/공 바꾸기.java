import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int arrSize = Integer.parseInt(st.nextToken());
        int repetition = Integer.parseInt(st.nextToken());
        
        int[] arr = new int[arrSize];
        for(int i = 0; i < arrSize; i++){
            arr[i] = i+1;
        }
        
        for(int j = 0; j < repetition; j++){
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            int temp = arr[right - 1];
            arr[right - 1] = arr[left - 1];
            arr[left - 1] = temp;
        }
        
        StringBuilder sb = new StringBuilder();
        for(int k = 0; k < arrSize; k++){
            sb.append(Integer.toString(arr[k]));
            sb.append(" ");
        }
        
        System.out.println(sb.toString());
        
        
    }
}
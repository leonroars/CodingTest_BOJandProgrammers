import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int arrSize = Integer.parseInt(st.nextToken());
        int oppCnt = Integer.parseInt(st.nextToken());

        int[] arr = new int[arrSize];
        for(int i = 0; i < oppCnt; i++){

            st = new StringTokenizer(br.readLine());
            int floor = Integer.parseInt(st.nextToken());
            int ceiling = Integer.parseInt(st.nextToken());
            int content = Integer.parseInt(st.nextToken());

            for(int j = floor; j < ceiling+1; j++){
                arr[j - 1] = content;
            }

        }
        
        StringBuilder sb = new StringBuilder();
        for(int k = 0; k < arrSize; k++){
            sb.append(Integer.toString(arr[k]));
            sb.append(" ");
        }

        System.out.println(sb.toString());

    }
}
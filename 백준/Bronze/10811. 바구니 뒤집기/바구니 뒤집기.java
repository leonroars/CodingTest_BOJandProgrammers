import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int arrSize = Integer.parseInt(st.nextToken());
        int repetition = Integer.parseInt(st.nextToken());

        int[] arr = new int[arrSize];

        // Setting array as given condition.
        for(int i = 0; i < arrSize; i++){
            arr[i] = i+1;
        }

        for(int j = 0; j < repetition; j++){
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            // For edge case handling.
            if(left != right){
                for(int k = 0; k < (right - left + 1) / 2; k++){
                    int temp = arr[(right - 1) - k];
                    arr[(right - 1) - k] = arr[(left - 1) + k];
                    arr[(left - 1) + k] = temp;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int m = 0; m < arrSize; m++){
            sb.append(arr[m]);
            sb.append(" ");
        }

        for(int l : arr){
            System.out.println(l);
        }
    }
}
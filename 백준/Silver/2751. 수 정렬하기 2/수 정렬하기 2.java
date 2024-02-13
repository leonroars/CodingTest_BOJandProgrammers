import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        for(int i = 0; i < N; i++){
            int k = Integer.parseInt(br.readLine());
            arr[i] = k;
        }
        br.close();

        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j < arr.length; j++){
            sb.append(arr[j]);
            sb.append("\n");
        }

        System.out.println(sb);
    }
}
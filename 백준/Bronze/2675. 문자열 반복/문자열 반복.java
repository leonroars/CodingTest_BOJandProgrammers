import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        int R;
        String s;
        String[] resultArr = new String[N];
        
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            R = Integer.parseInt(st.nextToken());
            s = (String) st.nextToken();
            StringBuilder result = new StringBuilder();
            
            for(int j = 0; j < s.length(); j++){
                for(int k = 0; k < R; k++){
                    result.append(s.charAt(j));
                }
            }
            resultArr[i] = result.toString();
        }
        
        for(int l = 0; l < resultArr.length; l++){
            System.out.printf("%s\n", resultArr[l]);
        }
    }
}
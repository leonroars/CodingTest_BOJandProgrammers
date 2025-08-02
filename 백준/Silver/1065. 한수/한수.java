import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int cnt = 0;
        
        for(int num = 1; num <= N; num++){
            if(isProperSequence(num)){cnt++;}
        }
        
        System.out.print(cnt);
    }
    
    private static boolean isProperSequence(int K){
        // Edge Case : 한 자리수는 등차수열로 바라본다.
        if(K < 10){return true;}
        
        String numStr = Integer.toString(K);
        int diff = -Integer.MAX_VALUE;
        int prev = 0;
        int next = 1;
        boolean result = true;
        
        while(next < numStr.length()){
            int currentDiff = Character.getNumericValue(numStr.charAt(prev)) - Character.getNumericValue(numStr.charAt(next));

            if(diff == -Integer.MAX_VALUE){
                diff = currentDiff;
            }
            else {
                if(currentDiff != diff){result = false; break;}
            }
            next++;
            prev++;
        }
        
        return result;
    }
}
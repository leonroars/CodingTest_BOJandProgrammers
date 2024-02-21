import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 브루트포스 풀이
public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int[] cards = new int[N];
        int max = 0;
        
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            cards[i] = Integer.parseInt(st.nextToken());
        }
        
        for(int j = 0; j < N; j++){
            for(int k = 0; k < N; k++){
                if(j == k){continue;}
                for(int p = 0; p < N; p++){
                    if(p == k || p == j){continue;}
                    else{
                        int current = cards[j] + cards[k] + cards[p];
                        if((current > max) && (current <= M)){max = current;}
                    }
                }
            }
        }
        
        System.out.println(max);
        
    }
}
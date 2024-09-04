import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        String N = st.nextToken(); // 입력 수 N의 문자열 형태.
        int B = Integer.parseInt(st.nextToken()); // 진법
        Double answer = 0.0;
        // A = 65 ~ Z = 90 _ 0 ~ 9 : 48 ~ 57

        for(int i = 0; i < N.length(); i++){
            double pow = Math.pow(B, N.length() - 1 - i);
            if(B < 11){
                answer += pow * Integer.parseInt(Character.toString(N.charAt(i)));
            }
            else{
                // 진법 수를 넘어선 수라서 대문자 알파벳으로 표기된 경우.
                int currentCharAscii = (int) (N.charAt(i));

                if(currentCharAscii >= 65){
                    answer += pow * (10 + (currentCharAscii - 65));
                }
                else {
                    answer += pow * (currentCharAscii - 48);
                }
            }
        }
        
        System.out.println(answer.longValue());
    }
}
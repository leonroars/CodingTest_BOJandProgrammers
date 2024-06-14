import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    static int N;
    static int M;
    static StringBuilder ans = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 1 ~ N
        M = Integer.parseInt(st.nextToken()); // Total 'M' numbers for each pair.

        solution(0, 0, "");
        System.out.print(ans.toString().trim());
    }

    private static void solution(int priorNum, int count, String current){
        // priorNum : 이전 사용한 수
        // count : 현 함수 호출 직전까지 선택한 수의 갯수.
        // currnet : 지금까지의 수 쌍을 저장한 String.
        //           count == M일 경우, ans에 개행 및 current 추가 후 함수 실행 종료.
        //           !주의! : for 문 안에서 String updated를 새로 인스턴스화 해줌으로써 다음 호출 다녀온 후
        //                   원상 복귀가 이루어지도록 한다!

        // Base Case : 이미 M개의 수를 모두 채택한 경우 -> 개행 후 Return.
        if(count == M){
            ans.append(current); // 현재까지의 수 쌍 저장.
            ans.append("\n"); // 개행.
            return;
        }
        for(int num = priorNum + 1; num < N + 1; num++){
            String updated = current + num + " ";
            solution(num, count + 1, updated);
        }
    }
}
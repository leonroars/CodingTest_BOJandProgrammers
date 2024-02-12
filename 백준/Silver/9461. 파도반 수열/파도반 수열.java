import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

// 풀이 및 접근
// 동적 계획법은 작은 문제로 분할될 수 있고 실제 답이 Base-case의 결과로부터 차근차근 계산되어 도출될 수 있다는 점에서
// 재귀와 갈래를 같이 한다고 볼 수 있다. 
// 하지만 동적 계획법은 기억(Memoization)을 통해 재귀함수적 풀이를 효율적으로 처리할 수 있다.

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16); // 버퍼 제한걸어 받기.
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < T; i++){
            int Nth = Integer.parseInt(br.readLine());
            sb.append(wave(Nth));
            sb.append("\n");
        }
        br.close();

        System.out.println(sb);

    }

    // Nth가 100일 경우 비트 오버플로우가 발생해 음수가 출력된다.
    // 이렇게 숫자가 int의 4바이트를 초과하게되는 상황도 고려하여 long으로 데이터 타입을 지정해준다.
    static long wave(int Nth){
        long[] memoization = new long[Nth + 1];
        if(Nth < 4){return 1;}
        else {
            for(int i = 1; i < 4; i++){memoization[i] = 1;}
            for(int j = 4; j < memoization.length; j++){
                memoization[j] = memoization[j - 2] + memoization[j - 3];
            }
            return memoization[Nth];
        }
    }
}
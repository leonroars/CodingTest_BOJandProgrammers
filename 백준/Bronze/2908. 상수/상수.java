import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// * 접근 *
// StringBuilder는 reverse()를 지원한다.
// 이를 이용해 입력받은 문자열을 StringTokenizer를 이용해 파싱 후 뒤집어 비교한다.
// 이후 큰 쪽을 출력
public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 두 입력 문자열을 위해 각각 StringBuilder Instantiate.
        StringBuilder sba = new StringBuilder();
        StringBuilder sbb = new StringBuilder();

        // Parsing
        sba.append(st.nextToken());
        sbb.append(st.nextToken());

        // Flip & Assign
        String a = sba.reverse().toString();
        String b = sbb.reverse().toString();

        String result = (Integer.parseInt(a) > Integer.parseInt(b)) ? a : b;
        System.out.println(result);


    }

}

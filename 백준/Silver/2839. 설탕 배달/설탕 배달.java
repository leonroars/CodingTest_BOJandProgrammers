import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
    // ** 접근 방식 **
    // - 우선 해당 수를 5로 나누었을 때의 몫을 찾고(i = N / 5), 
    //    해당 몫과 5를 곱한 결과의 나머지가 3으로 나누어 떨어지는지 살펴본다.
    //    - 나누어 떨어지는 경우 정답은 5로 나눈 몫 + 나머지를 3으로 나눈 몫이다.
    //    - 나누어 떨어지지 않을 경우 i를 감산하여 재시도한다. 이를 i = 0이 될 때 까지 반복한다.
    // - 이후 5kg와 3kg 봉지로 옮길 수 있는 경우 answer 변수를 갱신한다. 이때 기존의 answer보다 크다면 갱신하지 않는다.
    // - 해당 접근방식의 장점은 복수의 방법이 가능한 경우까지 적절하게 처리할 수 있다는 점이다.
    // ex. N = 21인 경우, 3kg 봉지로만 들 경우 정답은 7이나, 5kg 봉지를 함께 쓸 경우 (5*3) + (3*2) = N, 즉 정답 5를 도출할 수 있다.
    //     - 정답을 비교 후 갱신하는 절차가 포함되어있으므로 문제가 없다.

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int answer = -1;
        for(int i = (N / 5); i >= 0; i--) {
            int forThree = N - (5 * i);
            if (forThree % 3 == 0) {
                int ans = i + (forThree / 3);
                if (ans < answer || answer == -1) {
                    answer = ans;
                }
            }
        }
        System.out.println(answer);
    }

}
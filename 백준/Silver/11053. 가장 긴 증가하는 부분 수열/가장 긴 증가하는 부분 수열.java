import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

// 풀이 및 접근
// 근래에 스스로의 힘으로 풀고 가장 뿌듯했던 문제!
// 동적계획법을 푸는 관성적 사고를 어느 정도 깨고서야 풀 수 있었다.
// 처음엔 효율적 풀이가 전혀 떠오르지 않았기 때문에 자연스럽게 완전탐색으로 푸는 방식을 고민했다.
// 그렇게 하면 분명 중복되는 부분문제나 최적화가 가능한 부분이 보일 것이라고 생각했다.
// 우선 입력받은 수열을 담은 배열을 seq라고 한다. seq[0]은 수열의 첫 번째 숫자이다.
// 내가 설계한 완전탐색 프로세스는 다음과 같다.
//  1) seq[0]을 부분 증가 수열의 시작으로 정한다.
//  2) seq[1]을 살펴본다.
//     만약 seq[1]이 seq[0]보다 작다면 아무 것도 하지 않는다.
//     만약 seq[1]이 seq[0]보다 크다면 seq[0]으로 시작하는 부분 증가 수열의 길이를 1 증가 시킨다.
//  3) 2번의 절차를 수열의 끝까지 탐색하는 동안 반복한다.
//  4) 수열의 끝까지 탐색했다면 이번에는 seq[1]을 시작으로 하여 위의 1, 2, 3을 반복한다.
//  5) 수열의 모든 숫자 각각을 시작점으로 하는 부분 증가 수열의 길이를 모두 파악했다면 비교하여 가장 긴 것을 반환한다.
//
//  입력 수열 길이의 상한이 1,000이므로 최악의 경우 최소 500,000 이상의 연산을 요구한다.
//  1초 안에 가능한 연산이지만 1초에 해결할 수 있는 연산의 상한을 넘을 수 있기 때문에 최적화 방법을 찾는 것이 좋다.
//  동적 계획법을 이용해 최적화를 하기 위해선 '중복되는 부분문제'가 존재해야하고 존재한다면 정의해야한다.
//  이 완전탐색 절차에서 '중복되는 부분문제'가 존재하는지, 존재한다면 어떻게 정의할 수 있는 지 고민했다.
//  내가 발견한 중복 부분은,
//  seq[i]를 시작으로 하는 부분 증가 수열의 길이를 파악하는 동안 탐색했던 seq[i+1]~seq[N-1]까지의 수들을
//  seq[i+1]을 시작으로 하는 부분 증가 수열의 길이를 파악하며 다시 재탐색하게 된다는 것이었다.
//  바로 이 부분이 '중복되는 부분문제'이다.
//  여기까진 내 예상보다 빠르게 파악할 수 있었지만 역시나 '어떤 것을 메모이제이션해야 완전탐색을 최적화할 수 있을까?'에 대한 대답이었다.
//  동시에 점화식을 세울 수 있는가에 대한 고민도 했다.
//  떠오른 아이디어는 다음과 같다.
//  "seq[i]에서 시작하는 LIS의 길이는, seq[i+1]에서 시작하는 LIS의 길이에 1을 더한 것과 같지 않을까?"
//  저 발상 자체도 설득력이 있다고 생각했고 발상 자체가 점화식의 구조 자체였기 때문에 어떻게 구현할지를 이어 고민했다.
//
//  우선 seq[i]에서 시작하는 부분 증가 수열이 있다면, seq[i] 다음으로 오는 수에는 다음과 같은 조건이 붙는다.
//  1) 다음에 오는 수의 수열 상 인덱스를 k라고 한다면 다음에 오는 수 = seq[k].
//  2) k > i
//  3) seq[k] > seq[i]
//  조건을 파악했으니 어떤 것을 시작으로 수열을 탐색해나갈 것인지에 대한 고민을 했다.
//  아이디어 자체가 seq[i]에 seq[i+1]부터 시작하는 부분 증가 수열을 붙이는 방식이니 수열의 끝에서 앞으로 오는 재귀적 구현이 적합하다고 판단했다.
//  그리고 중복되는 부분문제의 해답에 해당하는 'seq[k]부터 시작하는 LIS의 길이'를 캐싱하는 것이 적합하다고 생각했다.
//  그렇다면 dp[k] = seq[k(0 이상 N 미만)] 에서 시작하는 LIS의 길이로 생각해볼 수 있을 것이다.
//  만약 k가 N-1(수열의 끝)이라면 k부터 시작하는 LIS 길이는 1이므로 dp[N-1] = 1일 것이다.
//  그리고 N-1 보다 작은 k에 대해서,
//  dp[k]는 k 이후의 인덱스 위치 j에 대해 seq[k] < seq[j]인 것을 순차 탐색하며 조건을 만족하는 j 중 dp[j]가 가장 큰 것과 1을 더해주면 된다.
//  쉽게 말해, 수열의 k 번째 이후 숫자 중 'k 번째 숫자보다 큰 수에서 시작하는 부분 증가 수열의 길이'를 찾고 그 중 가장 큰 것을 선택하면
//  k 번째 숫자로 시작해 만들 수 있는 가장 큰 LIS라는 것이다.
//
//  가령, 60 20 10 30 40 70 90 80 40 이라는 수열이 있다고 가정하자.
//  40으로 시작하는 LIS 길이는 1이다. 수열의 끝이기 때문이다.
//  80으로 시작하는 LIS 는 80이후에 80보다 큰 수에서 시작하는 부분 증가 수열의 길이 중 가장 큰 것과 1을 더한 값이다. - dp[N-1] = 1
//  하지만 80보다 작은 수가 수열의 남은 부분에 존재하지 않으므로 dp[N-2] = 0 + 1 = 1이다.
//  90도 마찬가지이므로 dp[N-3] = 1이다.
//  그리고 70으로 시작하는 LIS의 길이를 파악하기 위해 70 뒤에 붙을 수 있는 수를 70의 위치 뒤로 찾아본다. - 80, 90
//  따라서 dp[N-4] = (dp[N-3], dp[N-2] 중 큰 것) + 1 = 2가 된다. N-1 번째인 40은 70보다 작으므로 후보군에 속하지 않는다.
//  이를 재귀함수 의사코드로 표현해보면 다음과 같다.
// 
//  func(int 시작인덱스)
//      // Base Case : 수열 마지막 수에서 시작하는 LIS 의 길이
//      if(시작인덱스 == N-1){return dp[시작인덱스] = 1;}
//      // 나머지 시작 위치에서의 LIS 길이
//      else{ 
//          if(dp[시작인덱스] == 0){ // Case I : 아직 해당 위치에서 시작하는 LIS의 길이가 계산되지 않은 경우
//              int nextLIS = 0; // 시작 인덱스 위치의 수(seq[시작인덱스]) 뒤에 붙을 수 있는 LIS 길이
//              for(int i = 시작인덱스 + 1; i < N; i++){
//                  int current = 0; i 위치(시작인덱스의 뒤부분)에서 시작하는 LIS 길이를 담을 변수
//                  if(seq[i] > seq[시작인덱스])
//                      current = func(i); // 시작 인덱스 위치의 수보다 큰 수에서 시작하는 LIS의 길이 담기.
//                      if(current > nextLIS){nextLIS = current;} // 가장 긴 LIS 수로 갱신
//              }
//              dp[시작인덱스] = nextLIS + 1; // 시작 인덱스 위치의 수부터 시작하는 LIS 길이
//          }
//          return dp[시작인덱스] // Case I, II 공통 : 인자로 주어진 인덱스 위치의 수에서 시작하는 LIS 길이 반환
//      }
//      


public class Main{
    static int[] dp; // dp[i] = The length of LIS starting from 'i'
    static int[] seq; // Sequence
    static int N; // Sequence Size

    // dp[startIdx] = startIdx에서 시작하는 LIS의 길이를 반환.
    // 끝에서부터 처리되어 거꾸로 올라오는 구조.
    static int func(int startIdx){
        // Base Case : 수열의 끝 숫자로 만들 수 있는 LIS 길이는 1.
        if(startIdx == N-1){return dp[startIdx] = 1;}
        // Base Case : dp[startIdx] == 0일때. 아직 구하지 않았다는 뜻이므로 구한다.
        // 아닐 경우 메모이제이션을 활용할 수 있으므로 아래의 절차를 활용하지 않고 바로 스킵.
        if(dp[startIdx] == 0){
            // seq[startIdx] 다음에 올 수 있는 수로부터 시작하는 LIS 중 가장 긴 것.
            // 만약 startIdx 이후의 수열의 모든 숫자가 전부 seq[startIdx] 보다 작을 경우 0 그대로 반환. LIS가 startIdx에서 끝이라는 뜻이기 때문.
            int nextLIS = 0;
            for(int i = startIdx + 1; i < N; i++){
                int current = 0; // seq[i]로 시작하는 LIS 길이
                if(seq[i] > seq[startIdx]){
                    current = func(i);
                    // startIdx 다음으로 올 수 있는 가능한 모든 수로부터 시작하는 LIS 중 최댓값 갱신하기.
                    if(current > nextLIS){nextLIS = current;}
                }
            }
            dp[startIdx] = nextLIS + 1;
        }
        return dp[startIdx];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        dp = new int[N]; // 메모이제이션 배열 초기화
        seq = new int[N]; // 입력 수열을 담을 배열 초기화
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            seq[i] = Integer.parseInt(st.nextToken());
        }

        int max = 0; // 모든 시작 위치에서의 LIS 중 가장 큰 것의 길이.
        for(int j = 0; j < N; j++){
            int LISfromCurrent = func(j); // seq[j]에서 시작하는 LIS 길이
            if(LISfromCurrent > max){max = LISfromCurrent;} // 최댓값 갱신
        }

        System.out.print(max);
    }
}
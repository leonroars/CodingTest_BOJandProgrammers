import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/*
 0. 풀이 아이디어 : 나머지 연산의 성질 + 구간 합
    1) 나머지 연산의 성질
       - A, B가 모두 피제수 M으로 나누어 떨어진다면 (A + B) % M == 0 이다.
       - A, B를 M으로 나눈 나머지를 각각 p, q라고 할 때, p + q = M 이면 (A + B) % M == 0 이다.
         마찬가지로 p - q = 0이라면 |A - B| % M == 0 이다.
    2) 구간합
       - A1 ~ An 수열이 존재하고, 1 이상 n 이하의 i, j에 대해 Ai~Aj까지의 구간합을 sum(i, j)라고 하자.
         이때 sum(i, j) = sum(1, n) - sum(1, i - 1) - (sum(1, n) - sum(1, j))
                       = sum(1, j) - sum(1, i - 1)이다.
    위의 두 가지를 조합해보면 문제에서 요구하는 "연속하는 부분 구간의 합 % M == 0"은 다음의 두 가지로 생각해볼 수 있다.
    
 1. 풀이 설계
  - 입력을 받으며 sum(1, n)을 계산한다. 이때 이를 저장하는 배열 sum[N + 1] 을 초기화한다.
    : sum[p] = 1~p번째 수까지의 구간합
    동시에 n보다 작은 자연수 k에 대해 sum(1, k)도 함께 계산하여 sum[k]에 저장한다. 
    이때 나머지 연산의 성질을 활용하여 계산 결과가 Overflow 하지 않도록 나머지 연산을 한 결과를 저장한다.
    => O(n)
  - 앞서 i~j로 이루어진 구간합 sum(i, j)는 sum(j) - sum(i - 1)로 구할 수 있음을 이야기했다.
    즉, (sum(i, j) % M) = (sum(j) % M) - (sum(i-1) % M) 이다.
    우리가 찾는 부분 구간합은 sum(i, j) % M == 0이 되도록 하는 구간의 수 이다.
    이 말은 즉, (sum(j) % M) ==  (sum(i - 1) % M) 을 만족하는 (i, j)를 찾는 것이다!
    그렇기 때문에 별도의 배열 mod[M]을 초기화한 후,
    위에서 입력 받고 저장하는 과정에서 나머지 연산한 결과를 확인하고
    나머지가 m인 sum(1, p)의 수를 mod[m]에 저장한다.
    ex. sum(1, 3), sum(1, 6)이 각각 M으로 나눈 나머지가 1인 유일한 구간이라고 한다면,
        mod[1] = 2가 되는 것이다!
  - 발생 가능한 모든 나머지에 대해, 나머지가 같은 구간을 가리키는 인덱스 중 2가지를 순서 없이 뽑는 경우의 수의 합이,
    바로 문제에서 요구하는 해답이다. 
 */


public class Main {
    static int N; // 수열의 길이 (1 <= N <= 100,000)
    static int M; // 피제수 M (2 <= M <= 1,000)
    static long[] sum; // sum[p] = A1~Ap까지의 구간 합.
    static long[] mod; // mod[k] = sum[]의 원소 중 M으로 나눈 나머지가 k인 것의 갯수.
    static long accum;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        sum = new long[N + 1];
        mod = new long[M];
        
        // 1. 입력
        for(int i = 1; i < N + 1; i++){
            int current = Integer.parseInt(st.nextToken());
            sum[i] = sum[i - 1] + current;
            int modulo = (int)(sum[i] % M);
            if(modulo == 0){accum++;} // 나머지가 이미 0인 경우 추가.
            mod[modulo]++; // 나머지가 같은 '구간의 오른쪽 끝 인덱스' 수 갱신.
        }
        
        // 2. 연산
        for(int j = 0; j < M; j++){
            if(mod[j] > 1){
                accum += (mod[j] * (mod[j] - 1) / 2);
            }
        }
        
        System.out.print(accum);
    }
}
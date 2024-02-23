import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

// 다사다난한 시도 끝에 푼 풀이 및 접근에 대한 주석이다.

// 1. 주먹구구식 브루트포스 풀이 - 실패
//    문제 풀 때 가장 안 좋은 습관은 문제를 제대로 읽지 않고 급한 마음에 바로
//    입출력 예시를 보고 문제의 요구조건과 생김새를 유추해서 푸는 것이라고 생각한다.
//    첫 번째 시도에 그런 실수를 저질렀다.
//    입출력에 분명 규칙이 있다고 생각했고 입출력 예시로 주어진 것을 보고
//    규칙을 수식화하여 접근하였다.
//    테스트케이스 중 일부는 맞았지만 일부는 어떻게 해도 답을 도출할 수 없었다.
//    이렇게 풀어버리면 예시로 주어진 테스트케이스에 맞춰 풀이를 구부러뜨리는 것이다.
//    잠시 시간을 갖고 다시 시도해보기로한다.
//
// 2. 분할정복 - 성공
//    분할정복의 핵심은 '대등한 크기를 갖는 부분문제로의 분할'만으로는 설명이 충분히 되지 않는다.
//    1) 분할 후 발생한 조각 중 어느 것을 선택할 것인지에 관한 결정조건을 특정할 수 있어,
//       해답과 무관한 나머지 조각을 무시할 수 있게 하거나,
//    2) 분할로부터 발생한 한 조각의 연산결과로부터 나머지 조각의 결과를 도출해낼 수 있어,
//       결과적으로 나머지 조각을 알기 위한 추가 연산을 무시할 수 있게 하는 것에 분할정복의 핵심이 있다고 생각했다.
//    이러한 분할정복의 특성을 고려해보며 문제를 다시 살펴본 결과,
//    문제의 구조가 분할정복의 핵심에 잘 부합함을 발견할 수 있었다.

//    먼저 우리가 쉽게 파악할 수 있거나 계산할 수 있는 것을 추려본다.
//    1. 각 사분면의 좌상단 시작점이 몇 번째로 방문되는가? (좌상단 좌표의 방문 순번)
//       문제는 '2^N X 2^N' 크기의 타일(편의 상 표현)을 '2^N-1 X 2^N-1'로 분할하여 Z모양으로 방문한다고 하였다.
//       그렇다면 2^N X 2^N 사분면의 각 좌상단 시작점은 - 0, 0에서 시작시 - 
//       0, (0 + 2^N-1), (0 + (2^N-1)*2), (0 + (2^N-1)*3) 으로 표현된다.
//    2. 어떤 사분면을 선택해야 하는가?
//       어떤 위치 (r, c)가 어떤 사분면에 특정하는지 알기 위해선
//       r과 c 각각 2^N-1과의 대소비교 결과를 알아야한다.
//       이렇게 표현하면 선뜻 이해가 어렵지만,
//       r을 세로축, c를 가로축임을 다시 한 번 기억하며 다음의 그림을 보면 바로 이해가 될 것이라 생각한다.
//       
//       가로 0---------2^(N-1)---------(2^N)-1
//       세로             |               |
//        0              |               |
//        |              |               |
//        |              |               |
//      2^(N-1)----------|---------------|
//        |              |               | 
//        |              |               |
//      (2^N)-1----------|---------------|
//       
//
//    다음으로 지금까지 살펴본 '파악할 수 있는 정보'를 바탕으로
//    문제를 어떻게 풀어나갈지에 대한 아이디어를 개략적 흐름대로 펼쳐보면 다음과 같다.
//    0. r과 c가 시작점과 일치하면 
//    1. 최고 좌상단 좌표가 (0, 0)인 크기 2^N X 2^N 크기의 2차원 공간이 입력으로 주어진다.
//    2. 우리가 찾아가야할 곳은 r행 c열 위치이며, 우리가 알아내고싶은 것은 해당 위치가 방문되는 순번이다.
//    3. 우선 r행 c열이 0,0을 시작으로 하는 2^N X 2^N 공간의 몇 사분면에 속하는지 특정한다.
//    4. 특정된 사분면의 좌상단 좌표와 방문 순번을 계산한다.
//    5. 특정된 사분면의 좌상단 좌표로부터 2^(N-1) X 2^(N-1) 공간에 대해 위의 3-5과정을 반복한다.
//    6. 만약 (r, c)를 만나면 방문 순번을 출력한다.
//    7. 만약 N = 1이 된 순간까지도 (r, c)를 만나지 못한다면,
//       N이 1이 된 시점의 좌상단 시작좌표로부터 Z모양으로 총 2X2공간의 타일을 순차 방문하며 탐색한다.
// 
//    이 아이디어가 분할정복 패러다임에 속하는 이유는,
//    사분면 특정을 통해 다른 세 개의 사분면을 무시하게 그들에 대한 탐색을 진행하지 않기 때문이다.
//    앞서 설명한 분할정복의 핵심에 부합한다.
//    
//    시간복잡도 분석을 개략적으로 진행해보자.
//    사분면 특정과 새로운 시작좌상단 좌표 특정이 각각 상수 시간 복잡도에 계산이 가능하고,
//    매 함수 호출 당 탐색 요구 입력의 크기가 1/4로 줄어든다.
//    N=1일 때 총 4회의 순차탐색을 진행하나, 지배적이지 않으므로 시간복잡도 표기에선 무시할 수 있다.
//    최악의 시나리오는 r,c가 ( (2^N)-1), (2^N)-1) ) 의 위치인 경우이다.
//    밑이 4인 log42^N 번의 함수호출과 마지막 4번의 순차탐색까지 모두 요구하기 때문이다.

public class Main{

    static class Z {
        private static int[] cMove = new int[]{1, -1, 1};
        private static int[] rMove = new int[]{0, 1, 0};

        private static int r;
        private static int c;

        public Z(int r, int c){
            this.r = r;
            this.c = c;
        }

        static int solution(int rStart, int cStart, int conorstone, int N){
            // conorstone = 좌상단(rStart, cStart)의 방문순번
            // Base-Case : 함수 호출 시 주어진 시작위치가 바로 찾던 위치일때.
            if(rStart == r && cStart == c){
                return conorstone;
            }
            // Base-Case II: N = 1. 찾고자 하는 r행 c열을 포함한 2*2 타일까지 좁힌 후 시작점으로부터 문제에 정의된 움직임대로 움직이며 순차탐색.
            // 최대 네 번의 탐색을 실시한다.
            if(N == 1){
                int rNext = rStart;
                int cNext = cStart;
                if(rNext == r && cNext == c){return conorstone;}
                for(int i = 0; i < 3; i++){
                    cNext += cMove[i]; // 열 이동(가로 방향 이동)
                    rNext += rMove[i]; // 행 이동(세로 방향 이동)
                    conorstone ++;
                    if(rNext == r && cNext == c){break;}
                }
                return conorstone;
            }
            // (r,c)가 위치한 사분면의 좌측 최상단 지점 좌표.
            int rNewStart = (r >= rStart + (1 << N-1)) ? rStart + (1 << N-1) : rStart;
            int cNewStart = (c >= cStart + (1 << N-1)) ? cStart + (1 << N-1) : cStart;

            // r행 c열이 1사분면 혹은 3사분면에 존재하는 경우
            if(cNewStart < (cStart + (1 << N - 1))){
                // 3사분면 탐색
                if(rNewStart >= rStart + (1 << N-1)){
                    return solution(rNewStart, cNewStart, conorstone + ((1 << ((2 * N) - 2)) * 2), N-1);
                }
                // 1사분면 탐색
                else {
                    return solution(rNewStart, cNewStart, conorstone, N-1);
                }
            }

            // r행 c열이 2사분면 혹은 4사분면에 존재하는 경우
            // 2사분면 탐색
            if(rNewStart < rStart + (1 << N - 1)){return solution(rNewStart, cNewStart, conorstone + (1 << ((2 * N) - 2)), N-1);}

            // 4사분면 탐색
            return solution(rNewStart, cNewStart, conorstone + ((1 << ((2 * N) - 2)) * 3), N - 1);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        Z board = new Z(r, c);
        int result = board.solution(0, 0, 0, N);
        System.out.println(result);
    }
}
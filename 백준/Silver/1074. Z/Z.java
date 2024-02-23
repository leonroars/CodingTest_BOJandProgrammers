import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

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
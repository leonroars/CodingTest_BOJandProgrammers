import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/*
 0. 풀이 아이디어 : 최적의 목표 높이 찾기 + 고르기 두 개로 나누어 접근.
   *** 왜 Bruteforce를 떠올려야하는가?
   *** 어째서 Bruteforce가 유효한가?
 */


public class Main {
    static int N; // 세로
    static int M; // 가로
    static int B; // 시작시 인벤토리 내 단위 블록 갯수
    static int[][] area;

    static int lowest = Integer.MAX_VALUE; // 최저 높이
    static int highest = -Integer.MAX_VALUE; // 최고 높이
    static int time = -Integer.MAX_VALUE;
    static int H = -Integer.MAX_VALUE;
    static StringBuilder answer = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        area = new int[N][M];

        // 0. 입력 : O(N * M) _ 개략적으로 250,000번의 연산.
        for (int row = 0; row < N; row++) {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < M; col++) {
                int current = Integer.parseInt(st.nextToken());
                // 갱신
                if (current < lowest) {
                    lowest = current;
                }
                if (current > highest) {
                    highest = current;
                }
                area[row][col] = current;
            }
        }

        // 1. 풀이 : lowest~highest까지 높이를 설정해보며 어느 높이일때가 최선인지 찾아보기.
        for (int height = lowest; height < highest + 1; height++) {
            int lowerDiffSum = 0;
            int lowerDiffCnt = 0;
            int higherDiffSum = 0;
            int higherDiffCnt = 0;

            int currentTime = 0;

            for (int row = 0; row < N; row++) {
                for (int col = 0; col < M; col++) {
                    if (area[row][col] < height) {
                        lowerDiffSum += (area[row][col] - height);
                        lowerDiffCnt++;
                    } else {
                        higherDiffSum += area[row][col] - height;
                        higherDiffCnt++;
                    }
                }
            }

            // Case I : 가지고 있는 블럭 B로 모자란 부분 채울 수 있는 경우 : 초과분만 깎기
            if (lowerDiffSum + B >= 0) {
                currentTime = Math.abs(lowerDiffSum) + (2 * higherDiffSum);
            }
            // Case II : 충당 안되는 경우 -> 모자란 만큼 초과분에서 떼어 와야한다!
            else {
                int mustBeFilled = Math.abs(lowerDiffSum + B); // 더 채워야하는 부분
                // Case II-i : 현재 높이 성립 가능
                if(higherDiffSum - mustBeFilled >= 0){
                    currentTime = (B + (3 * Math.abs(lowerDiffSum + B))) + (2 * (higherDiffSum - mustBeFilled));
                }
                // Case II-ii : 현재 높이 성립 불가.
                else {
                    continue;
                }
            }

            if (currentTime <= time || time == -Integer.MAX_VALUE) {
                time = currentTime;
                if (height > H || H == -Integer.MAX_VALUE) {
                    H = height;
                }
            }
        }

        answer.append(time);
        answer.append(" ");
        answer.append(H);
        System.out.print(answer.toString());
    }
}
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int[][] nums; // 층 별 숫자를 이차원 배열로 구조화.
    //최대 500 * 500 = 2,500 개의 칸을 가진 이차원 배열. 약 11KB 정도로 추정.
    static int[][] dp; // "i번째 층 j번째 수에 도달가능한 경로 중 가장 큰 것"을 저장하는 메모이제이션 배열
    static int N;

    static void addNum(String floor, int floorNum){
        StringTokenizer st = new StringTokenizer(floor);
        for(int i = 0; i < floorNum + 1; i++){
            int currentNum = Integer.parseInt(st.nextToken());
            nums[floorNum][i] = currentNum;
        }
    }

    static int findMax(){
        // i = 층
        for(int i = 0; i < N; i++){
            // j = i층의 숫자 인덱스.
            for(int j = 0; j < i + 1; j++){
                int current;
                if(i == 0){current = nums[i][j];}
                else{
                    if(j == 0){current = dp[i-1][j] + nums[i][j];}
                    else if(j == i){current = dp[i-1][j-1] + nums[i][j];}
                    else{current = Math.max(dp[i-1][j-1] + nums[i][j], dp[i-1][j] + nums[i][j]);}
                }

                if(dp[i][j] == 0 || current > dp[i][j]){dp[i][j] = current;}
            }
        }
        int biggest = -Integer.MAX_VALUE;
        for(int k = 0; k < N; k++){
            if(dp[N-1][k] > biggest){biggest = dp[N-1][k];}
        }

        return biggest;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        nums = new int[N][N];
        dp = new int[N][N];

        for(int i = 0; i < N; i++){
            String floor = br.readLine();
            addNum(floor, i);
        }

        System.out.print(findMax());
    }
}
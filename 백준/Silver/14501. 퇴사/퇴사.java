import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[][] counselling;
    static int max = -Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        counselling = new int[N + 1][2];
        
        // 0. 입력 받기.
        for(int i = 1; i < N + 1; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int T = Integer.parseInt(st.nextToken()); // 소요 기간
            int P = Integer.parseInt(st.nextToken()); // 상담 비용
            counselling[i][0] = T;
            counselling[i][1] = P;
        }
        
        // 1. 가능한 모든 상담 조합과 그에 따른 보수 탐색 시행
        findBest(1, 0);
        
        
        // 2. 출력
        System.out.print(max);
        
    }
    
    private static void findBest(int possibleStart, int accumPaid){
        
        for(int day = possibleStart; day < N + 1; day++){
            if(day + counselling[day][0] < N + 2){
                int nextPossibleStart = day + counselling[day][0];
                findBest(nextPossibleStart, accumPaid + counselling[day][1]);
            }
        }
        
        // 조건에 부합하는 상담이 더 이상 존재하지 않을 시 for 문 빠져나와 현 라인 도달. 이때 갱신.
        if(accumPaid > max){max = accumPaid;}
    }
}
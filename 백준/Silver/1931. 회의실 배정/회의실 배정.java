import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main{
    
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[][] meetings = new int[N][2];
        int max = 0;
        
        // 1. 입력 받기 : O(N)
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            
            meetings[i][0] = start;
            meetings[i][1] = end;
        }
        
        // 2. 종료시간 기준 오름차순 정렬. 같을 경우 시작시간 오름차순 정렬. O(N log N)
        Arrays.sort(meetings, (a1, a2) -> {
            if(a1[1] == a2[1]){return a1[0] - a2[0];}
            return a1[1] - a2[1];
        });
        
        int previousEnd = -1; // 가능한 회의의 시작시간이 0 이상이므로.
        // 3. 전체 회의를 탐색하며 회의 시작 시간이 현재 가능한 시작 시간과 같거나 보다 늦은 회의들 중
        // 종료시간이 가장 빠른 회의를 선택.
        for(int locator = 0; locator < N;){
            // 조건 만족 -> 선택 및 갱신 후 넘어가기.
            if(meetings[locator][0] >= previousEnd){
                previousEnd = meetings[locator][1];
                max++;
                locator++;
            }
            // 조건 불만족 : loc
            else {
                locator++;
            }
        }

        System.out.print(max);
        
    }
}
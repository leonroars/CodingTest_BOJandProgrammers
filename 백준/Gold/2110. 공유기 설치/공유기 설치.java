import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()); // 집의 수
        int C = Integer.parseInt(st.nextToken()); // 공유기 수
        int[] houses = new int[N];
        
        for(int house = 0; house < N; house++){
            houses[house] = Integer.parseInt(br.readLine());
        }
        
        // 정렬 : O(n log n)
        Arrays.sort(houses);
        
        // Parametric Search : '인접한 두 공유기 간 거리가 K 일때, C 개의 공유기를 놓을 수 있는가?'
        int maxDist = parametricSearchForMaxDist(houses, C);
        
        // 정답 출력.
        System.out.print(maxDist);
    }
    
    private static int parametricSearchForMaxDist(int[] houses, int C){
        // Binary Search 방식으로 K 설정 후, 이를 인자로 결정문제 호출하기.
        // 결정문제의 결과가 T/F 이므로, T/F 경계를 찾아야 한다.
        // K의 탐색 범위는 T/F 가 분명하게 나뉘어야 하므로 (1, (가장 먼 집 좌표 - 가장 가까운 집 좌표))로 한다.
        int low = 1;
        int high = houses[houses.length - 1] - houses[0]; // 최대 10억.
        int maxDist = -Integer.MAX_VALUE;
        
        while(low <= high){
            int mid = (high + low) / 2;
            
            if(isPossibleWithDist(houses, mid, C)){
                maxDist = Math.max(maxDist, mid);
                low = mid + 1;
            }
            else {
                high = mid - 1;
            }
        }
        
        return maxDist;
    }
    
    private static boolean isPossibleWithDist(int[] houses, int maxDistBetweenTwoRouters, int numberOfRouters){
        // houses[0] 부터 최소 maxDistBetweenTwoRouters 거리 만큼의 간격을 두고 공유기 배치.
        // houses[0] 에는 놓았다고 가정.
        int cnt = numberOfRouters - 1;
        int houseLocator = 1;
        int prevRouterLocation = houses[0];
        
        while(cnt > 0 && houseLocator < houses.length){
            int currentHouseLocation = houses[houseLocator];
            int currentDistBetweenRouter = currentHouseLocation - prevRouterLocation;
            
            // Case I : maxDistBetweenTwoRouters 규칙 준수 가능한 집이라면? -> 배치
            if(currentDistBetweenRouter >= maxDistBetweenTwoRouters){
                prevRouterLocation = currentHouseLocation;
                cnt--;
            }
            
            houseLocator++;
        }
        
        return cnt == 0;
    }
}
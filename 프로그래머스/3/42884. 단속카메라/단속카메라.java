import java.util.Arrays;

class Solution {
    public int solution(int[][] routes) {
        
        // 경로 종료 지점 좌표 오름차순 정렬
        Arrays.sort(routes, (r1, r2) -> {
            if(r1[1] == r2[1]){return Integer.compare(r1[0], r2[0]);}
            return Integer.compare(r1[1], r2[1]);
        });
        
        // routes 순회하며 카메라 설치 지점 고르기.
        int locator = 0;
        int prevCamLocation = routes[locator][1];
        int cameraCnt = 1;
        
        while(locator < routes.length){
            if(routes[locator][0] <= prevCamLocation){locator++;}
            else {
                prevCamLocation = routes[locator][1];
                cameraCnt++;
                locator++;
            }
        }
        
        return cameraCnt;
    }
}
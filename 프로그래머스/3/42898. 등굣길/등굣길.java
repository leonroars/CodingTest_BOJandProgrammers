import java.util.*;

// 풀이 및 접근
// 어릴 때 수학책에서 풀었던 최단경로 개수 문제랑 정확히 동일했다.
// '최단경로의 갯수'가 필요하지, '최단경로'를 알 필요가 없었다.
// 처음에 문제 접근을 잘못했다...


class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int[][] dp = new int[n+1][m+1];
        boolean[][] puddleLocation = new boolean[n+1][m+1];
        int[][] shortest = new int[n+1][m+1];
        
        for(int i = 0; i < puddles.length; i++){
            int puddleX = puddles[i][0];
            int puddleY = puddles[i][1];
            puddleLocation[puddleY][puddleX] = true;
        }
        
        for(int j = 1; j < n + 1; j++){
            for(int k = 1; k < m + 1; k++){
                if(!puddleLocation[j][k]){
                    if(j == 1 && k == 1){shortest[j][k] = 1;}
                    else if(j == 1){shortest[j][k] = shortest[j][k-1] % 1000000007;}
                    else if(k == 1){shortest[j][k] = shortest[j-1][k] % 1000000007;}
                    else{shortest[j][k] = (shortest[j-1][k] + shortest[j][k-1]) % 1000000007;}
                } else {shortest[j][k] = 0;}
            }
        }
        return shortest[n][m];
    }
}
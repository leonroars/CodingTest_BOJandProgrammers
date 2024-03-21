class Solution {
    public int solution(int[][] triangle) {
        int[][] dp = new int[triangle.length][triangle.length];
        
        int max = -Integer.MAX_VALUE;
        for(int i = 0; i < triangle.length; i++){
            for(int j = 0; j < triangle[i].length; j++){
                if(i == 0){dp[i][j] = triangle[i][j];}
                else {
                    // Case I : 왼쪽 끝
                    if(j == 0){dp[i][j] = dp[i-1][j] + triangle[i][j];}
                    // Case II : 오른쪽 끝
                    else if(j == (triangle[i].length - 1)){dp[i][j] = dp[i-1][j-1] + triangle[i][j];}
                    // Case II : 이 외, j번째 숫자로 도달하는 경우는 이전 층의 j-1/j로부터 출발하는 경우이다.
                    //           이 중 최대값을 저장해준다.
                    else{dp[i][j] = Math.max(dp[i-1][j-1] + triangle[i][j], dp[i-1][j] + triangle[i][j]);}
                }
                if(dp[i][j] > max){max = dp[i][j];}
            }
        }
        
        return max;
    }
}
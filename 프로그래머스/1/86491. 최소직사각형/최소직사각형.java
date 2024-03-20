class Solution {
    public int solution(int[][] sizes) {
        // 마찬가지. 풀이가 지나치게 지저분해진다면 잘못된 접근을 하고 있는 것.
        // 두 길이 중 긴 쪽을 너비라고 가정하고 짧은 쪽을 높이라고 가정한다.
        // 이는 종만북에서 말한 '문제에 명시되어 있진 않지만 풀이를 용이하게 하기 위해 조건을 추가하는 것'이다.
        // 이렇게 제약한다면 너비는 명함들의 너비 중 최고 길이, 높이는 명함들의 높이 중 최고 길이를 구하면 된다.
        // 이를 위한 완전탐색이지, 하나씩 다 돌려보라고 있는 완전탐색이 아니다...
        int maxH = 0;
        int maxW = 0;
        
        for(int i = 0; i < sizes.length; i++){
            int currentW = sizes[i][0];
            int currentH = sizes[i][1];
            
            // 높이 결정 : 지금까지의 최대 높이 vs (현재 명함의 너비/높이 중 긴 것) 비교 후 큰 쪽으로 갱신
            maxH = Math.max(maxH, Math.max(currentW, currentH));
            // 너비 결정 : 지금까지의 최대 너비 vs (현재 명함의 너비/높이 중 짧은 것) 비교 후 큰 쪽으로 갱신
            maxW = Math.max(maxW, Math.min(currentW, currentH));
        }
        
        return maxH*maxW;
    }
}
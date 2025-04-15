import java.util.Arrays;

class Solution {
    public int solution(int[] d, int budget) {
        Arrays.sort(d); // 오름차순 정렬
        int accum = 0;
        int cnt = 0;
        
        for(int i = 0; i < d.length; i++){
            int temp = accum + d[i];
            if(temp > budget){break;}
            accum = temp;
            cnt++;
        }
        
        return cnt;
    }
}
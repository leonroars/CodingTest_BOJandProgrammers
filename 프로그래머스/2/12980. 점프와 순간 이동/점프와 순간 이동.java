import java.util.*;

public class Solution {
    
    public int solution(int n) {
        
        return findOptimalMove(n);
    }
    
    // 재귀가 효율성 테스트에서 통과하지 않으므로 bottom-up 방식을 구현하도록 하자!
    // 여전히 효율성에서 땡. 하긴 10억개니까... 지금은 O(n)이니까.. 이걸 O(log n)으로 떨어뜨릴 방법이 있을까?
    // 다시 Top-down. 다만 이번엔 아예 처음부터 경로를 확정해서 0을 만나는 순간 끝나도록.
    private int findOptimalMove(int end){
        int current = end;
        int batteryUsage = 0;
        while(current > 0){
            if(current % 2 == 0){current = current / 2;}
            else {current = (current - 1) / 2; batteryUsage++;}
        }
        
        return batteryUsage;
    }
}
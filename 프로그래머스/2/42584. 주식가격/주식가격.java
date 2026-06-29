import java.util.Stack;

class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        Stack<Integer> s = new Stack<>();
        
        for(int i = 0; i < prices.length; i++){
            if(s.size() == 0 || prices[s.peek()] <= prices[i]){
                s.push(i);
            }
            else {
                while(!s.isEmpty() && prices[s.peek()] > prices[i]) {
                    int popped = s.pop();
                    answer[popped] = i - popped;
                }
                s.push(i);
            }
        }
        
        // 스택이 비어있는 경우 -> Monotically Increasing 한 원소들로만 채워진 Stack
        // -> Stack 의 후입선출 원칙을 생각해보면, stack 에서 먼저 뽑는 원소는 나중에 뽑는 원소보다 더 이후 시간대임이 보장됨.
        // 이를 활용하면, stack 내에 존재하는 모든 시간대의 비하락 구간 길이를 구할 수 있음.
        while(s.size() > 0){
            int popped = s.pop();
            answer[popped] = (prices.length - 1) - popped;
        }
        
        return answer;
    }
}

import java.util.*;

public class Solution {
    public int[] solution(int []arr) {
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for(int i = 0; i < arr.length; i++){
            if(queue.isEmpty() || queue.peekLast() != arr[i]){queue.addLast(arr[i]);}
        }
        
        int[] answer = new int[queue.size()];
        for(int j = 0; j < answer.length; j++){
            answer[j] = queue.removeFirst();
        }
        
        return answer;
    }
}
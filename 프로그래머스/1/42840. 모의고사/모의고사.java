import java.util.*;

// 만약 풀이가 지나치게 지저분해진다면, 다른 방식으로 접근하라는 뜻. 반드시 기억할 것!
class Solution {
    public int[] solution(int[] answers) {
        int[] first = {1, 2, 3, 4, 5};
        int[] second = {2, 1, 2, 3, 2, 4, 2, 5};
        int[] third = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
        
        int firstCnt = 0;
        int secondCnt = 0;
        int thirdCnt = 0;
        
        for(int i = 0; i < answers.length; i++){
            if(answers[i] == first[i % 5]){firstCnt++;}
            if(answers[i] == second[i % 8]){secondCnt++;}
            if(answers[i] == third[i % 10]){thirdCnt++;}
        }
        
        int[] cnts = {firstCnt, secondCnt, thirdCnt};
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(1);
        for(int i = 1; i < 3; i++){
            if(cnts[i] > cnts[q.peekLast() - 1]){q.clear(); q.addLast(i+1);}
            else if(cnts[i] == cnts[q.peekLast() - 1]){q.addLast(i+1);}
        }
        
        int[] result = new int[q.size()];
        for(int j = 0; j < result.length; j++){
            result[j] = q.removeFirst();
        }
        
        return result;
    }
}
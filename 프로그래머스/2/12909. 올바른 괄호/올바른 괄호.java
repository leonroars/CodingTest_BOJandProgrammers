import java.util.*;

class Solution {
    boolean solution(String s) {
        
        boolean answer = true;
        
        Stack<Character> stack = new Stack<>();
        
        for(int i = 0; i < s.length(); i++){
            char current = s.charAt(i); // s의 i위치 문자
            if(stack.isEmpty() && current == ')'){answer = false; break;}
            else if(current == '('){stack.push(current);}
            else{stack.pop();}
        }
        
        if(answer && !stack.isEmpty()){answer = false;}

        return answer;
    }
}
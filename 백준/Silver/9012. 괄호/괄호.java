import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayDeque;

/*
 1. 입력 문자열을 받는다.
 2. 문자열을 구성하는 문자 각각에 대해 다음과 같은 작업을 수행한다.
   i) 문자 == '(' : 스택에 삽입
   ii) 문자 == ')' : 스택에 가장 마지막으로 삽입된 좌괄호 pop.
 3. 전체 문자열 탐색 후, Stack 이 비어있다면 VPS이다.
 
 <왜 Stack 인가?>
 - 어떤 좌괄호와 우괄호가 서로 짝을 짓고 있는지 알아야한다.
 - 괄호의 특성 상, 가장 먼저 등장한 우괄호와 짝을 이루는 좌괄호는 가장 마지막에 등장한 괄호이다.
 - 이러한 점에 주목했을 때, '좌괄호를 출현한 순서대로 넣고, 그와 반대로 뽑아서 비교하기'가 가능하도록 돕는 자료구조가 필요하다. 
 - 즉, 후입선출 자료구조를 활용하여 해결할 수 있다.

 */


public class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            String result = isVPS(br.readLine());
            answer.append(result);
            if(tc < TC - 1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    private static String isVPS(String s){
        ArrayDeque<Character> stack = new ArrayDeque<>();
        ArrayDeque<Character> rightParenthesisStack = new ArrayDeque<>();
        
        for(int idx = 0; idx < s.length(); idx++){
            char current = s.charAt(idx);
            
            if(current == '('){stack.push(current);}
            else if(current == ')'){
                if(!stack.isEmpty()){stack.pop();}
                else{rightParenthesisStack.push(current);}
            }
        }
        
        return (stack.isEmpty() && rightParenthesisStack.isEmpty()) ? ("YES") : ("NO");
    }
}
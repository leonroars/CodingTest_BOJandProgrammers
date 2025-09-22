import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

/*
 I. 풀이 아이디어 : <Stack 을 활용한 선형 시간 복잡도 알고리즘>
   - 가장 왼쪽의 탑부터 오른쪽으로 진행하며 순차 탐색한다.
   - 이때, 스택의 peek() 와 size()를 체크해가며 다음과 같은 조건에 따라 작동한다.
     i) 스택이 비어있는 경우 : 왼쪽에 받을 수 있는 탑이 없음을 의미한다. 따라서 0을 기록.
     ii) 스택이 비어있지 않은 경우 : stack.peek()와 current 를 비교한다.
      ii-1) stack.peek() > current
          - 그렇다면 current 의 신호를 stack.peek() 높이의 탑이 받을 수 있으므로 기록.
          - stack.push(current)
      ii-2) stack.peek() < current
          - while(!stack.isEmpty() && stack.peek() < current){
              * stack.pop(); // current 보다 낮으므로, current 이후의 탑의 신호는 current가 받게될 예정.
          }
          - if(!stack.isEmpty()){
              * stack.peek() 이 current의 신호를 받을 수 있으므로 기록.
          }
          - else {
              * current 의 신호를 받을 수 있는 탑이 없으므로 0.
              * stack.push(current);
          }
 */


public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        
        StringBuilder answer = new StringBuilder();
        int[] arr = new int[N];
        
        // 0. 입력 받기 : 수신 받는 탑의 idx 로 기록해야하므로 필요.
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        // 1. 스택을 활용한 알고리즘 적용 : O(N) _ stack 에는 idx 를 삽입함을 주의.
        for(int idx = 0; idx < N; idx++){
            int current = arr[idx];
            
            while(!stack.isEmpty() && arr[stack.peek()] < current){stack.pop();}
            
            if(stack.isEmpty()){answer.append(0);}
            else{answer.append(stack.peek() + 1);}
            
            answer.append(" ");
            stack.push(idx);
        }
        
        System.out.print(answer.toString().trim());
    }
}
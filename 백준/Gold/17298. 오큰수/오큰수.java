import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main {
    static int N;
    static Component[] arr;
    static int[] NGE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new Component[N]; // index-0
        NGE = new int[N];
        
        // 0. 입력 받기. : O(N)
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = new Component(Integer.parseInt(st.nextToken()), i);
        }
        
        // 1. NGE 찾기 : O(k * N)...?
        findNGE();
        
        // 2. 출력 : O(N)
        StringBuilder answer = new StringBuilder();
        for(int j = 0; j < N; j++){
            answer.append(NGE[j]);
            if(j < N-1){answer.append(" ");}
        }
        
        System.out.print(answer.toString());
    }
    
    private static void findNGE(){
        ArrayDeque<Component> stack = new ArrayDeque<>();
        int locator = N - 1;
        
        while(locator >= 0){
            // Case A : 스택이 비지 않은 경우 -> 스택 내의 원소 중 오큰수 찾기.
            if(!stack.isEmpty()){
                if(arr[locator].number < stack.peek().number){
                    NGE[locator] = stack.peek().number;
                    stack.push(arr[locator--]);
                }
                else {
                    stack.pop();
                }
            }
            
            // Case B : 스택이 빈 경우 - 오큰수 존재하지 않음
            else {
                NGE[locator] = -1;
                stack.push(arr[locator--]);
            }
        }
        
    }
    
    static class Component {
        int number;
        int index;
        
        public Component(int num, int idx){
            this.number = num;
            this.index = idx;
        }
    }
}
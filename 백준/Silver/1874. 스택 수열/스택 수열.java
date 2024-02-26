import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.EmptyStackException;

public class Main{
    static class Stack{
        private static int[] stack;
        static int top;
        private StringBuilder sb;

        Stack(int size){
            stack = new int[size];
            top = -1;
            sb = new StringBuilder();
        }

        public void push(int x){
            if(top == stack.length - 1){throw new IndexOutOfBoundsException();}
            stack[++top] = x;
            sb.append("+\n");
        }

        public int pop(){
            if(top == -1){throw new EmptyStackException();}
            int popped = stack[top];
            stack[top] = -1;
            top--;
            sb.append("-\n");

            return popped;
        }

        public int peek(){
            return stack[top];
        }

        public String answer(){
            return sb.toString();
        }

        public void answer(String str){
            sb = new StringBuilder();
            sb.append(str);
        }

        public boolean isEmpty(){
            return top == -1;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] seq = new int[N + 1];
        Stack stack = new Stack(N);
        int endLoc = 0;

        // Sequence Initialize.
        for(int i = 1; i < seq.length; i++){
            int current = Integer.parseInt(br.readLine());
            seq[i] = current;
        }

        int seqNum = 1;
        int compareNum = 1;
        boolean flag = true;

        // Case I : 1부터 N까지 증가시켜나가는 수가 N보다 아직 작거나 같을 때.
        while(compareNum <= N){
            if(compareNum <= seq[seqNum]){
                stack.push(compareNum);
                if(compareNum == seq[seqNum]){stack.pop(); seqNum++;}
                compareNum++;
            }
            else{
                if(stack.peek() == seq[seqNum]){stack.pop(); seqNum++;}
                else{flag = false; break;} // NO.
            }
        }

        // Case II : 1부터 N까지 증가시켜나가던 수가 N보다 커질 경우, 스택에 남은 값과 수열의 남은 값을 비교해야한다.
        while(!stack.isEmpty() && seqNum < seq.length){
            if(stack.peek() != seq[seqNum]){flag = false; break;}
            stack.pop();
            seqNum++;
        }

        String answer = (flag) ? stack.answer() : "NO";
        System.out.print(answer.strip());

    }
}
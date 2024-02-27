import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main{
    static class Postfix{
        private static String infix;
        private static StringBuilder postfix;
        private static ArrayDeque<Character> stack;

        public Postfix(String input){
            infix = input;
            stack = new ArrayDeque<>();
            postfix = new StringBuilder();

            for(int i = 0; i < infix.length(); i++){
                char current = infix.charAt(i);
                boolean isAlphabet = (int) current > 64 && (int) current < 91;
                // 알파벳이 아닌 경우
                if(!isAlphabet){
                    if(!stack.isEmpty()){
                        // +, -, *, /
                        if(current != '(' && current !=')'){
                            if(isHigher(current, stack.peekLast())){stack.addLast(current);}
                            else {
                                while(!stack.isEmpty() && !isHigher(current, stack.peekLast()) && stack.peekLast() != '('){
                                    char popped = stack.removeLast();
                                    postfix.append(popped);
                                }
                                stack.addLast(current);
                            }
                        } else {
                            if(current == '('){stack.addLast(current);}
                            else{
                                while(true){
                                    char popped = stack.removeLast();
                                    if(popped == '('){break;}
                                    else{postfix.append(popped);}
                                }
                            }
                        }
                    } else {stack.addLast(current);}
                }
                else {postfix.append(current);}
            }

            while(!stack.isEmpty()){postfix.append(stack.removeLast());}

        }

        // 연산자 우선순위 판별.
        public boolean isHigher(char AtHand, char AtTop){
            return (AtHand == '*' || AtHand == '/') && (AtTop == '+' || AtTop == '-');
        }

        public String toString(){return postfix.toString();}
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        Postfix postfix = new Postfix(input);
        System.out.print(postfix);
    }
}
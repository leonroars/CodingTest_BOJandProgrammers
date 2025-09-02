import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayDeque;

public class Main {
    static String given;
    static String bomb;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 0. 입력 받기
        given = br.readLine();
        bomb = br.readLine();
        
        // 1. 폭발 처리
        String result = explode();
        
        // 2. 결과 출력
        System.out.print(result);
        
    }
    
    private static String explode(){
        boolean isOn = false;
        StringBuilder result = new StringBuilder();
        ArrayDeque<Character> dq = new ArrayDeque<>(); // 경계 대상 저장 공간
        ArrayDeque<Character> stack = new ArrayDeque<>(); // 감시 대상 저장 공간
        
        int locator = 0;
        
        while(locator < given.length()){
            char current = given.charAt(locator);
            
            /* Step 1 : 문자 분류 로직 */
            // Case I : 현 문자가 폭탄 문자열 구성원인 경우.
            if(isPartOfBomb(current)){
                // 폭탄 문자열의 길이가 2 이상인 경우
                if(bomb.length() > 1){
                    // I-1 첫 글자인 경우 : switch on(끝문자를 만나기 전까지 모든 문자를 dq에 저장)
                    if(current == bomb.charAt(0)){
                        isOn = true;
                        dq.addLast(current);
                    }
                    // I-2 마지막 글자인 경우
                    else if(current == bomb.charAt(bomb.length() - 1)){
                        // 폭탄 글자 모으던 중인 경우(dq에 폭탄의 시작문자 있는 경우) : 만들어서 비교
                        if(isOn){
                            stack.push(current);
                            for(int cnt = 0; cnt < bomb.length() - 1; cnt++){
                                if(!dq.isEmpty()){
                                    char popped = dq.removeLast();
                                    stack.push(popped);
                                }
                            }
                            StringBuilder candidate = new StringBuilder();
                            while(!stack.isEmpty()){
                                candidate.append(stack.pop());
                            }
                            // 폭탄 아닌 경우 : dq 비우고 뒤에 candidate도 추가.
                            if(!candidate.toString().equals(bomb)){
                                while(!dq.isEmpty()){result.append(dq.removeFirst());}
                                result.append(candidate);
                            }
                            // 폭탄인 경우 : 아무 것도 안하는 것이 없애는 것.
                        }
                        // 폭탄글자 모으던 중 아닌 경우 -> 바로 result에 추가.
                        else {
                            result.append(current);
                        }
                    }
                    // I-3 폭탄문자의 중간글자인 경우
                    else {
                        if(isOn){dq.addLast(current);}
                        else {result.append(current);}
                    }
                }
            }
            
            // Case II : 현 문자가 폭탄 문자열 구성원이 '아닌' 경우
            else{
                // 폭탄문자 모으던 중인 경우 -> non-promising 하므로 dq flush!
                if(isOn){
                    while(!dq.isEmpty()){result.append(dq.removeFirst());}
                    isOn = false;
                }
                result.append(current);
            }
            
            /* Step 2 : 문자 locator 증가 */
            locator++;
        }
        
        while(!dq.isEmpty()){result.append(dq.removeFirst());}
        
        return (result.length() == 0) ? ("FRULA") : (result.toString());
    }
    
    private static boolean isPartOfBomb(char c){
        boolean isPart = false;
        for(int i = 0; i < bomb.length(); i++){
            if(bomb.charAt(i) == c){isPart = true; break;}
        }
        
        return isPart;
    }
}
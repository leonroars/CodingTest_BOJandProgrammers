import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayDeque;

/*

 - 태그 : < 부터 >까지 모두 큐에 삽입. 이후 > 만날 경우 flush 하여 문자열 생성 후 StringBuilder 에 추가.
 - 단어 : 그 외의 알파벳 소문자/숫자는 스택에 담고 공백 만날 경우 stack.pop()하여 flush. 이후 StringBuilder에 추가.

 */

public class Main {
    static ArrayDeque<Character> tag = new ArrayDeque<>();
    static ArrayDeque<Character> word = new ArrayDeque<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        StringBuilder result = new StringBuilder();
        boolean isTag = false;
        
        for(int idx = 0; idx < s.length(); idx++){
            char current = s.charAt(idx);
            
            // Case I : Tag 시작부
            if(current == '<'){
                if(!word.isEmpty()){result.append(generateReverseWord());}
                tag.offer(current);
                isTag = true;
            }
            // Case II : Tag 종결부
            else if(current == '>'){
                tag.offer(current);
                result.append(generateTag());
                isTag = false;
            }
            
            // Case III : 그 외의 경우(영소문자/숫자/공백)
            else {
                // Case III-A : 현재 태그 문자열 생성 중인 경우 -> 공백/영소문자 관계 없이 넣는다. 숫자인 경우엔 isTag==false 여야 한다.
                if(isTag){
                    tag.offer(current);
                }
                else{
                    // 공백인 경우 : 기존 단어 뒤집어 추가하고 새로운 단어 시작
                    if(current == ' '){
                        result.append(generateReverseWord());
                        result.append(current);
                    }
                    else {
                        word.push(current);
                        if(idx == s.length() - 1){result.append(generateReverseWord());}
                    }
                }
            }
        }
        
        System.out.print(result.toString());
    }
    
    private static String generateTag(){
        // 1. Deque의 앞부터 뽑아 태그 단어 생성
        StringBuilder tagWord = new StringBuilder();
        
        while(!tag.isEmpty()){
            char c = tag.poll();
            tagWord.append(c);
        }
        
        // 3. 결과 반환.
        return tagWord.toString();
    }
    
    private static String generateReverseWord(){
        // 1. Stack 에서 뽑아 단어 생성
        StringBuilder reversedWord = new StringBuilder();
        
        while(!word.isEmpty()){
            reversedWord.append(word.pop());
        }
        
        // 2. 반환
        return reversedWord.toString();
    }
}

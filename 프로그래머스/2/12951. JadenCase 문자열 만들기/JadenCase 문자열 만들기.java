class Solution {
    public String solution(String s) {
        StringBuilder answer = new StringBuilder();
        
        String wordAccum = "";
        
        for(int i = 0; i < s.length(); i++){
            char current = s.charAt(i);
            if(current == ' '){
                if(!wordAccum.isBlank()){
                    answer.append(toJadenCase(wordAccum));
                    wordAccum = "";
                }
                answer.append(Character.toString(current));
            }
            else {
                wordAccum += current;
            }
        }
        
        if(!wordAccum.isBlank()){answer.append(toJadenCase(wordAccum));}
        
        return answer.toString();
        
    }
    
    private String toJadenCase(String str){
        char head = str.charAt(0);
        String body = str.substring(1).toLowerCase();
        
        if(isAlphabet(head)){
            head = Character.toUpperCase(head);
        }
        
        return head + body;
    }
    
    private boolean isAlphabet(char c){
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }
}
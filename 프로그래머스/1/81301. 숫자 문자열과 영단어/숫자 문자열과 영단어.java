import java.util.HashMap;

class Solution {
    public int solution(String s) {
        // HashMap Instantiation.
        HashMap<String, Integer> map = new HashMap<>();
        
        map.put("zero", 0);
        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.put("nine", 9);
        
        String answer = "";
        String charAccum = "";
        
        for(int i = 0; i < s.length();i++){
            char current = s.charAt(i);
            // Case A : 문자인 경우
            if(isChar(current)){
                if(map.containsKey(charAccum)){
                    int num = map.get(charAccum);
                    answer += Integer.toString(num);
                    charAccum = "";
                }
                charAccum += Character.toString(current);
            }
            // Case B : 숫자인 경우
            else {
                if(!charAccum.isEmpty()){
                    int num = map.get(charAccum);
                    answer += Integer.toString(num);
                    charAccum = "";
                }
                answer += Character.toString(current);
            }
        }
        
        if(!charAccum.isEmpty()){
            int num = map.get(charAccum);
            answer += Integer.toString(num);
        }
        
        return Integer.parseInt(answer);
    }
    
    private boolean isChar(char c){
        if((int)c >= 97 && (int)c <= 122){return true;}
        else{return false;}
    }
}
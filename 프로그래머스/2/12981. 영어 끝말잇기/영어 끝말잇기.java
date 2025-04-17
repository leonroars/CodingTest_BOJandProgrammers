import java.util.HashSet;
import java.util.HashMap;

class Solution {
    int[] answer;
    int numberOfPeople;
    String prev = "";
    HashSet<String> occured;
    HashMap<Integer, Integer> userNumberAndCnt;
    
    public int[] solution(int n, String[] words) {
        
        // class variable initialization
        answer = new int[2];
        numberOfPeople = n;
        occured = new HashSet<>();
        userNumberAndCnt = new HashMap<>();
        
        // Predication
        for(int i = 0; i < words.length; i++){
            int userNumber = (i + n) % n + 1;
            String userWord = words[i];
            
            // 성립 조건 세 가지 중 하나라도 만족하지 않을 경우 해당 사용자 탈락!
            if(!checkLen(userNumber, userWord)
               || !checkIfNotOccured(userNumber, userWord) 
               || !checkChainRule(userNumber, userWord)){
                answer[0] = userNumber;
                if(userNumberAndCnt.containsKey(userNumber)){answer[1] = userNumberAndCnt.get(userNumber) + 1;}
                else {answer[1] = 1;}
                break;
            }
            // 모두 만족한 경우 => 추가 후 다음 단어 검증
            else {
                occured.add(userWord);
                prev = userWord;
                
                if(userNumberAndCnt.containsKey(userNumber)){
                    int previousCnt = userNumberAndCnt.get(userNumber);
                    userNumberAndCnt.put(userNumber, previousCnt + 1);
                }
                else {
                    userNumberAndCnt.put(userNumber, 1);
                }
            }
        }
        
        return answer;
    }
    
    private boolean checkLen(int userNumber, String word){
        return word.length() > 1;
    }
    
    private boolean checkChainRule(int userNumber, String word){
        if(prev.isBlank()){
            prev = word;
            return true;
        }
        else {
            return prev.charAt(prev.length() - 1) == word.charAt(0);
        }
    }
    
    private boolean checkIfNotOccured(int userNumber, String word){
        return !occured.contains(word);
    }
    
    
}
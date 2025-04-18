import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;

class Solution {
    public int solution(String str1, String str2) {
        
        // 아하! 뭐가 문제냐! => 입력 받을 때 filter 하는 게 아니라, 짝을 만들 때 filter 하는 거야!
        
        // 1. 연속하는 문자 두 개를 묶음으로 하여 부분집합 만들기 : O(N)
        HashMap<String, Integer> str1WithFreq = new HashMap<>();
        HashMap<String, Integer> str2WithFreq = new HashMap<>();
        
        for(int i = 0; i < str1.length() - 1; i++){
            char head = str1.charAt(i);
            char tail = str1.charAt(i + 1);
            
            // 두 문자가 모두 알파벳인 경우에만 부분집합의 원소로 인정한다!
            if(Character.isAlphabetic(head) && Character.isAlphabetic(tail)){
                String createdPart = "" + Character.toLowerCase(head) + Character.toLowerCase(tail);
                if(str1WithFreq.containsKey(createdPart)){
                    int freq = str1WithFreq.get(createdPart);
                    str1WithFreq.put(createdPart, freq + 1);
                }
                else{
                    str1WithFreq.put(createdPart, 1);
                }
            }
        }
        
        for(int j = 0; j < str2.length() - 1; j++){
            char head = str2.charAt(j);
            char tail = str2.charAt(j + 1);
            
            // 두 문자가 모두 알파벳인 경우에만 부분집합의 원소로 인정한다!
            if(Character.isAlphabetic(head) && Character.isAlphabetic(tail)){
                String createdPart = "" + Character.toLowerCase(head) + Character.toLowerCase(tail);
                if(str2WithFreq.containsKey(createdPart)){
                    int freq = str2WithFreq.get(createdPart);
                    str2WithFreq.put(createdPart, freq + 1);
                }
                else{
                    str2WithFreq.put(createdPart, 1);
                }
            }
        }
        
        // 3. 교집합 및 합집합 만들기
        ArrayList<String> intersection = new ArrayList<>();
        ArrayList<String> union = new ArrayList<>();
        
        // 교집합
        Set<String> str1PartSet = str1WithFreq.keySet();
        Set<String> str2PartSet = str2WithFreq.keySet();
        
        for(String str1Part : str1PartSet){
            // 공통 원소라면? => 갯수 맞게 각 교/합집합에 추가.
            if(str2WithFreq.containsKey(str1Part)){
                int str1Freq = str1WithFreq.get(str1Part);
                int str2Freq = str2WithFreq.get(str1Part);
                
                int intersectionCnt = (str1Freq > str2Freq) ? (str2Freq) : (str1Freq);
                int unionCnt = (str1Freq == intersectionCnt) ? (str2Freq) : (str1Freq);
                
                for(int inCnt = 0; inCnt < intersectionCnt; inCnt++){
                    intersection.add(str1Part);
                }
                
                for(int uCnt = 0; uCnt < unionCnt; uCnt++){
                    union.add(str1Part);
                }
            }
            // 공통원소가 아니라면? => 합집합에 추가.
            else {
                for(int cnt = 0; cnt < str1WithFreq.get(str1Part); cnt++){
                    union.add(str1Part);
                }
            }
        }
        
        // B-A 원소 추가.
        for(String str2Part : str2PartSet){
            if(!str1WithFreq.containsKey(str2Part)){
                for(int cnt = 0; cnt < str2WithFreq.get(str2Part); cnt++){
                    union.add(str2Part);
                }
            }
        }
        
        // 4. 유사도 계산
        if(intersection.size() == 0 && union.size() == 0){return 65536;}
        
        float divided = (float)intersection.size() / union.size();
        float multiplied = divided * 65536;
        int answer = (int) multiplied;
        
        return answer;
    }
}
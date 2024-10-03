import java.util.HashMap;
import java.util.ArrayList;

class Solution {
    public int[] solution(String msg) {
        int[] answer = {}; // 정답으로 제출될 배열
        ArrayList<Integer> indices = new ArrayList<>(); // 출력된 사전 색인을 담을 ArrayList.
        int lastLoc = 26;
        int lastAddedLength = 1;
        
        // 1. 사전 초기화 _ Key - 단어 / Value : 인덱스(1부터 시작). | O(n), n = msg.length()
        HashMap<String, Integer> dict = new HashMap<>();
        
        for(int i = 65; i < 91; i++){
            char current = (char) i;
            dict.put(Character.toString(current), i- 64); // <단어, 인덱스>
        }
        // 2. 주어진 입력에 대한 LZW 압축 실시
        
        if(msg.length() > 1){
            for(int loc = 0; loc < msg.length();){
                // Case A : 압축 과정 중 가장 처음인 '글자 하나'에 대한 압축 단계
                if(lastAddedLength == 1){
                    // ** 중요!! 입력이 한 글자인 경우 여기서 IndexOutOfBoundException 발생.
                    // 항상 경계값 / 극한값 고려하기!
                    String searchKey = msg.substring(loc, loc+1);
                    int printedIdx = dict.get(searchKey);
                
                    indices.add(printedIdx); // 출력된 색인 목록에 추가.
                    dict.put(searchKey + msg.charAt(loc+1), ++lastLoc); // 새로운 단어 추가.
                    lastAddedLength++; // 마지막으로 추가된 단어 길이 갱신.
                    loc++; // Locator 갱신
                }
                // Case B : 이미 한 글자 이상에 대해 압축이 실시된 이후의 단계
                else {
                    boolean isOverLimit = ((loc + lastAddedLength) >= msg.length());
                    int maxLength = (isOverLimit) ? (msg.length() - loc) : (loc + lastAddedLength - 1);
                
                    for(int currentLength = maxLength; currentLength > 0; currentLength--){
                        String currentStr = ((loc + currentLength) >= msg.length()) ? (msg.substring(loc)) : (msg.substring(loc, loc + currentLength));
                    
                        // 발견한 경우.
                        if(dict.containsKey(currentStr)){
                            indices.add(dict.get(currentStr)); // 출력된 색인 목록에 추가.
                        
                            if(loc + currentLength < msg.length() - 1){
                                dict.put(currentStr + msg.charAt(loc + currentLength), ++lastLoc);
                            } else {
                                dict.put(currentStr, ++lastLoc);
                            }
                        
                            if(currentStr.length() == lastAddedLength){lastAddedLength++;}
                            loc += currentLength; // Locator 갱신
                            break;
                        }
                    }
                }
            }
        }
        else {
            indices.add(dict.get(msg));
        }
        
        answer = indices.stream().mapToInt(Integer::intValue).toArray();
        
        return answer;
    }
}
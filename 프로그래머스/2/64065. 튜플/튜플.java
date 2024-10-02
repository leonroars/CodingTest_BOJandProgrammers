import java.util.ArrayList;
import java.util.BitSet;

class Solution {
    public int[] solution(String s) {
        
        ArrayList<ArrayList<Integer>> parsedArr = new ArrayList<>();
        ArrayList<Integer> partialArr = new ArrayList<>();
        boolean delimiter = false;
        String numAccum = "";
        
        // 1. Parsing - O(k), k > n && 5 <= k <= 1,000,000
        for(int i = 0; i < s.length(); i++){
            // 처음과 끝의 '{', '}' 아 아닐 때만 로직 진행.
            if(i != 0 && i != s.length() - 1){
                char current = s.charAt(i);
                // Case A : current = '{'
                if(current == '{'){
                    partialArr = new ArrayList<>();
                    delimiter = false;
                }
                // Case B : current = '}'
                else if(current == '}'){
                    delimiter = true;
                    if(!numAccum.isEmpty()){
                        partialArr.add(Integer.parseInt(numAccum));
                        numAccum = "";
                    }
                    parsedArr.add(partialArr);
                }
                // Case C : current = ','
                else if(current == ','){
                    if(!delimiter){
                        partialArr.add(Integer.parseInt(numAccum));
                        numAccum = "";
                    }
                }
                // Case D : current = 숫자
                else {
                    numAccum += Character.toString(current);
                }
            }
        }
        
        // 2. Sorting - 부분 배열의 길이 오름차순 정렬 : O(n log n), 1 <= n <= 100,000
        parsedArr.sort((a1, a2) -> a1.size() - a2.size());
        
        // 3. Iterate over sorted ArrayList and add things up. : O(k)
        BitSet priorNumSet = new BitSet(100001);
        int[] answer = new int[parsedArr.size()]; // 정답 담을 배열.
        
        for(int j = 0; j < parsedArr.size(); j++){
            ArrayList<Integer> currentPartArr = parsedArr.get(j);

            // Case A : currentPartArr.size() == 1 -> 반복문에서 처음 마주하는 원소 1개짜리 부분집합.
            if(currentPartArr.size() == 1){
                priorNumSet.set(currentPartArr.get(0));
                answer[j] = currentPartArr.get(0);
            }
            // Case B : 원소의 개수가 2개 이상인 부분집합 조우.
            for(int k = 0; k < currentPartArr.size(); k++){
                int currentNum = currentPartArr.get(k);
                if(!priorNumSet.get(currentNum)){
                    priorNumSet.set(currentNum);
                    answer[j] = currentNum;
                    break;
                }
            }
        }
        return answer;
    }
}
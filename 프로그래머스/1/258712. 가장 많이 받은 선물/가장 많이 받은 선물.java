// start at : 52
// end at : 

import java.util.HashMap;
import java.util.StringTokenizer;
class Solution {
    
    // friends[] : 친구들 목록
    // gifts[] : From To
    // 친구들 목록의 길이를 N이라 했을 때, N^2 연산 필요
    public int solution(String[] friends, String[] gifts) {
        
        HashMap<String, Integer> indexOf = new HashMap<>(); // indexOf.get(A) : A 이름 가진 친구의 friends 배열 내 idx.
        HashMap<Integer, Integer> incomeOf = new HashMap<>(); // incomeOf.get(0) : 0이 받은 선물 수
        HashMap<Integer, Integer> outcomeOf = new HashMap<>(); // outcomeOf.get(0) : 0이 준 선물 수
        int[][] relation = new int[friends.length][friends.length]; // relation[i][j] : i가 j에게 준 선물 수
        
        // 0. 이름-idx 맵핑하기.
        for(int idx = 0; idx < friends.length; idx++){indexOf.put(friends[idx], idx);}
        
        // 1. 관계 파악하기.
        for(String gift : gifts){
            StringTokenizer st = new StringTokenizer(gift);
            String from = st.nextToken();
            int fromIdx = indexOf.get(from);
            
            String to = st.nextToken();
            int toIdx = indexOf.get(to);
            
            relation[fromIdx][toIdx]++;
            
            if(outcomeOf.containsKey(fromIdx)){
                int prev = outcomeOf.get(fromIdx);
                outcomeOf.put(fromIdx, prev + 1);
            }
            else{
                outcomeOf.put(fromIdx, 1);
            }
            
            if(incomeOf.containsKey(toIdx)){
                int prev = incomeOf.get(toIdx);
                incomeOf.put(toIdx, prev + 1);
            }
            else{
                incomeOf.put(toIdx, 1);
            }
        }
        
        // 2. 다음 달 기대 선물 수 계산하기.
        int maxByFar = -Integer.MAX_VALUE;
        
        for(int i = 0; i < friends.length; i++){
            int currentExpectation = 0; // i가 받을 선물 기대치
            for(int j = 0; j < friends.length; j++){
                if(i != j){
                    // Case A : i와 j가 서로 선물 주고 받은 기록이 없거나 동등하게 주고받은 경우 -> 선물 지수 계산에 따라 기대값 보태기.
                    if((relation[i][j] == 0 && relation[j][i] == 0) || relation[i][j] == relation[j][i]){
                        int pointOfI = outcomeOf.getOrDefault(i, 0) - incomeOf.getOrDefault(i, 0);
                        int pointOfJ = outcomeOf.getOrDefault(j, 0) - incomeOf.getOrDefault(j, 0);
                        
                        if(pointOfI > pointOfJ){currentExpectation++;}
                    }
                    // Case B : 서로 주고 받은 기록이 있는 경우 -> 서로에게 누가 더 많이 줬는지 판별 후 점수 가산.
                    else {
                        if(relation[i][j] > relation[j][i]){currentExpectation++;}
                    }
                }
            }
            System.out.println(friends[i] + "가 받을 선물 기대치 = " + currentExpectation);
            maxByFar = Math.max(maxByFar, currentExpectation);
        }
        
        // 3. 정답 반환
        return maxByFar;
        
    }
}
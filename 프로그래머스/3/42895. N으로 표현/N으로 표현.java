import java.util.*;

class Solution {
    public int solution(int N, int number) {
        // 1 ~ 8개의 N으로 만들 수 있는 수만 확인하면 되므로 크기 9를 갖는 배열 초기화.
        HashSet<Integer>[] madeNum = (HashSet<Integer>[]) new HashSet[9];
        for(int i = 1; i < 9; i++){madeNum[i] = new HashSet<>();}
        
        madeNum[1].add(N); // 숫자 N 한 개로 만들 수 있는 숫자.
        
        for(int numLen = 2; numLen < 9; numLen++){
            String onItsOwn = "";
            for(int repeatCnt = 0; repeatCnt < numLen; repeatCnt++){
                onItsOwn += Integer.toString(N);
            }
            madeNum[numLen].add(Integer.parseInt(onItsOwn)); // N을 numLen만큼 이어붙여 만든 수.
            
            for(int prev = numLen - 1; prev > 0; prev--){
                HashSet<Integer> prevSet = madeNum[prev];
                HashSet<Integer> restSet = madeNum[numLen - prev];
                
                for(Integer prevNum : prevSet){
                    for(Integer restNum : restSet){
                        madeNum[numLen].add(prevNum + restNum);
                        madeNum[numLen].add(prevNum - restNum);
                        madeNum[numLen].add(prevNum * restNum);
                        if(prevNum != 0 && restNum != 0){
                            madeNum[numLen].add(prevNum / restNum);
                        }
                    }
                }
            }
        }
        
        int answer = -1;
        for(int k = 1; k < 9; k++){
            if(madeNum[k].contains(number)){answer = k; break;}
        }
        
        return answer;
        
    }
}
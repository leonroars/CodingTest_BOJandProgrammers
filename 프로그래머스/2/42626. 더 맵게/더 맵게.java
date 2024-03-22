import java.util.*;

class Solution {
    public int solution(int[] scoville, int K) {
        int answer = 0;
        // 최소 힙 초기화.
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        // scoville[] 내의 각 음식의 스코빌 지수 최소 힙에 삽입.
        for(int i = 0; i < scoville.length; i++){
            pq.add(scoville[i]);
        }
        
        // 반복문이 멈추는 조건
        // 1) 정답을 찾은 경우
        // 2) pq 내에 음식이 하나 남았지만 k 미만인 경우
        while(true){
            // Edge Case I : pq 내에 음식이 하나 남았지만 k 미만인 경우
            if(pq.size() == 1 && pq.peek() < K){answer = -1; break;}
            // Edge Case II : 정답을 찾은 경우 바로 종료.
            if(pq.peek() >= K){break;}
            
            int smallest = pq.remove(); // 가장 작은 것
            int itsNext = pq.remove(); // 그 다음 작은 것
            int mixed = (smallest + itsNext * 2); // 섞기
            pq.add(mixed); // 섞어 만든 것 넣기
            answer++; // 섞은 횟수 ++;
        }
        
        return answer;
    }
}
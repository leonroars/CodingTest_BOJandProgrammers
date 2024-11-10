import java.util.PriorityQueue;
import java.util.ArrayDeque;
import java.util.Collections;

class Solution {
    public int solution(int[] priorities, int location) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        ArrayDeque<Integer> q = new ArrayDeque<>();
        
        // 0. 입력 : O(n = priorities.length)
        for(int i = 0; i < priorities.length; i++){
            q.add(i); // 현재 작업 순서대로 큐에 추가.
            pq.add(priorities[i]); // 작업의 우선순위를 MaxPQ에 추가.
        }
        
        int currentProcess = 0; // 큐에서 방금 꺼낸 작업의 최초 인덱스.
        int order = 0;
        boolean processed = false;
        
        while(!processed){
            currentProcess = q.removeFirst(); // 작업 큐의 앞에 있는 작업 뽑기.
            // Case I : 현재 살펴보고있는 작업의 우선순위보다 높은 것이 큐에 존재 -> 다시 넣기
            if(pq.peek() > priorities[currentProcess]){q.addLast(currentProcess);}
            
            // Case II : 현재 살펴보는 작업의 우선순위가 가장 높다 -> 실행 후 실행 순서 갱신.
            else {
                order++;
                pq.poll();
                if(currentProcess == location){processed = true;}
            }
        }
        
        return order;
        
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder result = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            int opCnt = Integer.parseInt(br.readLine());
            DoublePQ q = new DoublePQ();
            for(int i = 0; i < opCnt; i++){
                StringTokenizer st = new StringTokenizer(br.readLine());
                String op = st.nextToken();
                int param = Integer.parseInt(st.nextToken());
                q.readCommand(op, param);
            }
            result.append(q.printQ());
            if(tc < TC - 1){result.append("\n");}
        }
        
        System.out.print(result.toString());
    }
    
    /*
     <작동 방식>
     - 모든 삽입 원소를 minPQ, maxPQ에 모두 삽입한다.
     - 삭제 시 두 큐 간의 원소 동기화가 필요하다.
     - 이를 해결하기 위해, 삽입 시에 현재 '큐에 논리적으로 존재해야할 수와 그 수의 갯수를 기록하는 HashMap'에도 마찬가지로 삽입.
     - 이후, 삭제 시마다 HashMap 의 해당 수를 차감해가는 것.
     - ex. 현재 큐 내의 원소가 n개 존재한다고 가정하자.
           최대값 삭제 쿼리가 만약 n/2번 발생하고 최솟값 쿼리 삭제 패턴이 n/2 + 1 번 발생한다면 이때 동기화 문제가 이루어질 수 있다.
           따라서, '이미 삭제가 된 원소가 아닌지 확인'하는 것이다.
    
    */
    static class DoublePQ {
        PriorityQueue<Integer> minPQ;
        PriorityQueue<Integer> maxPQ;
        HashMap<Integer, Integer> actualItemCnt;
        int totalCnt;
        
        public DoublePQ(){
            minPQ = new PriorityQueue<Integer>();
            maxPQ = new PriorityQueue<Integer>(Collections.reverseOrder());
            actualItemCnt = new HashMap<Integer, Integer>();
            totalCnt = 0;
        }
        
        public void readCommand(String op, int param){
            // I : 삽입
            if(op.equals("I")){this.putNum(param);}
            // D : 삭제
            else {
                if(param == -1){this.popMin();}
                else {this.popMax();}
            }
        }
        
        private void putNum(int num){
            minPQ.add(num);
            maxPQ.add(num);
            if(actualItemCnt.containsKey(num)){
                int prevCnt = actualItemCnt.get(num);
                actualItemCnt.put(num, prevCnt + 1);
            }
            else{actualItemCnt.put(num, 1);}
            totalCnt++;
        }
        
        // 출력 전에도 마지막으로 원소 정리 한 번 필요. -> 그래야 peekMin(), peekMax() 가 정상 작동.
        public String printQ(){
            if(totalCnt == 0){return "EMPTY";}
            else {
                StringBuilder answer = new StringBuilder();
                cleanMin();
                cleanMax();
                answer.append(peekMax());
                answer.append(" ");
                answer.append(peekMin());
                
                return answer.toString();
            }
        }
        
        private int peekMin(){return minPQ.peek();}
        
        private int peekMax(){return maxPQ.peek();}
        
        private void popMin(){
            // 현재 큐가 비어있지 않은 경우에만 시행.
            if(totalCnt > 0){
                cleanMin();
                int popped = minPQ.poll();
                int prevCnt = actualItemCnt.get(popped);
                actualItemCnt.put(popped, prevCnt - 1); // 앞에서 정리했으므로 0 미만으로 떨어지는 일 X.
                totalCnt--;
            }
        }
        
        private void popMax(){
            if(totalCnt > 0){
                cleanMax();
                int popped = maxPQ.poll();
                int prevCnt = actualItemCnt.get(popped);
                actualItemCnt.put(popped, prevCnt - 1);
                totalCnt--;
            }
        }
        
        // 삭제하지 않고 내버려둔 원소 정리.
        private void cleanMin(){
            // 없어야 하는데 있는 경우에만 제거.
            while(!minPQ.isEmpty()){
                // 여기에 도달한 시점에서 minPQ 내 아이템이 존재한다는 것 -> NPE 발생 X.
                Integer candidate = minPQ.peek(); // 확인 대상
                // Case A : 없어야 하는데 있는 경우 -> 뽑고 다음 수도 확인.(불일치는 최소 1개~k개까지 발생가능.)
                if(actualItemCnt.get(candidate) == 0){minPQ.poll();}
                // Case B : 있긴 한 경우 내버려둠. 순서는 맞기 때문.(담고 있는 candidate의 수는 minPQ와 maxPQ 차이가 존재 가능. 다만 나중에 clean 과정에서 조정됨.)
                else {break;}
            }
        }
        
        // 삭제하지 않고 내버려둔 원소 정리.
        private void cleanMax(){
            while(!maxPQ.isEmpty()){
                Integer candidate = maxPQ.peek();
                if(actualItemCnt.get(candidate) == 0){maxPQ.poll();}
                else{break;}
            }
        }
    }
}
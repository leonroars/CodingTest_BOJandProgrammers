import java.util.ArrayDeque;

class Solution {
    class Truck {
        int entranceTime;
        int weight;
        
        public Truck(int t, int w){this.entranceTime = t; this.weight = w;}
    }
    
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        
        // 0. Initialization
        ArrayDeque<Truck> bridge = new ArrayDeque<>();
        int time = 0;
        int weightSumOnBridge = 0;
        int nextTruckIdx = 0;
        
        // 1. Putting first car on bridge. (at 1 second)
        time++;
        bridge.addFirst(new Truck(time, truck_weights[nextTruckIdx]));
        weightSumOnBridge += truck_weights[nextTruckIdx];
        nextTruckIdx++;
        
        // 2. From 2 seconds...
        while(!bridge.isEmpty()){
            time++; // 1초 경과
            
            // 1) 출차 필요 차량 있는지 확인 후, 있다면 출차 처리
            if(time - bridge.peekLast().entranceTime == bridge_length){
                weightSumOnBridge -= bridge.peekLast().weight;
                bridge.pollLast(); // 출차
            }
            
            // 2) 새로운 차량 입차 가능 여부 확인 후 가능하다면 입차 처리
            if(nextTruckIdx < truck_weights.length
              && weightSumOnBridge + truck_weights[nextTruckIdx] <= weight){
                bridge.addFirst(new Truck(time, truck_weights[nextTruckIdx]));
                weightSumOnBridge += truck_weights[nextTruckIdx];
                nextTruckIdx++;
            }
        }
        
        return time;
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

import java.util.PriorityQueue;
import java.util.TreeMap;

/*

1. 풀이 아이디어의 핵심 : Greedy한 보석 선택(PQ 활용) + 해당 보석을 담기에 최적인 가방을 효율적으로 찾는 자료구조(TreeMap)
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        
        PriorityQueue<Jewel> jewels = new PriorityQueue<>();
        TreeMap<Integer, Integer> bags = new TreeMap<>(); // key : 가방의 무게 제한 / value : 가방의 수
        
        for(int n = 0; n < N; n++){
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            jewels.offer(new Jewel(m, v));
        }
        
        for(int k = 0; k < K; k++){
            int c = Integer.parseInt(br.readLine());
            
            if(bags.containsKey(c)){
                int cnt = bags.get(c);
                bags.put(c, cnt + 1);
            }
            else {
                bags.put(c, 1);
            }
        }
        
        // 담을 가방/보석이 없을 때 까지 보석 담기.
        long maxValueSum = 0;
        
        while(!jewels.isEmpty() && !bags.isEmpty()){
            Jewel current = jewels.poll();
            
            // 해당 보석을 담을 수 있는 가방이 있는지 없는지 확인하기.
            // 있으면 담고, 없으면 continue;
            // Nullable 하기에 Integer 활용
            Integer bagWeightLimit = bags.ceilingKey(current.weight);
            
            if(bagWeightLimit == null){continue;}
            else {
                int bagCnt = bags.get(bagWeightLimit);
                // 방금 꺼낸 보석 담은 가방이 마지막이라면 TreeMap 에서 삭제.(Eager Approach)
                if(bagCnt == 1){bags.remove(bagWeightLimit);}
                // 아니라면 갯수 차감.
                else{bags.put(bagWeightLimit, bagCnt - 1);}
                
                // 보석 담았으므로 가치 누적합에 가산.
                maxValueSum += current.value;
            }
        }
        
        System.out.print(maxValueSum);
    }
    
    static class Jewel implements Comparable<Jewel>{
        int weight;
        int value;
        
        public Jewel(int m, int v){
            this.weight = m;
            this.value = v;
        }
        
        @Override
        public int compareTo(Jewel thatJewel){
            if(this.value == thatJewel.value){
                return Integer.compare(this.weight, thatJewel.weight); // 무게 정렬 기준은 무관하나 오름차로.
            }
            return Integer.compare(thatJewel.value, this.value);
        }
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.PriorityQueue;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        MidIdentifier mi = new MidIdentifier();
        StringBuilder result = new StringBuilder();
        
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(br.readLine());
            mi.insert(current);
            result.append(mi.getMid());
            if(i < N - 1){result.append("\n");}
        }
        
        System.out.print(result.toString());
    }
    
    static class MidIdentifier {
        static PriorityQueue<Integer> hiQ; // mid 보다 큰 수를 넣어두는 min-Heap
        static PriorityQueue<Integer> loQ; // mid 보다 작은 수를 넣어두는 mas-Heap
        static int mid; // 현 중앙값.
        static int length;
        
        public MidIdentifier() {
            hiQ = new PriorityQueue<>();
            loQ = new PriorityQueue<>(Collections.reverseOrder()); // Max-heap 이므로 reverseOrder.
            mid = -Integer.MAX_VALUE;
            length = 0;
        }
        
        public static int getMid(){return mid;}
        
        public static void insert(int num){
            /* Case A : 최초의 수인 경우 -> 해당 수가 mid. */
            if(mid == -Integer.MAX_VALUE){mid = num;}
            
            
            /* Case B : 이미 보관된 수가 1 이상인 경우 -> loQ와 hiQ의 크기 && mid와의 비교를 통해 삽입. */
            else {
                // Case B-1 : num > mid
                if(num > mid){
                    // 넣기 전 짝수인 경우 -> 기존 Mid는 loQ에 삽입 && num은 hiQ에. 그리고 hiQ의 가장 작은 것이 새 mid.
                    if(length % 2 == 0){
                        loQ.add(mid);
                        hiQ.add(num);
                        mid = hiQ.poll();
                    }
                    // 넣기 전 홀수인 경우 -> 그대로 hiQ에 num 삽입.
                    else {
                        hiQ.add(num);
                    }
                }
                
                // Case B-2 : num < mid
                else if (num < mid){
                    if(length % 2 == 0){
                        loQ.add(num);
                    }
                    else {
                        hiQ.add(mid);
                        loQ.add(num);
                        mid = loQ.poll();
                    }
                }
                
                // Case B-3 : num == mid => loQ와 hiQ 크기를 보고 균형이 맞도록 삽입.
                else {
                    // 현 길이 짝수 -> loQ < hiQ. -> loQ에 삽입
                    if(length % 2 == 0){
                        loQ.add(num);
                    }
                    else {
                        hiQ.add(num);
                    }
                }
            }

            length++;
        }
    }
}
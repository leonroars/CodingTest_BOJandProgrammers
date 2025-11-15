import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

/* 
 위치를 정점, 도달시간을 간선 비용으로 생각할 수 있다.
 Dijkstra 를 쓸 경우, PQ가 아주 커졌을 때 매번 O(log (pq.size())) 연산이 발생한다.
 따라서, 쿨하게 BFS 를 쓰는 것이 좋을 것이라고 판단.
 경로 추적의 경우, 해당 위치로 더 빨리 도달하는 경우에 대해 prev[] 배열에 저장.
 이후 동생의 위치->수빈이 위치로 도달할 때 까지 prev[]를 타고 경로 재구성.

 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());
        
        int[] timeTo = new int[100001]; // 0~100,000
        int[] prev = new int[100001]; // 계속 1씩 움직여 갈 수도 있으니 넉넉하게 선언.
        
        // 초기화.
        for(int i = 0; i < timeTo.length; i++){
            timeTo[i] = Integer.MAX_VALUE;
            prev[i] = -1; // 0으로 둘 경우, 0번 위치로 오인할 수 있으므로.
        }
        
        ArrayDeque<Location> q = new ArrayDeque<>();
        int[] d = new int[]{-1, 1, 2};
        
        timeTo[A] = 0;
        q.offer(new Location(A, timeTo[A]));
        
        // 이 while 이 끝난다는 보장이 있는가? -> YES. time 이 1씩 증가하기 때문에, 계속해서 최단 시간이 줄어들 수 없음.
        while(!q.isEmpty()){
            Location current = q.poll();
            
            for(int i = 0; i < 3; i++){
                int nextPos;
                
                if(i < 2){nextPos = current.pos + d[i];}
                else{nextPos = current.pos * d[i];}
                
                if(nextPos >= 0 && nextPos <= 100000 && timeTo[nextPos] > current.timeArrival + 1){
                    timeTo[nextPos] = current.timeArrival + 1;
                    prev[nextPos] = current.pos;
                    q.offer(new Location(nextPos, timeTo[nextPos]));
                }
            }
        }
        
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int cursor = B;
        
        stack.push(B);
        
        while(cursor != A){
            stack.push(prev[cursor]);
            cursor  = prev[cursor];
        }
        
        StringBuilder path = new StringBuilder();
        
        while(!stack.isEmpty()){path.append(stack.pop()).append(" ");}
        
        System.out.println(timeTo[B]);
        System.out.print(path.toString().trim());
    }
    
    static class Location {
        int pos;
        int timeArrival;
        
        public Location(int p, int t){
            this.pos = p;
            this.timeArrival = t;
        }
    }
}
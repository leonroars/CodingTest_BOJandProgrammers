import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

/*
 * 풀이 핵심 : BFS + 최단경로 재구성
 * 왜 BFS가 최단거리를 보장하는가?
  - 각 연산 별 소모 비용 간 차이가 없음 -> '연산 수행 == 이동' 이라고 생각했을 때, 가중치가 없다는 이야기.
  - 따라서, '두 수 P, Q 간 거리 == 연산 횟수'가 됨.
  - 연산 횟수는 0보다 큰 정수임. -> 따라서 Level-order 의 특성인 '선발견한 지점으로 더 좋은 최단 경로를 나중에 발견하는 경우는 존재하지 않는다' 가 성립

 * 주의 사항 => switch 오랜만에 써서 break; 를 까먹는 건 좀 바보같았다.
 
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            
            DummyCalculator dc = new DummyCalculator(A, B);
            String currentMinOps = dc.getMinOps();
            answer.append(currentMinOps);
            if(tc < TC - 1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    static class DummyCalculator {
        int A; // Source
        int B; // Target
        int[] prev; // prev[k] : A->B 최단 경로 상 k 이전에 위치하는 숫자
        char[] minOp; // minOp[i] : A->i 가 되기 위해 필요한 연산 중 가장 마지막 연산
        
        public DummyCalculator(int A, int B){
            this.A = A;
            this.B = B;
            prev = new int[10000]; // 0 ~ 9999
            minOp = new char[10000]; // 0 ~ 9999
        }
        
        public String getMinOps(){
            BFS();
            
            ArrayDeque<Character> stack = new ArrayDeque<>();
            int current = B;
            
            while(current != A){
                stack.push(minOp[current]);
                current = prev[current];
            }
            
            StringBuilder path = new StringBuilder();
            
            while(!stack.isEmpty()){
                path.append(stack.pop());
            }
            
            return path.toString();
        }
        
        private void BFS(){
            ArrayDeque<Integer> q = new ArrayDeque<>();
            boolean[] visited = new boolean[10000];
            
            q.offer(A);
            visited[A] = true;
            
            while(!q.isEmpty()){
                int current = q.poll();
                
                for(int d = 0; d < 4; d++){
                    int next = move(current, d);
                    if(!visited[next]){
                        visited[next] = true;
                        prev[next] = current;
                        minOp[next] = getMappedOp(d);
                        q.offer(next);
                    }
                }
                // Check Fast
                if(visited[B]){break;}
            }
        }
        
        /* D=0, S=1, L=2, R=3 */
        private int move(int current, int d){
            int result = -1;
            
            int first = (current / 1000);
            int second = (current - 1000 * first) / 100;
            int third = (current - (1000 * first) - (100 * second)) / 10;
            int fourth = (current - (1000 * first) - (100 * second) - (10 * third));
            
            switch(d) {
                case 0 :
                    result = (current * 2) % 10000;
                    break;
                    
                case 1 :
                    result = (10000 + (current - 1)) % 10000;
                    break;
                    
                case 2 :
                    result = (second * 1000) + (third * 100) + (fourth * 10) + first;
                    break;
                case 3 : 
                    result = (fourth * 1000) + (first * 100) + (second * 10) + third;
                    break;
            }
            
            return result;
        }
        
        private char getMappedOp(int d){
            char result = '!';
            
            switch(d){
                case 0 : result = 'D'; break;
                case 1 : result = 'S'; break;
                case 2 : result = 'L'; break;
                case 3 : result = 'R'; break;
            }
            
            return result;
        }
    }
}
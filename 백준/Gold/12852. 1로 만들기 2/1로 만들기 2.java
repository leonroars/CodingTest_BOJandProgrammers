import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Main {
    static int[] prev;
    static int[] sp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int X = Integer.parseInt(br.readLine());
        
        prev = new int[X + 1]; // 최악의 경우 10만 개의 숫자에 대해 전체 탐색 및 갱신이 발생할 수 있음.
        sp = new int[X + 1]; // sp[k] : k->1 로 가는 최소 연산 수
        
        for(int i = 1; i < X + 1; i++){sp[i] = Integer.MAX_VALUE;}
        
        findMin(X);
        
        String path = generatePath(X);
        
        System.out.println(sp[1]);
        System.out.print(path);
    }
    
    public static void findMin(int X){
        ArrayDeque<Integer> q = new ArrayDeque<>();
        boolean[] visited = new boolean[X + 1];
        ArrayList<Operation> operations = new ArrayList<>();
        
        operations.add(new DivisionOperation(3));
        operations.add(new DivisionOperation(2));
        operations.add(new SubtractionOperation(1));
        
        visited[X] = true;
        sp[X] = 0;
        prev[X] = -1; // 시작점.
        q.offer(X);
        
        while(!q.isEmpty()){
            int current = q.poll();
            
            for(Operation op : operations){
                int next = op.operate(current);
                if(isPossible(next, X)){
                    if(sp[next] > sp[current] + 1){
                        sp[next] = sp[current] + 1;
                        prev[next] = current;
                        if(!visited[next]){visited[next] = true;}
                        q.offer(next);
                    }
                }
            }
        }   
    }
    
    public static String generatePath(int end){
        StringBuilder path = new StringBuilder();
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        
        int current = 1;
        stack.push(current);
        
        // 무한 루프 발생하지 않음. 1씩 빼나가더라도 10만 번 연산 내에 1에 도달하므로.
        while(current != end){
            current = prev[current];
            stack.push(current);
        }
        
        while(!stack.isEmpty()){
            path.append(stack.pop()).append(" ");
        }
        
        return path.toString().trim();
    }
    
    private static boolean isPossible(int k, int X){
        return k >= 0 && k <= X;
    }
    
    interface Operation {
        int operate(int arg);
    }
    
    static class DivisionOperation implements Operation {
        int divisor;
        
        public DivisionOperation(int divisor){
            this.divisor = divisor;
        }
        
        @Override
        public int operate(int arg){
            if(arg % divisor == 0){return arg / divisor;}
            return -1;
        }
    }
    
    static class SubtractionOperation implements Operation {
        int subtrahend;
        
        public SubtractionOperation(int subtrahend) {
            this.subtrahend = subtrahend;
        }
        
        @Override
        public int operate(int arg){
            return arg - subtrahend;
        }
    }
}
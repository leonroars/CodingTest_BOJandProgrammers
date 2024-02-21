import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int input = Integer.parseInt(br.readLine());
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for(int i = 1; i < input + 1; i++){
            q.addLast(i);
        }
        
        while(q.size() > 1){
            q.removeFirst();
            if(q.size() >= 1){
                int dq = q.removeFirst();
                q.addLast(dq);
            }
            if(q.size() == 1){break;}
        }
        
        System.out.println(q.poll());
    }
}
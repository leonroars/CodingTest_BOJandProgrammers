import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;

public class Main{
    static class Set{
        private BitSet set;

        public Set(){
            set = new BitSet(21); // 원소의 범위가 1부터 20까지이므로, 최대 갯수에 1을 더한 21로 초기화.
        }

        public void add(int x){
            if(x <= 20 && !set.get(x)){set.set(x);}

        }

        public int check(int x){
            return (set.get(x)) ? 1 : 0;
        }

        public void remove(int x){
            if(set.get(x)){set.clear(x);}
        }

        public void toggle(int x){
            if(set.get(x)){set.clear(x);}
            else{set.set(x);}
        }

        public void all(){
            set = new BitSet(21);
            set.set(1, 21);
        }

        public void empty(){
            set = new BitSet(21);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Set set = new Set();
        StringBuilder answer = new StringBuilder();

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();
            if(op.equals("add")){
                int element = Integer.parseInt(st.nextToken());
                set.add(element);
            }
            if(op.equals("remove")){
                int element = Integer.parseInt(st.nextToken());
                set.remove(element);
            }
            if(op.equals("check")){
                int element = Integer.parseInt(st.nextToken());
                answer.append(set.check(element));
                answer.append("\n");
            }
            if(op.equals("toggle")){
                int element = Integer.parseInt(st.nextToken());
                set.toggle(element);
            }
            if(op.equals("all")){set.all();}
            if(op.equals("empty")){set.empty();}
        }
        System.out.print(answer);
    }
}
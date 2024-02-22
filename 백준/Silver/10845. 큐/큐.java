import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 크기 재조정을 하지 않는 원형 큐 구현.
// 그렇기 때문에 처음에 입력받은 명령어의 수가 모두 삽입 연산인 경우를 상정하여
// 명령어 수만큼의 크기를 갖는 배열을 초기화하고 이를 큐로 사용하도록 구현한다.

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        Queue q = new Queue(N + 1); //

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            if(command.equals("push")){
                int elem = Integer.parseInt(st.nextToken());
                q.enqueue(elem);
                continue;
            }
            if(command.equals("pop")){
                if(q.isEmpty()){System.out.println(-1);}
                else{System.out.println(q.dequeue());}
                continue;
            }

            if(command.equals("size")){
                System.out.println(q.size());
                continue;
            }

            if(command.equals("front")){
                System.out.println(q.front());
                continue;
            }

            if(command.equals("back")){
                System.out.println(q.back());
                continue;
            }
            if(command.equals("empty")){
                if(q.isEmpty()){System.out.println(1);}
                else{System.out.println(0);}
            }
        }
    }
    static class Queue{
        private int[] q;
        private int front;
        private int rear;
        private int size;
        private int capacity;

        public Queue(int capa){
            q = new int[capa];
            front = 0;
            rear = 0;
            size = 0;
            capacity = capa;
        }

        public boolean isEmpty(){
            return front == rear;
        }

        // 원형 큐 구현은 본래 empty와 full 상태의 구분을 요구한다.
        // 이를 위해 항상 큐가 최대 capacity - 1 까지만 채워지도록하고 이를 full 상태로 정의하는 것이 보통이다.
        public void enqueue(int x){
            q[rear] = x;
            rear = (rear + 1) % capacity;
            size++;
        }

        public int dequeue(){
            int dq = q[front];
            front = (front + 1) % capacity;
            size--;
            return dq;
        }

        public int front(){
            if(isEmpty()){return -1;}
            return q[front];
        }

        public int back(){
            if(isEmpty()){return -1;}
            return q[(capacity + (rear - 1)) % capacity];
        }

        public int size(){
            return size;
        }
    }
}
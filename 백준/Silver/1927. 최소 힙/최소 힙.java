import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numOps = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        MinPQ pq = new MinPQ(numOps); // 전부 삽입연산일 경우를 상정하여 크기를 설정.
        for(int i = 0; i < numOps; i++){
            int op = Integer.parseInt(br.readLine());

            if(op == 0){sb.append(pq.deleteMin()); sb.append("\n");}
            else{pq.insert(op);}
        }

        System.out.println(sb);
    }
    // MinPQ.
    // 전체적인 구조는 Robert Sedgewick & Kevin Wayne의 저서 Algorithms (4th Edition)에서 설명 된
    // 최대 힙의 구조를 참고하였다.
    // 기본적인 구현 문제이기때문에 공부한 걸 복습하는 의미에서 진행한다.
    static class MinPQ{
        private int[] pq;
        private int endLoc;

        public MinPQ(int size){
            pq = new int[size + 1];
            endLoc = 0;
        }

        public boolean isEmpty(){
            return endLoc == 0;
        }

        public void insert(int x){
            pq[++endLoc] = x;
            promote(endLoc);
        }
        public int deleteMin(){
            if(isEmpty()){return 0;}
            int min = pq[1];
            exch(1, endLoc);
            endLoc--;
            pq[endLoc+1] = -1;
            demote(1);

            return min;
        }

        // 새로 삽입된 원소가 부모 원소보다 작을 경우 위치를 바꿔준다.
        // Heap-order를 되찾을 때까지 반복한다.
        private void promote(int xIdx){
            while(xIdx > 1 && greater(xIdx / 2, xIdx)){
                exch(xIdx, xIdx / 2);
                xIdx = xIdx / 2;
            }
        }
        // deleteMin() 시행 후 새로이 1번 인덱스에 위치하게 된 원소가
        // 그 자식보다 클 시 둘의 위치를 바꿔주는 작업을 한다.
        private void demote(int xIdx){
            while(xIdx * 2 <= endLoc){
                int child = xIdx * 2;
                if((child < endLoc) && !greater(child + 1, child)){child++;} // 더 작은 자식 고르기.
                if(!greater(xIdx, child)){break;}
                exch(xIdx, child);
                xIdx = child;
            }
        }

        // 두 인자에 위치한 원소의 위치를 바꿔준다.
        private void exch(int xIdx, int yIdx){
            int temp = pq[xIdx];
            pq[xIdx] = pq[yIdx];
            pq[yIdx] = temp;
        }

        private boolean greater(int xIdx, int yIdx){
            return pq[xIdx] > pq[yIdx];
        }
    }
}

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int commands = Integer.parseInt(br.readLine());
        AbsHeap H = new AbsHeap(commands);
        StringBuilder answer = new StringBuilder();

        for(int i = 0; i < commands; i++){
            int command = Integer.parseInt(br.readLine());
            if(command == 0){
                answer.append(H.pop() + "\n");
            }
            else {
                H.add(command);
            }
        }

        System.out.print(answer.toString().trim());
    }

    static class AbsHeap {
        int capacity;
        int[] heap;
        int end; // 마지막 원소 위치
        int size; // 현재 크기

        public AbsHeap(int commands){
            capacity = commands;
            heap = new int[capacity + 1]; // 자식 인덱스 계산의 용이성을 위해 1위치부터 삽입
            end = 0;
            size = 0;
        }

        public void add(int x){
            heap[++end] = x;
            size++;
            promote(); // Re-heapification.
        }

        private void promote(){
            int targetIdx = end; // 마지막 위치에 삽입된 원소를 부모와 비교해가며 자리 잡아주기.
            // 루트 위치에 도달하지 않았고 동시에 부모보다 작은 경우
            while(targetIdx > 1 && isSmaller(targetIdx, targetIdx / 2)){
                exch(targetIdx, targetIdx / 2); // 교체
                targetIdx = targetIdx / 2; // 갱신
            }
        }

        public int pop(){
            if(size > 0){
                int popped = heap[1]; // 최상단 원소 선택
                exch(1, end--); // 최상단 원소와 가장 마지막 원소 교체. 이후 end 1 감소.
                size--;
                demote();
                return popped;
            }
            return 0;
        }

        private void demote(){
            int targetIdx = 1;
            // 자식이 최소 하나 존재하는 동안,
            while(targetIdx * 2 <= end){
                int childIdx;
                int lcIdx = targetIdx * 2;

                // 왼쪽 자식과 오른쪽 자식 중 더 작은 것을 고른다. 새로운 부모로 만들기 위함.
                if(lcIdx + 1 <= end){
                    childIdx = (isSmaller(lcIdx, lcIdx + 1)) ? (lcIdx) : (lcIdx + 1);
                }
                else {childIdx = lcIdx;}


                // 바꿔야 하는 경우 -> 바꾸고 포인터 갯인
                if(!isSmaller(targetIdx, childIdx)){
                    exch(targetIdx, childIdx);
                    targetIdx = childIdx;
                }
                else {break;}
            }
        }

        // Return true if 'thisIdx' has to go uppermore than 'thatIdx'.
        private boolean isSmaller(int thisIdx, int thatIdx){
            return (Math.abs(heap[thisIdx]) < Math.abs(heap[thatIdx]))
                    || ((Math.abs(heap[thisIdx]) == Math.abs(heap[thatIdx])) && (heap[thisIdx] < heap[thatIdx]));
        }

        private void exch(int thisIdx, int thatIdx){
            int temp = heap[thatIdx];
            heap[thatIdx] = heap[thisIdx];
            heap[thisIdx] = temp;
        }


        private boolean isEmpty(){return end == 0;}

    }
}
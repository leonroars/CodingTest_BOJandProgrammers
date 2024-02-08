import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 풀이 접근
// MaxPQ API의 구현 자체를 요구하는 문제이다.
// 힙은 heap-order를 따르는 트리 형태의 추상자료형이다.
// 다양한 구현 방식이 있으나 여기서는 그 효율성으로 널리 사용되고 채택되는 배열 기반의 바이너리 힙으로 구현한다.
// 지난 밤에 최대 힙과 최소 힙의 개념 및 구현을 학습하였다. (Algorithms by Robert Sedgewick & Kevin Wayne)
// 헷갈릴 때까지 두었다가 도전한다. 책으로부터 학습한 것을 충실히 구현하며 복습하는 것을 목적으로 하므로 구현부의 논리는 책의 그것과 동일하다.
// 다만 혹시나 책의 맥락을 알지 못하는 누군가가 이 코드를 살펴볼 것을 생각해,
// 지나치게 간략한 부분은 여러 단계로 펼쳐 가독성을 높이고 전체적인 맥락 없이는 알기 어려운 변수명/메서드 명을 변경하였다.

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        MaxPQ pq = new MaxPQ(N); // 주어진 연산 모두 삽입일 때의 상황을 상정하여 배열의 크기를 설정.
        StringBuilder output = new StringBuilder();

        for(int i = 0; i < N; i++){
            int input = Integer.parseInt(br.readLine());
            if(input == 0){output.append(pq.deleteMax()); output.append("\n");}
            else{pq.insert(input);}
        }
        System.out.println(output);

    }

    static class MaxPQ {
        private int[] pq;
        private int end;

        public MaxPQ(int maxInput){
            pq = new int[maxInput + 1];
            end = 0;
        }

        private boolean less(int idx1, int idx2){return pq[idx1] < pq[idx2];}
        private void exch(int idx1, int idx2){int temp = pq[idx1]; pq[idx1] = pq[idx2]; pq[idx2] = temp;}

        private void promotion(int idx){
            while(idx > 1 && less(idx/2, idx)){
                exch(idx, idx/2);
                idx = idx / 2;
            }
        }
        private void demotion(int idx){
            while(2*idx <= end){ // IndexOutOfBound를 고려한 ceiling 설정
                int biggerChild = 2 * idx;
                if(biggerChild < end && less(biggerChild, biggerChild + 1)){biggerChild++;} // 두 개의 자식 노드 중 큰 쪽을 선택. 이 친구와 현재의 부모가 교체될 예정.
                if(!less(idx, biggerChild)){break;}
                exch(idx, biggerChild);
                idx = biggerChild;
            }
        }

        public void insert(int k){
            pq[++end] = k; // Pre-increment.
            promotion(end); // 최대 바이너리 힙의 가장 뒤(end) 위치에 삽입. 최대 1 + floor(log n) 만큼의 비교/교체 연산이 요구된다.
                          // 이후 부모 위치의 값과 비교해 필요하다면 교체한다. 이 과정은 promotion()을 통해 'heap-order'를 회복할 때까지 반복한다.
        }
        public int deleteMax(){

            if(isEmpty()){return 0;} // 문제의 요구사항.

            int max = pq[1];
            exch(1, end--);
            pq[end+1] = -1; // 원시 자료형은 nullify 할 수 없으므로 대신 문제의 조건을 고려하여 -1로 대체하여 표기한다. 실제 처리 상의 효율보다는 디버깅의 효율을 고려한 조치이다.
            demotion(1); // insert 에서의 promotion()과 같은 이유로 필요한 메서드. 필요하다면 heap-order가 회복될 때까지 자식 노드와 교체하며 트리의 단말쪽으로 밀어낸다.
                             // 최대 2*floor(log n) 만큼의 비교/교체 연산 요구된다. 자식 노드 하나와 교체될 노드 값의 비교 한 번, 다른 자식 노드 하나와의 비교 한 번. 총 두 번 비교 연산.
            return max;
        }

        public boolean isEmpty(){return end == 0;}
    }
}
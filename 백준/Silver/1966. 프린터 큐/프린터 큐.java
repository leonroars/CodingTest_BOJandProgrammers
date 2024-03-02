import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

// 풀이 및 접근
//
// - 우선순위 큐와 일반적인 선입선출 큐 두 가지를 함께 사용한다.
//   입력받은 큐의 문서 내용물을 테스트케이스 별로 만들어둔 선입선출 큐와 우선순위 큐에 동시에 넣어준다.
//   우리는 우선순위의 전체 정렬된 순서를 알 필요는 없다. 현재 가장 높은 문서 내 우선순위가 무엇인지만 알면 된다.
//   하지만 현재 가장 높은 우선순위의 문서가 뽑히고 나면 그 다음 우선순위를 알 수 있어야한다.
//   이를 위해선 우선순위 큐를 이용하는 방법과 일반 배열에 큐의 내용물을 입력하고 정렬하는 방식이 있을 것이다.
//   자바 내에서 지원하는 배열 정렬의 시간복잡도는 O(n log n)이고 우선순위 큐로의 n 개 삽입도 O(n log n)이 요구된다.
//   따라서 어느 쪽을 사용하던 상관없지만, 우선순위 큐 구현을 복습해볼겸 나는 우선순위 큐를 사용하기로 했다.
//
// - 우선순위 큐의 가장 상단(peek())의 우선순위와 현재 큐의 가장 앞(인덱스 0 위치) 아이템을 비교한다.
//   가능한 시나리오는 다음과 같다.

//   1) 현재 우리가 쫓고 있는 문서의 현재 큐 내 인덱스가 0인 경우
//      이 경우 해당 문서의 우선순위가 전체 우선순위 중 가장 높은 것이 아니면 다시 큐의 뒤로 삽입하고
//      맞다면 해당 우선순위와 문서 모두 인쇄해야한다.
//      a) 해당 문서 우선순위 == 현재 인쇄해야할 우선순위(가장 높은 우선순위)
//         * 문서와 우선순위 모두 각자의 큐(문서 - 큐 / 우선순위 - 우선순위 큐)에서 뽑는다.
//         * 인쇄가 실제로 이루어지는 경우는 이처럼 문서 우선순위가 현재 인쇄해야할 우선순위와 같은 경우이다.
//           따라서 printCount를 1 증가시켜준다.
//           이때의 printCount가 정답이므로 반복문을 종료시켜준다.
//      b) 해당 문서 우선순위 != 현재 인쇄해야할 우선순위
//         * 우선순위는 그 자리에 그대로 위치한다(뽑지않는다)
//         * 방금 비교한 큐 내의 문서(인덱스 0 위치의 문서)를 문서 후단에 삽입해준다.
//           인쇄가 발생하지 않았으므로, printCount에는 변화가 없다.
//           후단에 삽입됨으로써 우리가 쫓고있던 문서의 인덱스가 0에서 (큐 내 문서 개수 - 1)로 바뀌었으므로 갱신해준다.
//           
//   2) 0이 아닌 다른 경우
//      이 경우 우리가 쫓고자하는 문서는 인덱스 0 이후 어딘가에 위치한다.
//      따라서 인덱스 0 문서의 처리 결과가 어떻든 - 인쇄가 되거나 큐의 후단에 재삽입되거나 -
//      일단 처리된 후에는 쫓고자 하는 문서의 인덱스가 감소한다. 순번이 앞으로 이동하기 때문이다.
//      하지만 이 경우 중요한 것은 역시나 printCount를 적절하게 갱신해주는 것이다.
//      바로 printCount가 우리가 쫓고자 하는 문서의 인쇄 순번이 되기 때문이다.
//      a) 0번 인덱스 문서 우선순위 == 현재 인쇄해야할 우선순위
//         * 문서와 우선순위 모두 각자의 큐에서 뽑는다.
//         * 인쇄가 실제로 이루어졌으므로 printCount를 1 증가시켜준다.
//         * 0번 인덱스 이후의 문서가 모두 한 칸 앞으로 이동하므로, 우리가 쫓는 문서의 큐 내 인덱스도 감소시켜준다.
//      b) 0번 인덱스 문서 우선순위 != 현재 인쇄해야할 우선순위
//         * 우선순위는 그 자리에 그대로 위치한다.
//         * 인쇄가 이루어지지 않았으므로 printCount는 갱신하지 않고,
//           0번 인덱스의 문서는 다시 큐의 후단에 삽입해준다.
//         * 그러나 0번 인덱스 이후의 문서들은 모두 한 칸 앞으로 이동하므로, 우리가 쫓는 문서의 큐 내 인덱스도 감소시켜준다.

// 위와 같은 절차로 프로그램을 작성 시 원하는 문서의 실제 인쇄 순번을 알 수 있다.
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine()); // 테스트케이스 개수
        StringBuilder sb = new StringBuilder(); // 정답을 담을 StringBuilder

        for(int i = 0; i < TC; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            int docCount = Integer.parseInt(st.nextToken()); // 큐 내의 문서 갯수
            int targetCurrent = Integer.parseInt(st.nextToken()); // 우리가 쫓는 문서의 현재 큐 내 인덱스.
            int printCount = 0; // 지금까지 인쇄된 문서의 갯수.
                                //만약 현재 우선순위 비교 중인 문서가 출력된다면 해당 문서의 출력 순번은 printCount + 1이다.

            MaxPQ pq = new MaxPQ(docCount); // 문서의 개수 + 1 크기를 갖는 최대 힙 초기화.
            ArrayDeque<Integer> q = new ArrayDeque<>(); // 프린터 큐

            st = new StringTokenizer(br.readLine()); // 큐 내 문서 입력 받기
            for(int doc = 0; doc < docCount; doc++){
                int cursor = Integer.parseInt(st.nextToken());
                pq.insert(cursor);
                q.addLast(cursor);
            }

            while(true){
                int currentMaxPriority = pq.peek(); // 현재 큐 내 문서의 우선순위 중 가장 높은 우선순위
                if(targetCurrent == 0){
                    int currentDocPriority = q.removeFirst(); // 현재 큐의 가장 전단 문서의 우선순위
                    if(currentDocPriority == currentMaxPriority){
                        printCount++; pq.deleteMax();
                        break;
                    } else{
                        q.addLast(currentDocPriority);
                        targetCurrent = q.size() - 1;
                    }
                }
                else{
                    int front = q.removeFirst();
                    if(front == currentMaxPriority){
                        pq.deleteMax();
                        printCount++;
                        targetCurrent--;
                    } else {
                        q.addLast(front);
                        targetCurrent--;
                    }
                }
            }

            sb.append(printCount);
            sb.append("\n");
        }

        System.out.print(sb.toString().trim());
    }

    // 최대 힙 구현. 자세한 내용은 Robert Sedgewick & Kevin Wayne의 Algorithms 4th edition 참고바랍니다!
    static class MaxPQ {
        private int[] pq;
        private int N; // End-position locator.

        public MaxPQ(int maxSize){
            pq = new int[maxSize + 1];
            N = 0;
        }

        public void insert(int x){
            pq[++N] = x;
            promote(N);
        }

        public int deleteMax(){
            int max = pq[1];
            exch(1, N);
            N--;
            pq[N + 1] = -1; //
            demote(1);

            return max;
        }

        public int peek(){return pq[1];}

        private boolean less(int thisIdx, int thatIdx){
            return pq[thisIdx] < pq[thatIdx];
        }

        private void exch(int thisIdx, int thatIdx){
            int temp = pq[thisIdx];
            pq[thisIdx] = pq[thatIdx];
            pq[thatIdx] = temp;
        }

        private void promote(int idx){
            while(idx > 1 && less(idx / 2, idx)){
                exch(idx / 2, idx);
                idx = idx / 2;
            }
        }

        private void demote(int idx){
            while(idx * 2 <= N){
                int child = idx * 2;
                if(child < N && less(child, child + 1)){child++;}
                if(!less(idx, child)){break;}
                exch(child, idx);
                idx = child;
            }
        }
    }
}
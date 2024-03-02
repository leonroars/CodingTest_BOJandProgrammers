import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine()); // 테스트케이스 개수
        StringBuilder sb = new StringBuilder(); // 정답을 담을 StringBuilder

        for(int i = 0; i < TC; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            int docCount = Integer.parseInt(st.nextToken()); // 큐 내의 문서
            int targetCurrent = Integer.parseInt(st.nextToken()); // 쫓고자 하는 문서의 현재 큐 내 위치.
            int printCount = 0; // targetCurrent의 인쇄 순번. 문서가 인쇄 될때마다 하나씩 증가시킨다.

            MaxPQ pq = new MaxPQ(docCount); // 문서의 개수 + 1 크기를 갖는 최대 힙 초기화.
            ArrayDeque<Integer> q = new ArrayDeque<>(); // 프린터 큐

            st = new StringTokenizer(br.readLine());
            for(int doc = 0; doc < docCount; doc++){
                int cursor = Integer.parseInt(st.nextToken());
                pq.insert(cursor);
                q.addLast(cursor);
            }

            while(true){
                int currentMaxPriority = pq.peek();
                if(targetCurrent == 0){
                    int currentDocPriority = q.removeFirst();
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

    // 최대 힙 구현
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
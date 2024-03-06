import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.ArrayDeque;

// 풀이 및 접근
// 애석하게도 아직 DP 접근에 익숙하지 않은 탓인지, 문제를 보자마자 BFS 방식의 풀이를 떠올렸다.
// 문제 큐에 다시 넣고 주말에 다시 DP로 풀어볼 것이다.
// 이 문제를 그래프 탐색 알고리즘인 BFS로 풀 수 있는 이유는
// 그래프의 정점과 간선에 대응하는 개념이 문제에 존재하기 때문이다.
//  1) 정점 : 연산 결과로 도출된 숫자(1 ~ 1,000,000)
//  2) 간선 : 연산 세 가지 중 하나
//           - 모두 연산 횟수를 '1'만큼 증가시켜주기 때문에 모든 간선(연산)의 가중치가 동일한 무가중치 간선으로 생각해볼 수 있다.
// 또한 BFS의 레벨 오더 특성 상, 먼저 마주친 정점(숫자)에 대한 최단거리를 보장한다.
// 따라서 방문하지 않은 경우(tab[adjacent] == 0)에만 거리를 갱신해주면된다.
// 큐를 이용한 BFS로 구현하며, 일단 큐가 비지 않은 동안 절차를 반복하지만 만약 목표 숫자 N을 찾은 경우 break를 이용해
// 반복문을 종료하고 답을 반환하도록 구현한다.

public class Main{
    static int bfs(int N){
        int[] tab = new int[1000001]; // Tabulate the shortest path to each number from previous.
        ArrayDeque<Integer> q = new ArrayDeque<>(); // 큐 초기화
        q.addLast(1);
        boolean targetFound = false;
        while(!q.isEmpty()){
            int current = q.removeFirst(); // 큐의 전단으로부터 정점(숫자) 뽑기
            // Adjacent numbers processing procedure.
            for(int i = 1; i < 4; i++){
                int adjacent = 0;
                if(i == 1){adjacent = current + i;} // 현재 큐에서 뽑은 숫자(current)에 1 더하기
                else{adjacent = current * i;} // current에 2 곱하기 혹은 3곱하기

                if(adjacent <= 1000000){
                    if(tab[adjacent] == 0){
                        tab[adjacent] = tab[current] + 1; // tab[adjacent] != 0이라면 더 빠른 경로가 있다는 것.
                        q.addLast(adjacent); // 방금 거리를 갱신한 경우(이번이 첫 방문인 경우)에만 큐에 삽입.
                    }
                    if(adjacent == N){targetFound = true; break;}
                }
            }
            if(targetFound){break;}

        }
        return tab[N];


    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        System.out.print(bfs(N));
    }
}
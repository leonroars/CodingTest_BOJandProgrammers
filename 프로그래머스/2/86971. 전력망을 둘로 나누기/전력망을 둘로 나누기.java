// 풀이 및 접근
//
// * wires = 간선 목록
//   wires[i]의 [0], [1]은 송전탑 번호(정점 번호).
// * 트리의 성질을 정확히 알아야한다.
//   트리의 성질 : 모든 정점으로부터 다른 모든 정점으로의 도달이 가능하다.
//   이때 하나의 간선을 제거할 경우 자연스레 트리는 두 개의 트리로 분할된다.
//   따라서, wires[]의 간선을 하나씩 무시(제거)해보며, 그렇게 했을 때,
//   각각의 정점으로부터 DFS/BFS를 실시한다.
//   당연히 모든 정점으로의 도달이 불가능하므로 해당 탐색은 두 번 발생하게 된다.
//   이때, 각각의 도달 정점 수간의 차이 절대값을 min에 갱신하도록 한다.

import java.util.*;

class Solution {

    int min = Integer.MAX_VALUE;
    int[][] wireList;
    boolean[][] connected;

    public int solution(int n, int[][] wires) {
        wireList = wires; // BFS 메서드를 위해 wires에 대한 Reference 확보.

        // 송전탑 간 연결 상태를 그래프로 표현 및 저장.
        // 송전탑의 번호는 1부터 시작이므로, 간선의 갯수인 wires.length 보다 2 크게 초기화.
        connected = new boolean[wires.length + 2][wires.length + 2];
        for(int i = 0; i < wires.length; i++){
            int thisOne = wires[i][0]; // 현재 읽고 있는 간선에 연결된 송전탑 하나
            int thatOne = wires[i][1]; // 반대쪽 송전탑
            connected[thisOne][thatOne] = true;
            connected[thatOne][thisOne] = true;
        }

        BFS();

        return min;
    }

    private void BFS(){
        // 무시할 전선 고르기
        for (int[] ints : wireList) {
            int removedThis = ints[0]; // 무시할 전선의 한 쪽 송전탑
            int removedThat = ints[1]; // 무시할 전선의 다른 쪽 송전탑
            BitSet removed = new BitSet(wireList.length + 2);
            removed.set(removedThis); // 무시한 전선에 연결된 송전탑 비트마스킹
            removed.set(removedThat); // 반대 송전탑도 비트마스킹
            // 송전탑의 개수 = 전선갯수 + 1.
            // 하지만 송전탑 번호가 1부터 시작이므로, 전선 수보다 2만큼 큰 크기로 초기화해야한다.
            boolean[] visited = new boolean[wireList.length + 2];
            int partOne = 0;
            int partTwo = 0;
            boolean flag = false;
            // i 번째 간선 무시한 상태에서의 BFS 방문 로직
            for (int j = 1; j < wireList.length + 2; j++) {
                // 전의 탐색으로부터 도달한 정점이 아닌 정점에 대해서만 BFS 탐색 해야한다.
                if (!visited[j]) {
                    ArrayDeque<Integer> q = new ArrayDeque<>();
                    q.addLast(j);
                    while (!q.isEmpty()) {
                        int current = q.removeFirst(); // 큐 내의 송전탑은 미방문상태임이 보장되도록 구현했다.
                        visited[current] = true;
                        if (!flag) {
                            partOne++;
                        } // 만약 첫 부분 방문 중이라면 partOne을 증가.
                        else {
                            partTwo++;
                        } // 두 번째 부분 방문 중이라면 partTwo를 증가.
                        for (int k = 1; k < wireList.length + 2; k++) {
                            // 큐에 담는 것 : 다음 탐색 송전탑 대상
                            // 조건
                            // 1) 미방문한 송전탑일 것
                            // 2) 제거된 전선으로 연결된 송전탑들이 아닐 것
                            // 3) 현재 방문 중인 송전탑 current로부터 방문 가능한 송전탑일것.
                            if (!visited[k] && !(removed.get(current) && removed.get(k)) && connected[current][k]) {
                                q.addLast(k);
                            }
                        }
                        if (q.isEmpty()) {
                            flag = !flag;
                        }
                    }
                }

            }
            min = Math.min(min, Math.abs(partOne - partTwo));
        }
    }
}
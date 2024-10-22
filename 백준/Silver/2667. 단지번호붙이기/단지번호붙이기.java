import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.ArrayDeque;

/*
 0. 풀이 아이디어 : 무방향 그래프 시 BFS/DFS를 활용한 Connected Component 개수.

 */

public class Main{
    static int N;
    static int[][] board;
    static int complexCnt = 0;
    static boolean[][] visited;
    static int[] dRow = new int[]{-1, 1, 0, 0}; // 상 하 좌 우
    static int[] dCol = new int[]{0, 0, -1, 1}; // 상 하 좌 우

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        board = new int[N][N];
        visited = new boolean[N][N];
        ArrayList<int[]> starts = new ArrayList<>();
        ArrayList<Integer> houses = new ArrayList<>();

        // 1. 입력
        for(int row = 0; row < N; row ++){
            String cRow = br.readLine();
            for(int col = 0; col < N; col++){
                int current = Character.getNumericValue(cRow.charAt(col));
                board[row][col] = current;
                if(current == 1){starts.add(new int[]{row, col});}
            }
        }

        // 2. 아직 다른 단지 탐색으로부터 방문하지 못한 집으로부터 탐색 순차적으로 시작.
        for(int[] start : starts){
            if(!visited[start[0]][start[1]]){
                houses.add(BFS(start));
                complexCnt++;
            }
        }

        houses.sort((a1, a2) -> {return a1- a2;}); // 정렬

        // 3. 출력
        StringBuilder answer = new StringBuilder();
        answer.append(complexCnt);
        answer.append("\n");

        for(Integer houseCnt : houses){
            answer.append(houseCnt);
            answer.append("\n");
        }

        System.out.print(answer.toString().trim());


    }

    private static int BFS(int[] start){
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(start);
        visited[start[0]][start[1]] = true;

        int house = 1; // 현 단지 내 아파트 수.

        while(!q.isEmpty()){
            int[] current = q.removeFirst();
            int currentRow = current[0];
            int currentCol = current[1];

            for(int i = 0; i < 4; i++){
                int nextRow = currentRow + dRow[i];
                int nextCol = currentCol + dCol[i];

                // index 범위 안 & 미방문 & 집이 있음 -> 큐에 추가.
                if(isAvail(nextRow, nextCol) && !visited[nextRow][nextCol] && board[nextRow][nextCol] == 1){
                    q.addLast(new int[]{nextRow, nextCol});
                    visited[nextRow][nextCol] = true;
                    house++;
                }
            }
        }

        return house;
    }

    private static boolean isAvail(int row, int col){
        return row >= 0 && col >= 0 && row <= N-1 && col <= N-1;
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;

/*
 0. 핵심 아이디어 : 파이프가 걸친 좌표 두 개를 마치 하나 처럼 묶어서 다루는 그래프 탐색!
 1. 실패 했던 이유
    - BFS : 수평 및 수직 상태에서 (N - 1, N - 1) 위치가 아닌 벽에 다다랐을 경우,
            해당 위치에서 더 이상 진행이 안되므로 head 위치에 방문 표시를 하면 안됐다.
            실제로는 진행할 수 없는 위치임에도 해당 위치에 도달할 수 있는 경우의 수를 갱신함으로써,
            이후에 다른 경로를 통해 도달한 위치가 해당 위치와 인접할 경우,
            갱신 하는 과정에서 실제론 도달할 방안이 없는 경우의 수가 반영되어 합산되기 때문.
    - 벽의 존재 : 파이프가 두껍고, 대각선으로 움직이더라도 인접 위치에 벽이 있을 경우 '긁히기 때문에'
                이동이 불가능하다는 점을 간과했다.
 

 */

public class Main {
    static int[][] house;
    static int N;
    static int cnt = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        house = new int[N][N]; // House 구조.

        // 1. 입력 받기. O(N * N) : 최대 256 칸에 대한 연산이므로 큰 부담 X
        for(int row = 0; row < N; row++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int col = 0; col < N; col++){
                house[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        // 2. DFS 탐색.
        DFS(new int[][]{{0, 0}, {0, 1}});


        // 3. 출력
        System.out.print(cnt);

    }

    private static void DFS(int[][] current){
        int[] head = current[1];
        int headRow = head[0];
        int headCol = head[1];

        // Base Case I : N * N 위치 도달
        if(headRow == N - 1 && headCol == N - 1){cnt++; return;}

        // Base Case II : Dead-End 도달 -> 돌아가기.
        if(isDeadEnd(current)){return;}

        // Case I : 수평인 경우 : 대각 혹은 수평 이동 가능
        if(isHorizontal(current)){
            if(headCol < N - 1 && house[headRow][headCol + 1] != 1){
                DFS(new int[][]{{headRow, headCol}, {headRow, headCol + 1}}); // 수평
                if(headRow < N - 1
                        && house[headRow + 1][headCol] != 1
                        && house[headRow + 1][headCol + 1] != 1){
                    DFS(new int[][]{{headRow, headCol}, {headRow + 1, headCol + 1}}); // 대각
                }
            }
        }

        // Case II : 수직인 경우 : 대각 혹은 수직 이동 가능
        else if(isVertical(current)){
            if(headRow < N - 1 && house[headRow + 1][headCol] != 1){
                DFS(new int[][]{{headRow, headCol}, {headRow + 1, headCol}}); // 수직
                if(headCol < N - 1
                        && house[headRow + 1][headCol + 1] != 1
                        && house[headRow][headCol + 1] != 1){
                    DFS(new int[][]{{headRow, headCol}, {headRow + 1, headCol + 1}}); // 대각
                }
            }

        }

        // Case III : 대각인 경우 : 대각, 수평, 수직 이동 가능
        else {
            // 수평
            if(headCol < N - 1 && house[headRow][headCol + 1] != 1){
                DFS(new int[][]{{headRow, headCol}, {headRow, headCol + 1}});
            }
            // 수직
            if(headRow < N - 1 && house[headRow + 1][headCol] != 1){
                DFS(new int[][]{{headRow, headCol}, {headRow + 1, headCol}});
            }
            // 대각
            if(headRow < N - 1 && headCol < N - 1
                    && house[headRow][headCol + 1] != 1
                    && house[headRow + 1][headCol] != 1
                    && house[headRow + 1][headCol + 1] != 1){
                DFS(new int[][]{{headRow, headCol}, {headRow + 1, headCol + 1}});
            }
        }


    }

    // 수직인가
    private static boolean isVertical(int[][] pos){
        int[] tail = pos[0];
        int[] head = pos[1];
        if(tail[1] == head[1] && tail[0] != head[0]){return true;}
        return false;
    }

    // 수평인가
    private static boolean isHorizontal(int[][] pos){
        int[] tail = pos[0];
        int[] head = pos[1];
        if(tail[0] == head[0] && tail[1] != head[1]){return true;}
        return false;

    }

    private static boolean isDeadEnd(int[][] pos){
        // 수평 혹은 수직이나 앞부분(head)가 (N-1, N-1)이 아닌 한쪽 벽 끝에 도달한 경우 : Dead End.
        if(isVertical(pos)){
            if((pos[1][1] != N-1) && (pos[1][0] == N - 1)){return true;}
        }
        else if(isHorizontal(pos)){
            if((pos[1][0] != N - 1) && (pos[1][1] == N - 1)){return true;}
        }
        return false;
    }

}
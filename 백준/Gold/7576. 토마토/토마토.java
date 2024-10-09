import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.ArrayDeque;

/*
 - 시간 제한  : 1초
 - 메모리 제한 : 256MB
 - 입력 크기 : 1000 * 1000 = 1,000,000

 시간 제한에 비해 입력 크기가 상당하기 때문에 탐색한 위치를 반복적으로 탐색하는 일이 없어야한다.
 반복 탐색을 피하기 위해서는 어떤 시작점에서 출발했을 때 다른 시작점(익은 토마토) 만나는 경우 해당 경로 포기 해야한다.
 어차피 거기서 다시 탐색을 이어나가더라도, 추후 조우한 익은 토마토 위치에서 탐색을 하는 편이 더 빠르기 때문.
 또한 가능한 이동에 대해 비용이 균등하기 때문에, BFS는 최단 경로를 보장한다.
 이를 염두한 풀이 설계는 다음과 같다.

 1. 문자열 입력을 Parse 하여 2차원 배열에 데이터 입력. 이때 출발지점(1)이 1개 이상 존재하므로 리스트에 따로 저장 필요.  : O(N)
 2. 출발 지점 각각으로부터 순차적으로 BFS 출발.
 3. 각 출발 지점으로부터 도달 가능한 지점에 대한 탐색이 끝난 경우 1일 경과.
 4. 도중에 다른 출발점(시작부터 익어있는 토마토) 만난 경우 경로 포기.
 5. 상자 내 안 익은 토마토 전체 개수를 입력 당시부터 헤아려, 이후 BFS 탐색 시 익을 때마다 차감.
 6. 모든 익은 토마토로부터의 탐색이 끝난 시점에, 아직 안 익은 토마토가 남아있다면 정답은 -1.

 */

public class Main {

    static int[][] tab; // tab[p][q] : box[p][q] 위치 토마토가 익기까지 걸리는 최단 시간.
    static int[][] box; // 토마토 상자.
    static int M; // 열 최대
    static int N; // 행 최대
    
    static int max = -1;
    static int target = 0; // 안 익은 토마토 수.
    static int answer = -1; // 0 : 모두 다 익은 상태 / N : N일 / -1 : 다 익지 못하는 상태.

    static ArrayList<int[]> starts = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken()); // 가로
        N = Integer.parseInt(st.nextToken()); // 세로
        box = new int[N][M];
        tab = new int[N][M];

        // 처음부터 모두 다 익어 있을 수 있으므로(그 경우 0을 출력해야함)

        // 1. 입력 초기화. : O(N * M) _ 4<= N*M <= 1,000,000
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < M; col++){
                int current = Integer.parseInt(st.nextToken());

                box[row][col] = current; // 박스에 토마토 배치 정보 넣기.
                tab[row][col] = -1; // 도달 시간 담는 배열 초기화. -1로 초기화 하지 않을 경우, 처음부터 익은건지 아닌지 판단 불가.

                if(current == 1){starts.add(new int[]{row, col});} // 출발점 저장.
                if(current == 0){target++;} // 안 익은 토마토 수에 추가.
            }
        }

        // 2. 실행
        // Case I : 안 익은 토마토가 존재한다면 BFS.
        if(target != 0){
            BFS();
            answer = max;
        }
        else {
            answer = 0;
        }

        if(target != 0){answer = -1;}

        System.out.print(answer);

    }

    private static void BFS(){
        boolean[][] visited = new boolean[N][M]; // 이번 BFS 를 위한 방문 여부 판별 배열.
        ArrayDeque<int[]> q = new ArrayDeque<>(starts); // Queue 에 시작점 모두 삽입.
        
        for(int[] start : q){
            visited[start[0]][start[1]] = true; // 시작점 방문 표시
            tab[start[0]][start[1]] = 0; // 도달까지 소요시간 갱신.
        }

        while(!q.isEmpty()){
            int[] current = q.removeFirst(); // 현 위치.
            int currentRow = current[0];
            int currentCol = current[1];

            // 상, 하, 좌, 우 순 탐색 : 이동 가능한 위치에 대해서만 갱신 후 큐에 추가.
            int[] rowMove = new int[]{-1, 1, 0, 0};
            int[] colMove = new int[]{0, 0, -1, 1};

            for(int move = 0; move < 4; move++){
                int nextRow = currentRow + rowMove[move];
                int nextCol = currentCol + colMove[move];
                int[] nextLocation = new int[]{nextRow, nextCol};

                // 방문 가능 && 익은 토마토 아님 && 빈칸 아님 && 아직 미방문 위치에 대해서만 방문 표시 및 갱신
                if(isAvailable(nextLocation) && !visited[nextRow][nextCol]){
                    visited[nextRow][nextCol] = true; // 방문

                    if(tab[nextRow][nextCol] == -1 || tab[nextRow][nextCol] > tab[currentRow][currentCol] + 1){
                        if(tab[nextRow][nextCol] == -1){target--;}
                        tab[nextRow][nextCol] = tab[currentRow][currentCol] + 1; // 갱신.
                        if(tab[nextRow][nextCol] > max){max = tab[nextRow][nextCol];}
                    }

                    q.addLast(nextLocation); // Queue에 추가.
                }
            }
        }
    }

    // 이동하고자 하는 위치가 1) 옳은 인덱스 범위 내 위치, 2) 빈 칸이 아닐 것, 3) 익은 토마토 위치가 아닐 경우에만
    // TRUE. 나머지는 False.
    private static boolean isAvailable(int[] location){
        int locRow = location[0];
        int locCol = location[1];
        return locRow >= 0 && locRow <= N - 1 && locCol >= 0 && locCol <= M - 1
                && box[locRow][locCol] != -1 && box[locRow][locCol] != 1;
    }


}
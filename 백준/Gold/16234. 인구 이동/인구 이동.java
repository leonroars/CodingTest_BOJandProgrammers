import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.ArrayList;
import java.util.ArrayDeque;

public class Main {
    static int N;
    static int L; // 인구수 차이 하한.
    static int R; // 인구수 차이 상한.
    static int[][] A; // A[r][c] = (r, c) 위치 인구수
    static int moveCount = 0; // 이동 발생 일수.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        A = new int[N][N];

        // 0. A[][] 입력 받기 : O(N * N)
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < N; col++){
                A[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        // 1. 인구 이동 시작.
        populationMove();

        // 2. 이동이 지속된 일수 출력
        System.out.print(moveCount);


    }
    /*** 해당 연합 내 인구 이동 실시 ***/
    private static void populationMove(){
        int unionCount = 0; // 아직 연합 소속이 아닌 모든 지점으로부터 연합 파악.

        // K일 간의 인구 이동.
        while(unionCount < N * N){
            unionCount = 0; // 초기화.
            boolean[][] marked = new boolean[N][N]; // 맵 내 각 국의 연합 소속 상태 표시.

            // 1일 간의 인구 이동.
            for(int row = 0; row < N; row++){
                for(int col = 0; col < N; col++){
                    // 연합 소속 아닌 위치로부터 BFS 탐색 실시하여 속한 연합 파악하기.
                    if(!marked[row][col]){
                        Map.Entry<Integer, ArrayList<int[]>> currentUnion = BFS(row, col, marked);
                        movePeople(currentUnion);
                        unionCount++;
                    }
                }
            }

            // 구성국이 2개국 이상인 연합이 하나라도 존재했을 경우 -> 인구 이동 발생했으므로 갱신.
            if(unionCount < N * N){moveCount++;}
        }
    }

    /*** 특정 지점으로부터 이동가능한 하나의 연합 파악 후 <해당 연합 구성국의 '인구 수 총합', 구성국 위치 목록> 반환. ***/
    private static Map.Entry<Integer, ArrayList<int[]>> BFS(int startRow, int startCol, boolean[][] marked){
        int populationAccum = 0; // 해당 연합 소속국 인구수 총합.
        ArrayList<int[]> unionMember = new ArrayList<>(); // 해당 연합 소속국 위치 목록.
        int[] startPos = new int[]{startRow, startCol};

        ArrayDeque<int[]> q = new ArrayDeque<>();
        marked[startRow][startCol] = true; // 방문 처리.
        populationAccum += A[startRow][startCol];
        unionMember.add(startPos);
        q.addLast(startPos);

        int[] dRow = new int[]{-1, 1, 0, 0}; // 상하좌우
        int[] dCol = new int[]{0, 0, -1, 1}; // 상하좌우

        while(!q.isEmpty()){
            int[] current = q.removeFirst();

            for(int d = 0; d < 4; d++){
                int nextRow = current[0] + dRow[d];
                int nextCol = current[1] + dCol[d];
                int[] next = new int[]{nextRow, nextCol};
                if(isPossible(next) && !marked[nextRow][nextCol] && isUnion(current, next)){
                    q.addLast(next);
                    unionMember.add(next);
                    populationAccum += A[nextRow][nextCol];
                    marked[nextRow][nextCol] = true; // 방문 표시.
                }
            }
        }

        return new SimpleImmutableEntry<>(populationAccum, unionMember);
    }

    /*** 해당 연합 내 인구 이동 실시 ***/
    private static void movePeople(Map.Entry<Integer, ArrayList<int[]>> unionMember){
        int populationAccum = unionMember.getKey();
        ArrayList<int[]> members = unionMember.getValue();

        // Case A : 연합 구성국이 2개국 이상인 경우 -> 주어진 규칙에 따라 인구 이동(분할)
        if(members.size() > 1){
            int populationPerCountry = populationAccum / members.size();
            for(int[] member : members){
                A[member[0]][member[1]] = populationPerCountry;
            }
        }
        // Case B : 연합 구성국이 1개국인 경우 -> 이동이 발생하지 않았으므로 그대로 둔다.
    }

    /*** 주어진 두 위치가 같은 연합을 구성하는지 확인 후 true / false 반환. ***/
    private static boolean isUnion(int[] thisCountry, int[] thatCountry){
        int diff = Math.abs(A[thisCountry[0]][thisCountry[1]] - A[thatCountry[0]][thatCountry[1]]);
        return diff >= L && diff <= R;
    }

    private static boolean isPossible(int[] pos){
        return pos[0] >= 0 && pos[0] < N && pos[1] >= 0 && pos[1] < N;
    }
}
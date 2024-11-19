import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    static int N;
    static int M;
    static int[][] A; // A[r][c] : (r, c) 위치 물의 양
    static boolean[][] marked; // 현 구름 위치 표시.
    static ArrayList<int[]> cloud; // 현재 구름을 이루는 위치의 좌표를 저장한 HashSet;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        A = new int[N][N];
        marked = new boolean[N][N];
        cloud = new ArrayList<>();
        cloud.add(new int[]{N - 2, 0});
        cloud.add(new int[]{N - 2, 1});
        cloud.add(new int[]{N - 1, 0});
        cloud.add(new int[]{N - 1, 1});

        // 현 구름 위치 방문 표시.
        for(int[] cloudFrag : cloud){
            marked[cloudFrag[0]][cloudFrag[1]] = true;
        }

        // 0. 입력 받기
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < N; col++){
                A[row][col] = Integer.parseInt(st.nextToken());
            }
        }

        // 1. 이동하며 로직 실시.
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken()); // 방향
            int s = Integer.parseInt(st.nextToken()); // 거리
            doMagic(d, s);
        }

        // 2. 현 구름 위치의 바구니 순회하며 물의 양 합 구하기.
        int sum = 0;
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                sum += A[row][col];
            }
        }

        // 3. 출력
        System.out.print(sum);
    }

    // 마법 시행 : 구름 이동 + 이어지는 다른 행위 처리하는 메서드 호출
    private static void doMagic(int d, int s){

        // 이전의 구름 마킹 상태 지워주기.
        for(int row = 0; row < N; row++){
            Arrays.fill(marked[row], false);
        }

        cloud = moveCloud(d, s);

        // 해당 위치 비 내리기.
        rain();

        // 2) 물복사버그 시행.
        copyWater();

        // 3) 조건에 맞는 새 구름 생성하기.
        makeNewCloud();

    }

    private static ArrayList<int[]> moveCloud(int d, int s){
        ArrayList<int[]> movedCloud = new ArrayList<>();

        // 1) 현재 구름 -> d 방향 s만큼 이동.
        for(int[] cloudFrag : cloud){
            int[] nextFrag = nextPos(cloudFrag, d, s);
            movedCloud.add(nextFrag);
        }

        return movedCloud;
    }

    // 현 구름 위치에 비 내리기 -> 해당 위치 바구니에 물++.
    private static void rain(){
        for(int[] cloudFrag : cloud){
            A[cloudFrag[0]][cloudFrag[1]] += 1;
            marked[cloudFrag[0]][cloudFrag[1]] = true; // 구름 위치 마킹
        }
    }

    private static void copyWater(){
        for(int[] cloudFrag : cloud){
            int increment = searchDiagonal(cloudFrag);
            A[cloudFrag[0]][cloudFrag[1]] += increment;
        }
    }

    // 새로운 구름 생성
    private static void makeNewCloud(){
        ArrayList<int[]> newCloud = new ArrayList<>();
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                // 현 구름 위치가 아니며 조건을 만족 하는 경우 -> 새 구름 조각
                if(!marked[row][col] && A[row][col] >= 2){
                    A[row][col] -= 2;
                    newCloud.add(new int[]{row, col});
                }
            }
        }

        // 구름 교체.
        cloud = newCloud;
    }

    // 주어진 위치가 d 방향 s만큼 움직인 이후의 위치. - Clear
    private static int[] nextPos(int[] currentPos, int d, int s){
        int currentRow = currentPos[0];
        int currentCol = currentPos[1];
        int[][] distByDirection
                = new int[][]{{0, 0}, {0, s * -1}, {s * -1, s * - 1}, {s * -1, 0}, {s * -1, s}, {0, s}, {s, s}, {s, 0}, {s, s * -1}};
        int newRow = calculateMovedIdx(currentRow, distByDirection[d][0]);
        int newCol = calculateMovedIdx(currentCol, distByDirection[d][1]);

        return new int[]{newRow, newCol};
    }

    // 주어진 위치의 대각 방향(경계 넘어가기 X) 물의 양이 존재하는 칸의 수 반환. - Clear
    private static int searchDiagonal(int[] pos){
        int cnt = 0;
        int[][] distByDirection
                = new int[][]{{0, 0}, {0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}};
        for(int d = 2; d < 10; d += 2){
            int diagonalRow = pos[0] + distByDirection[d][0];
            int diagonalCol = pos[1] + distByDirection[d][1];
            int[] diagonalPot = new int[]{diagonalRow, diagonalCol};
            // 범위 내에 존재 && 바구니 내 물의 양이 1 이상인 경우를 센다!
            if(isInRange(diagonalPot) && A[diagonalRow][diagonalCol] > 0){cnt++;}
        }

        return cnt;
    }

    // 해당 방향, 위치 만큼 이동한 후의 idx 반환. - Clear
    private static int calculateMovedIdx(int idx, int sWithDirection){
        if(sWithDirection < 0){
            return (N + (idx - (Math.abs(sWithDirection) % N))) % N;
        }
        else {
            return (N + (idx + (Math.abs(sWithDirection) % N))) % N;
        }
    }

    // 범위 내 존재하는지 확인. - Clear
    private static boolean isInRange(int[] pos){
        int row = pos[0];
        int col = pos[1];

        return row >= 0 && row < N && col >= 0 && col < N;
    }
}
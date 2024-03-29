import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// 풀이 및 접근
//
// 1. 실패한 풀이
//   1) 'N*N 체스보드 위의 모든 위치에 첫 퀸을 두고 나머지 가능한 위치에 다른 퀸을 둬보자'
//       - 성공적으로 배치한 경우에 대해, 1~N번째 퀸의 위치가 서로 바뀌는 경우를 중복으로 세게 되므로 실패.
//       Lesson : 'N*N 체스보드에 N개의 퀸이 서로 공격받지 않는 위치에 존재하기 위해선,
//                 두 개 이상의 퀸이 하나의 행/열에 존재해선 안된다'는 문제의 숨겨진 조건 발견
//   2) 위에서 발견한 제약조건을 반영
//      - 위에서 발견한 문제의 조건에 입각해, 가능한 시나리오를 재귀 함수를 이용해 탐색.
//        이때, 이전 함수 호출에서 선택한 퀸의 위치에서 도달 가능한 지점을 표시한 Board를 복사해 다음 함수 호출에 전달.
//      -> 메모리 초과로 인한 실패
//      New Idea : 어차피 매 함수 호출마다 반복문을 이용해 '현재 위치가 가능한지 아닌지'를 파악해야한다면
//               매번 보드를 복사하는 것보단, 어떤 위치에 놓는다고 우선 가정하고 그 위치로부터 퀸의 움직임 규칙 그대로 움직여보며
//               다른 퀸이 존재하는지 아닌지 탐색하는 것이 더 낫지 않을까?

public class Main{
    static int N; // 보드의 가로 세로 길이. 놓을 퀸의 수
    static int count; // 가능한 경우의 수를 담은 변수.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        count = 0;
        boolean[][] startBoard = new boolean[N][N];

        putQueen(0, startBoard);

        System.out.print(count);
    }

    private static void putQueen(int rowY, boolean[][] checked){
        // Base Case : rowY가 가능한 행 범위를 벗어난 경우.
        if(rowY == N){count++; return;}
        for(int colX = 0; colX < N; colX++){
            // 현재 퀸을 놓을 것으로 가정한 위치로부터 도달 가능한 지점에 놓인 다른 퀸이 없는 경우
            if(!isOtherQueen(checked, rowY, colX)){
                checked[rowY][colX] = true; // 퀸 배치
                putQueen(rowY + 1, checked); // 해당 퀸 배치에 따라 다음 퀸 배치 가능 위치 재귀 탐색
                checked[rowY][colX] = false; // 해당 시나리오 탐색 완료했으므로 퀸 배치 해제.
            }
        }
    }

    // Supposing that the new queen of current row(y) is located at column x,
    // Check if there's any other queens reachable from the supposed location.
    private static boolean isOtherQueen(boolean[][] checked, int y, int x){
        boolean result = false;
        int currentY = y;
        int currentX = x;
        int d = 1; // Difference.

        while(currentY > 0){
            currentY -= 1;
            // 퀸을 둘 것으로 가정한 위치로부터 왼쪽 대각선 방향 탐색
            if(currentX - d >= 0){
                if(checked[currentY][currentX - d]){result = true; break;} // 퀸 발견
            }
            // 퀸을 둘 것으로 가정한 위치로부터 오른쪽 대각선 방향 탐색
            if(currentX + d < N){
                if(checked[currentY][currentX + d]){result = true; break;}
            }
            // 퀸을 둘 것으로 가정한 위치로부터 위쪽 직선 방향 탐색
            if(checked[currentY][currentX]){result = true; break;}

            d++; // Difference Increment.
        }

        return result;
    }
}
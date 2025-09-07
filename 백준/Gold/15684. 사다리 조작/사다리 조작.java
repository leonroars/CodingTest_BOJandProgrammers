import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int N; // 세로줄 수(1~N)
    static int M; // 입력 가로줄 수
    static int H; // 가로줄 수(가로줄 범위)
    static int min = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());
        
        // board[j][i] : (j번 가로줄, i번 가로줄) 에서 (j번 가로줄, i+1번 세로줄)로의 이동 경로 있는가.
        // 이후 순회시, 세로로 내려가다가 true 인 지점을 발견하면? -> 해당 지점의 좌/우 세로줄도 true인지 확인하고 true인 곳으로 이동.
        boolean[][] board = new boolean[H + 2][N + 1];
        
        // 1. 입력 받기
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            board[x][y] = true;
        }
        
        // 2. 메서드 호출
        placeLadder(0, board);
        
        // 3. 출력
        // 사다리를 최대 3번까지 놓는 모든 경우의 수를 탐색했으나 찾지 못한 경우
        if(min == Integer.MAX_VALUE){min = -1;}
        
        System.out.print(min);
    }
    
    // 가로줄을 하나씩 잡아 오른쪽으로 탐색하며 조건 만족할 경우 사다리 놓기
    public static void placeLadder(int depth, boolean[][] board){
        // Non-promising 한 경로 prunning.
        if(depth >= min){return;}
        
        if(depth == 3){
            if(checkDest(board)){min = Math.min(min, depth);}
            return;
        }
        else {
            // 사다리 놓기 전 현재 상태에서 가능한 경우 : 더 이상의 탐색은 유리하지 않으므로 return.
            if(checkDest(board)){
                min = Math.min(min, depth);
                return;
            }
            // 사다리를 놓아야 하는 경우 : 하나씩 놓고 다음 호출.
            else {
                boolean[][] copied = copyBoard(board);
                for(int x = 1; x < H + 1; x++){
                    for(int y = 1; y < N + 1; y++){
                        if(!copied[x][y] && y < N && !copied[x][y+1]){
                            // 1) 사다리 놓기
                            copied[x][y] = true;
                            placeLadder(depth + 1, copied);
                            copied[x][y] = false;
                        }
                    }
                }
            }
            return;
        }
    }
    
    public static boolean checkDest(boolean[][] currentBoard){
        
        boolean isPossible = true;
        
        // 1~N 번 y줄 각각에 대하여,
        for(int y = 1; y < N + 1; y++){
            int cursorY = y;
            int cursorX = 1;
            
            // cursorX가 H에 도달하면 도착한 것.
            while(cursorX < H + 1){
                // Case I : (cursorX, cursorY) 위치에서 오른쪽으로 가는 사다리 존재
                if(currentBoard[cursorX][cursorY]){
                    cursorY++; cursorX++;
                }
                
                // Case II : (cursorX, cursorY) 위치에서 오른쪽으로 가는 사다리 X. 왼쪽 확인해야함.
                else {
                    // 왼쪽으로 이동
                    if(cursorY > 1 && currentBoard[cursorX][cursorY - 1]){
                        cursorX++; cursorY--;
                    }
                    // 아래로 이동.
                    else {
                        cursorX++;
                    }
                }
            }
            if(cursorY != y){isPossible = false; break;}
        }
        return isPossible;
    }
    
    public static boolean[][] copyBoard(boolean[][] original){
        boolean[][] newBoard = new boolean[H + 2][N + 1];
        for(int y = 0; y < N + 1; y++){
            for(int x = 0; x < H + 2; x++){
                newBoard[x][y] = original[x][y];
            }
        }
        return newBoard;
    }
}
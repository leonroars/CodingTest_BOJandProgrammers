import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int N; // 보드 크기
    static int max = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        
        Block[][] board = new Block[N][N];
        
        for(int row = 0; row < N; row++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int col = 0; col < N; col++){
                int num = Integer.parseInt(st.nextToken());
                if(num != 0){board[row][col] = new Block(num);}
            }
        }
        
        backtrack(board, 0);
        
        System.out.print(max);
    }
    
    public static void backtrack(Block[][] board, int depth){
        // Base-case : 5번 모두 움직인 경우 합산 및 최대 갱신
        if(depth == 5){
            int currentMax = calculateMax(board);
            max = Math.max(max, currentMax);
            return;
        }
        
        // General-Cases : 네 방향으로 재귀적 탐색
        else {
            // 최소 1회 이동한 보드판은 받자마자 각 블럭 isComposed 상태 초기화 필요.
            if(depth > 0){initBoardStatus(board);}
            for(int d = 0; d < 4; d++){
                backtrack(move(copyBoard(board), d), depth + 1);
                
            }
        }
    }
    
    private static int calculateMax(Block[][] board){
        int currentMax = 0;
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                if(board[row][col] != null){currentMax = Math.max(currentMax, board[row][col].num);}
            }
        }
        
        return currentMax;
    }
    
    private static Block[][] copyBoard(Block[][] board){
        Block[][] newBoard = new Block[N][N];
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                if(board[row][col] != null){newBoard[row][col] = new Block(board[row][col].num, board[row][col].isComposed);}
            }
        }
        
        return newBoard;
    }
    
    private static void initBoardStatus(Block[][] board){
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                if(board[row][col] != null){
                    board[row][col].isComposed = false;
                }
            }
        }
    }
    
    private static Block[][] move(Block[][] board, int direction){
        // 0 : 상
        if(direction == 0){return moveUp(board);}
        
        // 1: 하
        else if(direction == 1){return moveDown(board);}
        
        // 2: 좌
        else if(direction == 2){return moveLeft(board);}
        
        // 3: 우
        return moveRight(board);
    }
    
    private static Block[][] moveUp(Block[][] board){
        // 위에서 두 번째 행에 위치한 블럭부터 순차적으로 가장 위로 옮기기(가장 위의 행 블럭은 움직일 필요가 없다.)
        for(int row = 1; row < N; row++){
            for(int col = 0; col < N; col++){
                
                // 빈 칸이 아닌 경우 블럭을 가장 위로 옮긴다.
                if(board[row][col] != null){
                    boolean isLocated = false;
                    int targetRow = row - 1; // 현 위치보다 한 줄 앞을 목표로 설정.
                    while(!isLocated && targetRow >= 0){
                        // Case I : [targetRow][col] == 빈칸 -> 이동 후 앞으로 마저 전진
                        if(board[targetRow][col] == null){
                            board[targetRow][col] = board[targetRow + 1][col];
                            board[targetRow + 1][col] = null;
                            targetRow--;
                        }
                        
                        // Case II : 합칠 수 있는 경우와 없는 경우.
                        else {
                            // 숫자 같고 && 이미 있던 자리 블럭이 !isComposed 일 때만 병합 및 전진(가능할때).
                            // 그 외 케이스에선 전진 불가.
                            if(board[targetRow][col].num == board[targetRow + 1][col].num
                               && !board[targetRow][col].isComposed
                               && !board[targetRow + 1][col].isComposed){
                                Block newBlock = new Block(board[targetRow][col].num * 2, true);
                                board[targetRow][col] = newBlock; // Overwrite
                                board[targetRow + 1][col] = null; // 병합했으므로 삭제.
                                targetRow--;
                            }
                            else {
                                isLocated = true;
                            }
                        }
                    }
                }
            }
        }
        
        return board;
    }
    
    private static Block[][] moveDown(Block[][] board){
        for(int row = N - 2; row >= 0; row--){
            for(int col = 0; col < N; col++){
                
                if(board[row][col] != null){
                    boolean isLocated = false;
                    int targetRow = row + 1;
                    while(!isLocated && targetRow < N){
                        if(board[targetRow][col] == null){
                            board[targetRow][col] = board[targetRow - 1][col];
                            board[targetRow - 1][col] = null;
                            targetRow++;
                        }
                        else {
                            if(board[targetRow][col].num == board[targetRow - 1][col].num
                               && !board[targetRow][col].isComposed
                               && !board[targetRow - 1][col].isComposed){
                                Block newBlock = new Block(board[targetRow][col].num * 2, true);
                                board[targetRow][col] = newBlock;
                                board[targetRow - 1][col] = null;
                                targetRow++;
                            }
                            else {
                                isLocated = true;
                            }
                        }
                    }
                }
            }
        }
        return board;
    }
    
    private static Block[][] moveLeft(Block[][] board){
        for(int col = 1; col < N; col++){
            for(int row = 0; row < N; row++){
                if(board[row][col] != null){
                    boolean isLocated = false;
                    int targetCol = col - 1;
                    while(!isLocated && targetCol >= 0){
                        if(board[row][targetCol] == null){
                            board[row][targetCol] = board[row][targetCol + 1];
                            board[row][targetCol + 1] = null;
                            targetCol--;
                        }
                        else {
                            if(board[row][targetCol].num == board[row][targetCol + 1].num
                               && !board[row][targetCol].isComposed
                               && !board[row][targetCol + 1].isComposed){
                                Block newBlock = new Block(board[row][targetCol].num * 2, true);
                                board[row][targetCol] = newBlock;
                                board[row][targetCol + 1] = null;
                                targetCol--;
                            }
                            else {
                                isLocated = true;
                            }
                        }
                    }
                }
            }
        }
        return board;
    }
    
    private static Block[][] moveRight(Block[][] board){
        for(int col = N - 2; col >= 0; col--){
            for(int row = 0; row < N; row++){
                
                if(board[row][col] != null){
                    boolean isLocated = false;
                    int targetCol = col + 1;
                    while(!isLocated && targetCol < N){
                        if(board[row][targetCol] == null){
                            board[row][targetCol] = board[row][targetCol - 1];
                            board[row][targetCol - 1] = null;
                            targetCol++;
                        }
                        else {
                            if(board[row][targetCol].num == board[row][targetCol - 1].num
                               && !board[row][targetCol].isComposed
                               && !board[row][targetCol - 1].isComposed){
                                Block newBlock = new Block(board[row][targetCol].num * 2, true);
                                board[row][targetCol] = newBlock;
                                board[row][targetCol - 1] = null;
                                targetCol++;
                            }
                            else {
                                isLocated = true;
                            }
                        }
                    }
                }
            }
        }
        return board;
    }
    
    static class Block {
        int num;
        boolean isComposed;
        
        public Block(int number){
            this.num = number;
            this.isComposed = false;
        }
        
        public Block(int number, boolean whetherComposed){
            this.num = number;
            this.isComposed = whetherComposed;
        }
    }
}
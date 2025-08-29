import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static int[][] board = new int[9][9];
    static ArrayList<int[]> blanks = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 0. Board 입력 받기 + 빈 칸 좌표 추리기
        for(int r = 0; r < 9; r++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int c = 0; c < 9; c++){
                board[r][c] = Integer.parseInt(st.nextToken());
                if(board[r][c] == 0){blanks.add(new int[]{r, c});}
            }
        }
        
        // 2. fillBoard 메서드 호출
        fillBoard(0);
        
        // 3. 완성된 스도쿠 보드 출력
        StringBuilder answer = new StringBuilder();
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                answer.append(board[row][col]);
                if(col < 8){answer.append(" ");}
            }
            if(row < 8){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    public static boolean fillBoard(int blankIdx){
        int[] currentPos = blanks.get(blankIdx);
        boolean[] available = new boolean[10]; // 1~9 까지의 수 중 가능한 수 목록
        Arrays.fill(available, true);
        
        available = checkVertical(currentPos[0], currentPos[1], available);
        available = checkHorizontal(currentPos[0], currentPos[1], available);
        available = checkSector(currentPos[0], currentPos[1], available);
        
        // Base Case
        if(blankIdx == blanks.size() - 1){
            ArrayList<Integer> availableNum = new ArrayList<>();
            for(int i = 1; i < 10; i++){
                if(available[i]){availableNum.add(i);}
            }
            
            if(availableNum.size() == 1){
                board[currentPos[0]][currentPos[1]] = availableNum.get(0);
                return true;
            }
            return false;
        }
        else {
            boolean isFound = false;
            for(int num = 1; num < 10; num++){
                if(available[num]){
                    board[currentPos[0]][currentPos[1]] = num; // 할당
                    isFound = fillBoard(blankIdx + 1); // 다음 칸 채우기
                    
                    // Case A : 현재 선택지 경로를 통해 정답 찾은 경우 -> 종료
                    if(isFound){break;}
                    else {board[currentPos[0]][currentPos[1]] = 0;}
                }
            }
            return isFound;
        }
    }
    
    
    private static boolean[] checkVertical(int row, int col, boolean[] available){
        for(int r = 0; r < 9; r++){
            available[board[r][col]] = false;
        }
        
        return available;
    }
    
    private static boolean[] checkHorizontal(int row, int col, boolean[] available){
        for(int c = 0; c < 9; c++){
            available[board[row][c]] = false;
        }
        
        return available;
    }
    
    private static boolean[] checkSector(int row, int col, boolean[] available){
        int sectorStartRow = (row / 3) * 3;
        int sectorStartCol = (col / 3) * 3;
        
        for(int r = sectorStartRow; r < sectorStartRow + 3; r++){
            for(int c = sectorStartCol; c < sectorStartCol + 3; c++){
                available[board[r][c]] = false;
            }
        }
        
        return available;
    }
}
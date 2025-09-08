import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Main {
    static int[][] board = new int[9][9];
    static ArrayList<int[]> blanks = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 0. 입력 받기
        for(int r = 0; r < 9; r++){
            String row = br.readLine();
            for(int c = 0; c < 9; c++){
                board[r][c] = Character.getNumericValue(row.charAt(c));
                if(board[r][c] == 0){blanks.add(new int[]{r, c});}
            }
        }
        
        // 1. 보드 채우기 : 찾으면 바로 조기 종료. 순서 정의를 통해 사전순 결과 보장.
        fillBlank(0);
        
        // 2. 보드 출력
        StringBuilder result = new StringBuilder();
        
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                result.append(board[r][c]);
            }
            if(r < 8){result.append("\n");}
        }
        
        System.out.print(result.toString());
    }
    
    public static boolean fillBlank(int blankIdx){
        // Base case : 모두 채웠으므로 성공했다는 뜻.
        if(blankIdx == blanks.size()){
            return true;
        }
        else {
            int[] blank = blanks.get(blankIdx);
            int numPool = getAvailable(blank[0], blank[1]);
            
            for(int i = 1; i < 10; i++){
                if((numPool & (1<<i)) != 0){
                    board[blank[0]][blank[1]] = i; // 채우기
                    boolean result = fillBlank(blankIdx + 1); // 다음 칸 재귀 호출.
                    if(result){return true;}
                    
                    else{board[blank[0]][blank[1]] = 0;} // 원복
                }
            }
        }
        return false; // 반환 없이 현 위치 도달한 경우 글러먹은 것.
    }
    
    public static int getAvailable(int row, int col){
        int availableInRow = checkRow(row, 0);
        int availableInCol = checkCol(col, 0);
        int availableInArea = checkArea(row, col, 0);
        
        return ~(availableInRow | availableInCol | availableInArea) & 0b1111111110;
    }
    
    public static int checkRow(int row, int numPool){
        for(int c = 0; c < 9; c++){
            if(board[row][c] != 0){
                numPool |= (1 << board[row][c]);
            }
        }
        return numPool;
    }
    
    public static int checkCol(int col, int numPool){
        for(int r = 0; r < 9; r++){
            if(board[r][col] != 0){numPool |= (1 << board[r][col]);}
        }
        return numPool;
    }
    
    public static int checkArea(int row, int col, int numPool){
        int sectorRowStart = row / 3 * 3;
        int sectorColStart = col / 3 * 3;
        
        for(int r = sectorRowStart; r < sectorRowStart + 3; r++){
            for(int c = sectorColStart; c < sectorColStart + 3; c++){
                if(board[r][c] != 0){numPool |= (1 << board[r][c]);}
            }
        }
        
        return numPool;
    }
    
    
}
import java.util.Arrays;

class Solution {
    
    int[][] board;
    
    public int[] solution(int[][] arr) {
        board = arr;
        int[] answer = quadTree(0, 0, board.length);
        
        return answer;
    }
    
    private int[] quadTree(int row, int col, int length){
        
        // Base Case
        if(length == 1){
            if(board[row][col] == 1){return new int[]{0, 1};}
            else{return new int[]{1, 0};}
        }
        
        int currentLength = length / 2;
        
        int[] fQ = quadTree(row, col + currentLength, currentLength);
        int[] sQ = quadTree(row, col, currentLength);
        int[] tQ = quadTree(row + currentLength, col, currentLength);
        int[] foQ = quadTree(row + currentLength, col + currentLength, currentLength);
        
        int[] allOne = new int[]{0, 1};
        int[] allZero = new int[]{1, 0};
        
        if(Arrays.equals(allOne, fQ) && Arrays.equals(allOne, sQ)
          && Arrays.equals(allOne, tQ) && Arrays.equals(allOne, foQ)){
            return allOne;
        }
        if(Arrays.equals(allZero, fQ) && Arrays.equals(allZero, sQ)
          && Arrays.equals(allZero, tQ) && Arrays.equals(allZero, foQ)){
            return allZero;
        }
        
        int[] ans = new int[]{fQ[0] + sQ[0] + tQ[0] + foQ[0], fQ[1] + sQ[1] + tQ[1] + foQ[1]};
        return ans;
    }
}
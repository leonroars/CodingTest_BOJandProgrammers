import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int X = Integer.parseInt(br.readLine());
        
        // 1. 메서드 호출
        String answer = getFraction(X);
        
        // 2. 출력
        System.out.print(answer);
        
    }
    
    public static String getFraction(int X){
        // 1. X가 속한 줄의 가장 첫 머리에 위치한 순번의 행/열/ 좌표 + 순번 구하기
        int[] head = getLocationOfHead(X);
        
        // 2. 해당 순번으로부터 X의 행/열 좌표 구하기.
        StringBuilder answer = new StringBuilder();
        
        if(head[0] == X){
            answer.append(head[1]);
            answer.append("/");
            answer.append(head[2]);
        }
        else {
            int[] XLocation = getXLocation(head, X);
            answer.append(XLocation[0]);
            answer.append("/");
            answer.append(XLocation[1]);
        }
        
        return answer.toString();
    }
    
    private static int[] getLocationOfHead(int X){
        // 1) X 가 몇 번째 줄에 위치한지 파악하기. 홀수/짝수도 파악.
        int lineNum = 0;
        int adder = 1;
        int turnOfHead = 0;
        boolean isOdd = false;
        
        while(turnOfHead < X){
            turnOfHead += adder;
            isOdd = !isOdd;
            lineNum++;
            adder++;
        }
        
        // 이 시점의 lineNum 과 같은 줄에 X 위치.
        int headRow;
        int headCol;
        
        if(!isOdd){
            headRow = lineNum;
            headCol = 1;
        }
        else {
            headCol = lineNum;
            headRow = 1;
        }
        
        return new int[]{turnOfHead, headRow, headCol};
    }
    
    private static int[] getXLocation(int[] headLocation, int X){
        int turnOfHead = headLocation[0];
        int headRow = headLocation[1];
        int headCol = headLocation[2];
        
        // Case I : 같은 경우는 1/1 밖에 없음.
        if(headRow == headCol){return new int[]{1, 1};}
        
        int diff = Math.abs(X - turnOfHead);
        int[] XLocation = new int[2];
        
        if(headRow < headCol){
            XLocation[0] = headRow + diff;
            XLocation[1] = headCol - diff;
        }
        else {
            XLocation[0] = headRow - diff;
            XLocation[1] = headCol + diff;
        }
        
        return XLocation;
    }
}
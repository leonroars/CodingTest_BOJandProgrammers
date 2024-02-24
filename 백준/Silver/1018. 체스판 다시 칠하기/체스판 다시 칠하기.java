import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        char[][] board = new char[N][M];

        char[] bFirst = new char[8];
        char[] wFirst = new char[8];

        int replaceCount = 2500;

        for(int k = 0; k < 8; k++){
            if(k % 2 == 0){bFirst[k] = 'B'; wFirst[k] = 'W';}
            else{bFirst[k] = 'W'; wFirst[k] = 'B';}
        }



        // Board Initialize
        for(int i = 0; i < N; i++){
            String row = br.readLine();
            for(int j = 0; j < M; j++){
                board[i][j] = row.charAt(j);
            }
        }

        // 0 1 2 3 4 5 6 7 8 9
        // 1 1 1 1 1 1 1 1 1 1
        // 8*8 단위로 반복문 돌기
        int nCap = N - 8 + 1;
        int mCap = M - 8 + 1;

        for(int yFieldCount = 0; yFieldCount < nCap; yFieldCount++){
            for(int xFieldCount = 0; xFieldCount < mCap; xFieldCount++){
                int BcurrentCount = 0;
                int WcurrentCount = 0;
                boolean trigger = (board[yFieldCount][xFieldCount] == 'W');// false인 경우 B로 시작하는 8자 한 줄, true일 경우 W로 시작하는 8자 한 줄.
                for(int yLoc = yFieldCount; yLoc < yFieldCount + 8; yLoc++){
                    for(int xLoc = xFieldCount, xcursor = 0; xLoc < xFieldCount + 8; xLoc++, xcursor++){
                        if(!trigger){
                            if(board[yLoc][xLoc] != bFirst[xcursor]){BcurrentCount++;}
                            else{WcurrentCount++;}
                        }
                        else{
                            if(board[yLoc][xLoc] != wFirst[xcursor]){BcurrentCount++;}
                            else{WcurrentCount++;}
                        }
                    }
                    trigger = !trigger;
                }
                int smaller = Math.min(BcurrentCount, WcurrentCount);
                if(smaller < replaceCount){replaceCount = smaller;}
            }
        }

        System.out.println(replaceCount);



    }
}
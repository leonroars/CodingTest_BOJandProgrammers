import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main{
    static boolean[][] board;

    static int N; // Board Width & Height

    static void createBoard(String row, int col){
        StringTokenizer st = new StringTokenizer(row);
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(st.nextToken());
            if(current == 0){board[col][i] = false;}
            if(current == 1){board[col][i] = true;}
        }
    }

    static int[] blueWhite(int startY, int startX, int width){
        if(width == 1){
            if(board[startY][startX]){return new int[]{1, 0};}
            return new int[]{0, 1};
        }

        int localBlue = 0; // The number of blue paper in current quadrant.
        int localWhite = 0; // The number of white paper in current quadrant.

        int[] first = blueWhite(startY, startX, width / 2);
        localBlue += first[0];
        localWhite += first[1];

        int[] second = blueWhite(startY, startX + width / 2, width / 2);
        localBlue += second[0];
        localWhite += second[1];

        int[] third = blueWhite(startY + width / 2, startX, width / 2);
        localBlue += third[0];
        localWhite += third[1];

        int[] fourth = blueWhite(startY + width / 2, startX + width / 2, width / 2);
        localBlue += fourth[0];
        localWhite += fourth[1];

        if(localBlue == 4 && localWhite == 0){return new int[]{1, 0};}
        else if(localWhite == 4 && localBlue == 0){return new int[]{0, 1};}
        return new int[]{localBlue, localWhite};
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        board = new boolean[N][N];

        for(int i = 0; i < N; i++){
            String row = br.readLine();
            createBoard(row, i);
        }
        br.close();

        int[] blueAndWhite = blueWhite(0, 0, N);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(Integer.toString(blueAndWhite[1]));
        bw.write("\n");
        bw.write(Integer.toString(blueAndWhite[0]));
        bw.flush();
        bw.close();
    }
}
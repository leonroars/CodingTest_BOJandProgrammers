import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        int[][] matrixSum;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        matrixSum = new int[N][M];
        for(int i  = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                int mn = Integer.parseInt(st.nextToken());
                matrixSum[i][j] += mn;
            }
        }

        StringBuilder sb = new StringBuilder();

        for(int i  = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                int mn = Integer.parseInt(st.nextToken());
                matrixSum[i][j] += mn;
                sb.append(matrixSum[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }
}
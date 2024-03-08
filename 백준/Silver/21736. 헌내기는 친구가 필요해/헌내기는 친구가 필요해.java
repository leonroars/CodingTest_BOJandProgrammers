import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main{
    static int N;
    static int M;
    static int startX;
    static int startY;
    static int[] dx = new int[]{0, 0, -1, 1};
    static int[] dy = new int[]{1, -1, 0, 0};
    static char[][] map;
    static boolean[][] visited;
    static int encountered;

    static void bfs(){
        encountered = 0;
        visited[startY][startX] = true;
        ArrayDeque<int[]> q = new ArrayDeque<>();
        q.addLast(new int[]{startY, startX});
        while(!q.isEmpty()) {
            int[] current = q.removeFirst();
            for (int i = 0; i < 4; i++) {
                int nextY = current[0] + dy[i];
                int nextX = current[1] + dx[i];
                if (nextX >= 0 && nextX < M && nextY >= 0 && nextY < N && map[nextY][nextX] != 'X') { // 갈 수 있는 범위 내에서
                    if (map[nextY][nextX] == 'O' && !visited[nextY][nextX]) {
                        visited[nextY][nextX] = true;
                        q.addLast(new int[]{nextY, nextX});
                    }
                    if (map[nextY][nextX] == 'P') {
                        if(!visited[nextY][nextX]){encountered++; visited[nextY][nextX] = true; q.addLast(new int[]{nextY, nextX});}
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new char[N][M];
        visited = new boolean[N][M];

        for(int i = 0; i < N; i++){
            String row = br.readLine();
            for(int j = 0; j < M; j++){
                map[i][j] = row.charAt(j);
                if(row.charAt(j) == 'I'){startY = i; startX = j;}
            }
        }

        bfs();
        String answer = (encountered == 0) ? "TT" : Integer.toString(encountered);
        System.out.print(answer);


    }
}
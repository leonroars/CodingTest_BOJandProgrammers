import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main{
    static class Graph{
        private int X;
        private int Y;
        private int destX;
        private int destY;
        private boolean[][] graph;
        private int[][] dist;
        private int[] dx;
        private int[] dy;

        public Graph(int height, int width){
            X = width;
            Y = height;
            graph = new boolean[height][width];
            dist = new int[height][width];
            dx = new int[]{0, 0, -1, 1}; // 상 하 좌 우
            dy = new int[]{1, -1, 0, 0}; // 상 하 좌 우
        }

        public void addVertex(String row, int col){
            StringTokenizer st = new StringTokenizer(row);
            for(int i = 0; i < X; i++){
                int current = Integer.parseInt(st.nextToken());
                if(current == 0){graph[col][i] = false;}
                if(current == 1){graph[col][i] = true;}
                if(current == 2){destX = i; destY = col; graph[col][i] = false;}
            }
        }

        public String toString(){
            bfs(destY, destX);
            StringBuilder sb = new StringBuilder();
            for(int y = 0; y < Y; y++){
                for(int x = 0; x < X; x++){
                    // dist[y][x] != 0 => 도달 가능함
                    if(dist[y][x] != 0){sb.append(dist[y][x]);}
                    else{
                        if(graph[y][x]){sb.append(-1);}
                        else{sb.append(0);}
                    }
                    if(x != X - 1){sb.append(" ");} // 해당 행의 마지막 열 숫자가 아닐때만 공백 추가.
                }
                if(y != Y - 1){sb.append("\n");} // 방금 처리한 행이 마지막 행이 아닐 때만 줄넘김.
            }

            return sb.toString();
        }

        private void bfs(int y, int x){
            ArrayDeque<int[]> q = new ArrayDeque<>(); // int[]로 표현된 정점을 담을 큐 초기화.
            int[] start = new int[]{y, x}; // 시작 정점을 int[]로 표현.
            q.addLast(start);
            // 모든 정점을 방문하는 완전탐색이 필요하므로 while(!q.isEmpty()) 이외의 조건을 걸지 않는다.
            while(!q.isEmpty()){
                int[] current = q.removeFirst();
                for(int i = 0; i < 4; i++){
                    int adjX = current[1] + dx[i];
                    int adjY = current[0] + dy[i];
                    // 인접 정점이 범위를 벗어나지 않는 경우에만 절차 진행. 벗어날 경우 무시.
                    if(adjX < X && adjX >=0 && adjY < Y && adjY >= 0){
                        // Case I : 갈 수 있는 땅이며, 이번이 최초 도달일 경우 => 최단거리
                        if(graph[adjY][adjX] && dist[adjY][adjX] == 0){
                            dist[adjY][adjX] = dist[current[0]][current[1]] + 1;
                            int[] adj = new int[]{adjY, adjX}; // 인접 정점을 표현하는 배열 초기화
                            q.addLast(adj); // 초기화한 인접 정점 배열을 큐의 후단에 삽입.
                        }
                    }
                }
            }


        }

    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        Graph graph = new Graph(N, M);
        for(int i = 0; i < N; i++){
            String row = br.readLine();
            graph.addVertex(row, i);
        }

        System.out.print(graph);
    }
}
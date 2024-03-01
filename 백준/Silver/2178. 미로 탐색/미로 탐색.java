import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main{
    static class Graph{
        private boolean[][] graph;
        private boolean[][] visited;
        private int[][] dist;

        private int N; // 세로 길이
        private int M; // 가로 길이


        public Graph(int Ntile, int Mtile){
            N = Ntile;
            M = Mtile;
            graph = new boolean[N+1][M+1];
            visited = new boolean[N+1][M+1];
            dist = new int[N+1][M+1];
        }

        public void addVertex(String row, int col){
            for(int i = 1; i < row.length() + 1; i++){
                int current = Character.getNumericValue(row.charAt(i - 1));
                if(current == 1){graph[col][i] = true;}
                else{graph[col][i] = false;}
            }
        }

        public int shortestPath(){
            BFS(1, 1, N, M);
            return dist[N][M];
        }

        private boolean isAvailable(int[] vertex){
            return vertex != null && graph[vertex[0]][vertex[1]];
        }

        private int[] moveLeft(int[] vertex){
            int currentY = vertex[0];
            int currentX = vertex[1];
            if(currentX == 1 || (currentX > 1 && !graph[currentY][currentX - 1])){return null;}
            return new int[]{currentY, currentX - 1};
        }
        private int[] moveRight(int[] vertex){
            int currentY = vertex[0];
            int currentX = vertex[1];
            if(currentX == M || (currentX < M && !graph[currentY][currentX + 1])){return null;}
            return new int[]{currentY, currentX + 1};
        }
        private int[] moveUp(int[] vertex){
            int currentY = vertex[0];
            int currentX = vertex[1];
            if(currentY == 1 || (currentY > 1 && !graph[currentY - 1][currentX])){return null;}
            return new int[]{currentY - 1, currentX};
        }
        private int[] moveDown(int[] vertex){
            int currentY = vertex[0];
            int currentX = vertex[1];
            if(currentY == N || (currentY < N && !graph[currentY + 1][currentX])){return null;}
            return new int[]{currentY + 1, currentX};
        }


        private void BFS(int y, int x, int targetY, int targetX){
            ArrayDeque<int[]> q = new ArrayDeque<>();
            int[] startVertex = new int[]{y, x};
            dist[y][x] = 1;
            q.addLast(startVertex);
            while(!q.isEmpty()){
                int[] current = q.removeFirst();
                int currentX = current[1];
                int currentY = current[0];
                if(!visited[currentY][currentX]){
                    visited[currentY][currentX] = true;
                    int distByFar = dist[currentY][currentX];
                    if(isAvailable(moveLeft(current))){q.addLast(moveLeft(current)); if(dist[currentY][currentX - 1] == 0){dist[currentY][currentX - 1] = distByFar + 1;}}
                    if(isAvailable(moveRight(current))){q.addLast(moveRight(current)); if(dist[currentY][currentX + 1] == 0){dist[currentY][currentX + 1] = distByFar + 1;}}
                    if(isAvailable(moveUp(current))){q.addLast(moveUp(current)); if(dist[currentY - 1][currentX] == 0){dist[currentY - 1][currentX] = distByFar + 1;}}
                    if(isAvailable(moveDown(current))){q.addLast(moveDown(current)); if(dist[currentY + 1][currentX] == 0){dist[currentY + 1][currentX] = distByFar + 1;}}
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
        for(int i = 1; i < N + 1; i++){
            String row = br.readLine();
            graph.addVertex(row, i);
        }

        System.out.print(graph.shortestPath());
    }
}
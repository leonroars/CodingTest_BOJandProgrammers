import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.BitSet;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int caseNum = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < caseNum; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int vertices = Integer.parseInt(st.nextToken());
            Graph g = new Graph(N, M, vertices);

            for(int j = 0; j < vertices; j++){
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                g.addVertex(x, y);
            }
            g.DFS();
            sb.append(g.componentCount);
            sb.append("\n");
        }

        System.out.println(sb.toString().trim());
    }

    static class Graph{
        private int xWidth;
        private int yWidth;

        private BitSet[] visited;

        private int[][] vertices;

        private boolean[][] graph;

        private int vertexCount;

        private int componentCount;

        public Graph(int xWidth, int yWidth, int vertices){
            this.xWidth = xWidth;
            this.yWidth = yWidth;
            this.vertexCount = 0;
            this.componentCount = 0;

            this.graph = new boolean[xWidth][yWidth]; // 그래프는 byte 형의 데이터로 표현된 x,y 좌표를 가진 정점을 인접 행렬의 형태로 구현하는 방식으로 표현하기로 한다.
            this.vertices = new int[vertices][2]; // [[0, 1], [2, 3] ... [n-th vertex.x, n-th vertex.y]] 과 같은 형태로 현재 그래프 내의 정점이 저장된다.

            // Graph Initialization
            visited = (BitSet[]) new BitSet[xWidth];
            for(int i = 0; i < xWidth; i++){
                visited[i] = new BitSet(yWidth);
            }
        }

        public void addVertex(int x, int y){
            graph[x][y] = true;
            vertices[vertexCount] = new int[]{x, y};
            vertexCount++;
        }

        private int[] moveRight(int[] vertex){
            int currentX = vertex[0], currentY = vertex[1];
            if(currentX + 1 > xWidth - 1){return null;} // Wall-Preventing
            if(!graph[currentX+1][currentY]){return null;}
            return new int[]{currentX+1, currentY};
        }

        private int[] moveLeft(int[] vertex){
            int currentX = vertex[0], currentY = vertex[1];
            if(currentX == 0){return null;} // Wall-Preventing
            if(!graph[currentX-1][currentY]){return null;}
            return new int[]{currentX-1, currentY};
        }

        private int[] moveUp(int[] vertex){
            int currentX = vertex[0], currentY = vertex[1];
            if(currentY == 0){return null;} // Wall-Preventing
            if(!graph[currentX][currentY - 1]){return null;}
            return new int[]{currentX, currentY - 1};
        }

        private int[] moveDown(int[] vertex){
            int currentX = vertex[0], currentY = vertex[1];
            if(currentY == yWidth - 1){return null;} // Wall-Preventing
            if(!graph[currentX][currentY + 1]){return null;}
            return new int[]{currentX, currentY + 1};
        }

        public void DFS(){
            for(int i = 0; i < vertices.length; i++){
                if(!visited[vertices[i][0]].get(vertices[i][1])){
                    DFS(vertices[i]);
                    componentCount++;
                }
            }
        }

        private void DFS(int[] vertex){
            if(vertex == null){return;}
            if(!visited[vertex[0]].get(vertex[1])){
                visited[vertex[0]].set(vertex[1]);
                DFS(moveUp(vertex));
                DFS(moveDown(vertex));
                DFS(moveLeft(vertex));
                DFS(moveRight(vertex));
            }
        }
    }
}
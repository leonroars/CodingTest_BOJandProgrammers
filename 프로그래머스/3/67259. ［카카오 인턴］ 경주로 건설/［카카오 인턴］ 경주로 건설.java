import java.util.PriorityQueue;

class Solution {
    public int solution(int[][] board) {
        return findOptimalCost(board);
    }
    
    public int findOptimalCost(int[][] board) {
        int N = board.length;
        int[] dRow = new int[]{-1, 1, 0, 0};
        int[] dCol = new int[]{0, 0, -1, 1};
        
        int[][][] distFromSource = new int[N][N][2]; // [r][c][0] : (r, c)에 수직 경로 진입 시의 최단 도달 비용
        initiateDistMatrix(distFromSource, N);
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(0, 0, 0));
        
        while(!pq.isEmpty()){
            Node current = pq.poll();
            
            // Prunning non-promising candidates!
            if(distFromSource[current.row][current.col][0] < current.distByFar
               && distFromSource[current.row][current.col][1] < current.distByFar){continue;}
            
            // Search adjs
            for(int d = 0; d < 4; d++){
                int nextRow = current.row + dRow[d];
                int nextCol = current.col + dCol[d];
                
                if(isAvailable(board, nextRow, nextCol, N)){
                    int currentDirection = (d < 2) ? 0 : 1; // current-next 간 건설 도로 방향.
                    int nextDist = -1;
                    
                    if(currentDirection == 0){
                        nextDist = Math.min(distFromSource[current.row][current.col][0] + 100
                                            , distFromSource[current.row][current.col][1] + 600);
                    }
                    else {
                        nextDist = Math.min(distFromSource[current.row][current.col][1] + 100
                                            , distFromSource[current.row][current.col][0] + 600);
                    }
                    
                    if(distFromSource[nextRow][nextCol][currentDirection] > nextDist){
                        distFromSource[nextRow][nextCol][currentDirection] = nextDist;
                        pq.offer(new Node(nextRow, nextCol, nextDist));
                    }
                }
            }
            
        }
        
        return Math.min(distFromSource[N-1][N-1][0], distFromSource[N-1][N-1][1]);
    }
    
    private void initiateDistMatrix(int[][][] matrix, int N) {
        int INF = Integer.MAX_VALUE / 4;
        for(int r = 0; r < N; r++){
            for(int c = 0; c < N; c++){
                for(int d = 0; d < 2; d++){
                    if(r == c && c == 0){matrix[r][c][d] = 0;}
                    else {matrix[r][c][d] = INF;}
                }
            }
        }
    }
    
    // Return true if given location is in boundaries && is not wall
    private boolean isAvailable(int[][] board, int row, int col, int N){
        return row >= 0 && row < N && col >= 0 && col < N && board[row][col] != 1;
    }
    
    class Node implements Comparable<Node>{
        int row;
        int col;
        int distByFar; // 그렇게 진입했을 때, 해당 (row, col) 로의 최단 거리
        
        public Node(int r, int c, int distByFar) {
            this.row = r;
            this.col = c;
            this.distByFar = distByFar;
        }
        
        @Override
        public int compareTo(Node otherNode) {
            return this.distByFar - otherNode.distByFar;
        }
        
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 0. 입력 받기
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        int currentRow = Integer.parseInt(st.nextToken());
        int currentCol = Integer.parseInt(st.nextToken());
        int currentDirection = Integer.parseInt(st.nextToken());
        
        int[][] map = new int[N][M];
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < M; col++){
                map[row][col] = Integer.parseInt(st.nextToken());
            }
        }
        
        // 1. 로봇 청소기 객체 초기화
        RobotCleaner cuteRobot = new RobotCleaner(currentRow, currentCol, currentDirection, map);
        
        // 2. 청소 부탁하기
        cuteRobot.cleanRoom();
        
        // 3. 청소가 이루어진 구역의 수 여쭤보기.
        System.out.print(cuteRobot.getNumberOfCleanedArea());
    }
    
    static class RobotCleaner {
        int currentRow;
        int currentCol;
        int currentDirection;
        int numberOfCleanedArea;
        int[][] area;
        boolean[][] cleanedArea;
        int[] dRow = new int[]{-1, 0, 1, 0};
        int[] dCol = new int[]{0, 1, 0, -1};
        
        public RobotCleaner(int currentR, int currentC, int currentD, int[][] map){
            this.currentRow = currentR;
            this.currentCol = currentC;
            this.currentDirection = currentD;
            numberOfCleanedArea = 0;
            area = map;
            cleanedArea = new boolean[map.length][map[0].length];
        }
        
        public int getNumberOfCleanedArea() {
            return this.numberOfCleanedArea;
        }
        
        public void cleanRoom() {
            // 가동 중지가 아닌 이상, while 내에서 현 위치와 상태를 체크하고 이에 따른 로직을 반복 수행하도록 설계.
            boolean isDone = false;
            
            while(!isDone){
                if(isCleaned(this.currentRow, this.currentCol)){
                    int promisingDirection = -1;
                    // 4방향 탐색.
                    for(int i = 0; i < 4; i++){
                        rotate();
                        if(checkForward()){
                            promisingDirection = this.currentDirection;
                            break;
                        }
                    }
                    
                    // 방문 가능 위치 존재하는 경우 -> 해당 방향을 보고 있음.
                    if(promisingDirection != -1){
                        moveForward();
                    }
                    // 없는 경우 : 후진 가능 시 후진. 아닐 경우 정지 후 isDone = true;
                    else {
                        if(checkBackward()){
                            moveBackward();
                        }
                        else {
                            isDone = true;
                        }
                    }   
                }
                else {
                    clean();
                    this.numberOfCleanedArea++;
                }
            }
        }
        
        private boolean checkForward() {
            int forwardRow = currentRow + dRow[currentDirection];
            int forwardCol = currentCol + dCol[currentDirection];
            return isLegal(forwardRow, forwardCol) && isAvailable(forwardRow, forwardCol) && !isCleaned(forwardRow, forwardCol);
        }
        
        private void moveForward() {
            this.currentRow += dRow[currentDirection];
            this.currentCol += dCol[currentDirection];
        }
        
        private boolean checkBackward() {
            int backwardDirection = (currentDirection + 2) % 4;
            int backwardRow = currentRow + dRow[backwardDirection];
            int backwardCol = currentCol + dCol[backwardDirection];
            
            return isLegal(backwardRow, backwardCol) && isAvailable(backwardRow, backwardCol);
        }
        private void moveBackward(){
            int backwardDirection = (currentDirection + 2) % 4;
            this.currentRow += dRow[backwardDirection];
            this.currentCol += dCol[backwardDirection];
        }
        
        private void clean(){
            cleanedArea[currentRow][currentCol] = true;
        }
        
        // 벽인가 빈공간인가.
        private boolean isAvailable(int row, int col){
            return area[row][col] == 0;
        }
        
        // 맵 내에 존재하는 위치인가
        private boolean isLegal(int row, int col){
            return row >= 0 && row < area.length && col >= 0 && col < area[0].length;
        }
        
        // 청소가 된 영역인가
        private boolean isCleaned(int row, int col){
            return cleanedArea[row][col];
        }
        
        // 로봇청소기 반시계 방향으로 90도 회전
        private void rotate() {
            this.currentDirection = ((this.currentDirection - 1) + 4) % 4;
        }
        
    }
}
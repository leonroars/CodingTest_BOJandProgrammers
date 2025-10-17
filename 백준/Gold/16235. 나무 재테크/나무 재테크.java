import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.*;

public class Main {
    static int N;
    static int M;
    static int K;
    static int[][] nutritionIn; // nutritionIn[r][c] : (r, c) 칸의 양분 양
    static int[][] A; // A[r][c] : 매년 겨울 S2D2가 (r, c) 땅에 추가 투입하는 양분 양.
    static PriorityQueue<Tree>[][] treeIn; // treeIn[r][c] : (r, c) 칸의 트리 정보
    
    // 수직 위부터 시계방향으로 팔방위
    static int[] dRow = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dCol = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        
        nutritionIn = new int[N + 1][N + 1]; // 1-index
        A = new int[N + 1][N + 1]; // 1-index.
        treeIn = new PriorityQueue[N + 1][N + 1]; // 1-index
        
        // 0. 매년 겨울 S2D2 가 추가 투입하는 양분 양 정보 받기.
        for(int row = 1; row < N + 1; row++){
            StringTokenizer a = new StringTokenizer(br.readLine());
            for(int col = 1; col < N + 1; col++){
                A[row][col] = Integer.parseInt(a.nextToken());
            }
        }
        
        // 1. Initialization : 모든 땅의 양분은 5. / ArrayList<> 넣기
        for(int i = 1; i < N + 1; i++){
            for(int j = 1; j < N + 1; j++){
                nutritionIn[i][j] = 5;
                treeIn[i][j] = new PriorityQueue<Tree>();
            }
        }
        
        // 2. 현재 나무 위치 받기.
        for(int m = 0; m < M; m++){
            StringTokenizer tree = new StringTokenizer(br.readLine());
            int treeRow = Integer.parseInt(tree.nextToken());
            int treeCol = Integer.parseInt(tree.nextToken());
            int treeAge = Integer.parseInt(tree.nextToken());
            
            treeIn[treeRow][treeCol].add(new Tree(treeAge));
        }
        
        // 2. K 번 봄, 여름, 가을, 겨울 Simultate.
        for(int k = 0; k < K; k++){
            simulate();
        }
        
        // 3. 정답 출력 : 살아 남은 나무 수.
        int aliveTreeCnt = 0;
        
        for(int r = 1; r < N + 1; r++){
            for(int c = 1; c < N + 1; c++){
                aliveTreeCnt += treeIn[r][c].size();
            }
        }
        
        System.out.print(aliveTreeCnt);
    }
    // 각 1*1 크기 칸 별로 시뮬레이션 시행
    public static void simulate(){
        simulateSpringAndSummer();
        simulateFall();
        simulateWinter();
    }
    
    /* 봄/여름 시뮬레이션. */
    private static void simulateSpringAndSummer(){
        for(int row = 1; row < N + 1; row++){
            for(int col = 1; col < N + 1; col++){
                /* 1. Spring Simulation */
                PriorityQueue<Tree> newTrees = new PriorityQueue<Tree>();
                
                // 현 위치에 존재하는 나무 리스트를 돌며, 성장하는 나무와 죽은 나무 솎아내기.
                ArrayList<Tree> deadTrees = new ArrayList<>();
                
                while(!treeIn[row][col].isEmpty()){
                    Tree currentTree = treeIn[row][col].poll();
                    int remainingNutrition = nutritionIn[row][col];
                    
                    if(currentTree.age <= remainingNutrition){
                        nutritionIn[row][col] -= currentTree.age;
                        currentTree.age++;
                        newTrees.add(currentTree);
                    }
                    else {
                        deadTrees.add(currentTree);
                    }
                }
                
                treeIn[row][col] = newTrees;
                
                /* 2. Summer Simulation : 죽은 나무들 양분으로 만들기 */
                fillNutritionFromDeadTrees(deadTrees, row, col);
            }
        }
    }
    
    /* 가을 시뮬레이션 */
    private static void simulateFall(){
        for(int row = 1; row < N + 1; row++){
            for(int col = 1; col < N + 1; col++){
                int reproductionCnt = 0;
                for(Tree currentTree : treeIn[row][col]){
                    // 번식 가능 나무인 경우 번식하기.
                    if(currentTree.isInReproductionAge()){
                        reproductionCnt++;
                    }
                }
                reproduction(row, col, reproductionCnt);
            }
        }
    }
    
    /* 겨울 시뮬레이션 */
    private static void simulateWinter(){
        for(int row = 1; row < N + 1; row++){
            for(int col = 1; col < N + 1; col++){
                nutritionIn[row][col] += A[row][col];
            }
        }
    }
    
    
    /* (row, col) 위치의 나무가 번식 : 8방으로 나이가 1인 나무 추가. / 대상 여부는 상위 메서드에서 체크 */
    private static void reproduction(int row, int col, int cnt){
        for(int d = 0; d < 8; d++){
            int nextRow = row + dRow[d];
            int nextCol = col + dCol[d];
            
            if(isAvailable(nextRow, nextCol)){
                for(int i = 0; i < cnt; i++){
                    treeIn[nextRow][nextCol].add(new Tree(1));
                }
            }
        }
    }
    
    /* 죽은 나무로부터 양분 계산하여 땅에 다시 채우기 _ 여름에 해당 */
    private static void fillNutritionFromDeadTrees(ArrayList<Tree> deadTrees, int row, int col){
        for(Tree tree : deadTrees){
            nutritionIn[row][col] += (tree.age / 2);
        }
    }
    
    /* 해당 위치가 N*N 크기의 땅 내에 존재하는 위치인지 확인. */
    private static boolean isAvailable(int r, int c){
        return r > 0 && r < N + 1 && c > 0 && c < N + 1;
    }
    static class Tree implements Comparable<Tree> {
        int age;
        
        public Tree (int age){
            this.age = age;
        }
        
        public boolean isInReproductionAge(){
            return this.age % 5 == 0;
        }
        
        // 정렬을 위한 Comparable 인터페이스 구현
        @Override
        public int compareTo(Tree thatTree){
            return Integer.compare(this.age, thatTree.age);
        }
    }
}
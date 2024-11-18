import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Arrays;

public class Main{
    static int N;
    static boolean isGameOver = false;
    static int[] currentHead = new int[]{0, 0}; // 뱀의 머리 위치.
    static int[] currentTail = new int[]{0, 0}; // 뱀의 꼬리 위치.
    static boolean[][] snake;
    static boolean[][] apple;
    static int currentDirection; // 현 뱀의 진행방향 - 0, 90, 180, 270.
    static HashMap<Integer, String> commands = new HashMap<>(); // 시간 : 전환 방향
    // static ArrayDeque<Integer> directionLog = new ArrayDeque<>(); // head의 방향 로그.
    static int[][] directionLog;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 0. 입력
        N = Integer.parseInt(br.readLine());
        snake = new boolean[N][N];
        apple = new boolean[N][N];
        directionLog = new int[N][N];

        snake[0][0] = true;
        currentDirection = 90;
        directionLog[0][0] = currentDirection;

        int K = Integer.parseInt(br.readLine());
        for(int k = 0; k < K; k++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int appleRow = Integer.parseInt(st.nextToken()) - 1;
            int appleCol = Integer.parseInt(st.nextToken()) - 1;
            apple[appleRow][appleCol] = true;
        }

        int L = Integer.parseInt(br.readLine());
        for(int l = 0; l < L; l++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int turnTime = Integer.parseInt(st.nextToken());
            String turnD = st.nextToken();
            commands.put(turnTime, turnD);
        }

        // 1. 초 단위 작동 프로그램 실행.
        int t = 1;
        while(!isGameOver){
            move();
            if(commands.containsKey(t)){
                turn(commands.get(t));
            }
            if(isGameOver){
                break;
            }
            else {
                directionLog[currentHead[0]][currentHead[1]] = currentDirection;
                t++;
            }
        }

        System.out.print(t);
    }

    private static void move(){
        int[] nextHead = findNext(currentHead);

        // Case A : 이동 가능한 경우 -> 이동 실시.
        if(isPossible(nextHead)){
            currentHead = nextHead;
            // 해당 위치 사과 존재하지 않는 경우 -> 꼬리 부분 방문 상태 해제.
            if(!apple[currentHead[0]][currentHead[1]]){
                int[] newTail = findNext(currentTail); // 다음 꼬리 위치 찾기.
                snake[currentTail[0]][currentTail[1]] = false;
                currentTail = newTail;
            }
            // 해당 위치 사과 존재하는 경우 -> 먹었으므로 해제!
            else {
                apple[currentHead[0]][currentHead[1]] = false;
            }
            snake[currentHead[0]][currentHead[1]] = true;
        }
        else {
            isGameOver = true;
        }
    }

    private static int[] findNext(int[] headOrTail){
        int[] next;
        int direction = (Arrays.equals(headOrTail, currentHead)) ? (currentDirection) : (directionLog[currentTail[0]][currentTail[1]]);
        // Case A : 진행방향 = 수직 위.
        if(direction == 0){
            next = new int[]{headOrTail[0] - 1, headOrTail[1]};
        }
        // Case B : 진행방향 = 수평 오른쪽
        else if(direction == 90){
            next = new int[]{headOrTail[0], headOrTail[1] + 1};
        }
        // Case C : 진행방향 = 수직 아래
        else if(direction == 180){
            next = new int[]{headOrTail[0] + 1, headOrTail[1]};
        }
        // Case D : 진행방향 = 수평 왼쪽
        else {
            next = new int[]{headOrTail[0], headOrTail[1] - 1};
        }

        return next;
    }

    // 방향 전환
    private static void turn(String nextDirection){
        if(nextDirection.equals("L")){
            currentDirection = (currentDirection + 270) % 360;
        }
        else {
            currentDirection = (currentDirection + 450) % 360;
        }
    }

    // 움직일 수 있는 위치인가 -> 벽에 부딪히거나 자기 자신의 몸 위로 움직이는 경우 false.
    private static boolean isPossible(int[] pos){
        int row = pos[0];
        int col = pos[1];

        return row >= 0 && row < N && col >= 0 && col < N && !snake[row][col];
    }
}
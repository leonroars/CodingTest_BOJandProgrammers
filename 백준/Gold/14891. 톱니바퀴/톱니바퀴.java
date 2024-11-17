import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayDeque;

public class Main{
    static ArrayDeque<Integer>[] wheels = (ArrayDeque<Integer>[])new ArrayDeque[4];
    static int[] moves = new int[]{-1, 1};
    static int[] directions = new int[4]; // directions[0] : 1번 톱니바퀴가 회전해야할 방향.
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 0. 네 개의 톱니바퀴 상태 입력 받기
        for(int i = 0; i < 4; i++){
            wheels[i] = new ArrayDeque<>();
            String status = br.readLine();
            for(int j = 0; j < 8; j++){
                wheels[i].addLast(Character.getNumericValue(status.charAt(j)));
            }
        }
        
        // 1. 회전
        int k = Integer.parseInt(br.readLine()); // 회전 횟수
        while(k > 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int wheelNum = Integer.parseInt(st.nextToken()) - 1; // 회전한 톱니바퀴 번호
            int direction = Integer.parseInt(st.nextToken()); // 회전 방향.
            
            applyRotation(wheelNum, direction); // 회전 메서드 호출
            k--;
        }
        
        int score = 0;
        
        // 2. 점수 계산
        for(int wheelLoc = 0; wheelLoc < 4; wheelLoc++){
            if(wheels[wheelLoc].peekFirst() == 1){
                score += (int)Math.pow(2, wheelLoc);
            }
        }
        
        // 3. 출력
        System.out.print(score);
        
    }
    
    // 회전할 톱니바퀴의 좌, 우 재귀적으로 탐색 -> 함께 회전하게될 톱니바퀴 정하기.
    private static void applyRotation(int targetWheel, int direction){
        // 1. targetWheel 좌, 우 방향으로 탐색하며 targetWheel 회전 시 함께 회전하게 될 톱니바퀴와 방향 파악하기.
        findWheel(targetWheel, targetWheel, direction);
        
        // 2. 회전해야할 톱니바퀴 회전시키기.
        for(int wheel = 0; wheel < 4; wheel++){
            rotate(wheel, directions[wheel]);
        }
    }
    
    private static void findWheel(int currentWheel, int targetWheel, int direction){
        // Base Case
        if(isEnd(currentWheel)){return;}
        
        // Case I : currentWheel == targetWheel : 시작점
        if(currentWheel == targetWheel){
            directions[targetWheel] = direction;
            findWheel(currentWheel - 1, targetWheel, direction);
            findWheel(currentWheel + 1, targetWheel, direction);
        }
        // Case II : 회전시킨 톱니바퀴의 왼쪽 탐색
        else if(currentWheel < targetWheel){
            int rightWheel = currentWheel + 1;
            Integer[] currentWheelIdx = wheels[currentWheel].toArray(new Integer[0]);
            Integer[] rightWheelIdx = wheels[rightWheel].toArray(new Integer[0]);
            // 맞닿은 톱니의 자성 극이 같은 경우 -> 회전 X
            if(currentWheelIdx[2].equals(rightWheelIdx[6])){
                directions[currentWheel] = 0;
                findWheel(currentWheel - 1, targetWheel, 0);
            }
            else {
                if(direction == 0){
                    directions[currentWheel] = 0;
                    findWheel(currentWheel - 1, targetWheel, 0);
                }
                else {
                    directions[currentWheel] = direction * -1;
                    findWheel(currentWheel - 1, targetWheel, direction * -1);
                }
            }
        }
        // Case III : 오른쪽 탐색
        else {
            int leftWheel = currentWheel - 1;
            Integer[] currentWheelIdx = wheels[currentWheel].toArray(new Integer[0]);
            Integer[] leftWheelIdx = wheels[leftWheel].toArray(new Integer[0]);
            if(currentWheelIdx[6].equals(leftWheelIdx[2])){
                directions[currentWheel] = 0;
                findWheel(currentWheel + 1, targetWheel, 0);
            }
            else {
                if(direction == 0){
                    directions[currentWheel] = 0;
                    findWheel(currentWheel + 1, targetWheel, 0);
                }
                else {
                    directions[currentWheel] = direction * -1;
                    findWheel(currentWheel + 1, targetWheel, direction * -1);
                }
            }
        }
        
    }
    
    // 주어진 톱니바퀴 회전
    private static void rotate(int wheelNum, int direction){
        int removed = -1;
        
        // 시계 방향
        if(direction == 1){
            removed = wheels[wheelNum].removeLast();
            wheels[wheelNum].addFirst(removed);
        }
        // 반시계 방향
        else if(direction == -1){
            removed = wheels[wheelNum].removeFirst();
            wheels[wheelNum].addLast(removed);
        }
        // 회전 X
        else {return;}
    }
    
    private static boolean isEnd(int wheelNum){
        return wheelNum < 0 || wheelNum > 3;
    }
    
}
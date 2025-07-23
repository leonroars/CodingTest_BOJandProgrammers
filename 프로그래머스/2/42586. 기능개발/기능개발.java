import java.util.*;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        if(progresses.length == 0){return new int[]{0};}
        else {
            ArrayDeque<Integer> perDay = new ArrayDeque<>(); // 각 배포시 배포되는 기능의 수
            int[] leftDays = new int[progresses.length]; // 각 작업 별 완료 후 배포 시까지 걸리는 시간
            for(int i = 0; i < progresses.length; i++){
                leftDays[i] = 
                    (int)(Math.ceil((double) ((100 - progresses[i])) / speeds[i]));
            }

            int locator = 0; // 다음 배포될 작업을 결정하는 작업의 배열 인덱스.
            int deployAccum = 0; // 현재까지의 누적 배포 작업 수.
            int willBeDeployed = 0; // 한 번에 배포될 작업의 수
            // 배포가 된 작업 수가 전체 작업 수보다 작은 동안,
            while(deployAccum < progresses.length){
                willBeDeployed++; // locator 번 작업.
                for(int i = locator + 1; i < progresses.length; i++){
                    // 현 시점 가장 먼저 배포되어야하는 작업보다 빨리 끝난 이후의 '연속적' 작업들만 큐에 추가.
                    if(leftDays[i] <= leftDays[locator]){willBeDeployed++;}  
                    else{locator = i; break;}
                }
                perDay.addLast(willBeDeployed);
                deployAccum += willBeDeployed;
                willBeDeployed = 0; // 초기화
            }
            
            System.out.print(perDay);
            int[] answer = new int[perDay.size()];
            for(int k = 0; k < answer.length; k++){
                answer[k] = perDay.removeFirst();
            }
            return answer;
        }

    }
}
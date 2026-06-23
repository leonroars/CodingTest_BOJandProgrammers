/*

 1. 문제 : 신규 사용자 진입 급감
 2. 원인 : 신규 사용자와 기존 사용자 사이의 스테이지 차이가 너무 큼
 3. 솔루션 : '동적'으로 게임 시간을 늘려 난이도를 조절
 4. 솔루션을 위해 정의한 것 : '실패율' 
    실패율 = (스테이지 도달했으나 아직 클리어하지 못한 플레이어 수) / (스테이지에 도달한 플레이어 수 = 깬사람+못깬사람)

 5. 문제 입력
   - N : 전체 스테이지 수
   - stages : 사용자가 현재 멈춰있는 스테이지 번호가 담긴 배열 (ex. stages[i] = k : i번 사용자가 k번 스테이지에 멈춰있음)
 
 6. 출력 : 실패율 높은 스테이지부터 '내림차순'으로 스테이지 번호 배열 반환.
 
 7. 풀이 설계
  1) 스테이지 별 실패율 구하기
    i) 도달 but 깸 X : stages[i] = p 인 i의 수 | 도달한 사람stages[j] = q(q >= p)
       - stages.length : 사용자 수 (20만명)
    ii) int progress[k] 를 "k 스테이지에 도달한 사람들 누적합" 으로 한다면 어떨까? 그러면,
       - 도달 but 깸 x : (progress[k] - progress[k - 1])
       - 도달한 사람 : (progress[N + 1] - progress[k-1]
  2) 정렬하기
 */

import java.util.ArrayList;
import java.util.Collections;

class Solution {
    
    public int[] solution(int N, int[] stages) {
        // 0. 스테이지에 도달한 인원 수 헤아리기 : max(O(stages.length, O(N))
        int[] reachedCnt = new int[N + 2]; // 1~N+1 까지 담아야함.
        
        for(int playerId = 0; playerId < stages.length; playerId++){
            reachedCnt[stages[playerId]]++;
        }
        
        // 1. 구간합 계산 : O(N)
        int[] rangeSum = new int[N + 2];
        
        for(int stageNum = 1; stageNum < N + 2; stageNum++){
            rangeSum[stageNum] = rangeSum[stageNum - 1] + reachedCnt[stageNum];
        }
        
        
        // 2. 스테이지별 실패율 계산 : reachedCnt[k] / (rangeSum[N + 1] - rangeSum[k - 1])
        // 주의할 점은, stages 의 크기는 N이라는 것.
        ArrayList<Stage> stageList = new ArrayList<>();
        for(int stageNum = 1; stageNum < N+1; stageNum++){
            double currentStageFailRatio;
            if(reachedCnt[stageNum]!=0){currentStageFailRatio = (double)reachedCnt[stageNum] / (rangeSum[N + 1] - rangeSum[stageNum - 1]);}
            else {currentStageFailRatio = (double)0;}
            stageList.add(new Stage(stageNum, currentStageFailRatio));
        }
        
        
        // 3. 정렬 : O(N log N) - N 의 최대 크기 고려 시, 무시 가능한 수준으로 작음
        Collections.sort(stageList);
        
        // 4. 정답 배열 완성 : O(N)
        int[] answer = new int[N];
        
        for(int i = 0; i < N; i++){answer[i] = stageList.get(i).getStageNum();}
        return answer;
    }
    
    class Stage implements Comparable<Stage> {
        private int num; // 스테이지 번호
        private double failRatio;
        
        public Stage(int num, double failRatio){
            this.num = num;
            this.failRatio = failRatio;
        }
        
        public int getStageNum(){return this.num;}
        public double getFailRatio(){return this.failRatio;}
        
        @Override
        public int compareTo(Stage thatStage){
            if(this.failRatio == thatStage.getFailRatio()){
                return Integer.compare(this.num, thatStage.getStageNum());
            }
            else if(this.failRatio > thatStage.getFailRatio()){return -1;}
            return 1;
        }
    }
}
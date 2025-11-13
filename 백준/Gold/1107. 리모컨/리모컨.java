import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashSet;

public class Main {
    static int target; // 최대 500,000 -> 즉, 채널 이동을 할 경우 최대 6번까지만 눌러보면 된다.
    static int brokenCnt;
    static HashSet<Integer> brokenNums;
    static int minCnt;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        target = Integer.parseInt(br.readLine());
        brokenCnt = Integer.parseInt(br.readLine());
        brokenNums = new HashSet<>();
        minCnt = Math.abs(target - 100);
        
        if(brokenCnt != 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 0; i < brokenCnt; i++){brokenNums.add(Integer.parseInt(st.nextToken()));}
        }
        
        calculateMinCnt(new StringBuilder(), 0);
        
        System.out.print(minCnt);
    }
    
    public static void calculateMinCnt(StringBuilder choiceByFar, int depth){
        // Base Case : 6자리 숫자를 모두 누른 경우 -> 현재 choiceByFar 로부터 이동 필요 횟수 계산 후 return.
        if(depth == 6){
            int currentCnt = choiceByFar.length() + Math.abs(target - Integer.parseInt(choiceByFar.toString()));
            minCnt = Math.min(minCnt, currentCnt);
            return;
        }
        
        // General Cases
        else {
            for(int num = 0; num < 10; num++){
                if(!brokenNums.contains(num)){
                    StringBuilder currentChoice = new StringBuilder(choiceByFar);
                    currentChoice.append(num);
                    
                    // 방금의 선택으로 만들어진 수로부터 이동 필요 횟수 계산.
                    int currentCnt = currentChoice.length() + Math.abs(target - Integer.parseInt(currentChoice.toString()));
                    minCnt = Math.min(minCnt, currentCnt);
                    
                    // 다음 선택 재귀 호출
                    calculateMinCnt(currentChoice, depth + 1);
                }
            }
            // 누를 수 있는 수 모두 눌러보았으면 반환 후 종료.
            return;
        }
    }
}
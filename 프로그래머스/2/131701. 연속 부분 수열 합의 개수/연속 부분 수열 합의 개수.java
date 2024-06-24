import java.util.HashSet;

class Solution {
    public int solution(int[] elements) {
        // HashSet에 구한 합계를 추가하는 방식으로 문제를 풀이하도록 한다.
        // 다음 인덱스를 지정 시 단순히 +k 을 하는 것이 아닌,
        // (Current + k) % elements.length 로 파악하기.
        
        HashSet<Integer> nums = new HashSet<>(); // 부분 수열의 합을 저장하기 위한 HashSet.
        int[] sum = new int[elements.length];
        
        for(int sumCount = 1; sumCount < elements.length + 1; sumCount++){
            boolean allFlag = false;
            if(sumCount == elements.length){allFlag = true;}
            for(int startLoc = 0; startLoc < elements.length; startLoc++){
                nums.add(roundSum(startLoc, sumCount, elements, sum));
                if(allFlag){break;} // 주어진 배열의 길이와 같은 부분 수열은 한 번만 구하면 되므로, 바로 break.
            }
        }
        
        return nums.size();
    }
    
    private int roundSum(int startLoc,int sumCount, int[] elements, int[] sum){
        // sumCount : 어떤 수로부터 시작해 해당 수를 포함하는 연속 부분 수열의 길이.
        // elements : 입력
        // sum : sum[i] = i로부터 (sumCount - 1)개의 원소로 구성된 연속 부분 수열의 합.
        if(sumCount == 1){
            sum[startLoc] = elements[startLoc];
            return sum[startLoc];
        }
        return sum[startLoc] = sum[startLoc] + elements[(startLoc + sumCount - 1) % elements.length];
        
    }
}
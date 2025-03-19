class Solution {
    public long solution(int n, int[] times) {
        
        // 1. 소요 시간 상한 찾기
        long longest = Long.MIN_VALUE;
        for(int i = 0; i < times.length; i++){
            if(times[i] > longest){longest = times[i];}
        }
        long worstTimeTaken = longest * (long)n;
        
        // 2. 이진탐색.
        long lowerBound = 0L;
        long upperBound = worstTimeTaken;
        
        while(lowerBound < upperBound){
            long mid = (lowerBound + upperBound) / 2;
            
            // Case I : 중간 시간이 성립 가능 -> 해당 시간이 upper bound.
            if(calculateCapacity(n, times, mid)){upperBound = mid;}
            
            // Case II : 중간 시간 성립 불가 -> 중간 + 1 이 새로운 Lower bound.
            else {lowerBound = mid + 1;}
        }
        
        return lowerBound;
    }
    
    private boolean calculateCapacity(int n, int[] times, long targetTime){
        long sum = 0L;
        
        for(int i = 0; i < times.length; i++){
            long capacityPerOfficer = targetTime / (long)times[i];
            sum += capacityPerOfficer;
        }
        
        if(sum < n){return false;}
        return true;
    }
}
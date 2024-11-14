class Solution {
    public int[] solution(int[] sequence, int k) {
        int[] answer = new int[2];
        
        // 0. 포인터 두 개 및 기록/갱신 위한 변수 초기화.
        int head = 0;
        int tail = 0;
        int minLen = Integer.MAX_VALUE;
        int partialSum = sequence[head];
        
        // 1. 탐색 : O(n)
        while(head < sequence.length){
            // Edge Case : 단일 원소의 값 == k -> 갱신 필요시 갱신 후 바로 반환.
            if(sequence[tail] == k && minLen > 1){
                minLen = 1;
                answer[0] = tail;
                answer[1] = tail;
                break;
            }
            
            // Case I : 현 구간합이 k보다 작은 경우 -> tail 증가시켜 구간 연장 시켜 확인.
            if(partialSum < k){
                // i) tail을 더 증가시킬 수 있는 경우 -> 증가 시키고 새로운 구간에 대한 검증
                if(tail < sequence.length - 1){
                    tail++;
                    partialSum += sequence[tail];
                }
                // ii) tail이 끝에 다다른 경우 -> non-promising하므로 종료.
                else {
                    break;
                }
            }
            else if(partialSum == k){
                int currentLen = tail - head;
                if(currentLen < minLen){
                    answer[0] = head;
                    answer[1] = tail;
                    minLen = currentLen;
                }
                partialSum -= sequence[head];
                head++;
            }
            else {
                partialSum -= sequence[head];
                head++;
            }
        }
        
        
        return answer;
    }
}
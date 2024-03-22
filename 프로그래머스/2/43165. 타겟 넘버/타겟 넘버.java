class Solution {
    
    int count = 0;
    
    public int solution(int[] numbers, int target) {
        DFS(0, 0, target, numbers);
        return count;
    }
    
    private void DFS(int currentIdx, int prev, int target, int[] numbersList){
        // Base - Case : currentIdx > numbersList.length - 1
        if(currentIdx > numbersList.length - 1){return;}
        
        for(int i = 0; i < 2; i++){
            int current = 0;
            if(i == 0){
                current = prev + numbersList[currentIdx];
            } else {
                current = prev - numbersList[currentIdx];
            }
            
            if(currentIdx == numbersList.length - 1 && current == target){count++;}
            else{DFS(currentIdx + 1, current, target, numbersList);}
        }
    }
}
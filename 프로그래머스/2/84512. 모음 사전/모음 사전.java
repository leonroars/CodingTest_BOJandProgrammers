class Solution {
    int cnt = 0;
    String target; // dfs()에서 word를 참조할 수 있도록 word에 대한 reference 가지는 클래스 변수
    boolean flag = false; // 찾을 경우 true.
    char[] vowels = {'A', 'E', 'I', 'O', 'U'};
    
    public int solution(String word) {
        target = word;
        DFS("", 1);
        
        return cnt;
    }
    
    private void DFS(String prev, int length){
        if(length > 5){return;}
        
        for(int i = 0; i < vowels.length; i++){
            String current = prev + vowels[i];
            cnt++;
            if(current.equals(target)){flag = true; break;}
            else{DFS(current, length + 1);}
            if(flag){break;}
        }
    }
        
}
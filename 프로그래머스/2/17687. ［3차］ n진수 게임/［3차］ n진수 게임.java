class Solution {
    
    String num = "";
    
    public String solution(int n, int t, int m, int p) {
        numGen(n, m, t);
        
        int count = 0;
        int turn = p;
        String answer = "";
        
        while(count < t){
            answer += Character.toUpperCase(num.charAt(turn - 1));
            turn += m;
            count++;
        }
        
        return answer;
        
    }
    
    private void numGen(int n, int m, int t){
        int numLen = 0;
        int target = 0;
        
        while(numLen < m * t){
            String currentNum = Integer.toString(target, n); // N진수 변환.
            num += currentNum;
            target++;
            numLen += currentNum.length();
        }
    }
}
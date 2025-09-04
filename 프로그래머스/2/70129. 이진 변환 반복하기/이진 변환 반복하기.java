class Solution {
    public int[] solution(String s) {
        int numberOfRemovedZero = 0;
        int transformCnt = 0;
        
        while(!s.equals("1")){
            StringBuilder sMod = new StringBuilder();
            
            // 1. 0 제거.
            for(int idx = 0; idx < s.length(); idx++){
                if(s.charAt(idx) != '0'){sMod.append(s.charAt(idx));}
                else{numberOfRemovedZero++;}
            }
            
            // 2. 2진변환.
            s = Integer.toBinaryString(sMod.length());
            transformCnt++;
        }
        
        return new int[]{transformCnt, numberOfRemovedZero};
    }
}
class Solution {
    boolean solution(String s) {
        
        int pCount = 0;
        int yCount = 0;
        
        for(int i = 0; i < s.length(); i++){
            char x = s.charAt(i);
            if(x == 'p' || x == 'P'){pCount++;}
            else if(x == 'y' || x == 'Y'){yCount++;}
            else{continue;}
        }

        return pCount==yCount;
    }
}
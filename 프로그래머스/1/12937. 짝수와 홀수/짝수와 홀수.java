class Solution {
    public String solution(int num) {
        if(isEven(num)){return "Even";}
        else{return "Odd";}
    }
    
    private boolean isEven(int num){
        if(num == 0 || num % 2 == 0){return true;}
        return false;
    }
}
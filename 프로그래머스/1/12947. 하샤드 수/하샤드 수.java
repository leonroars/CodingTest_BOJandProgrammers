class Solution {
    public boolean solution(int x) {
        int sum = 0;
        String xStr = Integer.toString(x);
        
        for(int i = 0; i < xStr.length(); i++){
            sum += Character.getNumericValue(xStr.charAt(i));
        }
        
        return x % sum == 0;
    }
}
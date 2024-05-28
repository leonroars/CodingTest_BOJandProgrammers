class Solution {
    public double solution(int[] arr) {
        int sum = sum(arr);
        double answer = (double) sum / (double) (arr.length);
        return answer;
    }
    
    private int sum(int[] arr){
        int sum = 0;
        for(int i = 0; i < arr.length; i++){
            sum += arr[i];
        }
        return sum;
    }
}
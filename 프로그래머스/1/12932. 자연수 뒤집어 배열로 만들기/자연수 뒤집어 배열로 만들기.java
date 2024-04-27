class Solution {
    public int[] solution(long n) {
        String s = Long.toString(n);
        int[] answer = new int[s.length()]; // n의 길이 크기를 갖는 배열 초기화
        for(int i = 0; i <= ((s.length() - 1) / 2); i++){
            answer[i] = Character.getNumericValue(s.charAt(s.length() - 1 - i));
            answer[s.length() - 1 - i] = Character.getNumericValue(s.charAt(i));
        }
        return answer;
    }
}
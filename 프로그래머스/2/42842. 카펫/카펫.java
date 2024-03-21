class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        int y = 0; // 노랑 타일의 세로 갯수
        for(int x = yellow; x >= (int) Math.sqrt(yellow); x--){
            // 노랑 타일의 갯수가 노랑 타일의 가로 갯수로 나누어 떨어질때만 세로를 살펴보기
            if(yellow % x == 0){ 
                y = yellow / x; // 노랑 타일의 세로 갯수 = 노랑 타일 갯수 / 노랑 타일 갯수
                if((2 * (x + y) + 4) == brown){
                    System.out.print(x + " " + y);
                    answer[0] = x + 2; answer[1] = y + 2; break;
                }
            }
        }
        
        return answer;
    }
}
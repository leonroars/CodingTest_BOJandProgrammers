import java.util.*;

class Solution {
    // numbers로부터 만들어진 수를 중복없이 저장하는 해시 집합.
    HashSet<Integer> set = new HashSet<>();
    BitSet alreadyMade;
    
    public int solution(String numbers) {
        alreadyMade = new BitSet(numbers.length());
        DFS(numbers, "", 0); // DFS 호출 통해, 가능한 모든 수를 만들고 HashSet에 저장.
        int primeCnt = 0;
        for(Integer createdNum : set){
            if(isPrime(createdNum)){primeCnt++;}
        }
        
        return primeCnt;
        
    }
    
    // 만들 수 있는 모든 수를 만들기 위한 깊이우선탐색 메서드
    private void DFS(String number, String prev, int length){
        // Edge Case : 만들려고 하는 숫자의 길이가 numbers의 길이보다 길어질 시 => return
        if(length > number.length()){return;}
        
        for(int i = 0; i < number.length(); i++){
            // 만약 numbers의 i번째 숫자가 사용가능하다면,
            if(!alreadyMade.get(i)){
                alreadyMade.set(i); // 현재 숫자 '사용 중' 임을 표시
                set.add(Integer.parseInt(prev + number.charAt(i))); // 만들어진 수 집합에 추가.
                DFS(number, prev + number.charAt(i), length + 1); // 현재 길이보다 하나 더 긴 숫자 만들기 위한 재귀호출.
                alreadyMade.flip(i); // 현재 숫자를 사용한 수를 만들고 왔으므로 다시 '사용 가능' 상태로 표시해주기.
                // 1, 12, 123 까지 만들고 다시 돌아온 상황이니 이젠 132를 만들어야 하는 상황이라는 것을 생각하면 이해가 쉽다.
            }
        }
    }
    // 소수 판별 메서드. O(sqrt(N))의 시간복잡도
    private boolean isPrime(int number){
        if(number < 2){return false;} // 1은 합성수도, 소수도 아니므로 따로 처리해준다.
        for(int i = 2; i <= (int)Math.sqrt(number); i++){
            if(number % i == 0){return false;}
        }
        
        return true;
    }
}
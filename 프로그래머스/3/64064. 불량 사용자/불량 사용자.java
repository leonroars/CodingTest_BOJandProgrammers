import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;

class Solution {
    
    HashSet<String> availableIdPool = new HashSet<>(); // 사용자 ID 집합
    HashSet<HashSet<String>> possibleSets = new HashSet<>();
    
    public int solution(String[] user_id, String[] banned_id) {
        
        availableIdPool.addAll(Arrays.asList(user_id)); // 사용자 ID 집합 생성
        findPossibleSet(user_id, banned_id, 0, new HashSet<>());
        return possibleSets.size();
    }
    
    /** Backtracking을 위한 재귀함수. **/
    public void findPossibleSet(String[] user_id,
                                String[] banned_id,
                                int bannedIdx,
                                HashSet<String> currentSet){
        
        // Base Case : banned_id 탐색이 끝난 경우. -> 완성된 경우의 수 갱신 및 리턴.
        if(bannedIdx > banned_id.length - 1){
            if(!possibleSets.contains(currentSet)){
                possibleSets.add(new HashSet<>(currentSet)); // ConcurrentModificationException 방지.
            }
            return;
        }
        
        String currentBanPattern = banned_id[bannedIdx]; // 현재 비교 대상으로 설정된 banned_id
        // 복사본 사용하지 않을 시 아래의 36번 라인~43에서 ConcurrentModificationException 발생.
        // Iteration 도중에 내용에 변경이 발생하기 때문.
        HashSet<String> availableIdPoolCopy = new HashSet<>(availableIdPool);
        
        // 아직 다른 제재 패턴과 매칭되지 않은 사용자 ID에 대해,
        for(String id : availableIdPoolCopy){
            // Case I : 일치할 경우.
            if(isPatternMatch(id, currentBanPattern)){
                availableIdPool.remove(id); // 매칭되었으므로 삭제.
                currentSet.add(id);
                findPossibleSet(user_id, banned_id, bannedIdx + 1, currentSet);
                availableIdPool.add(id); // 매칭되었던 ID 다시 가용풀에 추가.
                currentSet.remove(id);
            }
        }
    }
    
    /* banned_id : user_id 의 패턴이 일치하는 지 검사하는 메서드. */
    public boolean isPatternMatch(String id, String banPattern){
        if(id.length() != banPattern.length()){return false;}
        else{
            for(int c = 0;  c < banPattern.length(); c++){
                char current = banPattern.charAt(c);
                if(current == '*'){
                    continue;
                }
                else {
                    if(id.charAt(c) != current){return false;}
                }
            }
            return true;
        }
    }
}
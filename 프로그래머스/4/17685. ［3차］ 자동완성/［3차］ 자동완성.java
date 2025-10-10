import java.util.HashMap;

class Solution {
    public int solution(String[] words) {
        Trie trie = new Trie();
        
        // 0. Trie 에 문자 모두 삽입
        for(String word : words){
            trie.insert(word);
        }
        
        // 1. 유일하게 식별 가능하도록 하는 검색어 길이 누적 합 구하기
        int accum = 0;
        for(String wrd : words){
            int currentCnt = trie.findMinCountToDistinguish(wrd);
            accum += currentCnt;
        }
        
        return accum;
        
    }
    
    class Trie {
        TrieNode root;
        
        public Trie(){
            root = new TrieNode();
        }
        
        public void insert(String word){
            TrieNode currentNode = root;
            for(Character ch : word.toCharArray()){
                currentNode.next.putIfAbsent(ch, new TrieNode());
                currentNode = currentNode.next.get(ch);
                currentNode.cnt++;
            }
        }
        
        public int findMinCountToDistinguish(String target){
            TrieNode currentNode = root;
            int idx = 0;
            int result = 0;
            
            while(idx < target.length()){
                char ch = target.charAt(idx);
                currentNode = currentNode.next.get(ch);
                
                if(currentNode.cnt == 1){result = idx + 1; break;}
                idx++;
            }
            
            if(result == 0){result = idx;}
            
            return result;
        }
    }
    
    class TrieNode {
        HashMap<Character, TrieNode> next;
        int cnt; // Root~해당 character 로 이어지는 문자열을 공통으로 갖는 word의 수!
        
        public TrieNode(){
            next = new HashMap<Character, TrieNode>();
            cnt = 0;
        }
    }
}
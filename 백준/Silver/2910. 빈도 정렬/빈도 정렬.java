import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());
        
        // {num : Item}
        HashMap<Integer, Item> map = new HashMap<>();
        
        // 입력 받기 & 빈도 기록 : O(N)
        st = new StringTokenizer(br.readLine());
        for(int idx = 0; idx < N; idx++){
            int current = Integer.parseInt(st.nextToken());
            if(map.containsKey(current)){
                Item currentItem = map.get(current);
                currentItem.freq++;
                map.put(current, currentItem);
            }
            else {
                map.put(current, new Item(current, idx));
            }
        }
        
        ArrayList<Item> arr = new ArrayList<>();
        
        // 옮겨 담기 : O(N)
        for(Map.Entry<Integer, Item> entry : map.entrySet()){
            arr.add(entry.getValue());
        }
        
        // 정렬 : O(N log N)
        Collections.sort(arr); // Comparable 정의했으므로 이에 따라 정렬될 것.
        
        // 정답 문자열 만들기
        StringBuilder result = new StringBuilder();
        
        for(Item item : arr){
            for(int cnt = 0; cnt < item.freq; cnt++){
                result.append(item.num);
                result.append(" ");
            }
        }
        
        System.out.print(result.toString().trim());
        
    }
    
    static class Item implements Comparable<Item>{
        int num; // C 최대가 10억이므로 문제 X.
        int freq; // 이후 freq 수만큼 StringBuilder에 쓰기하면 된다.
        int idx; // 2차 정렬 조건. 최초 생성 시에 설정된 idx가 가장 빠른 idx.
        
        public Item(int number, int idx){
            this.num = number;
            this.freq = 1;
            this.idx = idx;
        }
        
        @Override
        public int compareTo(Item other) {
            if(this.freq != other.freq){return other.freq - this.freq;} // 내림차.
            return this.idx - other.idx; // 인덱스는 오름차
        }
    }
}
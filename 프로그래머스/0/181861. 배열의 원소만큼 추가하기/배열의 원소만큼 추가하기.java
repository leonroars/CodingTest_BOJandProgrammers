import java.util.*;

class Solution {
    public int[] solution(int[] arr) {
        ArrayList<Integer> lst = new ArrayList<>();
        
        for(int i = 0; i < arr.length; i++){
            int count = 0;
            while(count < arr[i]){
                lst.add(arr[i]);
                count++;
            }
        }
        return lst.stream().mapToInt(Integer::intValue).toArray();
    }
}
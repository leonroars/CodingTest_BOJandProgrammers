import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {
    
    static class NumberItem implements Comparable<NumberItem>{
        String num; // 해당 수의 문자열 데이터.

        public NumberItem(String num){
            this.num = num;
        }
        
        @Override
        public int compareTo(NumberItem other){
            StringBuilder AOrder = new StringBuilder();
            StringBuilder BOrder = new StringBuilder();
                
            AOrder.append(this.num).append(other.num);
            BOrder.append(other.num).append(this.num);
                
            String A = AOrder.toString();
            String B = BOrder.toString();
            
            return A.compareTo(B);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        ArrayList<NumberItem> arr = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(br.readLine());
        
        
        // 1. N 개의 수 입력 받아 배열에 추가 : O(N) _ check-fast 위해 All-Zero 여부 검증
        
        boolean isAllZero = true;
        
        for(int i = 0; i < N; i++){
            NumberItem current = new NumberItem(st.nextToken());
            if(Integer.parseInt(current.num) != 0){isAllZero = false;}
            arr.add(current);
        }
        
        // 2. AllZero 면 바로 0 반환. 아니라면 정렬 후 문자열 완성하기
        String answer;
        
        if(isAllZero){
            answer = "0";
        }
        else {
            Collections.sort(arr, Collections.reverseOrder());
            StringBuilder ans = new StringBuilder();
            for(NumberItem item : arr){
                ans.append(item.num);
            }
            
            answer = ans.toString();
        }
        
        System.out.print(answer);
    }
}
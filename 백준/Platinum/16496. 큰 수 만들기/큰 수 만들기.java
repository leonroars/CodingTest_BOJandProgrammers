import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/*
현 문제는 그리디 알고리즘을 이용해 구한 해가 최적해임이 성립한다.

해당 문제가 Greedy Property 와 Optimal Substructure 성질을 만족하기 때문이다.

<Greedy Property 증명>
 '두 수 A, B에 대해 AB > BA이면 A를 B보다 앞에 배치하는 것이 최적이다.
증명:
최적해에서 어떤 위치에 B가 A보다 앞에 있다고 가정 (...BA...)
AB > BA이므로, B와 A의 위치를 바꾸면 (...AB...)가 되어 더 큰 수가 됨
이는 최적해라는 가정에 모순
따라서 AB > BA일 때 A를 B보다 앞에 두는 것이 항상 최적

<Optimal Substructure 증명>
'n개 수의 최적해는 (n-1)개 수의 최적해를 포함한다.'
증명:

n개 수 {a₁, a₂, ..., aₙ}의 최적 배치를 S라고 하자
S에서 맨 앞의 수를 제거한 부분을 S'이라고 하자.

귀류법: S'이 (n-1)개 수의 최적해가 아니라고 가정.
그러면 더 좋은 배치 T'이 존재한다.
맨 앞 수를 T'에 붙인 결과가 S보다 좋아지는데, 이는 S가 최적이라는 가정에 모순된다.

*/

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
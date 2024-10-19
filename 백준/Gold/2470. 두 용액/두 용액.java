import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

/*
 0. 핵심 풀이 아이디어 : 정렬 후 투 포인터
 1. 시간 복잡도 : O(N log N) / 정렬 O(N log N) + 입력 받기 O(N) + 투 포인터 O(N)
 2. 풀이 시 까다로웠던 부분 : 왼쪽과 오른쪽 포인터 중 어떤 것을 움직여야할 지 결정하는 부분.
    - 해결 방안 : 현 위치 기준 왼쪽과 오른쪽을 움직이는 것 중 어느 쪽이 '0' 에 가까이 움직이는데에 유리한 지 비교 후 결정.
    - 정렬을 한 상태와 더불어 비교 후 한 번에 왼쪽 혹은 오른쪽 하나의 포인터만 순차적으로 움직이기 때문에,
      이전의 선택보다 항상 더 나은 선택을 향해 움직인다.

 */

public class Main {
    static int N;
    static int[] arr;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 1. 입력 : O(n)
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        // Sorting : O(n log n) : Approximately 100,000,000 operations for sorting.
        Arrays.sort(arr);
        
        System.out.print(findPair());
    }
    
    private static String findPair(){
        int left = 0;
        int right = N - 1;
        int sum = Integer.MAX_VALUE;
        String pair = "";
        
        while(left < right){
            int leftNum = arr[left];
            int rightNum = arr[right];
            int currentSum = leftNum + rightNum;
            // Updating
            if(Math.abs(currentSum) < Math.abs(sum)){
                sum = currentSum;
                pair = Integer.toString(leftNum) + " " + Integer.toString(rightNum);
            }
            
            // Moving Pointers : 지금보다 둘의 합이 0에 더 가까워지는 쪽으로 움직이기.
            if(Math.abs(leftNum + arr[right - 1]) <= Math.abs(rightNum + arr[left + 1])){
                right--;
            }
            else {left++;}
        }
        
        return pair;
    }
    
}
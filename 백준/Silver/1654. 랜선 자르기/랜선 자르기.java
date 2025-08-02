import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    static int K;
    static int N;
    static int[] preparedCables;
    static int longestLength = -Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 0. Initialization
        K = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        preparedCables = new int[K];
        
        // 1. Taking input.
        for(int i = 0; i < K; i++){
            preparedCables[i] = Integer.parseInt(br.readLine());
        }
        
        // 2. Sort : O(n log n)
        Arrays.sort(preparedCables);
        
        // 2. Binary Search. : O(n log n)
        solution();
        
        // 3. Print answer.
        System.out.print(longestLength);
    }
    
    private static void solution(){
        int low = 1;
        int high = preparedCables[K - 1];
        
        while((low <= high) && isLegal(low, high)){
            long lowHighSum = (long)low + (long)high;
            int trialLen = (int)(lowHighSum / 2); // low / high 에서 overflow 발생 시 trailLen == 0.
            
            // 만약 trialLen == 1 이고 최대 길이가 2^31-1인 경우, 합산 결과가 int 표현 범위 초과 가능.
            long accumCnt = 0;
            
            for(int i = 0; i < K; i++){
                if(preparedCables[i] >= trialLen){
                    accumCnt += preparedCables[i] / trialLen;
                }
            }
            
            // Case I : trialLen 길이로 잘랐을 때 N개 이상인 경우 -> 길이를 늘려보기.
            if(accumCnt >= N){
                longestLength = Math.max(longestLength, trialLen);
                low = trialLen + 1;
            }
            else {
                high = trialLen - 1;
            }
        }
    }
    
    private static boolean isLegal(int low, int high){
        return low > 0 && high > 0 && low <= Integer.MAX_VALUE && high <= Integer.MAX_VALUE;
    }
}
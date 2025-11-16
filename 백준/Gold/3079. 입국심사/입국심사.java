import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()); // 검색대 수
        int M = Integer.parseInt(st.nextToken()); // 대기자 수
        
        int[] durationOf = new int[N];
        
        for(int i = 0; i < N; i++){
            durationOf[i] = Integer.parseInt(br.readLine());
        }
        
        long minTime = calculateMinProcessTime(N, M, durationOf);
        
        System.out.print(minTime);
    }
    
    public static long calculateMinProcessTime(int N, int M, int[] durationOf){
        long lo = 0;
        long hi = Long.MAX_VALUE; // 심사대 1개, M 10억명, 심사 소요시간 10억일 경우 10억의 제곱까지 가능.
        
        while(lo + 1 < hi){
            long mid = lo + (hi - lo)/2; // Overflow 방지 위한 계산.
            
            if(isFeasible(N, M, durationOf, mid)){
                hi = mid;
            }
            else {
                lo = mid;
            }
        }
        
        return hi; // T/F 경계에 멈추게 되고, 불가한 시간은 없으므로.
    }
    
    /* t 시간에 처리 가능한지 판단하는 판별식 */
    private static boolean isFeasible(int N, int M, int[] durationOf, long t){
        long currentPossibleThroughput = 0;
        
        for(int i = 0; i < N; i++){
            currentPossibleThroughput += t / durationOf[i];
            if(currentPossibleThroughput >= M){break;} // DONE
        }
        
        return currentPossibleThroughput >= M;
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static int N;
    static boolean[] isPrime;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        isPrime = new boolean[N + 1];
        
        // 1. 에라토스테네스의 체 호출을 통해 N 이하의 소수 목록 파악. : 합이 N 이기에, N 이하의 소수를 대상으로만 수행한다.
        ArrayList<Integer> primes = sieveOfEratosthenes();
        
        // 2. 포인터 두 개를 활용해 합이 N인 '연속된 소수' 쌍의 수를 구한다.
        int answer = numberOfPrimeSumEqualN(primes);
        
        // 3. 출력
        System.out.print(answer);
        
    }
    
    private static ArrayList<Integer> sieveOfEratosthenes(){
        // N 이하의 수에 대한 소수 판별
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        
        for(int k = 2; k * k <= N; k++){
            if(isPrime[k]){
                for(int j = k * k; j <= N; j+=k){
                    isPrime[j] = false;
                }
            }
        }
        
        // 소수 판별 결과 ArrayList 에 복사.
        ArrayList<Integer> primes = new ArrayList<>();
        for(int i = 2; i <= N; i++){
            if(isPrime[i]){primes.add(i);}
        }
        
        return primes;
    }
    
    private static int numberOfPrimeSumEqualN(ArrayList<Integer> primes){
        int l = 0;
        int r = 0;
        int count = 0;
        
        // N == 1 인 경우, primes.size() == 0 -> IndexOutOfBoundException 발생.
        if(primes.size() > 0){
            int currentSum = primes.get(l);
            while(l < primes.size() && r < primes.size() && l <= r){
                if(l == r){ // N 이하 소수 집합의 원소가 1이라 해당 원소 인덱스 위치했거나 or 배열의 가장 끝에 위치했거나 or 중간이거나.
                    if(currentSum <= N){
                        if(currentSum == N){count++;}
                        r++;
                        if(r < primes.size()){currentSum += primes.get(r);}
                    }
                    // Non-promising : l==r 이지만 벌써 N 
                    else {
                        l++;
                    }
                }
                else {
                    if(currentSum <= N){
                        if(currentSum == N){count++;}
                        r++;
                        if(r < primes.size()){currentSum += primes.get(r);}
                    }
                    else if(currentSum >= N){
                        currentSum -= primes.get(l++);
                    }
                }
            }
        }
        
        return count;
    }
}
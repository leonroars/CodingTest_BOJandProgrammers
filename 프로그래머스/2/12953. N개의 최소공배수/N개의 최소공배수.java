import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

class Solution {

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[] arr = new int[]{2, 6, 8, 14};

        int answer = sol.solution(arr);

        System.out.print(answer);
    }

    HashSet<Integer> primes = new HashSet<>(); // 소수 목록.
    HashMap<Integer, Integer> commonPrimeFactor = new HashMap<>(); // 공통 소인수.

    public int solution(int[] arr) {
        // 0. 문제에서 명시된 수의 범위(1 ~ 100) 사이의 모든 소수 찾기.
        findPrime();

        // 1. arr[] 내 모든 원소 소인수 분해. 공통 소인수가 있는 경우, 최고 차수만 남기기.
        for(int num : arr){

            if(num > 1){
                HashMap<Integer, Integer> factorized = primeFactorization(num);

                for(Map.Entry<Integer, Integer> primeFactor : factorized.entrySet()){
                    if(commonPrimeFactor.containsKey(primeFactor.getKey())){
                        if(commonPrimeFactor.get(primeFactor.getKey()) < primeFactor.getValue()){
                            commonPrimeFactor.put(primeFactor.getKey(), primeFactor.getValue());
                        }
                    }
                    else {
                        commonPrimeFactor.put(primeFactor.getKey(), primeFactor.getValue());
                    }
                }
            }
        }

        // 2. commonPrimeFactor에 저장된 모든 key에 대하여, Math.pow(key, value) 값들의 누적곱 반환하기.
        int LCM = 1;
        for(Map.Entry<Integer, Integer> factor : commonPrimeFactor.entrySet()){
            LCM *= (int)Math.pow(factor.getKey(), factor.getValue());
        }

        return LCM;

    }

    /* num 소인수 분해한 후 <소인수 : 차수> 반환 */
    private HashMap<Integer, Integer> primeFactorization(int num){
        HashMap<Integer, Integer> factorized = new HashMap<>();

        int dividend = num;

        while(!primes.contains(dividend) && dividend > 1){
            for(int prime : primes){
                if(dividend % prime == 0){
                    if(factorized.containsKey(prime)){
                        int currentExpo = factorized.get(prime);
                        factorized.put(prime, currentExpo + 1);
                    }
                    else {
                        factorized.put(prime, 1);
                    }
                    dividend = dividend / prime;
                    break;
                }
            }
        }

        if(dividend != 1){
            if(factorized.containsKey(dividend)){
                int currentExpo = factorized.get(dividend);
                factorized.put(dividend, currentExpo + 1);
            }
            else {
                factorized.put(dividend, 1);
            }
        }

        return factorized;
    }

    /* 에라토스테네스의 체를 활용한 '주어진 범위 내의' 소수 찾기 */
    private void findPrime(){
        for(int i = 2; i < 101; i++){
            boolean isPrime = true;
            for(int j = 2; j < 11; j++){
                if(i <= j){break;}
                else {if(i % j == 0){isPrime = false; break;}}
            }
            if(isPrime){primes.add(i);}
        }
    }
}
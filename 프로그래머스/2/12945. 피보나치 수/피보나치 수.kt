/*

 1. 풀이 아이디어 : Modulo 연산의 분배법칙 활용 & 재귀
   - K > 1 인 K에 대해, F(K) = F(K % 1234567) 임이 성립한다.
   - 따라서 전체 n 에 대해 위와 같은 계산을 반복문을 통해 수행한다.

 */

class Solution {
    fun solution(n: Int): Int {
        
        val DIVISOR = 1234567
        
        var kMinus2 = 0
        var kMinus1 = 1
        var k = 1;
        
        for(cnt in 2..n){
            k = ((kMinus2 % DIVISOR) + (kMinus1 % DIVISOR)) % 1234567
            kMinus2 = kMinus1
            kMinus1 = k
        }
        
        return k;
    }
}
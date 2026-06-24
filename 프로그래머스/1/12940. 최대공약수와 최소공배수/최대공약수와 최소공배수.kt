class Solution {
    fun solution(n: Int, m: Int): IntArray {
        val gcd = if(n > m) gcd(n, m) else gcd(m, n)
        val lcm = lcm(n, m, gcd)
        
        return intArrayOf(gcd, lcm)
    }
    
    // dividend 와 divisor 간 크기는 호출부에서 dividend > divisor 이도록 정리하여 호출하도록 정의
    fun gcd(dividend: Int, divisor: Int) : Int {
        if(divisor == 0){return dividend}
        return gcd(divisor, dividend % divisor)
    }
    
    fun lcm(a: Int, b: Int, gcd: Int) : Int {
        return a * (b / gcd) // Overflow 방지 위해 나누기 먼저.
    }
}
class Solution {
    fun solution(n: Int): Int {
        
        // 1) 3진법 변환 함수 호출 : 뒤집힌 채로 오도록 설계
        val base3Reversed = convertToBase3(n, StringBuilder()).toString()
        
        // 2) 10진법으로 변환 후 반환
        return convertToBase10(base3Reversed)
    }
    
    // 3진법 변환
    fun convertToBase3(n: Int, accumulated: StringBuilder) : StringBuilder {
        // Base Case : n / 3 == 0
        if(n / 3 == 0){
            accumulated.append(n % 3)
            return accumulated
        }
        return convertToBase3(n / 3, accumulated.append(n % 3))
    }
    
    // 10진법 변환
    fun convertToBase10(base3String: String) : Int {
        val len = base3String.length
        var result = 0
        
        for(i in len-1 downTo 0 step 1) {
            val digit = base3String[len - 1 - i]
            result += digit.digitToInt() * Math.pow(3.0, i.toDouble()).toInt()
        }
        
        return result
    }
}
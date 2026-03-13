import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    
    val N = Integer.parseInt(br.readLine())
    
    val result = StringBuilder()
    
    for(i in 1..N){
        result.append("*".repeat(i).padStart(N, ' '))
        if(i<N){result.append("\n")}
    }
    
    print(result.toString())
}
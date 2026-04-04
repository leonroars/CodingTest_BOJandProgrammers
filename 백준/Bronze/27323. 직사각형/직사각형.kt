import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    
    val A = Integer.parseInt(br.readLine())
    val B = Integer.parseInt(br.readLine())
    
    print(A * B)
}
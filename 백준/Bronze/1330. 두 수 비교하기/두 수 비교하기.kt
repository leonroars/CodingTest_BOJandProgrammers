import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

fun main () {
    val br = BufferedReader(InputStreamReader(System.`in`))
    val st = StringTokenizer(br.readLine())
    
    val a = Integer.parseInt(st.nextToken())
    val b = Integer.parseInt(st.nextToken())
    
    val ans = if(a > b) ">" else if(a == b) "==" else "<"
    
    System.out.print(ans)
}
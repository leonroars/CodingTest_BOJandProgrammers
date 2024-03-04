import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
    // factorial(20)은 int형 표현범위를 넘기 때문에 오버플로우가 발생하지 않도록 반환 결과 타입을 long.
    static long factorial(int N){
        if(N == 0){return 1;}
        return N * factorial(N-1);
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        System.out.print(factorial(N));
    }
}
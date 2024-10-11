import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.ArrayList;

/*
 1. 풀이 아이디어 : 나머지 연산의 분배법칙 성질 + 분할정복
                 문제 해답을 func, 인자를 B 값으로 갖는다고 해보자.
                 1) func(1) : A % C
                 2) func(2) : (A * A) % C = ((A % C) * (A % C)) % C = (func(1) ^ 2) % C
                 3) func(3) : (A * A * A) % C = (func(2) * func(1)) % C
                 4) func(4) : (func(2) * func(2)) % C = (func(2) ^ 2) % C

 */

public class Main {

    static int A;
    static int B;
    static int C;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        int answer = func(B);
        System.out.print(answer);
    }

    private static int func(int exponent){
        if(exponent == 1){return A % C;}
        // 지수가 2의 배수인 경우
        if(exponent % 2 == 0){
            int squareCall = func(exponent / 2);
            Long result = ((long) squareCall * squareCall);
            return (int)(result % C);
        }
        // 지수가 2의 배수가 아닌 경우
        int shareByTwo = func((exponent - 1));
        Long result = ((long) shareByTwo * func(1));
        return (int)(result % C);
    }
}
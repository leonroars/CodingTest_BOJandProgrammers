import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        StringTokenizer st2 = new StringTokenizer(br.readLine());
        
        int A = Integer.parseInt(st1.nextToken());
        int B = Integer.parseInt(st1.nextToken());
        int C = Integer.parseInt(st2.nextToken());
        int D = Integer.parseInt(st2.nextToken());
        
        // 두 분수의 합 구하여 기약분수로 반환하는 메서드 호출
        String answer = sumOfFraction(A, B, C, D);
        
        System.out.print(answer);
    }
    
    private static String sumOfFraction(int A, int B, int C, int D){
        StringBuilder result = new StringBuilder();
        
        // 1. 두 분모를 공통분모 가진 분수로 만들기.
        int gcdOfDenom = GCD(B, D);
        int commonDenom = (B * D) / gcdOfDenom;
        
        int multA = A * (commonDenom / B);
        int multC = C * (commonDenom / D);
        
        int resultNume = multA + multC;
        
        // 2. (resultNume / commonDenom) : 분모, 분자의 최대 공약수를 구하여 기약분수로 만들기.
        int gcdOfFraction = GCD(resultNume, commonDenom);
        
        // 3. 정답 반환
        result.append(resultNume / gcdOfFraction);
        result.append(" ");
        result.append(commonDenom / gcdOfFraction);
        
        return result.toString();
    }
    
    // 메모리 제약이 타이트하니 재귀가 아닌 반복문으로 구현하기.
    private static int GCD(int k, int j){
        int dividend = Math.max(k, j);
        int divisor = Math.min(k, j);
        int mod = dividend % divisor;
        int gcd = (mod == 0) ? (divisor) : (-Integer.MAX_VALUE);
        
        while(mod > 0){
            dividend = divisor;
            divisor = mod;
            mod = dividend % divisor;
            if(mod == 0){gcd = divisor;}
        }
        
        return gcd;
    }
}
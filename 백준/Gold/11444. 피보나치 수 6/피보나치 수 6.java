import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/*

도대체 누가 이걸... 그냥 떠올리냐고,,, 진짜 믿기지가 않는구만

<풀이 핵심 : 선형 변환(Linear Transformation)>

 - 함수는 입력값을 투입하면 계산에 의해 '일정한 규칙을 갖는 결과'를 반환한다.
 - 선형대수에도 유사한 개념이 있다. 어떤 벡터를 행렬에 입력하면 일정한 규칙을 갖는 결과 벡터가 출력된다는 것.
 - 이를 '변환(Transformation)' 이라고 한다.
 - 그리고 피보나치 수열의 일반항 규칙은 다음과 같다.(n >= 2)
   f(n) = f(n-1) + f(n-2)
   이처럼 상수배 또는 덧셈만을 이용하여 값을 조합하는 것을 '선형 조합(Linear Combination)'이라고 한다.
   피보나치 수열의 일반항 또한 이러한 선형 조합을 보인다.
   이를 행렬을 활용하여 '선형 변환' 형태로 표현할 수 있다.
   피보나치 수 f(n)을 구성하는 선형 조합의 각 항(벡터)인 f(n-1), f(n-2)를 2*1 행렬로 표현해볼 수 있다.
   이때 어떤 행렬 M 이 있을 때,
   f(n) = M^(n-1) * f(1) = M^(n-1)가 성립한다.
   따라서 이는 행렬 거듭제곱을 구하는 분할정복 문제가 되는 것.
   
   자세한 것은.. 문제 풀고 다시 알아보자...

 */

public class Main {
    static int[][] M = new int[][]{{1, 1}, {1, 0}};
    static int divisor = 1000000007;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long N = Long.parseLong(br.readLine());
        
        int[][] fibonacciOfNth = calculateNthFibonacci(N);
        int answer = (int)(((long)fibonacciOfNth[0][0]) % divisor);
        System.out.print(answer);
    }
    
    public static int[][] calculateNthFibonacci(long N){
        if(N == 0){return new int[][]{{0}, {0}};}
        if(N == 1){return new int[][]{{1}, {0}};}
        
        int[][] multiplierMatrix = calculateExponentOfMatrix(M, N - 1);
        
        return multiplyMatrices(multiplierMatrix, calculateNthFibonacci(1));
    }
    
    private static int[][] calculateExponentOfMatrix(int[][] matrix, long exponent){
        if(exponent == 1){return matrix;}
        if(exponent % 2 == 0){
            int[][] left = calculateExponentOfMatrix(matrix, exponent / 2);
            return multiplyMatrices(left, left);
        }
        else {
            return multiplyMatrices(calculateExponentOfMatrix(matrix, exponent - 1), matrix);
        }
    }
    
    private static int[][] multiplyMatrices(int[][] a, int[][] b){
        int[][] result = new int[a.length][b[0].length];
        
        for(int row = 0; row < result.length; row++){
            for(int col = 0; col < result[0].length; col++){
                long accum = 0;
                for(int k = 0; k < result.length; k++){
                    accum += ((long)a[row][k] * (long)b[k][col]) % divisor;
                }
                result[row][col] = (int)(accum % divisor);
            }
        }
        
        return result;
    }
}
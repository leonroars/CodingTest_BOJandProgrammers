import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    static int[][] a;
    static int[][] aSquared;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int A = Integer.parseInt(st.nextToken());
        long B = Long.parseLong(st.nextToken());
        
        // 행렬 A 입력 받기
        a = new int[A][A];
        for(int row = 0; row < A; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < A; col++){
                a[row][col] = Integer.parseInt(st.nextToken()) % 1000;
            }
        }
        
        // 행렬 A 제곱 구하기
        aSquared = multiplyMatrices(a, a);
        
        int[][] AToThePowerOfB = exponentiation(a, B);
        
        System.out.print(generateStringFromMatrix(AToThePowerOfB));
    }
    
    public static int[][] exponentiation(int[][] matrix, long exponent){
        if(exponent == 1){return a;}
        
        if(exponent == 2){return aSquared;}
        
        if(exponent % 2 == 0){
            int[][] half = exponentiation(matrix, exponent / 2);
            
            return multiplyMatrices(half, half);
        }
        
        return multiplyMatrices(exponentiation(matrix, exponent - 1), matrix);
    }
    
    public static int[][] multiplyMatrices(int[][] p, int[][] q){
        int size = p.length;
        
        int[][] result = new int[size][size];
        
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                long accum = 0;
                
                for(int k = 0; k < size; k++){
                    accum += (long)p[row][k] * (long)q[k][col];
                }
                
                result[row][col] = (int)(accum % 1000);
            }
        }
        
        return result;
    }
    
    private static String generateStringFromMatrix(int[][] matrix){
        StringBuilder result = new StringBuilder();
        
        for(int row = 0; row < matrix.length; row++){
            StringBuilder rowStr = new StringBuilder();
            for(int col = 0; col < matrix.length; col++){
                rowStr.append(matrix[row][col]);
                if(col < matrix.length - 1){rowStr.append(" ");}
            }
            result.append(rowStr);
            if(row < matrix.length - 1){result.append("\n");}
        }
        
        return result.toString();
    }
}
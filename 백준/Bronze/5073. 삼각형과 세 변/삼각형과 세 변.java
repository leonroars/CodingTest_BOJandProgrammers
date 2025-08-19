import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder answer = new StringBuilder();
        
        while(true){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] sides = new int[3];
            sides[0] = Integer.parseInt(st.nextToken());
            sides[1] = Integer.parseInt(st.nextToken());
            sides[2] = Integer.parseInt(st.nextToken());
            sides = Arrays.stream(sides)
                .boxed()
                .sorted(Collections.reverseOrder())
                .mapToInt(Integer::intValue).toArray();
                
            if(!isEnd(sides)){
                if(isTriangle(sides)){
                    if(isEquilateral(sides)){answer.append("Equilateral");}
                    else if(isIsosceles(sides)){answer.append("Isosceles");}
                    else if(isScalene(sides)){answer.append("Scalene");}
                }
                else {
                    answer.append("Invalid");
                }
                
                answer.append("\n");
            }
            else{
                break;
            }
        }
        
        System.out.print(answer.toString().trim());
    }
    
    private static boolean isEnd(int[] sides){
        return sides[0] == sides[1] && sides[1] == sides[2] && sides[2] == 0;
    }
    
    private static boolean isTriangle(int[] sides){
        int biggest = sides[0];
        return biggest < sides[1] + sides[2];
    }
    
    private static boolean isEquilateral(int[] sides){
        return sides[0] == sides[1] && sides[1] == sides[2];
    }
    
    private static boolean isIsosceles(int[] sides){
        return !isEquilateral(sides) && ((sides[0] == sides[1]) || (sides[0] == sides[2]) || (sides[1] == sides[2]));
    }
    
    private static boolean isScalene(int[] sides){
        return sides[0] != sides[1] && sides[1] != sides[2];
    }
}
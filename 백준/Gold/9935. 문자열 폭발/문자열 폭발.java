import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayDeque;

public class Main {
    static String given;
    static String bomb;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        given = br.readLine();
        bomb = br.readLine();
        
        String result = explode();
        
        System.out.print(result);
    }
    
    private static String explode(){
        int locator = 0;
        StringBuilder stack = new StringBuilder();
        
        while(locator < given.length()){
            char current = given.charAt(locator);
            
            stack.append(current);
            
            if(isEndOfBomb(current)){
                if(stack.length() >= bomb.length()){
                    String candidate = stack.substring(stack.length() - bomb.length(), stack.length());
                    if(candidate.equals(bomb)){stack.delete(stack.length() - bomb.length(), stack.length());}
                }
            }
            
            locator++;
        }
        
        return (stack.length() == 0) ? ("FRULA") : (stack.toString());
    }
    
    private static boolean isEndOfBomb(char c){
        return bomb.charAt(bomb.length() - 1) == c;
    }
}
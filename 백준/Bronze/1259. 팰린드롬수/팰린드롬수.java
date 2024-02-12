import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String input = br.readLine();
            int N = Integer.parseInt(input);
            if(N == 0){break;}
            String answer = "yes";
            for(int i = 0; i < input.length() / 2; i++){
                if(input.charAt(i) != (input.charAt(input.length() - 1 - i))){
                    answer = "no";
                    break;
                }
            }
            System.out.println(answer);
        }

    }
}
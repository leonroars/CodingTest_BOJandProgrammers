import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        int[] dial = new int[26];
        int answer = 0;

        int map = 2;
        int count = 1;
        for(int i = 0; i < dial.length; i++){
            dial[i] = map;
            count++;

            if((i > 14 && i < 19) | (i > 21)){
                if(count > 4){count = 1; map++;}
            } else {
                if(count > 3){count = 1; map++;}
            }
        }

        for(int j = 0; j < input.length(); j++){
            int converted = (int)input.charAt(j) - 65;
            answer += (dial[converted] - 1) + 2;
        }

        System.out.println(answer);
    }
}
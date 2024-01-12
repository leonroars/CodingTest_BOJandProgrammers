import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;

public class Main{
    public static void main(String[] args) throws IOException{
        BitSet arr = new BitSet(30);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 28; i++){
            int input = Integer.parseInt(br.readLine());
            arr.set(input - 1);
        }

        for(int j = 0; j < 30; j++){
            boolean check = arr.get(j);
            if(!check) {
                sb.append(j + 1);
                sb.append(" ");
            }
        }

        System.out.println(sb.toString());
    }
}
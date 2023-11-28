import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        int N;
        int v;
        int count = 0;
        String numStream;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        numStream = br.readLine();
        v = Integer.parseInt(br.readLine());
        String[] nsArr = numStream.split(" ");
        
        for(int i = 0; i < nsArr.length; i++){
            if(v == Integer.parseInt(nsArr[i])){count++;}
        }
        
        System.out.printf("%d", count);
        
        
        
        
    }
}
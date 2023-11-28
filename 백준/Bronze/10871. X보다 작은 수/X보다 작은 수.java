import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException{
        int N;
        int X;
        String numString;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        //Initializing N,X as given.
        N = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
        
        numString = br.readLine();
        String[] nsArr = numString.split(" ");
        
        for(int i = 0; i < nsArr.length; i++){
            if(X > Integer.parseInt(nsArr[i])){System.out.printf("%s ", nsArr[i]);}
        }
        
        
        
    }
}
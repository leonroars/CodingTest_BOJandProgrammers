import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] atWhichIdx;
        boolean[] marked;
        int count = 0;
        for(int i = 0; i < N; i++){
            String word = br.readLine();
            atWhichIdx = new int[26];
            marked = new boolean[26];
            int passedChar = 0;

            for(int j = 0; j < word.length(); j++){
                char current = word.charAt(j);
                int currentIdx = ((int) current) - 97;

                if(!marked[currentIdx]){
                    marked[currentIdx] = true;
                    atWhichIdx[currentIdx] = j;
                }
                else {
                    if(atWhichIdx[currentIdx] == j - 1){
                        atWhichIdx[currentIdx] = j;
                    }
                    else {break;}
                }
                passedChar++;
            }
            if(passedChar == word.length()){count++;}


        }

        System.out.println(count);
    }
}
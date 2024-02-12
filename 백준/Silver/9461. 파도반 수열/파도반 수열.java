import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16); // 버퍼 제한걸어 받기.
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < T; i++){
            int Nth = Integer.parseInt(br.readLine());
            sb.append(wave(Nth));
            sb.append("\n");
        }
        br.close();

        System.out.println(sb);

    }

    static long wave(int Nth){
        long[] memoization = new long[Nth + 1];
        if(Nth < 4){return 1;}
        else {
            for(int i = 1; i < 4; i++){memoization[i] = 1;}
            for(int j = 4; j < memoization.length; j++){
                memoization[j] = memoization[j - 2] + memoization[j - 3];
            }
            return memoization[Nth];
        }
    }
}
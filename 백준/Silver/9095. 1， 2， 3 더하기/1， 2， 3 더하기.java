import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class Main{
    static int[] dp;

    static int func(int num){
        if(num == 1){return dp[1] = 1;} // 1을 1,2,3을 이용해 표현하는 방법 : 1가지(1)
        if(num == 2){return dp[2] = 2;} // 2를 1,2,3을 이용해 표현하는 방법 : 2가지(1+1, 2)
        if(num == 3){return dp[3] = 4;} // 3 "" : 5가지(1+1+1, 1+2, 2+1, 3)
        if(dp[num] == 0){
            int count = 0;
            for(int i = 1; i < 4; i++){
                count = count + func(num - i);
            }
            dp[num] = count;
        }
        return dp[num];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < TC; i++){
            int current = Integer.parseInt(br.readLine());
            dp = new int[current + 1];
            sb.append(func(current));
            if(i != TC - 1){sb.append("\n");}
        }
        br.close();
        System.out.print(sb);
    }
}
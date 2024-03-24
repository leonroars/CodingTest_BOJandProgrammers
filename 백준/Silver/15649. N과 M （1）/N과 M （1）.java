import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int N;
    static int M;
    static boolean[] usedNum;
    static StringBuilder answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        usedNum = new boolean[N+1];
        answer = new StringBuilder();

        makeNum("", 1);

        System.out.print(answer.toString().trim());
    }

    private static void makeNum(String prev, int depth){
        // Edge Case : 깊이가 목표 길이 M을 넘을 시 지금까지 만들어진 수 StringBuilder에 추가 후 돌아가기.
        if(depth > M){answer.append(prev); answer.append("\n"); return;}

        for(int i = 1; i <= N; i++){
            if(!usedNum[i]){
                usedNum[i] = true;
                String current;
                if(depth < M){current = prev + i + " ";}
                else{current = prev + i;}

                makeNum(current, depth + 1);
                usedNum[i] = false;
            }
        }
    }
}
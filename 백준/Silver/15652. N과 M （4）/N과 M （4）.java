import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.BitSet;

// 숫자 중복 채택은 허용하되, 완성된 수열이 중복되지 않도록 하는 방법은?
// for 반복을 이용해 1 ~ N까지 순차적으로 올라가며 총 M회 재귀호출을 하도록 함으로써 두 가지를 강제하는 방법.

public class Main {
    static StringBuilder answer = new StringBuilder();
    static int maxNum;
    static int maxDepth;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        maxNum = Integer.parseInt(st.nextToken());
        maxDepth = Integer.parseInt(st.nextToken());


        seqGenerator(1, "", 0);

        System.out.println(answer.toString().trim());
    }

    private static void seqGenerator(int startNum, String current, int currentDepth){
        // Base Case
        if(currentDepth == maxDepth){
            answer.append(current);
            answer.append("\n");
            return;
        }
        for(int i = startNum; i < maxNum + 1; i++){
            String updated = current + i;
            if(currentDepth < maxDepth - 1){updated += " ";}
            seqGenerator(i, updated, currentDepth + 1);
        }

    }
}
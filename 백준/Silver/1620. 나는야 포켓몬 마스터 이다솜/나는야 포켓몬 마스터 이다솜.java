import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.HashMap;

public class Main{
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        HashMap<String, Integer> nameAndNum = new HashMap<>();
        String[] numAndName = new String[N + 1]; // 포켓몬 1~N번까지 존재.

        StringBuilder answer = new StringBuilder();

        // 1. 입력 받기.
        for(int i = 1; i < N + 1; i++){
            String current = br.readLine();
            nameAndNum.put(current, i);
            numAndName[i] = current;
        }

        for(int j = 0; j < M; j++){
            String question = br.readLine();
            // 이름인 경우.
            if(Character.isAlphabetic(question.charAt(0))){
                answer.append(nameAndNum.get(question));
            }
            else{
                answer.append(numAndName[Integer.parseInt(question)]);
            }
            answer.append("\n");
        }
        System.out.print(answer.toString().trim());
    }
}
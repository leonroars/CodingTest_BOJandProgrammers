import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    static int unitCnt = 0; // Pn 의 갯수.
    static int sLen;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        sLen = Integer.parseInt(br.readLine());
        String S = br.readLine();

        findPn(S, N);

        System.out.print(unitCnt);

    }

    private static void findPn(String s, int n){
        int loc = 0; // 탐색 구간의 머리 idx
        int currentPc = 0; // 끊김없이 지금까지 발견한 연결된 P1 갯수.

        while(loc < sLen - 2){
            // Case I : Pc의 머릿글자 I 발견한 경우.
            if(s.charAt(loc) == 'I'){
                // i) P1 발견
                if(s.charAt(loc + 1) == 'O' && s.charAt(loc + 2) == 'I'){
                    currentPc++;
                    loc += 2;
                }
                // ii) 깨졌으므로 새로 찾아야함.
                else {
                    if(currentPc >= n){unitCnt += (currentPc - n + 1);}
                    currentPc = 0;
                    loc++;
                }
            }
            // 아닌 경우 : head++ 함으로써 가장 처음 출현하는 I 찾기.
            else{
                loc++;
            }
        }
        if(currentPc >= n){unitCnt += (currentPc - n + 1);}
    }
}
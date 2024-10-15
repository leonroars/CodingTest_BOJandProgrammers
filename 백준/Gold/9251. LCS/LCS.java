import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    static String X;
    static String Y;
    static int[][] lcsLength;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        X = br.readLine();
        Y = br.readLine();

        // X.길이 >= Y.길이인 경우 : X 길이 기준 탐색
        String shorter = (X.length() <= Y.length()) ? X : Y;
        String longer = (shorter.equals(X)) ? Y : X;

        // lcsLength[i][j]
        //       : 짧은 문자열의 앞 i 개를 취한 부분순열과 긴 문자열 앞 j개 취한 부분 순열 간 존재하는 LCS 길이.
        lcsLength = new int[shorter.length() + 1][longer.length() + 1];

        boolean currentRowFlag = false; // 이번 행 이전 열에서 같은 것을 찾았는가?
        // 1. LCS 탐색 : O(N^2) - 두 문자열의 길이가 모두 최대 입력 길이인 1,000인 경우 백 만 번의 연산 요구.
        for(int row = 0; row < shorter.length() + 1; row++){
            for(int col = 0; col < longer.length() + 1; col++){
                // Case I : 두 문자열 모두 혹은 둘 중 하나가 비어있을 경우, LCS 길이는 0이다.
                if(row == 0 || col == 0){lcsLength[row][col] = 0;}
                // Case II : 0 이 아닌 길이의 부분 순열 길이에 대하여,
                else {
                    char s = shorter.charAt(row - 1);
                    char l = longer.charAt(col - 1);

                    if(s == l){
                        int possibleNewLength = lcsLength[row - 1][col - 1] + 1;
                        if(lcsLength[row][col] < possibleNewLength){
                            lcsLength[row][col] = possibleNewLength;
                        }
                    }
                    else {
                        // Edge Case
                        if(row == 1){lcsLength[row][col] = lcsLength[row][col - 1];}
                        // Common Case :
                        // 서로 다를 경우, 둘 중 LCS 길이가 넓은 단어 쪽을 포함한 LCS 길이가 정답.
                        else{lcsLength[row][col] = Math.max(lcsLength[row - 1][col], lcsLength[row][col - 1]);}
                    }
                }
            }
        }

        System.out.print(lcsLength[shorter.length()][longer.length()]);
    }
}
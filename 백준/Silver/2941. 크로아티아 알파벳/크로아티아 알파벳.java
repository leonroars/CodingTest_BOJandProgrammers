import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
  !! 잦은 String.substring() => MLE를 야기한다!
 */

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        System.out.print(croatianCounter(input));

    }

    public static int croatianCounter(String word) {
        int cnt = 0;
        if (word.isBlank()) { return cnt; }

        for (int idx = 0; idx < word.length(); ) {
            char current = word.charAt(idx);

            if (isCroatian(current) && idx < word.length() - 1) {
                if (current == 'c') {
                    char next = word.charAt(idx + 1);
                    if (next == '=' || next == '-') {
                        idx += 2;
                    } else {
                        idx += 1;
                    }
                } else if (current == 'l' || current == 'n') {
                    char next = word.charAt(idx + 1);
                    if (next == 'j') {
                        idx += 2;
                    } else {
                        idx += 1;
                    }
                } else if (current == 'd') {
                    if (idx < word.length() - 2 && word.charAt(idx + 1) == 'z' && word.charAt(idx + 2) == '=') {
                        idx += 3;
                    } else if (word.charAt(idx + 1) == '-') {
                        idx += 2;
                    } else {
                        idx += 1;
                    }
                } else if (current == 's' || current == 'z') {
                    char next = word.charAt(idx + 1);
                    if (next == '=') {
                        idx += 2;
                    } else {
                        idx += 1;
                    }
                } else {
                    idx += 1;
                }
            } else {
                idx++;
            }
            cnt++;
        }
        return cnt;
    }
    private static boolean isCroatian(char c){
        if(c == 'c' || c == 'd' || c == 'z' || c == 'l' || c == 'n' || c == 's'){return true;}
        return false;
    }

}
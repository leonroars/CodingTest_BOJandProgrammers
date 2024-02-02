import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
// import java.util.StringTokenizer;
// - javadoc은 StringTokenizer가 deprecated는 아니나 legacy 코드이기 때문에 이를 사용하기보단 split()을 사용하길 권장하고 있다.

// I. 풀이 접근
// 먼저 주어진 문자열에서 단어를 판별하는 기준을 파악해야한다.
// 이 문제는 입력 문자열이 영문대소문자와 공백으로만 이루어져있으므로, 다른 경우의 수(ex. 문장부호)를 고려할 필요가 없다.
// 그렇다면 크게 두 가지 방법을 생각해볼 수 있다.
//  1) 문자 단위로 입력문자열을 탐색하며 공백의 수를 파악한다. 단어의 수는 (공백의 수 + 1)이므로 이를 결과로서 반환한다.
//  2) 입력받은 문자열을 입력문자열.strip().split(구분자)를 이용해 공백을 제거한 단어 단위의 배열을 반환받고, 이 배열의 길이를 반환한다.
// String.split(delimiter) 메서드 또한 루프를 이용해 주어진 문자열을 순차탐색하는 방식으로 작동하기 때문에 둘 간의 시간/공간복잡도의 차이는 유의미하지 않다.
//      - 물론 단어 수만큼의 크기를 가진 배열을 이용하기 때문에 split()의 공간비용이 1)의 방법보다 큰 것은 사실이다.
// 따라서 더 깔끔해보이는 split()을 쓰기로 한다.
// 한 가지 주의할 점은 split()이 " "를 공백 구분자로 사용하는 것에서 알 수 있듯이, 공백 하나만 입력으로 주어지는 경우가 처리 되지 않으므로 따로 처리해주어야한다.

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine().strip();
        if(input.length() == 0){System.out.println("0");}
        else{
            String[] parsed = input.split(" ");
            System.out.println(parsed.length);
            br.close();   
        }

        //The Curious Case of Benjamin Button
        // The first character is a blank
        //The last character is a blank
    }
}
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Arrays;

// I. 문제 요구조건 분석
//     a. 입력으로 주어진 문자열을 순차탐색한다.
//     b. 매 탐색마다 해당 문자가 어떤 알파벳인지 '확인'하고 '알파벳 풀(pool)'에 '표시'한다.
//     c. 표시가 완료되면, 알파벳 풀의 알파벳 순서대로 포함/미포함 결과를 공백으로 구분하여 출력한다.
//    -> 요구조건 분석 결과, 주어진 입력(크기 N)에 대한 순차탐색이 불가피하다는 문제의 제약조건 도출.
//    -> 이러한 경우 문제 해결방안은 최소 O(n)의 시간복잡도를 요구한다.
//    -> 따라서 전체 해결방안의 시간복잡도를 선형으로 한정하고 싶다면,
//         입력 문자열 탐색 외의 작업 - 알파벳 풀에서 탐색, 변경, 변경된 알파벳 풀 탐색 및 출력 - 의 작업이 상수 시간 복잡도에 이루어져야한다.

// II. 접근
//     a. 총 두 번의 반복문을 이용하는 단순한 방법. : O(n) (주어진 입력 탐색 O(n) + 알파벳 풀 탐색(O(1)))
//        1) 소문자 알파벳의 갯수와 순서에 대응하는 배열 생성. 모두 -1로 초기화.
//        2) 주어진 입력을 순차탐색하며 S.charAt(iterator)로 확인 후 해당 알파벳의 순서에 맞는 배열의 인덱스로 접근해 값 수정.
//            - 주의해야할 점 : 접근했을 시 해당 인덱스의 값이 -1일때만 갱신해준다. 처음 위치만 출력해야하기 때문.
//            - 문자를 이용해 배열의 인덱스에 바로 접근이 가능한 것은 해당 문자의 아스키코드가 소문자 a(97)로부터 얼마나 떨어져있는가를 확인함으로써 가능하다.
//        3) 알파벳 풀을 순차탐색하며 StringBuilder에 추가해준다.
//     b. 해시맵 이용.
//        - 이 경우 확실하게 입력 순차탐색 이외의 작업 시간복잡도를 상수시간에 수렴하게한다. 이때 해시맵의 노드의 키-값 매핑은 {알파벳 : 출력값} 형태이다.
//        - 문자열 순차 탐색시 상수시간에 내부에 저장된 알파벳과 그 값에 접근 및 수정이 가능하다.
//        - 하지만 주어진 입력을 순차탐색하기 때문에 전체 풀이의 시간복잡도는 O(n)에서 개선 X
//        - 해시맵은 요소가 객체이기때문에 공간 복잡도는 a.풀이보다 많은 비용을 요구함.
//    따라서 a의 방법으로 해결하기로 결정한다.


public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String S = br.readLine(); // ASCII of a = 97 / z = 122
        int[] alphabets = new int[26];
        Arrays.fill(alphabets, -1);

        for(int i = 0; i < S.length(); i++){
            int idx; // 현재 문자(char)의 아스키코드로부터 계산한 배열 인덱스를 담을 변수!
            char c = S.charAt(i);
            if(alphabets[idx = ((int) c) - 97] == -1){alphabets[idx] = i;}
        }
        
        StringBuilder sb = new StringBuilder();
        for(int j : alphabets){
            sb.append(j);
            sb.append(" ");
        }
        
        System.out.println(sb);

    }
}
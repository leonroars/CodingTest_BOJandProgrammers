import java.util.HashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    // I. 요구조건 분석
    //
    // 해당 문제의 요구조건은 다음과 같이 정리할 수 있다.
    // 1. 두 번째 입력으로 주어지는 N개의 정수(현재 보유 중인 카드의 표기 정수 집합)를 어떤 형태로든 저장해야한다.
    //     - 네 번째 줄의 입력에서 주어지는 비교 대상(임의로 주어지는 카드 M개의 표기 정수 집합)을 받아 비교해보야하기 때문.
    // 2. 네 번째 줄의 입력으로 주어지는 M개의 정수가 현재 보유 중인 카드의 표기 정수 집합에 속한다면 1, 아니면 0을 표기하여
    //     공백으로 구분된 M개의 1 혹은 0으로 이루어진 문자열을 출력한다.


    // II. 풀이 접근 방식 지정 및 풀이 설계
    //
    // 문제의 요구조건을 만족하기 위한 방법으로 크게 두 가지 종류를 생각해볼 수 있다.
    //  1) 두 번째 줄의 입력('N'개)에서 주어진 '현재 보유 중인 카드의 표기 정수'들을 배열 혹은 다른 어떤 자료구조에 저장한다.
    //     이후 네 번째 줄의 입력('M'개)이 주어지면 네 번째 입력을 순차 탐색한다.
    //     동시에 앞서 자료구조에 저장한 N개의 '현재 보유 중인 카드의 표기정수'를 순차탐색하며 비교한다. 결과에 따라 1 혹은 0을 결과로 출력될 문자열에 추가한다.
    //     - 최악의 경우 O(M * N) - 하나도 보유 중이지 않은 경우 - 의 시간복잡도를 필요로한다.
    //
    //  2) '현재 보유 중인 카드의 표기 정수'를 이진탐색트리의 형태로 저장한다. 이분탐색을 활용한 탐색을 지원하므로 효율적인 탐색이 가능하다.
    //       - 'Degenerate Tree' 형태로 저장되는 경우를 제외하면 이진탐색트리에서의 탐색 시간 복잡도는 O(log n)에 수렴하므로,
    //          차후 M개의 네 번째 줄 입력에 대한 순차 탐색을 고려하면 전체 풀이의 시간복잡도는 O(M log N)에 근사함을 알 수 있다.
    //
    //  3) 반복을 피하기 위해 사전에 정의된 어떤 위치 지정 규칙 혹은 함수에 따라 계산된 위치에 두 번째 줄의 입력 각각을 저장한다.
    //     이 함수는 사전에 정의된 어떤 계산방식으로서, 주어진 정수 입력을 계산하여 저장위치를 반환한다.
    //
    //     지정 함수는
    //      첫째, 실행시간 동안 같은 입력에 대해 복수의 실행에 걸쳐 일관된 계산 결과를 보장해야하고
    //      둘째, 반환된 저장위치는 실제 저장 가능한 위치(저장 자료구조의 인덱스 범위 내)여야 한다.
    //
    //     저장 자료구조는 해당 함수로부터 반환받은 저장위치에 대해 최대한 효율적인 접근방식을 제공해야한다.
    //     예를 들어 배열을 저장 자료구조로 사용할 경우, 배열은 주어진 인덱스 위치로의 즉시 접근을 제공한다.
    //     이와 더불어 위치 지정 함수의 연산 또한 지나치게 복잡하지 않을 경우 상수 시간이 소요된다고 가정해도 무리가 없을 것이므로,
    //     이 두 가지를 활용한 풀이의 시간복잡도는 O(1)에 수렴하게된다. - 구현방식에 따라 최악의 경우 O(n)이 소요될 수 있다. 상세 내용 '해시테이블' 참조바람.
    //
    // 3)번 접근 방식은 흔히 해시테이블이라고 불리는 자료구조의 정의와 일치한다. 따라서 java.util.HashMap Collection을 이용하기로 한다.
    // ps. 자바의 해시 컬렉션 두 가지 - HashMap, Hashtable - 을 참고하여 직접 만들어 본 해시테이블 모듈이 있으나,, 너무 길어서 그냥 만들어진 걸 쓰겠습니다,,,
    //     => https://github.com/leonroars/datastructure/blob/main/DataStructure_Java/src/hashtable/HashTable.java


    public static void main(String[] args) throws IOException{
        HashMap<Integer, Integer> hm = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 1. 첫 번째 입력 : 보유 중인 카드의 갯수 N
        int N = Integer.parseInt(br.readLine());

        // 2. 두 번째 입력 : 보유 중인 카드의 표기 정수 시퀀스 _ 이를 쪼개어 해시맵 인스턴스에 추가.
         StringTokenizer st = new StringTokenizer(br.readLine());
         for(int i = 0; i < N; i++){
             Integer current = Integer.parseInt(st.nextToken());
             hm.put(current, current);
         }

         // 3. 세 번째 입력 : 비교할 카드 갯수 M
        int M = Integer.parseInt(br.readLine());

         // 4. 네 번째 입력 : 비교할 카드의 표기 정수 시퀀스 _
        // * 공간 복잡도를 고려하여 별도로 배열에 넣는 작업을 하지 않고 바로바로 비교하여 결과를 출력될 StringBuilder에 추가한다.
        st = new StringTokenizer(br.readLine());
        StringBuilder result = new StringBuilder();
        for(int j = 0; j < M; j++){
            int comp = Integer.parseInt(st.nextToken());
            if(hm.containsKey(comp)){result.append("1");}
            else{result.append("0");}
            if(j != M-1){result.append(" ");}
        }

        System.out.println(result);

    }
}
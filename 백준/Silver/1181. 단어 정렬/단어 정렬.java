import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.BitSet;

/*
   [풀이]

   1. 문제 조건 파악 및 답안 시간복잡도 상한 정하기 : O(n * sqrt(n))
      - 1<= 단어의 개수 N <= 20,000
      - 메모리 : 256MB
      - 시간 : 2초 -> 연산 수 2,000,000

      통상적으로 채점 컴퓨터의 성능 상 1초 당 백 만 번의 연산이 가능할 것이라고 상정한다. (구종만 - 알고리즘 풀이 전략)
      이러한 전제와 위의 조건을 종합해볼 때 이 문제 해답이 가질 수 있는 시간복잡도 상한은 O(n * sqrt(n)) 이 된다.
       (2,000,000 < 20,000 * sqrt(20,000))
   2. 풀이 아이디어.
      이 문제의 해답은 다음의 세 가지 로직을 포함해야한다.
       1) 중복 여부 검증
       2) 단어 별 길이 기준 정렬
       3) 같은 길이 단어 2개 이상일 경우 사전순(알파벳 오름차순) 정렬.

       위의 세 가지 로직을 포함하는 답안을 작성하기 위해,
       브루트 포스 방식의 풀이 시나리오를 완성하고 효율적으로 대체 가능한 부분을 찾아 대체하는 방식으로 구상하였다.
       그 결과 위의 각 로직 세 가지를 효율적으로 해결할 수 있는 자료구조를 확정할 수 있게 되었다.
       1) 중복 여부 검증 - O(n) in Amortized Analysis
        - java.util.HashMap / HashSet을 사용할 수도 있으나 HashSet도 HashMap을 활용하므로 익숙한 쪽을 활용하기로 했다.
        - Key : 단어 / Value : 길이
       2) 단어 별 길이 기준 정렬 - O(n)
        - 중복 여부 검증 후 중복 존재 하지 않는 경우만 별도의 String[] 배열에 담은 후 Comparator를 이용해 정렬하는 방식도
          생각해보았으나, 더 좋은 방식이 생각났다.
         a. ArrayList<String>을 원소로 갖는 ArrayList<String>[] 배열을 초기화한다.
            이때 배열의 인덱스 = 단어 길이와 대응한다.
         b. 단어를 HashMap에 삽입하는 경우, 배열에도 대응하는 인덱스 위치에 함께 삽입해준다.
         c. 이때 BitSet.set(해당 길이) 설정한다. 이는 출현한 적 없는 단어 길이를 차후 방문하는 경우를 배제하기 위함.
             - 이렇게 할 경우 단어의 길이순 정렬을 하지 않아도 배열 순차방문을 통해 정렬이 가능하다.
               O(n log n) -> O(n)
        3) 같은 길이 단어 2개 이상일 경우 사전순(오름차순) 정렬.
         - BitSet을 확인하며 true로 set 되어있는 위치의 배열 인덱스만 방문하여 안에 담긴 단어를 확인한다.
           이때 배열 인덱스에 위치한 ArrayList 원소가 한 개 이상인 경우 사전순 정렬을 시행한다.
           사전순 정렬은 Comparator와 Collections.sort() 를 사용한다.
           그렇다면 단어의 사전순 정렬은
            최악의 경우 - 모든 단어의 길이가 최대 길이(50)로 동일하고정렬 필요한 경우 -
             O(n * 50 * log n) -> O(nlogn)이다.

        위의 답안의 시간 복잡도는 최악의 경우 O(n log n)이므로 위에서 설정한 해답의 시간 복잡도 상한 조건을 만족한다.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 입력될 단어의 수.

        ArrayList<String>[] wordArr = (ArrayList<String>[]) new ArrayList[51]; // idx : 단어의 길이 / wordArr[n] = 길이가 n인 단어가 담긴 ArrayList.
        HashMap<String, Integer> map = new HashMap<>(); // Key : 단어 / Value : 단어의 길이.
        BitSet existingLength = new BitSet(51); // 글자 수 최대 50자임을 감안한 크기 설정.

        for(int i = 0; i < N; i++){
            String word = br.readLine();
            if(!map.containsKey(word)){
                map.put(word, word.length()); // HashMap에 <단어, 단어 길이> 추가.
                if(wordArr[word.length()] == null){
                    wordArr[word.length()] = new ArrayList<>();
                }
                wordArr[word.length()].add(word); // 배열[단어길이]에 해당 단어 추가.
                existingLength.set(word.length()); // 해당 단어 길이를 가진 단어가 존재함을 표시.
            }
        }

        StringBuilder answer = new StringBuilder(); // 정답을 담을 StringBuilder.

        for(int j = 0; j < existingLength.size(); j++){
            // 해당 길이를 가진 단어가 존재하는 경우.
            if(existingLength.get(j)){
                ArrayList<String> words = wordArr[j];
                if(wordArr[j].size() > 1){
                    words.sort(String::compareTo);
                }
                for(String word : words){
                    answer.append(word).append("\n");
                }
            }
        }

        System.out.println(answer.toString().trim());


    }
}
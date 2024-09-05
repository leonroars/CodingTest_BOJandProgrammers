import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayList;

/*
 [문제 풀이]
 
 0. 문제 제약 조건 분석.
 * 메모리 : 256MB (최대 입력 개수에 비해 상당히 여유있게 설정 되었다는 생각을 했다.)
 * 시간 : 1초 (개략적으로 백 만 번의 연산까지 가능한 시간 제한).
 * 최대 입력 크기 : N <= 100,000
 
 시간과 공간 두 가지 조건을 살펴보았을 때 상대적으로 공간 상한이 시간 상한 보다 여유롭게 주어졌다고 생각했다.
 따라서 풀이를 설계한다면 공간 복잡도를 다소 높게 가져가더라도 시간 복잡도를 낮추는 방향으로 진행해야겠다고 생각했다.
 
 본격적인 해답 설계를 위해 시간 복잡도를 계산 해보기로 했다.
 시간 제약과 최대 입력 크기를 종합하여 생각해보면 사실 상 평균적인 경우 O(n)에 근사하게 나와야 함을 알 수 있다.
 최악을 표현하는 Big-O 표기법으로 생각해볼 경우 O(ln n) - 자연 로그 - 로 표현될 수 있겠지만,
 최악의 경우인 입력 10만 개에 대하여 모두 ln 100000(약 11)번의 연산을 수행할 경우 시간 초과가 날 수 있다.
 
 그럼 "전반적으로 입력 크기에 비례하지만 '종종' 자연로그 시간복잡도를 갖는 연산이 필요한 풀이"는 무엇일까?
 로그 시간복잡도에서 정렬 알고리즘을 떠올렸다.
 그 말은 x와 y 중 어느 쪽 하나는 정렬을 할 필요가 없어야 한다는 의미이다.
 당연히 일반적인 경우 - 경계값 테스트 성이 아닌 무작위에 가까운 입력을 받는 상황 -,
  x가 같은 좌표가 많지 않고, 몇 몇 같은 x 좌표를 가진 y 좌표가 복수 개 존재하는 경우에 대해서만 
  y 좌표 정렬을 실행해주는 상황을 그려볼 수 있을 것이다.
 
 따라서 다음과 같이 필요한 로직을 구분하고 이에 맞는 자료 구조를 고민해보도록 한다.
 1. 좌표를 입력 받으면서 저장한다. 저장한 후에는 x좌표 기준 정렬할 필요가 없어야 한다.
  - ArrayList<Integer>[] 를 떠올렸다.
  - 일종의 padding 혹은 offset 개념을 차용하면 구현이 가능함을 생각했다.
    * x와 y 모두 -100,000 ~ 100,000 범위 내에 존재한다.
    따라서 배열을 초기화할 때 크기를 200,001 로 설정한다.
    입력 받은 x좌표에 100,000을 더한 결과가 바로 해당 x좌표의 배열 내 저장 위치가 되도록 하는 것이다.
    이렇게 할 경우 모든 입력을 받고난 후 x 기준 정렬을 할 필요가 없어진다.
    배열을 처음부터 끝까지 읽으면서 y 좌표들을 살펴보고 정렬이 필요하면 정렬하면 되니까!
 2. 저장한 후 배열을 처음부터 살펴보며 복수의 y 좌표가 존재하는 경우 y 좌표 오름차순 정렬을 수행한다.
   - 만약 주어진 10만 개 입력의 모든 좌표의 x 좌표값이 동일하고 y만 다른 경우가 바로 최악의 시간복잡도를 갖는 경우일 것이다.
     이 경우 전체 풀이의 시간 복잡도는 O(n ln n)이 될 것이다. 단순 연산 수로만 100만이 조금 넘는 것일 것이다.
     만약 이 풀이가 통과가 안된다면 다른 방법을 찾아야할 것이다..
     
   - 통상적인 경우, 복수의 y좌표가 존재하는 x좌표의 수를 k(k < N), y 좌표 수 평균을 p(p < N) 라고 한다면,
     O(k * p log p) 정도로 생각해볼 수 있을 것이다.
     하지만 앞서 시행한 입력 저장 과정과 저장한 배열 읽기의 시간 복잡도가 O(n)이고 이 시간 복잡도가
     전체 알고리즘의 시간 복잡도에 가장 지배적인 영향을 미칠 것으로 생각된다.
     따라서 문제 조건 상 풀이의 시간복잡도 상한 조건을 만족할 것이다.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer>[] coordinates = (ArrayList<Integer>[]) new ArrayList[200001]; // -100,000 ~ 0 ~ 100,000
        StringBuilder answer = new StringBuilder();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if(coordinates[x + 100000] == null){
                coordinates[x+100000] = new ArrayList<>();
            }
            coordinates[x+100000].add(y);
        }
        
        for(int j = 0; j < coordinates.length; j++){
            if(coordinates[j] != null){
                ArrayList<Integer> ys = coordinates[j];
                ys.sort(Integer::compareTo); // Sort ys.
                for(int y : ys){
                    answer.append(j - 100000).append(" ").append(y).append("\n");
                }
            }
        }
        
        System.out.println(answer.toString().trim());
    }
}
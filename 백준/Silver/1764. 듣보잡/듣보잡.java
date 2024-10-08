import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;

/*
 - 시간 제한 : 2초
 - 메모리 제한 : 256MB
 - 1 <= N, M <= 500,000
 
 정답의 시간 및 용량 제한, 입력의 최대 크기를 고려했을 때 해답의 시간복잡도는 O(N log N) 보다 복잡해서는 안된다.
 또한 용량 제한이 시간에 비해 비교적 여유롭게 주어진 것을 고려했을 때, 
 공간복잡도를 올려 시간복잡도를 낮출 수 있는 방향으로 해답을 설계 해야한다.
 따라서 풀이를 다음과 같이 설계해본다.
 
 1. N 입력 받으며 HashSet에 추가. : O(N)
 2. M 입력 받으며 HashSet에 존재하는 것만 별도의 ArrayList에 추가. : O(M)
 3. ArrayList에 담긴 단어를 알파벳순으로 정렬. 최대 O(N log N) _ 듣도 못한 사람의 이름과 보도 못한 사람의 이름 목록이 완전 일치하는 경우.
 4. 출력 : 최대 O(N).
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        
        HashSet<String> neverHeardOf = new HashSet<>();
        ArrayList<String> both = new ArrayList<>();
        
        // 1. N 개의 듣도 못한 사람 이름 입력 받기.
        for(int i = 0 ; i < N; i++){
            neverHeardOf.add(br.readLine());
        }
        
        // 2. M 개의 보도 못한 사람 이름 입력 받으며 필터.
        for(int j = 0; j < M; j++){
            String neverSeenBefore = br.readLine();
            if(neverHeardOf.contains(neverSeenBefore)){
                both.add(neverSeenBefore);
            }
        }
        
        // 3. 정렬
        both.sort(Comparator.naturalOrder());
        
        System.out.println(both.size());
        for(int idx = 0; idx < both.size(); idx++){
            if(idx < both.size() - 1){System.out.println(both.get(idx));}
            else{System.out.println(both.get(idx));}
        }
    }
}
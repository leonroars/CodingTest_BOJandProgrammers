import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Arrays;

// 풀이 및 접근
// 1. 입력받은 수열을 배열에 넣는다(seq[]). 이때 차후 입력 수열을 정렬 및 중복 제거한 결과를 담을 배열에도 추가한다(sorted[]).
// 2. sorted[] 를 정렬 후 중복 제거한다.
// 3. 이후 수열을 순차탐색하며, 해당 수와 일치하는 수가 sorted[]에서 몇 번 인덱스인지 확인한다.
//    이는 곧, 중복 제거한 수열 내에서 해당 수의 크기 오름차순 순위이다.
//    확인 후, 답안으로 제출할 StringBuilder에 sorted[] 내에서의 해당 수 인덱스를 추가해준다.
public class Main{
    static int N;
    static int[] seq;
    static int[] sorted; // sorted = Arrays.stream(sorted).distinct().toArray(); 를 이용한 정렬/중복제거.
    static StringBuilder compressed;

    // 압축 : O(n log n)
    static void compress(){
        for(int i = 0; i < N; i++){
            int current = seq[i];
            compressed.append(binarySearch(0, sorted.length, current));
            if(i != N-1){compressed.append(" ");}
        }
    }

    // 이진탐색 구현 - O(log n)
    static int binarySearch(int start, int end, int targetValue){
        int middle = (start + end) / 2; // 중앙 위치
        if(sorted[middle] == targetValue){return middle;}
        if(sorted[middle] < targetValue){return binarySearch(middle, end, targetValue);}
        return binarySearch(start, middle, targetValue);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        N = Integer.parseInt(br.readLine());
        seq = new int[N];
        sorted = new int[N];
        compressed = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(st.nextToken());
            seq[i] = current;
            sorted[i] = current;
        }
        Arrays.sort(sorted);
        sorted = Arrays.stream(sorted).distinct().toArray(); // 중복 제거
        compress();
        bw.write(compressed.toString().trim());
        bw.flush();
        bw.close();
        br.close();
    }
}
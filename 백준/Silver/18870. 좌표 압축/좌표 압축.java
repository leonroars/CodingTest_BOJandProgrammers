import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Arrays;
public class Main{
    static int N;
    static int[] seq;
    static int[] sorted; // sorted = Arrays.stream(sorted).distinct().toArray(); 를 이용한 정렬/중복제거.
    static StringBuilder compressed;


    static void compress(){
        for(int i = 0; i < N; i++){
            int current = seq[i];
            compressed.append(binarySearch(0, sorted.length, current));
            if(i != N-1){compressed.append(" ");}
        }
    }

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
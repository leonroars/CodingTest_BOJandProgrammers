import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

/*
 문제 해결의 핵심 : 소요시간이 적은 사람을 먼저 처리하여 전체 소요 대기시간을 줄이는 방식.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] time = new int[N];
        int[] tab = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            time[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(time); // 소요시간 순 오름차순 정렬.
        int accum = 0;
        tab[0] = time[0];

        for(int j = 0; j < N; j++){
            if(j > 0){
                tab[j] = tab[j-1] + time[j];
            }
            accum += tab[j];
        }

        System.out.print(accum);
    }
}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{
    public static void main(String[] args) throws IOException {
        // 풀이 로직
        // 1. for문을 통해 받은 입력을 쪼개 배열에 넣으며 동시에 최댓값 비교 갱신
        // 2. for문을 통해 배열을 순회하며 주어진 연산 시행 후 값 업데이트.
        // 3. 배열 반환

        int N;
        int max = 0;
        float newSum = 0f; // 상대오차가 10^-2 허용이므로 크기가 큰 double이 아닌 float 선택.
        int[] arr;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 배열에 입력하며 최댓값 업데이트
        for(int i = 0; i < N; i++){
            int a = Integer.parseInt(st.nextToken());
            if(a > max){
                max = a;
            }
            arr[i] = a;
        }

        // 입력값 업데이트 후 배열 반환_ 범위가 필요 없는 반복문이므로 enhanced for loop 활용.
        for(int j : arr){
            newSum += (float)j / (float)max * 100;
        }

        System.out.println(newSum/N);



    }
}
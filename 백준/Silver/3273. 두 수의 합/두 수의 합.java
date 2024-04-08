import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

// 풀이 및 접근
// 처음의 풀이는 BitSet을 이용한 풀이를 사용했으나, 어째서인지 IndexOutOfBound,,,
// 분명 접근 방식은 참신하고 괜찮았던 거 같은데, 잠시 미뤄두고
// 정렬 후 두 개의 포인터를 이용해 풀이하였다.
// 1) 배열 정렬
// 2) 양 포인터가 '교차하기 전까지' 반복하며 아래의 각 포인터는 양 쪽 끝에서 시작.
// 3) 만약 양 포인터의 합이 목표 수보다 클 경우 오른쪽 포인터를 안쪽으로 이동 / 반대의 경우 바깥쪽으로 이동.
// 4) 이렇게 할 경우 정렬을 위해 요구되는 O(N log N)에 두 개의 포인터를 이용한 선형탐색 시간복잡도 O(N)이 더해진 시간복잡도를 갖게 된다.
//    하지만 시간 복잡도는 지배적인 항(Dominant factor)만 표기함으로, 해당 풀이의 시간 복잡도는 O(N log N)이 된다.


public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 수열의 길이
        int[] seq = new int[N]; // 수열의 수를 담은 배열
        int count = 0; // 수열의 수들 중 합해서 target이 되는 수 쌍의 갯수

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){seq[i] = Integer.parseInt(st.nextToken());}
        int target = Integer.parseInt(br.readLine()); // 목표 합

        Arrays.sort(seq); // 배열 정렬.

        int front = 0;
        int rear = N - 1;

        while(rear > front){
            int currentSum = seq[front] + seq[rear];
            if(currentSum == target){front++; count++;}
            else{
                if(currentSum < target){front++;}
                else{rear--;}
            }
        }

        System.out.print(count);




    }
}
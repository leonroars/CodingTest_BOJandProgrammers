import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/*
 0. 풀이 아이디어 : 1~9 까지 중 1개 혹은 2개 고르는 경우인 C(9, 1) = 9, P(9, 2) = 81 가지 경우에 대해
                 배열을 돌며 해당 경우를 만족하는 최장 길이를 모두 탐색해보기.
                 최대 입력 상황에서 90 * 200,000  = 18,000,000 개의 연산 필요.
                 따라서 2초 및 1GB라는 제약 상에서 충분히 가능하다.
 */

public class Main {
    static int N; // 1 <= N <= 200,000
    static int[] arr; // arr.length == N
    static int max;
    static int longest = 1; // 조건을 만족하는 최대 길이. 모두 다를 경우 1.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());

        // 0. 입력 받기. + 단일 종류 최장 부분 찾기 : O(N)
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 1. 1개짜리 찾기 : O(N)
        for(int target = 1; target < 10; target++){
            int currentLength = 0;
            for(int i = 0; i < N; i++){
                // 같을 경우 : 갱신
                if(arr[i] == target){
                    currentLength++;
                    if(currentLength > longest){longest = currentLength;}
                }
                // 다를 경우 : 바로 초기화.
                else {currentLength = 0;}
            }
        }

        // 2. 2개 짜리 찾기 : O(91 * N)
        for(int A = 1; A < 10; A++){
            for(int B = 1; B < 10; B++){
                // Case I : 1개짜리는 이미 탐색 완료했으므로 Skip.
                if(A == B){continue;}
                int head = 0;
                int tail = -1;
                int currentLength = 0;
                while(tail < N && head < N){ // A, B 둘 중 하나 혹은 둘 다 없을 수 있으므로 주의.
                    // Case I-i : A와 같은 수 찾은 경우 - tail 움직이면서 A 혹은 B와 같지 않을 때까지 증가시키며 길이 갱신.
                    if(arr[head] == A){
                        // * 처음 찾거나 다시 찾은 경우 : tail 포인터 설정.
                        if(tail == -1){currentLength = 1; tail = head + 1;}
                        // * 이미 head 위치 잡은 경우 : tail 증가 시키며 길이 최대 갱신
                        else{
                            if(arr[tail] == A || arr[tail] == B){
                                currentLength++;
                                tail++;
                            }
                            else {
                                head = tail;
                                tail = -1;
                                if(currentLength > longest){longest = currentLength;}
                                currentLength = 0;
                            }
                        }
                    }
                    else{
                        head++;
                    }
                    if(currentLength > longest){longest = currentLength;}
                }

            }
        }

        System.out.print(longest);

    }
}
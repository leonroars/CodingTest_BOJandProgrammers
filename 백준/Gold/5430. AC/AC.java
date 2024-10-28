import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine()); // The number of test cases.
        StringBuilder answer = new StringBuilder();

        // 0. 입력
        while(T > 0){
            String p = br.readLine(); // 실행할 함수의 길이
            int N = Integer.parseInt(br.readLine()); // 배열의 길이
            int[] arr = new int[N]; // 배열
            String arrString = br.readLine();
            int idxLocator = 0;

            // 배열 만들기 : O(N)
            if(N > 0){
                String numPool = "";
                for(int i = 0; i < arrString.length(); i++){
                    // delimiter가 아닌 문자에 대해서만 배열에 추가.
                    char current = arrString.charAt(i);
                    if(current != ',' && current != '[' && current != ']'){
                        numPool += Character.getNumericValue(current); // 숫자는 홀수 인덱스에만 존재한다.
                    }
                    else {
                        if(current == ','){
                            arr[idxLocator] = Integer.parseInt(numPool); numPool = "";
                            idxLocator++;
                        }
                        if(current == ']' && !numPool.isBlank()){arr[idxLocator] = Integer.parseInt(numPool);}
                    }
                }

            }
            answer.append(AC(arr, p)).append("\n");
            T--;
        }

        // 1. 정답 출력.
        System.out.print(answer.toString().trim());

    }

    public static String AC(int[] arr, String p){
        boolean isRotated = false;
        int left = 0; // 배열의 왼쪽 끝 포인트
        int right = (arr.length == 0) ? (-1) : (arr.length - 1); // 배열의 오른쪽 끝 포인터
        StringBuilder currentAnswer = new StringBuilder(); // 반환할 문자열

        // 1. 함수 실행
        for(int i = 0; i < p.length(); i++){
            char command = p.charAt(i);

            // Case I : 뒤집기
            if(command == 'R'){isRotated = !isRotated;}

            // Case II : 삭제
            else {
                // Case II-A : 비어있는 경우 - error 반환 후 종료.
                if(left > right){
                    return currentAnswer.append("error").toString();
                }
                // Case II-B : 비어있지 않은 경우
                else {
                    // Case II-B-1 : 뒤집어진 상태인 경우 - right 포인터 감소.
                    if(isRotated){right--;}
                    // Case II-B-2 : 본 상태인 경우 - left 포인터 증가.
                    else{left++;}
                }
            }
        }

        // 2. 출력물 만들기.
        int start;
        if(isRotated){start = right;}
        else{start = left;}
        int length = (left > right) ? (0) : (Math.abs(right - left) + 1); // R 명령만 존재할 경우 이때 문제 발생할 수 있다. 길이가 1이 되기 때문!

        if(length == 0){return "[]";}

        for(int j = 0; j < length * 2 + 1; j++){
            if(j == 0){
                currentAnswer.append("[");}
            else if(j == length * 2){
                currentAnswer.append("]");}
            else{
                if(j % 2 == 0){
                    currentAnswer.append(",");}
                else{
                    currentAnswer.append(arr[start]);
                    if(isRotated){start--;}
                    else{start++;}
                }
            }
        }

        return currentAnswer.toString();
    }
}
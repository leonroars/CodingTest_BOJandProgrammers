// (charAt(i) - 65) % 32 -> Arr Index.

/* 접근 방식
  <사용 자료 구조> : Array
  <시간 복잡도> : O(n) (n = 문자열을 이루는 문자의 수 <= 1,000,000)
   - HashMap을 생각했으나, 최대 문자의 수가 100만에 가까운 수를 하나씩 살피며
   HashMap에 추가해둔 후 추후 갱신 시 다시 회수할 때의 오버헤드를 고려했을 때 적합하지 않다고 판단했다. (HashMap.get() : O(log n) 
   - 그렇다면 어떻게 하면 배열의 인덱스를 활용하여, 배열의 빠른 접근을 잘 활용할 수 있을까?
   - 대문자와 소문자를 같게 취급한다는 점이 힌트. 
       대문자의 갯수와 소문자의 갯수가 동일하다는 점에 착안하여, 해당 문자를 배열의 인덱스로 변환하는 방식.
   - 예를 들어 a와 A는 각각 ASCII 코드 상 10진수 65, 97에 해당한다.
     a와 A 모두 0이 되도록 하는 수식이 무엇일까? => 65를 뺀 나머지를 32로 나누어주는 것.
   - 그렇다면 최대 빈도가 같은 문자가 무엇인지, 혹은 두 개 이상인지 확인하기 위해선 어떻게 해야할까?
     => 최대값 갱신 + 두 개 이상 여부를 표현하는 boolean 플래그 활용.
     => 배열 전체 정렬보다 경제적.
*/

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine(); // 입력 문자열
        int size = s.length(); // 문자열을 이루는 문자의 수
        int[] arr = new int[26]; // 문자 별 출현 빈도를 기록할 배열. 배열의 인덱스는 대소문자 구분 없는 알파벳과 대응.
        int maxFrequency = -1; // 최다 출현 빈도를 기록할 변수.
        int mostFrequentIndex = -1; // 가장 많이 출현한 알파벳의 배열 인덱스.
        boolean moreThanOne = false; // 최다 출현 빈도를 가진 알파벳이 두 개일 때 true.
        
        for(int i = 0; i < size; i++){
            char current = s.charAt(i);
            int idx = ((int)(current) - 65) % 32;
            arr[idx]++;
            
            if(arr[idx] > maxFrequency){
                maxFrequency = arr[idx];
                mostFrequentIndex = idx;
                moreThanOne = false;
            }
            else if (arr[idx] == maxFrequency) {
                moreThanOne = true;
            }
            else {
                continue;
            }
        }
        
        String answer;
        
        if(moreThanOne){answer = "?";}
        else {
            answer = Character.toString(Character.toUpperCase((char)(mostFrequentIndex) + 65));
        }
        
        System.out.println(answer);
    }
}
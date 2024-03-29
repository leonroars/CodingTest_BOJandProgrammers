import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

// 풀이 및 접근
// 
// 문제의 구현 자체는 어렵지 않으나 - 물론 가능한 경우에 따라 분기하는 처리가 까다롭기는 하다 - 
// 실제로 가장 어려웠던 부분은 '어떤 입력에 대해서건 결과를 최소로 만들 수 있는 괄호 위치를 어떻게 특정할 것인가?'에 대한
// 대답을 준비하는 것이었다.
// 이런 상황에서 보통 가장 쓸모있는 것은 역시 우리의 머리이다.
// 이런 문제들을 우리 머리는 생각보다 영리하고 또 직관적으로 풀어내기 때문이다.
// 우리의 머리 속에서 문제가 풀리는 과정을 0.5배속 하고 천천히 따라간다고 생각하면 힌트를 얻을 수 있는 경우가 많다.
// 나의 머리가 찾아낸 방법은 '-와 -사이의 모든 수를 괄호로 묶어버리는 것'이었다.
// 묶인 수들은 전부 양수이므로(-와 -사이의 수만 묶었기 때문에) 우선 이들의 합을 구한다.
// 이때 앞서 발견한 -가 괄호로 묶인 수들의 합 앞에 붙게 되며 순식간에 큰 절대값을 가진 음수가 된다.
// 구현은 철저하게 이 아이디어를 정확하게 수행할 수 있도록 코드로 옮기는 과정이었다.
// 다행히 정답이었으나, 아직 말끔하게 해결했다는 느낌이 들지 않았다.
// 왜 이 문제가 그리디 알고리즘 문제인가에 마땅한 대답을 할 수 없었기 때문이다.
// 지금도 내가 생각한 대답이 정확한 대답인지에 대한 확신은 없지만,
// 수식을 탐색하다가 -를 만나는 시점에서 다양한 선택이 존재한다는 것(ex. 그냥 바로 다음 수만 빼거나, 두 번째 까지만 합해 빼거나 .. etc),
// 그리고 이때마다 최적의 선택(이후의 수를 가장 크게 만들기 위해, 즉 '가능한' 가장 큰 수를 뺄 수 있도록 하기 위해)인
// '다음 -까지의 모든 수를 괄호로 묶어 앞의 -가 적용되는 대상으로 만들어 주는 것'이 존재하며, 이것이 전체의 최적해로 이어진다는 점에서
// 그리디 알고리즘 문제에 해당하지 않을까하는 생각을 한다.

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String expression = br.readLine();
        ArrayList<String> parsed = new ArrayList<>(); // 0까지 섞여서 들어온 중위표기식을 일반적으로 우리가 사용하는 중위표기식의 형태로 정리한 결과식.
        ArrayList<String> addAll = new ArrayList<>(); // 이 ArrayList의 원소를 모두 더하면 정답이 도출된다.
        StringBuilder operand = new StringBuilder();

        // 1. 입력받은 수식을 연산자 & 피연산자 단위로 파싱하여 ArrayList에 저장.
        for(int i = 0; i < expression.length(); i++){
            char current = expression.charAt(i);
            // Case I: 연산자가 아닌 경우 StringBuilder에 추가.
            if(current != '+' && current != '-'){operand.append(current);}
            // Case II : 연산자를 만난 경우, 이전까지 모아둔 숫자들이 모두 피연산자 하나라는 뜻이므로 숫자 하나로 바꿔서 배열에 넣어준다.
            else{
                // StringBuilder -> 일반적으로 표현되는 정수형 숫자 -> 다시 문자열.
                String parsedOperand = Integer.toString(Integer.parseInt(operand.toString()));
                parsed.add(parsedOperand); // 파싱 결과를 담는 ArrayList에 피연산자 저장
                parsed.add(Character.toString(current)); // 곧이어 이번에 마주친 연산자 저장.
                operand = new StringBuilder(); // 다음 피연산자 파싱 위해 StringBuilder 초기화.
            }
        }
        // Case III : 수식의 끝까지 돌았으나 operand에 잔여 데이터가 있는 경우 => operand 내의 데이터가 수식의 마지막 피연산자라는 의미.
        if(!operand.isEmpty()){
            String parsedOperand = Integer.toString(Integer.parseInt(operand.toString()));
            parsed.add(parsedOperand); // 피연산자 큐에 추가.
        }

        // 2. 연산자/피연산자 단위로 파싱한 결과를 저장한 ArrayList에서 하나씩 확인하며 처리.
        //    a) -를 만난 경우
        //      a-1) 그 직후의 피연산자 ~ 다음 - 전까지의 피연산자 or 수식의 마지막 피연산자 => 모두 누적해 더한 후 부호 반전시켜 addAll에 추가.
        //      a-2) + => continue
        //    b) +를 만난 경우 : continue
        //    c) 피연산자를 만난 경우
        //      c-1) 앞에서 -를 만났고 아직 다음 - 못 만난 경우 : 누적합에 추가.
        //      c-2) 그 외의 경우 : addAll에 추가.
        int numsInParen = 0; // -와 다음 -사이의 모든 수를 합해 저장하는 누적 합 변수
        boolean minusFlag = false; // - 발견 여부 플래그. true일 경우, 앞에서 -를 발견했으며 현재 누적합 처리 절차 진행 중이라는 의미.

        for(int i = 0; i < parsed.size();){
            String current = parsed.get(i);
            // Case I : parsed의 i번째 인덱스에 저장된 문자열(current)이 연산자인 경우
            if(current.equals("+") || current.equals("-")){
                // Case I - 1 : -인 경우 => 다음 -까지 만나는 피연산자를 모두 numInParen에 합산.
                if(current.equals("-")){
                    // 앞서 발견한 -가 있었고 이번에 새로운 -를 발견한 경우, 방금까지 누적하여 합산했던 numInParen을 부호 반전시킨 후 addAll에 저장.
                    // 추가한 후 i를 증가시켜주고 numsInParen이 새로운 - 이후의 피연산자 누적합을 받을 수 있도록 0으로 초기화.
                    if(minusFlag){addAll.add(Integer.toString(-numsInParen)); i++; numsInParen = 0;}
                    // 지금 발견한 -가 처음인 경우
                    else{minusFlag = true; i++;}
                }
                // Case I - 2 : +인 경우 => i를 증가시켜줌으로써 탐색 계속 진행.
                else {i++;}
            }
            // Case II : paresed의 i번째 문자열 current가 피연산자인 경우
            else {
                // Case II - 1 : 앞서 발견한 - 연산자가 존재하며, 아직 해당 -에 대한 처리 중일 경우, numsInParen에 합산.
                if(minusFlag){
                    numsInParen += Integer.parseInt(current);
                    // current가 현재 수식의 마지막 피연산자인경우(수식의 끝인 경우), 지금까지의 누적합을 부호반전시켜 addAll에 추가.
                    if(i == parsed.size() - 1){addAll.add(Integer.toString(-numsInParen)); break;}
                    i++;
                }
                // Case II - 2 : 처리 중인 -가 없을 경우, 바로 addAll에 추가.
                else{
                    addAll.add(current);
                    if(i == parsed.size() - 1){break;}
                    i++;
                }
            }
        }
        int answer = 0;
        for(int j = 0; j < addAll.size(); j++){
            answer += Integer.parseInt(addAll.get(j));
        }

        System.out.print(answer);
    }
}
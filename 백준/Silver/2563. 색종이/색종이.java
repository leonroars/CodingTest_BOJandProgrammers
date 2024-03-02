import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

// 풀이 접근.
// 하마터면 또 복잡한 로직으로 접근할 뻔 했다.
// 처음엔 정석적으로, 각 색종이의 넓이 합 - (겹치는 영역의 넓이 * (겹친 색종이의 수 - 1)) 과 같은 방식으로 구하려고 했다.
// 하지만 이렇게 할 경우, 겹치는 영역의 특정과 넓이 계산을 위해 다음과 같은 질문에 대한 대답을 준비해야한다.
// Q1. 어떤 색종이(x, y)가 주어질때, 해당 색종이의 네 모서리 특정 가능한가? - Yes.
// Q2. 위의 정보를 바탕으로, 색종이들이 주어질때 겹치는 영역과 겹치는 횟수를 계산할 수 있는가?
//     불가능하진 않을 것이지만 굉장히 복잡해진다.
//     색종이 두 가지를 겹치는 방식이 겹쳐지는 부분의 위치에 따라 총 9가지가 나오기 때문이다.
//     만약 이렇게 색종이 100가지를 겹치는 경우 - 최대 100개이기 때문 - , 
//     매번 새로운 색종이를 입력받는 동안, 기존의 색종이들과 위치를 비교해가며 겹치는지, 겹친다면 영역은 얼마인지, 몇 개가 겹치는 지 등을
//     파악해야하기 때문이다.
//     이 쯤 되면 실버 5가 아니다,, 내가 잘못한 거다 라는 생각을 했다.
// 다시 잠시 고민하다가, 다음과 같은 생각을 했다.
// * 넓이는 반드시 가로 * 세로로만 계산할 수 있는가? -> No, 1*1타일의 갯수 합으로도 알 수 있다.
//   만약 겹치는 영역의 넓이와 갯수를 알 필요가 없다면 어떨까? -> 위의 방식대로 한다면 알 필요가 없다.
//   도화지를 boolean[100][100]의 이차원 배열로 표현한다면
//   하나의 색종이가 덮는 도화지의 각 영역을 true로 만들어줄 수 있다. 그렇게 하면 이미 색종이가 덮은 영역을 알 수 있다.
//   이렇게 하면 새로운 색종이가 포개어질때, 기존 색종이가 이미 덮어 true가 된 영역은 빼고 표시해주면된다.
//   그렇게 매 색종이의 좌하단 좌표가 주어질때 - (x,y)는 해당 색종이의 좌하단 모서리 좌표이다 - ,
//   위의 절차들 - 이미 다른 색종이가 덮은 영역이 아니라면 true 표시를 해서 덮어주기 - 을 시행한 후
//   전체 도화지에서 true인 영역의 좌표 갯수만 세어주면 된다!

//   시간 복잡도를 생각해보자.
//   입력 시 좌하단의 좌표로부터 최대 100개의 좌표에 true 표시를 해주어야한다. - 기존에 해당 영역을 덮은 색종이가 없을 시.
//   이때 색종이의 수를 n이라고 한다면 이는 T = 100n 으로 표현해볼 수 있을 것이다.
//   이후 표시가 다 끝난 후 100*100 넓이의 도화지를 1*1 단위로 탐색하며 true인 좌표의 개수를 헤아리는 것은
//   100*100 = 10,000 번의 계산이 필요하다.
//   T = 100n + 10,000이고 이는 시간복잡도로 표현시 O(n)로 표현할 수 있다.
//   빅오 표기법이 아니라, 실제 최악의 상황에 요구되는 연산의 수를 따져보면 - 색종이 갯수가 최대입력인 100인 경우 -
//   T = 100 * 100 + 10,000 = 20,000 번의 탐색 연산이 필요하다.
//   이는 충분히 제한시간인 1초에 완료될 수 있는 연산의 수이므로
//   이와 같은 완전탐색 알고리즘이 유효하며 또 충분히 효율적이다.


public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        boolean[][] background = new boolean[100][100]; // boolean[][] 형태로 표현된 도화지. 아직 백지이므로 전부 false.
        int tileCount = 0;

        for(int i = 0; i < N; i++){
            // 색종이의 좌하단 위치를 입력받으며 해당 색종이의 영역 모두 true 로 바꿔준다. 이미 true 일 경우 다른 색종이의 영역이라는 뜻이므로 무시한다.
            StringTokenizer st = new StringTokenizer(br.readLine());
            int xLeft = Integer.parseInt(st.nextToken());
            int yLow = Integer.parseInt(st.nextToken());

            for(int yInc = 0; yInc < 10; yInc++){
                for(int xInc = 0; xInc < 10; xInc++){
                    // 아직 어느 색종이도 덮지 않은 부분일 경우에만 ture 표시.
                    if(!background[yLow + yInc][xLeft + xInc]){
                        background[yLow + yInc][xLeft + xInc] = true;
                        tileCount++; // 색종이가 덮은 영역의 개수. 각각의 tileCount는 1*1 = 1만큼의 넓이이다.
                    }
                }
            }
        }

        System.out.print(tileCount);

    }
}

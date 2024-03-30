import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int h; // 맵 높이
    static int w; // 맵 너비
    static int[] walls; // 존재하는 벽들의 높이 담은 배열
    static int leftIdx; // 현 구획 왼쪽 기준벽 위치
    static int leftStandard; // 현 구획 왼쪽 기준벽 높이
    static int rightIdx; // 현 구획 오른쪽 기준벽 위치
    static int rightStandard; // 현 구획 오른쪽기준벽 높이
    static int rainAccum; // 맵 전체 저수량
    static boolean flag; // 맵 탐색 필요 유무를 표현하는 boolean 플래그.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 1. 맵 높이, 너비 입력 받기 & 벽 높이 배열 및 변수 초기화.
        h = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        walls = new int[w];
        leftIdx = 0;
        leftStandard = -1;
        rightIdx = -1;
        rightStandard = -1;
        rainAccum = 0;
        flag = true;

        // 2. 벽 입력 받기
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < w; i++){
            walls[i] = Integer.parseInt(st.nextToken());
        }

        // 3. 맵에서 비가 고일 수 있는 가장 첫 번째 구역의 왼쪽 벽 찾기
        //    만약 전체 벽을 탐색했음에도 leftIdx = -1이라는 것은 맵 전체에 벽이 존재하지 않는다는 것이므로
        //    flag = false로 바꿔준다.
        for(int cursor = 0; cursor < w; cursor++){
            if(walls[cursor] > 0){leftIdx = cursor; leftStandard = walls[cursor]; break;}
        }
        if(leftIdx == -1){flag = false;}


        // 4. 맵을 두 개의 포인터로 탐색.
        //    이 부분에서 flag가 false가 되는 경우는 맵에 벽이 존재하지 않는 경우이므로,
        //    while문이 실행된다는 것은 벽이 적어도 하나는 존재한다는 것이고
        //    따라서 leftStandard > 0 && leftIdx != -1 만족함을 의미.
        while(flag){
            // Case A : 왼쪽 벽이 갱신되는 경우는,
            //          구획이 존재하거나 / 오른쪽 벽이 왼쪽 벽 바로 앞이고 높이가 0이 아닌 경우 뿐이다.
            //          어느 쪽이건, 왼쪽 벽의 위치가 맵 오른쪽 끝에 도달했기 때문에 더 이상 탐색이 불가하므로 종료.
            if(leftIdx == w - 1){break;}
            // Case B : 아직 더 탐색해야할 벽이 남은 경우
            else {
                // 맵의 가장 오른쪽부터 왼쪽 벽 바로 다음 위치까지 탐색하며 왼쪽 벽과 짝을 지어 구획을 형성할 오른쪽 벽 찾기.
                for(int rightCursor = w - 1; rightCursor > leftIdx; rightCursor--){
                    // 왼쪽 벽 바로 앞까지 탐색하며 가장 크거나 / 왼쪽 기준벽보다 높거나 같은 벽을 찾는다.
                    if(walls[rightCursor] > rightStandard || walls[rightCursor] >= leftStandard){rightIdx = rightCursor; rightStandard = walls[rightCursor];}
                }

                // Case I : 오른쪽에 벽이 존재하지 않는 경우, 물이 고일 수 없으므로 탐색 종료.
                if(rightStandard == 0){break;}
                // Case II : 왼쪽 기준벽의 오른쪽 벽들 중 가장 큰 것이 왼쪽 벽 바로 다음 위치인 경우.
                //           이때는 rightStandard보다 큰 벽이 오른쪽에 존재하지 않음을 의미하므로,
                //           현재의 leftIdx만큼 물이 고일 수 없다는 것을 의미한다.
                //           따라서 왼쪽 기준벽을 바로 다음인 오른쪽 기준벽으로 갱신해준다.
                else if(rightIdx == leftIdx + 1){
                    leftIdx = rightIdx; leftStandard = rightStandard;
                    rightIdx = -1; rightStandard = -1;
                    continue;
                }
                // Case III : 오른쪽에 벽이 존재하고, 오른쪽에서 가장 큰 벽이 왼쪽 벽과 1 이상의 거리를 두고 있으며,
                //            왼쪽 벽과 오른쪽 벽 사이의 1개 이상의 벽은 왼쪽 벽과 오른쪽 벽 모두보다 작음을 의미.
                //            이때는 물이 고일 수 있는 구획이 존재함을 의미한다. 따라서 저수량을 구해 더해준다.
                else{
                    // 구획에 물이 고이는 높이 기준. 낮은 쪽보다 많이 고일 수 없으므로 낮은 벽을 기준으로 계산한다.
                    int sectorStandard = Math.min(leftStandard, rightStandard);
                    for(int sectorCursor = leftIdx + 1; sectorCursor < rightIdx; sectorCursor++){
                        if(walls[sectorCursor] < sectorStandard){rainAccum += sectorStandard - walls[sectorCursor];}
                    }
                    leftIdx = rightIdx; leftStandard = rightStandard;
                    rightIdx = -1; rightStandard = -1;
                }
            }
        }

        System.out.print(rainAccum);
    }

}
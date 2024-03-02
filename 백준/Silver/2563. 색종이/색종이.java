import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

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

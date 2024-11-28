import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main {
    static int N; // 맵 행/열 길이.
    static int L; // 경사로 길이.
    static int[][] rowCol; // rowCol[i] = i행 경로 전체.
    static int[][] colRow; // colRow[i] = i열 경로 전체.
    static boolean[] slope; // 각 경로 별 경사로 배치 지점을 boolean으로 표현한 것.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        rowCol = new int[N][N];
        colRow = new int[N][N];

        // 0. 입력 받기.
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < N; col++){
                int current = Integer.parseInt(st.nextToken());
                rowCol[row][col] = current;
                colRow[col][row] = current;
            }
        }

        // 1. solution 메서드 호출.
        int numOfPath = findPaths();

        // 2. 출력
        System.out.print(numOfPath);

    }

    /*** 맵 상의 통행 가능한 경로 수 파악 ***/
    private static int findPaths(){
        int numOfPath = 0;

        // 통행 가능한 행 탐색.
        for(int idx = 0; idx < N; idx++){
            if(isPassable(true, idx)){numOfPath++;} // 행 탐색.
            if(isPassable(false, idx)){numOfPath++;} // 열 탐색.
        }

        return numOfPath;
    }

    /*** 주어진 행 또는 열이 통행 가능한지 확인하는 메서드 ***/
    private static boolean isPassable(boolean isRow, int idx){
        int[] currentPath = (isRow) ? (rowCol[idx]) : (colRow[idx]); // 현재 검토 중인 경로.
        boolean isCurrentPathPassable = true;

        slope = new boolean[N]; // 슬로프를 놓은 자리를 표시한 boolean 배열. 각 경로 탐색시 마다 초기화되도록.
        int head = 0;
        int tail = 1;

        while(isCurrentPathPassable && tail < N){
            int headH = currentPath[head];
            int tailH = currentPath[tail];

            // Case A : 연속하는 두 지점의 높이가 같은 경우 -> 계속 탐색.
            if(headH == tailH){
                head = tail;
                tail++;
            }
            // Case B : 후행하는 지점의 높이가 더 높은 경우 -> 오르막 슬로프 놓을 수 있는지 확인해야한다.
            else if(headH < tailH){
                isCurrentPathPassable = isSlopeAvailable(true, currentPath, head);
                // 해당 구간에 슬로프 놓을 수 없는 경우 -> 바로 false 반환.
                if(!isCurrentPathPassable){return isCurrentPathPassable;}
                head = tail;
                tail++;
            }
            else {
                isCurrentPathPassable = isSlopeAvailable(false, currentPath, tail);

                if(!isCurrentPathPassable){return isCurrentPathPassable;}
                head += L;
                tail = head + 1;
            }
        }

        return isCurrentPathPassable;

    }


    /*** oneEndIdx에서 시작하거나 끝나도록 경사로를 놓을 수 있는지 탐색. ***/
    private static boolean isSlopeAvailable(boolean isUpward, int[] path, int oneEndIdx){
        boolean isSlopePlaceable = true;
        int otherEndIdx = oneEndIdx;
        int len = L;

        // Case A : 단차 1 초과인 경우 -> 경사로 놓을 수 없으므로 통행 불가.
        if((isUpward && path[oneEndIdx + 1] - path[oneEndIdx] > 1)
                || (!isUpward && path[oneEndIdx - 1] - path[oneEndIdx] > 1)){return false;}

        // Case B : 단차 1인 경우 -> 경사로 놓을 수 있는지 탐색.
        else {
            // Case B-1 : 경사로 길이가 1인 경우 -> 이미 비교가 끝났고 이 위치에 도달했다면 경사로를 놓을 수 있다.
            if(L == 1 && !slope[oneEndIdx]){slope[oneEndIdx] = true;}
            // Case B-2 : 경사로 길이가 1 초과인 경우 -> idx 포인터를 움직여 탐색해야한다.
            else {
                while(len > 0 && isSlopePlaceable){
                    if(isPossible(otherEndIdx) && path[otherEndIdx] == path[oneEndIdx] && !slope[otherEndIdx]){
                        slope[otherEndIdx] = true;
                        if(isUpward){otherEndIdx--;}
                        else{otherEndIdx++;}
                    }
                    else {
                        isSlopePlaceable = false;
                    }
                    len--;
                }
            }

            return isSlopePlaceable;
        }
    }


    private static boolean isPossible(int idx){
        return idx >= 0 && idx < N;
    }
}
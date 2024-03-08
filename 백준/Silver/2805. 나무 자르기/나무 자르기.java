import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
    static int numOfTree;
    static int[] tree;
    static int target; // 20억 이하이기 때문에 int 사용 가능
    static int longest = 0; // 최대 높이가 10억이기 때문에 int 사용 가능

    static int treeCut(int low, int high, int M){
        int mid = (low + high) / 2; // 가장 긴 막대 기준 현재 찾고자하는 범위의 중앙값
        long midAccum = 0; // mid를 기준으로 모든 나무들을 잘랐을 때 나오는 나무들의 길이 합.
        long lowAccum = 0; // low 기준으로 잘랐을 때 나오는 나무들의 길이 합
        long highAccum = 0; // high 기준으로 잘랐을 때 나오는 나무들의 길이 합

        for(int i = 0; i < numOfTree; i++){
            if(tree[i] > mid){midAccum += tree[i] - mid;}
            if(tree[i] > low){lowAccum += tree[i] - low;}
            if(tree[i] > high){highAccum += tree[i] - high;}
        }


        if(midAccum == M){return mid;} // Base Case I : mid 위치에서 자르는 것이 M을 얻는 가장 높은 H.
        else if(midAccum > M){ // Base Case II :
            if(high - mid >= 2){return treeCut(mid, high, M);} //
            else{
                if(highAccum != M && midAccum != M){return mid;}
                return (highAccum == M) ? high : mid;
            }
        }
        else{
            if(mid - low >= 2){return treeCut(low, mid, M);}
            if(lowAccum != M && midAccum != M){return low;}
            return (lowAccum == M) ? low : mid;
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        numOfTree = Integer.parseInt(st.nextToken());
        target = Integer.parseInt(st.nextToken());
        tree = new int[numOfTree];

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < numOfTree; i++){
            tree[i] = Integer.parseInt(st.nextToken());
            if(tree[i] > longest){longest = tree[i];}
        }

        System.out.print(treeCut(0, longest, target));
    }
}
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.ArrayList;

public class Main{
    static int N;
    static int M;
    static int minDist = Integer.MAX_VALUE;
    static ArrayList<int[]> houses = new ArrayList<>();
    static ArrayList<int[]> chickens = new ArrayList<>();
    static HashMap<int[], Integer> chickenDistPerHouse = new HashMap<>();
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        // 0. 입력 받기
        for(int row = 0; row < N; row++){
            st = new StringTokenizer(br.readLine());
            for(int col = 0; col < N; col++){
                int current = Integer.parseInt(st.nextToken());
                // 집
                if(current == 1){houses.add(new int[]{row, col});}
                // 치킨집
                if(current == 2){chickens.add(new int[]{row, col});}
            }
        }
        
        // 1. M개 선택하는 조합에 대한 완전 탐색 실시
        HashMap<int[], Integer> dist = new HashMap<>();
        findBest(0, dist, 0);
        
        // 2. 정답 출력.
        System.out.print(minDist);
    }
    
    private static void findBest(int possibleStart, HashMap<int[], Integer> dist, int depth){
        // Base Case
        if(depth == M){
            int accumDist = 0;
            for(int[] house : houses){
                accumDist += dist.get(house);
            }
            
            if(accumDist < minDist){minDist = accumDist;}
        }
        
        for(int pick = possibleStart; pick < chickens.size(); pick++){
            int[] currentChicken = chickens.get(pick);
            
            // Deep Copy
            HashMap<int[], Integer> currentSet = new HashMap<>(dist);
            
            // Calculating Distance from current chicken shop to each house.
            for(int[] house : houses){
                int currentDist = calculateDist(currentChicken, house);
                if(currentSet.containsKey(house)){
                    if(currentSet.get(house) > currentDist){currentSet.put(house, currentDist);}
                }
                else {currentSet.put(house, currentDist);}
            }
            findBest(pick + 1, currentSet, depth + 1);
        }
    }
    
    private static int calculateDist(int[] chicken, int[] house){
        return Math.abs(chicken[0] - house[0]) + Math.abs(chicken[1] - house[1]);
    }
    
    
}
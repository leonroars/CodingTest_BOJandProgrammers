import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.BitSet;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer>[] coordinates = (ArrayList<Integer>[]) new ArrayList[200001]; // -100,000 ~ 0 ~ 100,000
        StringBuilder answer = new StringBuilder();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            if(coordinates[x + 100000] == null){
                coordinates[x+100000] = new ArrayList<>();
            }
            coordinates[x+100000].add(y);
        }
        
        for(int j = 0; j < coordinates.length; j++){
            if(coordinates[j] != null){
                ArrayList<Integer> ys = coordinates[j];
                ys.sort(Integer::compareTo); // Sort ys.
                for(int y : ys){
                    answer.append(j - 100000).append(" ").append(y).append("\n");
                }
            }
        }
        
        System.out.println(answer.toString().trim());
    }
}
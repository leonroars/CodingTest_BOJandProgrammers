import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.BitSet;
import java.util.ArrayDeque;

public class Main{
    static int bfs(int N){
        int[] tab = new int[1000001]; // Tabulate the shortest path to each number from previous.
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.addLast(1);
        boolean targetFound = false;
        while(!q.isEmpty()){
            int current = q.removeFirst();
            // Adjacent numbers processing procedure.
            for(int i = 1; i < 4; i++){
                int adjacent = 0;
                if(i == 1){adjacent = current + i;}
                else{adjacent = current * i;}

                if(adjacent <= 1000000){
                    if(tab[adjacent] == 0){
                        tab[adjacent] = tab[current] + 1; // tab[adjacent] != 0이라면 더 빠른 경로가 있다는 것.
                        q.addLast(adjacent);
                    }
                    if(adjacent == N){targetFound = true; break;}
                }
            }
            if(targetFound){break;}

        }
        return tab[N];


    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        System.out.print(bfs(N));
    }
}
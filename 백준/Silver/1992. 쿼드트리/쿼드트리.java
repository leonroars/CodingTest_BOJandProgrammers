import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main{
    static class QuadTree{
        private final int[][] qt;
        private final int N;

        public QuadTree(int givenN){
            qt = new int[givenN][givenN];
            N = givenN;
        }

        public void addRow(String row, int col){
            for(int i = 0; i < row.length(); i++){
                qt[col][i] = Character.getNumericValue(row.charAt(i));
            }
        }

        private String compress(int startY, int startX, int N){
            StringBuilder compressed = new StringBuilder();
            if(N == 1){
                compressed.append(qt[startY][startX]);
                return compressed.toString();
            }
            String first = compress(startY, startX, N/2);
            String second = compress(startY, startX + N/2, N/2);
            String third = compress(startY + N/2, startX, N/2);
            String fourth = compress(startY + N/2, startX + N/2, N/2);

            if(first.equals(second) && second.equals(third) && third.equals(fourth) && (first.equals("0") || first.equals("1"))){compressed.append(first); return compressed.toString();}

            compressed.append("(");
            compressed.append(first);
            compressed.append(second);
            compressed.append(third);
            compressed.append(fourth);
            compressed.append(")");

            return compressed.toString();
        }

        public String toString(){
            return compress(0, 0, N);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        QuadTree qt = new QuadTree(N);
        for(int i = 0; i < N; i++){
            qt.addRow(br.readLine(), i);
        }

        System.out.println(qt);
    }
}
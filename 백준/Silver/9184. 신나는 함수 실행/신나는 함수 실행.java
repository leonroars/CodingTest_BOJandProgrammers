import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        W w = new W();
        

        while (true) {
            String input = br.readLine();
            StringTokenizer st = new StringTokenizer(input);
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if(a == -1 && b == -1 && c == -1){break;}
            sb.append("w");
            sb.append("(");
            sb.append(a);
            sb.append(", ");
            sb.append(b);
            sb.append(", ");
            sb.append(c);
            sb.append(") = ");
            sb.append(w.w(a, b, c));
            sb.append("\n");
        }

        System.out.print(sb.toString().trim());
    }
    
    static class W{
    	private int[][][] cache;
    	private boolean[][][] checked;
    	
    	public W() {
    		cache = new int[21][21][21]; // 0 ~ 20까지.
    		checked = new boolean[21][21][21];
    		cache[0][0][0] = 1; // if(a <= 0 || b <= 0 || c <= 0)
    		for(int i = 0; i < 21; i++) {
    			for(int j = 0; j < 21; j++) {
    				for(int k = 0; k < 21; k++) {
    					cache[i][j][k] = w(i, j, k);
    					checked[i][j][k] = true;
    				}
    			}
    		}
    	}
    	
    	public int w(int a, int b, int c) {
    		if(a <= 0 || b <= 0 || c <= 0) {checked[0][0][0] = true; return cache[0][0][0];}
    		else if(a > 20 || b > 20 || c > 20) {
    			if(!checked[20][20][20]) {w(20, 20, 20);}
    			return cache[20][20][20];
    		}
    		else if(a < b && b < c) {
    			if(checked[a][b][c]) {return cache[a][b][c];}
    			else {return  w(a, b, c-1) + w(a, b-1, c-1) - w(a, b-1, c);}
    		}
    		else if(checked[a][b][c]) {return cache[a][b][c];}
    		return  w(a-1, b, c) + w(a-1, b-1, c) + w(a-1, b, c-1) - w(a-1, b-1, c-1); 
    	}
    }
}
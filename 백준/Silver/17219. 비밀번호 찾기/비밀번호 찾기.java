import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.HashMap;

// 풀이 및 접근
// 해시맵을 사용하면 간단하게 풀 수 있는 문제이다.
// 해시맵을 구현하라는 문제가 아니라 사용해도 무방할테지만,,, 괜히 양심이 아파온다!

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int numOfPass = Integer.parseInt(st.nextToken());
        int wantToKnow = Integer.parseInt(st.nextToken());

        HashMap<String, String> hm = new HashMap<>();

        for(int i = 0; i < numOfPass; i++){
            st = new StringTokenizer(br.readLine());
            String site = st.nextToken();
            String pass = st.nextToken();
            hm.put(site, pass);
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        for(int j = 0; j < wantToKnow; j++){
            String query = br.readLine();
            bw.write(hm.get(query));
            bw.write("\n");
        }
        br.close();
        bw.flush();
        bw.close();
    }
}
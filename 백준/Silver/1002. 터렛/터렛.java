import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int TC = Integer.parseInt(br.readLine());
        StringBuilder answer = new StringBuilder();
        
        for(int tc = 0; tc < TC; tc++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int r1 = Integer.parseInt(st.nextToken());
            
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            int r2 = Integer.parseInt(st.nextToken());
            
            String result = numberOfPossibleLocationOfRyu(new int[]{x1, y1}, new int[]{x2, y2}, r1, r2);
            answer.append(result);
            if(tc < TC - 1){answer.append("\n");}
        }
        
        System.out.print(answer.toString());
    }
    
    private static String numberOfPossibleLocationOfRyu(int[] jo, int[] baek, int r1, int r2){
        // Edge : 조와 백의 위치가 동일한 경우
        if(jo[0] == baek[0] && jo[1] == baek[1]){
            // Edge #1 : 지름까지 같은 동심원 -> 무한
            if(r1 == r2){return "-1";}
            // Edge #2 : 지름은 다른 동심원 -> 0
            else {return "0";}
        }
        
        // Case : 내접 혹은 외접
        double distBetweenJoAndBaek = dist(jo, baek);

        if(distBetweenJoAndBaek > (double)(r1 + r2)){return "0";}
        else if(distBetweenJoAndBaek == (double)(r1 + r2)){return "1";}
        else {
            // 두 녀석 간 거리가 지름의 합보다 작은 경우 크게 세 가지 가능성이 있다.
            int smallerR = (r1 > r2) ? r2 : r1;
            int biggerR = (smallerR == r1) ? r2 : r1;
            
            // 1) 한 점에서 접함(내접)
            if(distBetweenJoAndBaek + smallerR == biggerR){return "1";}
            
            // 2) 만나지 않음(작은 원이 큰 원 내부에 존재)
            else if(distBetweenJoAndBaek + smallerR < biggerR){return "0";}
            // 3) 두 점에서 접함.
            else {return "2";}
        }
        
    }
    
    private static double dist(int[] center1, int[] center2){
        return Math.sqrt(
            Math.pow(center1[0] - center2[0], 2) + Math.pow(center1[1] - center2[1], 2)
        );
    }
}
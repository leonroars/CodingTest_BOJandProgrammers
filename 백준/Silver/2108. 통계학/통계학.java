import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    // 산술평균, 중앙값, 최빈값, 범위
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[] arr = new int[N];
        int sum = 0;
        int range;
        int arthMean;
        int median;
        int mostFrequentNumber;
        int min = Integer.MAX_VALUE;
        int max = -Integer.MAX_VALUE;
        HashMap<Integer, Integer> frequency = new HashMap<>(); // <num, frequency>
        
        // 0. 입력 받으며 수열합, 최소, 최대, 출현 빈도 파악하기.
        for(int i = 0; i < N; i++){
            int current = Integer.parseInt(br.readLine());
            arr[i] = current;
            sum += current;
            min = Math.min(min, current);
            max = Math.max(max, current);
            
            if(frequency.containsKey(current)){
                int currentFreq = frequency.get(current);
                frequency.put(current, currentFreq + 1);
            }
            else {
                frequency.put(current, 1);
            }
        }
        
        // 1. 산술 평균 계산
        arthMean = (int)Math.round((double)sum / N);
        
        // 2. 범위 계산
        range = Math.abs(max - min);
        
        // 3. 최빈값 도출
        ArrayList<Integer> mostFrequentNumbers = new ArrayList<>();
        int topFrequencyByFar = -Integer.MAX_VALUE;
        
        for(Map.Entry<Integer, Integer> entry : frequency.entrySet()){
            int currentNum = entry.getKey();
            int currentFreq = entry.getValue();
            
            if(currentFreq > topFrequencyByFar){
                topFrequencyByFar = currentFreq;
                mostFrequentNumbers = new ArrayList<Integer>();
                mostFrequentNumbers.add(currentNum);
            }
            else if(currentFreq == topFrequencyByFar){
                mostFrequentNumbers.add(currentNum);
            }
            else {
                continue;
            }
        }
        

        if(mostFrequentNumbers.size() == 1){mostFrequentNumber = mostFrequentNumbers.get(0);}
        else {
            Collections.sort(mostFrequentNumbers);
            mostFrequentNumber = mostFrequentNumbers.get(1);
        }
        
        // 4. 중앙값.
        Arrays.sort(arr);
        median = arr[(N - 1) / 2];
        
        // 5. 출력.
        StringBuilder answer = new StringBuilder();
        answer.append(arthMean);
        answer.append("\n");
        
        answer.append(median);
        answer.append("\n");
        
        answer.append(mostFrequentNumber);
        answer.append("\n");
        
        answer.append(range);
        
        
        System.out.print(answer.toString());
    }
}
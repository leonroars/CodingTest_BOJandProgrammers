import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        ArrayList<String> cars = new ArrayList<>(); // 전체 차량 목록.
        
        // 차량 번호 - 입차 시간
        HashMap<String, String> carAndTime = new HashMap<>();
        // 차량 번호 - 시간 누적
        HashMap<String, Integer> carAndAccum = new HashMap<>();

        // 1. O(n) - 주어진 입력을 Parse 하여 carAndTime 기록 및 누적 요금 계산
        for(int i = 0; i < records.length; i++){
            String current = records[i];
            String carNum = current.substring(6, 10);
            String time = current.substring(0, 5);
            String inOrOut = current.substring(11);
            
            // 입출차기록에 존재하지 않는 경우(새로운 차) => 차 목록에 저장.
            if(!carAndTime.containsKey(carNum) && !carAndAccum.containsKey(carNum)){
                cars.add(carNum);
            }
            // Case I : 입차
            if(inOrOut.equals("IN")){
                carAndTime.put(carNum, time);
            }
            // Case II : 출차
            else {
                String inTime = carAndTime.get(carNum);
                int duration = timeCalculator(inTime, time);
                // Case II-1 : 기존 입-출차 기록이 존재하는 경우 : 가산
                if(carAndAccum.containsKey(carNum)){
                    carAndAccum.put(carNum, carAndAccum.get(carNum) + duration);
                }
                else {
                    carAndAccum.put(carNum, duration);
                }
                carAndTime.remove(carNum);
            }
        }
        
        // 2. 아직 출차하지 않은 차량에 대한 요금 계산
        for(Entry<String, String> entry : carAndTime.entrySet()){
            String carNum = entry.getKey();
            String inTime = entry.getValue();
            int duration = timeCalculator(inTime, "23:59");
            
            // Case I : 기존 입-출차 기록 있는 경우 - 합산
            if(carAndAccum.containsKey(carNum)){
                carAndAccum.put(carNum, carAndAccum.get(carNum) + duration);
            }
            else {
                carAndAccum.put(carNum, duration);
            }
        }
        
        
        // 3. 정렬.
        cars.sort((o1, o2) -> Integer.parseInt(o1) - Integer.parseInt(o2));
        
        // 4. 정답 배열 완성.
        int[] answer = new int[cars.size()];
        for(int j = 0; j < cars.size(); j++){
            String currentCar = cars.get(j);
            answer[j] = feeCalculator(fees, carAndAccum.get(currentCar));
        }
        
        return answer;
    }

    // 시작시각~ 종료시각 까지 분 단위 계산 결과 반환하는 메서드.
    public int timeCalculator(String start, String end){
        // 시작 시각 & 종료 시각 시, 분 분해.
        int startMin = Integer.parseInt(start.substring(3));
        int endMin = Integer.parseInt(end.substring(3));
        int startHour = Integer.parseInt(start.substring(0, 2));
        int endHour = Integer.parseInt(end.substring(0, 2));

        int forHour = 0;
        int forMin = 0;

        if(endMin < startMin){
            forMin = (60 - startMin) + endMin;
            forHour = (endHour - startHour - 1);
        }
        else {
            forMin = endMin - startMin;
            forHour = endHour - startHour;
        }

        return (60 * forHour) + (forMin);
    }

    public int feeCalculator(int[] fees, int duration){
        if(duration < fees[0]){
            return fees[1];
        }
        else{
            return fees[1] + ((int)(Math.ceil((duration - fees[0]) / (double)(fees[2]))) * fees[3]);
        }
    }
}
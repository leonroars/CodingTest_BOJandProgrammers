import java.util.TreeSet;
import java.util.Iterator;

/**

<전체 풀이 아이디어> : Parametric Search 를 활용한 최적화문제->결정 문제.

1. 콘의 대기열 합류 시각 결정 : O(log K)
 - 00:00~23:59 시간을 '00:00 기준 경과 시간'을 기준으로 연속하는 수의 범위로 표현하여 나누면 [0, 1439] 내에 콘의 대기열 합류 시각이 존재.
 - 이 범위값은 '출근 가능한가?' 라는 참/거짓 질의에 TTTT...TFFF..FFF 와 같은 응답 분포에 대응한다.
 - 우리는 가장 늦는 T를 찾는다.
 - 수를 고르면 이를 00:00 시 기준 절대 시각으로 변환해 반환하는 메서드가 있으면 좋다.

2. 콘을 대기열에 합류 시킨다.
 - 이 지점에서 효율적 자료구조가 필요.
 - 다음과 같은 요구사항을 효율적으로 처리해야함.
  1) 사람들의 도착 시각 기준으로 형성된 대기열에서 콘의 대기열 합류 시각 기준 위치를 찾는다.
  2) 거기에 콘을 세운다.
  
  TreeSet 은 두 가지 기능을 모두 효율적으로 제공한다. TreeSet 을 활용하도록 한다.
  '콘과 같은 시각에 서있는 사람들이 있으면 콘은 그 사람들 뒤에 선다.' 라는 규칙때문에 PQ 를 쓸 수 없다.

3. 콘이 버스에 탈 수 있는지 아닌지 실제로 탑승을 Simulate 해보며 체크한다.
 - 버스 출발이 09:00 이므로 이 시간부터 배차 간격, 횟수에 맞게 버스를 세운다.
 - 이 버스에 (현 시각 >= 대기열 합류시각 && 현재 탑승 인원 < 정원)일 때만 TreeSet 에서 제거 or skip.
 - 이때 TreeSet 을 구성하는 객체 모델링 시 필드에 isKorn 과 같은 플래그를 두면 check-fast 하게 설계 가능.
    - skip 한다면, 막차에 사람 태울 때까지 korn 못 태우면 게임 End. => return false; or return true;
    - TreeSet 에 저장될 객체 설계 시 korn 을 특정할 수 있도로 설계하면 효율적으로 제거 가능하므로,
      매번 새로 TreeSet 형성할 필요가 없어 시간 복잡도 측면에서 유리.
 

**/

class Solution {
    public String solution(int n, int t, int m, String[] timetable) {
        
        return findLatestArrivalTimeOfKorn(n, t, m, timetable);
    }
    
    public String findLatestArrivalTimeOfKorn(int n, int t, int m, String[] timetable){
        // 0. TreeSet 만들기.
        TreeSet<Crew> crews = new TreeSet<>();
        for(String crewArrivalTime : timetable){crews.add(new Crew(crewArrivalTime));}
        
        // 1. Parametric Search 로 상한 시간 찾기 && 반환.
        int lo = 0; // 00:00
        int hi = 1439; // 23:59
        
        // 끝나고 lo 반환하면 된다.
        while(lo < hi - 1) {
            int mid = (lo + hi) / 2;
            String kornTime = calculateTimeFromOffset(mid);
            boolean isPossible = simulateBusRide(crews, kornTime, n, t, m);
            
            if(isPossible){lo = mid;}
            else {hi = mid;}
        }
        
        String hiTime = calculateTimeFromOffset(hi);
        
        return (simulateBusRide(crews, hiTime, n, t, m)) ? hiTime : calculateTimeFromOffset(lo);
    }
    
    private boolean simulateBusRide(TreeSet<Crew> crews, String kornTime, int n, int t, int m){
        // 0. 콘의 도착 시각, id 로 Korn 인스턴스 만들기.
        Crew korn = new Crew(Integer.MAX_VALUE, kornTime);
        
        // 1. 대기열에 korn 세우기. - 이 메서드 종료 전에 삭제하는 거 잊지 않기.
        crews.add(korn);
        
        // 2. 버스에 태워보기.
        int currentBusOffset = 540; // 09:00 의 offset
        String currentBusTime = "09:00";
        int busTurnCnt = 0;
        
        Iterator<Crew> crewIterator = crews.iterator();
        Crew firstCrew = crewIterator.next();
        boolean isKornOnboard = false;
        
        // 아직 사람이 남아있고 && 운행 횟수 남았고 && Korn 안 탄 동안,
        while(crewIterator.hasNext() && busTurnCnt < n && !isKornOnboard){
            
            int onboardCnt = 0;
            
            // 이번 버스 사람 태우기 (가능할 경우, 가능한 정원 내에서만.)
            while(onboardCnt < m && !isKornOnboard){
                
                String earliestArrivalTime = firstCrew.getArrivalTime();
                boolean isOnTime = (earliestArrivalTime.compareTo(currentBusTime)) <= 0;
                
                // 맨 앞 사람 탑승 가능 -> 태우기.
                if(isOnTime){
                    onboardCnt++;
                    if(firstCrew.getId() == Integer.MAX_VALUE){isKornOnboard = true;}
                    firstCrew = (crewIterator.hasNext()) ? crewIterator.next() : null; // Cusor 이동.
                }
                // 탑승 불가 -> 이번 버스엔 더 이상 못 탐.
                else {
                    break;
                }
            }
            
            busTurnCnt++;
            currentBusOffset += t;
            currentBusTime = calculateTimeFromOffset(currentBusOffset);
        }
        
        // Korn 삭제.
        crews.remove(korn);
        
        return isKornOnboard;
    }
    
    // offset -> time : 이런 거 좀 깔끔하고 효율적으로 하는 법 없을까?
    private String calculateTimeFromOffset(int offset){
        int hourDiff = offset / 60;
        int minDiff = (offset - (hourDiff * 60));
        
        StringBuilder time = new StringBuilder();
        
        if(hourDiff < 10){time.append(0).append(hourDiff);}
        else{time.append(hourDiff);}
        
        time.append(":");
        
        if(minDiff < 10){time.append(0).append(minDiff);}
        else{time.append(minDiff);}
        
        return time.toString();
    }
    
    // time -> offset
    private int calculateOffsetFromTime(String time) {
        String[] timeParsed = time.split(":");
        String absHour = timeParsed[0];
        String absMin = timeParsed[1];
        
        return (60 * Integer.parseInt(absHour)) + Integer.parseInt(absMin);
    }
    
    
    static class Crew implements Comparable<Crew> {
        private static int idCounter = 1; 
        private int id;
        private String arrivalTime;
        
        // 콘을 표현하는 객체 인스턴스 생성 시엔 id 직접 부여 필요.
        // 콘의 id 는 'Integer.MAX_VALUE' 로 고정. -> 그래야 어디에 넣건 같은 시각이면 korn이 뒤일테니까.
        public Crew (int id, String arrivalTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
        }
        
        // 다른 사람들 : static 필드의 idCounter 로부터 id 부여
        public Crew (String arrivalTime) {
            this.id = idCounter++;
            this.arrivalTime = arrivalTime;
        }
        
        // Getter
        public int getId(){return this.id;}
        public String getArrivalTime(){return this.arrivalTime;}
        
        @Override
        public int compareTo(Crew thatCrew) {
            if(this.id == thatCrew.getId() 
               && this.arrivalTime.equals(thatCrew.getArrivalTime())){
                return 0;
            }
            
            if(this.arrivalTime.equals(thatCrew.getArrivalTime())){return Integer.compare(this.id, thatCrew.getId());}
            return this.arrivalTime.compareTo(thatCrew.getArrivalTime());
        }
    }
}
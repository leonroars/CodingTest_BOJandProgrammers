import java.util.Arrays;

class Solution {
    public int solution(int[] people, int limit) {
        int head = 0;
        int tail = people.length - 1;
        int numberOfFilledBoat = 0;
        int rescued = 0;
        
        Arrays.sort(people);
        
        while(head < tail){
            if(people[head] + people[tail] <= limit){
                numberOfFilledBoat++;
                head++;
                tail--;
                rescued += 2;
            }
            else {
                tail--;
            }
        }
        
        return numberOfFilledBoat + (people.length - rescued);
        
    }
}
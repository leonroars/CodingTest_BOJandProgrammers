import java.util.HashSet;
import java.util.ArrayDeque;

class Solution {
    public int solution(int cacheSize, String[] cities) {
        int runtime = 0;
        
        // Cache Queue
        ArrayDeque<String> dq = new ArrayDeque<>();
        HashSet<String> cache = new HashSet<>();
        
        for(int i = 0; i < cities.length; i++){
            String currentCity = cities[i].toLowerCase();
            
            // Case A : currentCity IS IN the CACHE
            if(cache.contains(currentCity)){
                // Update its priority in cache.
                dq.remove(currentCity); 
                dq.addLast(currentCity);
                runtime += 1;
            }
            else {
                if(cacheSize > 0){
                    if(cache.size() == cacheSize){
                    String removedCity = dq.removeFirst();
                    cache.remove(removedCity);
                }
                cache.add(currentCity); // Add city into cache.
                dq.addLast(currentCity); // Add city into dq.
                }
                runtime += 5;
            }
        }
        
        return runtime;
    }
}
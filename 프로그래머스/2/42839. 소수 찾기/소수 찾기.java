    
    /*
     1. 직관적 접근
       1) 종이 조각으로 만들 수 있는 가장 큰 수 n 보다 작은 소수를 O(1) 에 판별할 수 있도록 에라토스테네스의 체 준비
       2) 완전탐색을 활용한 조합을 활용해 가능한 모든 수 조합에 대해 소수인지 판별 후 갯수 카운트.
         - 문자열 만들기 : Backtracking (Prunning 을 포함해야함.)
         - 하나의 숫자가 여러 개 있을 수 있기 때문에 비트마스킹은 적절하지 않다.
         - 길이가 k 인 숫자를 만들기 (1<=k<=numbers.length()) => 이 k 가 곧 recursion depth
         - 어떤 자료구조가 적절할까? -> 길이 10인 int[] 배열을 들고다니며 복사? -> 복사!
       
     2. 입력 및 문제 공간 크기 추정
       - 주어진 입력으로 만들 수 있는 가장 큰 수 : 9,999,999 (약 천만)
       - 천만 미만의 모든 소수 찾기 : O(N log log N) -> 거의 O(N) 에 근사.
     
     3. 소수 판별: 에라토스테네스의 체 v.s sqrt(N) 까지의 모든 수로 나눠보기
      - 처음엔 에라토스테네스의 체 사용하려 했으나, 도무지 그 효율이 필요한 까닭을 알기 어려웠고 구현이 쓸데 없이 복잡해짐. 심지어 공간복잡도 또한 O(N) 을 차지함.
      - 그래서 그냥 sqrt(N) 으로 나눠버리면 공간복잡도는 없는 수준이고 시간복잡도도 O(sqrt N) 으로 더 효율적.
    */
class Solution {
    
    boolean[] generatedNums; // For prunning
    int accumCnt = 0;
    
    public int solution(String numbers) {
        
        generatedNums = new boolean[(int)Math.pow(10, numbers.length())];
        
        // 0) 종이 조각에 적힌 숫자 현황 파악하기
        int[] nums = parseNum(numbers);
        
        // 1) 종이 조각을 활용해 길이가 len 인 수를 모두 만들고 소수 판별 후 갯수 세기
        for(int len = 1; len <= numbers.length(); len++){
            generateNumberAndCountPrimeNumber(nums, len);
        }
        
        return accumCnt; // mock
    }
    
    
    private void generateNumberAndCountPrimeNumber(int[] nums, int len){
        generateNumberAndCountPrimeNumber(nums, len, 0, new StringBuilder());
    }
    
    private void generateNumberAndCountPrimeNumber(int[] nums, int len, int currentDepth, StringBuilder generated){
        // Base Case : len 도달 -> 숫자로 변환 후 소수 판별
        if(currentDepth == len){
            int currentNum = Integer.parseInt(generated.toString());
            if(!generatedNums[currentNum] && isPrime(currentNum)){
                accumCnt++;
                generatedNums[currentNum] = true; // Pruning 을 위한 방문 처리.
                System.out.println(currentNum);
            }
            return;
        }
        
        // General Case : 선택 -> 복사 -> 원복
        for(int i = 0; i < 10; i++){
            // 선택가능한 수 발견 -> 선택
            if(nums[i] != 0){
                StringBuilder currentlyByFar = new StringBuilder(generated.toString());
                currentlyByFar.append(i);
                nums[i]--; // 카운트 차감
                generateNumberAndCountPrimeNumber(nums.clone(), len, currentDepth + 1, currentlyByFar);
                nums[i]++; // 원상 복귀
            }
        }
    }
    
    private boolean isPrime(int N){
        if(N == 1 || N == 0){return false;}
        for(int i = 2; i * i <= N; i++){
            if(N % i == 0){return false;}
        }
        
        return true;
    }
    
    
    private int[] parseNum(String numbers){
        int[] nums = new int[10];
        for(int i = 0; i < numbers.length(); i++){
            int a = Character.getNumericValue(numbers.charAt(i));
            nums[a]++;
        }
        
        return nums;
    }
}
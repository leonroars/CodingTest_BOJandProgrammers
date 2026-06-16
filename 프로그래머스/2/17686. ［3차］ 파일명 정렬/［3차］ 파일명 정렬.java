import java.util.Collections;
import java.util.ArrayList;

class Solution {
    public String[] solution(String[] files) {
        ArrayList<FileName> fileNames = new ArrayList<>();
        
        for(int i = 0; i < files.length; i++){
            fileNames.add(new FileName(files[i]));
        }
        
        Collections.sort(fileNames);
        
        String[] sortedFileNames = new String[fileNames.size()];
        
        for(int j = 0; j < sortedFileNames.length; j++){
            sortedFileNames[j] = fileNames.get(j).getOriginalFileName();
        }
        
        return sortedFileNames; // Mock
    }
    
    // 정석적으로, 깔끔하게 푼다면 다음과 같은 과정일 것.
    // 1) 입력으로 들어오는 files 에서 파일 이름을 하나씩 읽어온 후, Comparable 인터페이스를 구현한 객체로 변환.
    // 2) 이를 PQ 에 삽입.
    // 3) PQ 에서 하나씩 뽑으며 String[] 배열에 삽입.
    // : 공간 복잡도 O(N) - 하지만 객체 크기 오버헤드로 인해 약간의 비효율 존재 -, 시간복잡도 O(N log N)
    
    // 이를 만약 시간이 모자란 코테 환경에서 한다면?
    // 바로 Collections.sort() 에 Lambda API 활용해 정렬 규칙 정의.
    // : 시간/공간 복잡도 위와 동일.
    // 다만 빠르게 코드를 작성할 수 있음.
    // But, 문제 생기면 Lambda 쪽 코드에서 난잡하게 정의된 규칙을 수정해가며 고쳐야함.
    
    // 이번엔 들어온 문자열을 다시 조건에 맞게 대소문자 변환, 문자 조건에 따른 구간 분할, 구간별 조건에 따른 정렬 필요.
    // 따라서 String fileName -> FileName 오브젝트로 변환하고, FileName 생성자에 조건에 맞게 fileName 을 Parsing 하고 필드에 저장하는 형태로 정의하는 것이 빠를 것으로 예상.
    // 그럼 복합 조건을 실제로 명시하는 compareTo 쪽이 아주 수월할 것으로 예상.
    // 심지어 음... Stable Sorting 이 필요함. 정렬 조건 판별 상 동등한 경우, 기존 입력 순서가 지켜져야함.
    // 따라서 Collections.sort() 를 쓰는 것이 타당함. (Timsort, Stable Sorting.)
    
    class FileName implements Comparable<FileName>{
        private String originalFileName;
        private String head;
        private String number;
        private String tail; // Can be empty
        
        public FileName(String filename){
            this.originalFileName = filename;
            
            // 구간 Parsing. 
            // 처음엔 전체 순회를 돌려고 했음. 하지만 포인터 조작이 좀 까다로웠고, 언제 while 문 종료할지 판단하기 어려웠음.
            // 의외로 오랜만에 하니 이런 부분이 더 까다로웠다.
            // 그래서 꼼수일지 모르겠지만, 어차피 숫자가 반드시 하나는 나온댔고, 하나 나왔다면 그건 TAIL 이 아니라 NUMBER.
            // 그걸 활용해서, 첫 숫자 등장과 연속 구간 종료 지점이 바로 NUMBER 구간인거고, NUMBER 구간 시작/종료 알면 HEAD/TAIL 바로 나옴.
            int numberStartIdx = -1;
            for(int i = 0; i < filename.length(); i++){
                if(Character.isDigit(filename.charAt(i))){numberStartIdx = i; break;}
            }
            
            int numberEndIdx = -1;
            for(int j = numberStartIdx; j < filename.length(); j++){
                if(!Character.isDigit(filename.charAt(j))){numberEndIdx = j - 1; break;}
                // 숫자인 상태로 문자열 끝 도달한 케이스
                if(j == filename.length() - 1){numberEndIdx = j;}
            }
            
            this.head = filename.substring(0, numberStartIdx);
            this.number = filename.substring(numberStartIdx, numberEndIdx + 1); // 중요!! : numberEndIdx == 문자열 끝이어도 이 부분 컴파일 됌.
            this.tail = (numberEndIdx == filename.length() - 1) ? null : filename.substring(numberEndIdx + 1);
            
        }
        
        public String getOriginalFileName(){return this.originalFileName;}
        
        
        @Override
        public int compareTo(FileName thatFileName){
            // HEAD 동일할 경우 Number 비교 결과 반환.
            if(this.head.equalsIgnoreCase(thatFileName.head)){
                // Integer.compare(a, b) / Integer.compareTo(b); 주의할 것!
                return Integer.compare(Integer.parseInt(this.number), Integer.parseInt(thatFileName.number));
            }
            else {return this.head.compareToIgnoreCase(thatFileName.head);}
        }
    }
    
}
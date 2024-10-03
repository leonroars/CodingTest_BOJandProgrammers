class Solution {
    public String[] solution(int n, int[] arr1, int[] arr2) {
        
        String[] answer = new String[n];
        
        for(int i = 0; i < n; i++){
            StringBuilder unionResult = new StringBuilder();
            
            String binaryArr1 = Integer.toBinaryString(arr1[i]);
            String binaryArr2 = Integer.toBinaryString(arr2[i]);
            
            int biggerLength = Math.max(binaryArr1.length(), binaryArr2.length());
            
            String totalPadding = "";
            
            // Case I : 전체 변의 길이 n 보다 양 지도 각 행의 이진수 길이가 짧은 경우 -> 모자란 만큼 padding 처리
            if(biggerLength < n){
                for(int p = 0; p < n - biggerLength; p++){totalPadding += "0";}
            }
            
            // Case A : 해당 수 이진수로 바꿨을 때 자릿수가 다른 경우.
            if(binaryArr1.length() != binaryArr2.length()){
                String padding = "";
                // Case A-1 : 첫 번째가 더 긴 경우.
                if(biggerLength == binaryArr1.length()){
                    for(int j = 0; j < biggerLength - binaryArr2.length(); j++){
                        padding += "0";
                    }
                    binaryArr2 = padding + binaryArr2;
                }
                // Case A-2 : 두 번째가 더 긴 경우.
                else {
                    for(int k = 0; k < biggerLength - binaryArr1.length(); k++){
                        padding += "0";
                    }
                    binaryArr1 = padding + binaryArr1;
                }
            }
            
            binaryArr1 = totalPadding + binaryArr1;
            binaryArr2 = totalPadding + binaryArr2;
            
            for(int loc = 0; loc < n; loc++){
                if(binaryArr1.charAt(loc) == '1' || binaryArr2.charAt(loc) == '1'){unionResult.append("#");}
                else{unionResult.append(" ");}
            }
            
            answer[i] = unionResult.toString();
        }
        
        return answer;
    }
}
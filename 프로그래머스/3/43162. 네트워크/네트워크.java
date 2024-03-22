// Connected Component의 갯수를 반환
// 미방문 정점(컴퓨터)에 대해 순차적으로 DFS를 시행한다.

class Solution {
    
    boolean[] visited;
    int count = 0;
    
    public int solution(int n, int[][] computers) {
        // computers 자체가 Graph 인접리스트 구조.
        // 따라서 별도의 작업 없이 바로 해당 이차원 배열에 접근하여 문제를 풀도록 한다.
        
        visited = new boolean[n]; // 방문 정점 표기 위한 boolean[].
        for(int i = 0; i < n; i++){
            if(!visited[i]){
                DFS(i, computers);
                count++;
            }
        }
        
        return count;
    }
    
    // for문을 이용해 정점을 돌며 solution 함수에서 자체적으로 미방문 정점에 대한 DFS를 하지 않도록 할 것이므로
    // DFS 호출 인자로 넘겨진 startVertex = 미방문 상태 이다.
    private void DFS(int startVertex, int[][] computerList){
        visited[startVertex] = true;
        for(int i = 0; i < computerList[startVertex].length; i++){
            if(computerList[startVertex][i] == 1 && !visited[i]){DFS(i, computerList);}
        }
    }
}
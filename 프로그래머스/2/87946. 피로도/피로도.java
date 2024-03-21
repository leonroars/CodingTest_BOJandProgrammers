class Solution {
    int max = 0; // 최대 던전 탐험 수
    boolean[] visited;
    int[][] dungeonList;


    public int solution(int k, int[][] dungeons) {
        visited = new boolean[dungeons.length]; // 방문한 던전 표시 위한 boolean[].
        dungeonList = dungeons;
        dfs(k, 0, 1);
        return max;
    }

    private void dfs(int rest, int prev, int cnt){
        // Base-Case : 탐험한 던전 수가 던전 수에 도달했거나, 잔여 피로도 0인 경우 return.
        if(cnt > dungeonList.length || rest == 0){return;}
        for(int i = 0; i < dungeonList.length; i++){
            // 던전을 방문하는 경우 => 방문할 수 있고, 아직 방문하지 않은 던전만!
            if(dungeonList[i][0] <= rest && !visited[i]){
                visited[i] = true;
                // 방문 최대 갱신
                if(cnt > max){max = cnt;}
                dfs(rest - dungeonList[i][1], i, cnt + 1);
                visited[i] = false; // 다른 경로로부터 방문 가능하므로 방문 표시 해제.
            }
        }
    }
}
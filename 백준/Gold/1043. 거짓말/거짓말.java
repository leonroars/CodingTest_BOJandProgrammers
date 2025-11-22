import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()); // 사람 수
        int M = Integer.parseInt(st.nextToken()); // 파티 수
        UnionFind uf = new UnionFind(N);
        
        ArrayList<Integer>[] partiesAndParticipants = new ArrayList[M];
        for(int k = 0; k < M; k++){
            partiesAndParticipants[k] = new ArrayList<Integer>();
        }
            
        st = new StringTokenizer(br.readLine());
        // 1. 진실을 아는 치사한 녀석들 입력 받고 서로 연결시켜주기.
        int numberOfWhistleBlowers = Integer.parseInt(st.nextToken());
        int prevWhistleBlower = -1; // 진실을 아는 사람이 아무도 없다면 여기가 -1로 남기 때문에 별도 처리 필요.
        
        for(int i = 0; i < numberOfWhistleBlowers; i++){
            int currentWhistleBlower = Integer.parseInt(st.nextToken());
            
            if(prevWhistleBlower == -1){prevWhistleBlower = currentWhistleBlower;}
            else {
                uf.union(prevWhistleBlower, currentWhistleBlower);
                prevWhistleBlower = currentWhistleBlower;
            }
        }
        
        // 2. 파티에 참가하는 사람들 입력 받으며 서로 연결 시켜주기. (이 연결은 '같은 내용을 아는 사람들의 집합'으로 생각하면 좋다.)
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            int numberOfParticipants = Integer.parseInt(st.nextToken());
            int prev = -1;
            
            for(int p = 0; p < numberOfParticipants; p++){
                int current = Integer.parseInt(st.nextToken());
                // 최초 참가자라면 -> prev에 참조 할당.
                if(prev == -1){
                    prev = current;
                }
                // 최초 참가자가 아니라면 -> 이전 참가자와 같은 union 으로 묶어주기.
                else {
                    uf.union(prev, current);
                }
                
                // 공통 : 파티와 참여 멤버 정보 기록
                partiesAndParticipants[m].add(current);
            }
        }
        
        // 3. 이제 진실을 아는 사람과 '같은 내용을 아는 사람들의 집합'에 속하지 않는 사람을 골라내기.
        int feasiblePartyCnt = (prevWhistleBlower == -1) ? M : 0;
        
        if(feasiblePartyCnt == 0){
            for(int party = 0; party < M; party++){
                int sizeOfParty = partiesAndParticipants[party].size();
                int naiveCnt = 0;
                for(Integer participant : partiesAndParticipants[party]){
                    if(!uf.isConnected(participant, prevWhistleBlower)){naiveCnt++;}
                }
                if(naiveCnt == sizeOfParty){feasiblePartyCnt++;}
            }
        }
        
        // 4. 정답 출력
        System.out.print(feasiblePartyCnt);
    }
    
    static class UnionFind {
        int N;
        int[] parent;
        int[] size;
        
        public UnionFind(int N){
            this.N = (N == 0) ? 50 : N;
            parent = new int[N + 1]; // 사람 번호가 1부터 N
            size = new int[N + 1];
            
            for(int i = 1; i < N + 1; i++){
                parent[i] = i;
                size[i] = 1;
            }
        }
        
        public int root(int p){
            while(p != parent[p]){
                parent[p] = parent[parent[p]]; // Path compression by halving.
                p = parent[p];
            }
            
            return p;
        }
        
        public void union(int p, int q){
            int i = root(p);
            int j = root(q);
            
            if(i == j){return;}
            
            if(size[i] > size[j]){
                parent[j] = i;
                size[i] += size[j];
            }
            else{
                parent[i] = j;
                size[j] += size[i];
            }
        }
        
        public boolean isConnected(int p, int q){
            return root(p) == root(q);
        }
    }
}
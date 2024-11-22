import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    /******* Static Variables *********/
    static int N;
    static int M;
    static int K;
    static ArrayList<Fireball>[][] map;
    static ArrayList<Fireball> fireballs;
    static int[][] directions;

    /******* Main Program *********/
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = (ArrayList<Fireball>[][]) new ArrayList[N][N];
        fireballs = new ArrayList<>();
        directions = new int[][]{{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        // 이차원 배열 map 내 각 위치에 ArrayList<Fireball> 초기화.
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                map[row][col] = new ArrayList<>();
            }
        }

        // Fireball 입력 받기.
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            Fireball fireball = new Fireball(r, c, m, s, d); // 파이어볼 생성.
            map[r][c].add(fireball); // 파이어볼 해당 위치에 추가.
            fireballs.add(fireball); // 전체 파이어볼 집합에 추가.
        }

        // 파이어볼 이동 K번 수행.
        for(int k = 0; k < K; k++){
            castASpell(); // 마법 부리기. 즉, 이동 시키기.
        }

        // K 번 이동 실시 후 존재하는 파이어볼 질량 합 출력.
        System.out.print(getTotalMass());


    }

    /******* Fireball Class *********/
    static class Fireball {
        int row;
        int col;
        int mass;
        int speed;
        int direction;

        // 전체 필드 한 번에 인자로 받는 생성자.
        Fireball(int r, int c, int m, int s, int d){
            this.row = r;
            this.col = c;
            this.mass = m;
            this.speed = s;
            this.direction = d;
        }

        // 행/열 위치만 인자로 받아 Fireball 생성하는 생성자.
        Fireball(int r, int c){
            this.row = r;
            this.col = c;
        }
        @Override
        public boolean equals(Object o){
            if(this == o){return true;}
            if(o == null || getClass() != o.getClass()){return false;}
            Fireball f = (Fireball) o;

            return row == f.getRow() && col == f.getCol()
                    && mass == f.getMass() && speed == f.getSpeed() && direction == f.getDirection();
        }

        @Override
        public int hashCode(){
            return Objects.hash(row, col, mass, speed, direction);

        }

        int getRow(){return this.row;}
        int getCol(){return this.col;}
        int getMass(){return this.mass;}
        int getSpeed(){return this.speed;}
        int getDirection(){return this.direction;}

        void setRow(int newRow){this.row = newRow;}
        void setCol(int newCol){this.col = newCol;}
        void setMass(int m){this.mass = m;}
        void setSpeed(int s){this.speed = s;}
        void setDirection(int d){this.direction = d;}
    }


    // 현재 존재하는 파이어볼 질량 합 반환.
    private static int getTotalMass(){
        int accum = 0;
        for(Fireball fireball : fireballs){
            accum += fireball.getMass();
        }

        return accum;
    }

    private static void castASpell(){
        // 1. 현재 파이어볼 모두 각자의 d, s 만큼 이동. - DONE.
        moveEveryFireball();

        // 2. 2개 이상의 파이어볼이 존재하는 칸에서, '합치기 + 나누기' 수행.
        updateEveryFireball();

    }
    // 모든 파이어볼 각자의 방향, 속도 만큼 이동!
    private static void moveEveryFireball(){
        ArrayList<Fireball>[][] newMap = (ArrayList<Fireball>[][])new ArrayList[N][N];
        // 새로운 맵 초기화.
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                newMap[row][col] = new ArrayList<>();
            }
        }

        // 존재하는 모든 파이어볼 이동 실시.
        for(Fireball fireball : fireballs){
            int[] nextPosition = nextPos(fireball.getRow(), fireball.getCol(), fireball.getDirection(), fireball.getSpeed());
            fireball.setRow(nextPosition[0]);
            fireball.setCol(nextPosition[1]);
            newMap[nextPosition[0]][nextPosition[1]].add(fireball);
        }

        map = newMap;
    }

    // 이동 후 위치 반환.
    private static int[] nextPos(int r, int c, int d, int s){
        int newRow = calculateNext(r, s * directions[d][0]);
        int newCol = calculateNext(c, s * directions[d][1]);

        return new int[]{newRow, newCol};
    }

    // 주어진 행 혹은 열 인덱스가 특정 방향으로 s만큼 움직이고난 후의 인덱스.
    private static int calculateNext(int idx, int sWithDirection){
        if(sWithDirection < 0){
            return (N  + (idx - (Math.abs(sWithDirection) % N))) % N;
        }
        return (N + (idx + (sWithDirection % N))) % N;
    }

    // 모든 위치 탐색하며 파이어볼 갯수가 2 이상인 위치 특정 후 합치기 & 나누기 & 변경 수행.
    private static void updateEveryFireball(){
        ArrayList<Fireball> newFireballs = new ArrayList<>(); // 새로운 전체 파이어볼 리스트.

        // map 내 모든 위치에 대하여,
        for(int row = 0; row < N; row++){
            for(int col = 0; col < N; col++){
                if(map[row][col].size() >= 2){
                    map[row][col] = sumAndDivide(row, col, map[row][col]);
                }
                newFireballs.addAll(map[row][col]);
            }
        }

        fireballs = newFireballs; // 교체.
    }

    // 파이어볼 두 개 이상이 존재하는 위치에 대하여 합치기 & 나누기 & 갱신 실시.
    private static ArrayList<Fireball> sumAndDivide(int row, int col, ArrayList<Fireball> fireballsInPosition){
        int massSum = getMassSum(fireballsInPosition);
        int speedSum = getSpeedSum(fireballsInPosition);
        boolean isEveryDirectionIdentical = isDirectionsIdentical(fireballsInPosition);
        ArrayList<Fireball> newFireballsInPosition = new ArrayList<>();

        int massPerEach = (int)Math.floor((double)massSum / 5);
        int speedPerEach = (int)Math.floor((double)speedSum / fireballsInPosition.size());

        // 1. 새로운 파이어볼 생성.

        // Case A : 새로 생성될 파이어볼의 질량이 0보다 큰 경우 -> 기존 파이어볼은 없어지고, 새로운 파이어볼 네 개가 생겨난다!
        if(massPerEach > 0){
            if(isEveryDirectionIdentical){
                for(int d = 0; d < 8; d+=2){
                    Fireball newFireball = new Fireball(row, col, massPerEach, speedPerEach, d);
                    newFireballsInPosition.add(newFireball);
                }
            }
            else {
                for(int d = 1; d < 8; d+=2){
                    Fireball newFireball = new Fireball(row, col, massPerEach, speedPerEach, d);
                    newFireballsInPosition.add(newFireball);
                }
            }
        }
        // Case B : 새로 생성될 파이어볼의 질량이 0인 경우 -> 해당 위치 새로운 파이어볼이 생성되지 않는다.

        return newFireballsInPosition;
    }

    private static int getMassSum(ArrayList<Fireball> fireballsInPosition){
        int accum = 0;
        for(Fireball fireball : fireballsInPosition){
            accum += fireball.getMass();
        }

        return accum;
    }

    private static int getSpeedSum(ArrayList<Fireball> fireballsInPosition){
        int accum = 0;
        for(Fireball fireball : fireballsInPosition){
            accum += fireball.getSpeed();
        }

        return accum;
    }

    private static boolean isDirectionsIdentical(ArrayList<Fireball> fireballsInPosition){
        boolean flag = true;
        int standard = (fireballsInPosition.get(0).getDirection() % 2);

        for(int i = 1; i < fireballsInPosition.size(); i++){
            if(fireballsInPosition.get(i).getDirection() % 2 != standard){
                flag = false;
                break;
            }
        }

        return flag;
    }
}
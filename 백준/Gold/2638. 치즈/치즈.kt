import java.io.InputStreamReader
import java.io.BufferedReader
import java.util.StringTokenizer
import java.util.ArrayList
import java.util.ArrayDeque

lateinit var map: Array<BooleanArray>
var N = 0
var M = 0
val dRow = intArrayOf(-1, 1, 0, 0)
val dCol = intArrayOf(0, 0, -1, 1)

fun main() {
    val br = BufferedReader(InputStreamReader(System.`in`))
    var st = StringTokenizer(br.readLine())
    
    // 0. 필요 변수 정의 및 초기화
    N = Integer.parseInt(st.nextToken())
    M = Integer.parseInt(st.nextToken())
    map = Array(N){BooleanArray(M)}
    
    var cheeseCnt = 0
    
    // 1. 입력 받기
    for(row in 0 until N){
        st = StringTokenizer(br.readLine())
        for(col in 0 until M){
            val cur = Integer.parseInt(st.nextToken())
            if(cur == 1){
                cheeseCnt++
                map[row][col] = true
            }
        }
    }
    
    // 2. 녹지 않은 치즈가 1개라도 남아있는 동안,
    var timePassed = 0
    while(cheeseCnt > 0){
        val toBeMeltedCheese = ArrayList<IntArray>()
        val airConnectedSpaceMap = calculateAirConnectedSpace()
        
        // 2-1. 모든 위치를 살펴보다가,
        for(row in 0 until N){
            for(col in 0 until M){
                // 2-2. 해당 위치가 공기에 노출된 치즈인 경우
                if(map[row][col] && isExposedCheese(row, col, airConnectedSpaceMap)){
                    toBeMeltedCheese.add(intArrayOf(row, col)) // 이번 턴에 녹을 치즈 목록에 추가.
                }
            }
        }
        
        // 2-2. 이번 턴에 녹을 치즈 처리 && 남은 치즈 수 cnt 갱신
        for(meltingCheese in toBeMeltedCheese){
            map[meltingCheese[0]][meltingCheese[1]] = false
            cheeseCnt--
        }
        
        // 2-3 시간 경과 처리
        timePassed++
    }
    
    print(timePassed)
}

// 해당 치즈 위치의 공기 노출 여부 검증
fun isExposedCheese(row: Int, col: Int, airConnectedSpaceMap: Array<BooleanArray>): Boolean {
    
    var exposedCnt = 0 // 공기에 노출된 면 수.
    
    // 1) 해당 치즈와 상하좌우 인접한 빈 공간에 대해,
    for(d in 0 until 4){
        // 치즈는 벽과 최소 1칸 거리를 두고 존재하므로, adjRow 와 adjCol 에 대해선 Index Bound 검증 불필요.
        val adjRow = row + dRow[d]
        val adjCol = col + dCol[d]
        
        // 공기와 접촉 가능한 빈 공간인 경우 exposedCnt++
        if(!map[adjRow][adjCol] && airConnectedSpaceMap[adjRow][adjCol]){
            exposedCnt++
        }
        if(exposedCnt == 2){break}
    }
    
    // 3) 해당 구간의 수가 2를 넘으면 해당 치즈는 녹을 예정
    return exposedCnt == 2
}

// 주어진 빈 공간의 공기 연결 여부 검증 : BFS
fun calculateAirConnectedSpace(): Array<BooleanArray> {
    
    val startRow = 0
    val startCol = 0
    
    val airConnectedSpaceMap = Array(N){BooleanArray(M)}
    val visited = Array(N){BooleanArray(M)}
    val q = ArrayDeque<IntArray>()
    
    q.offer(intArrayOf(startRow, startCol))
    visited[startRow][startCol] = true
    airConnectedSpaceMap[startRow][startCol] = true
    
    while(q.isNotEmpty()){
        val current = q.poll()
        val currentRow = current[0]
        val currentCol = current[1]
        
        for(d in 0 until 4){
            val adjRow = currentRow + dRow[d]
            val adjCol = currentCol + dCol[d]
            
            // 바운더리 내에 존재하고, 인접하였으며, 아직 미방문 상태인 빈 공간에 대해
            if(isAvailable(adjRow, adjCol) && !map[adjRow][adjCol] && !visited[adjRow][adjCol]){
                q.offer(intArrayOf(adjRow, adjCol))
                airConnectedSpaceMap[adjRow][adjCol] = true
                visited[adjRow][adjCol] = true
            }
        }
    }
    
    return airConnectedSpaceMap
}

fun isAvailable(row: Int, col: Int): Boolean {
    return row >= 0 && row < N && col >= 0 && col < M
}

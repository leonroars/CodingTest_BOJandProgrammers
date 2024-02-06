import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

// 접근
// - 철저하게 정석적인 접근으로 풀이하였다.
// - 하지만 입력으로 주어지는 노드 시퀀스가 해당 노드로 구성된 이진탐색트리를 '전위순회'한 결과라고 하였으므로,
//   전위순회의 특성에 착안한 풀이도 존재할 수 있겠다.
// - 입력되는 노드의 값이 10^6이므로 안심하고 int 타입을 노드의 값 타입으로 사용한다.
// - 노드의 개수가 최대 10만 개 까지 입력되는 문제인데, 이진검색트리의 경우 삽입과 삭제연산의 시간/공간복잡도 모두 
//   완성된 트리의 높이에 비례한다.
//   분할상환분석법(Amortized Analysis)에 따라 이진검색트리의 '평균적' 시간/공간복잡도는 O(log n)으로 표기 가능하지만,
//   이진검색트리의 '입력 순서 의존성'에 따라 최악의 경우 이진검색트리가 Degenerate 트리 형태가 된다면 시간/공간복잡도는 모두 O(n)이 된다.
//   자바의 시스템 스택 크기는 일반적으로 10,000개의 스택프레임을 저장할 수 있도록 설정되어있다고 하므로,
//   재귀를 사용하기보단 반복을 사용하는 것이 안전하나, 극히 드문 확률로 O(n)의 복잡도를 가지므로
//   재귀를 사용하는 것을 선택했다. 스택오버플로우가 뜨면 반복으로 바꿔줄 생각이었으나 문제없이 해결되었다.

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BST bst = new BST();
        while(true){
            String input = br.readLine();
            if(input == null || input.isEmpty()){break;}
            bst.insert(Integer.parseInt(input));
        }
        System.out.println(bst.postOrder());
    }
    
    // 이진탐색트리 구현부
    static class BST{
        private Node root;

        public BST(){
            this.root = null;
        }

        public void insert(int key){
            root = insert(root, key);
        }
        private Node insert(Node x, int key){
            if(x == null){return new Node(key);}
            if(key < x.key){x.left = insert(x.left, key);}
            if(key > x.key){x.right = insert(x.right, key);}
            return x;
        }

        // 후위순회 : 클라이언트 인터페이스
        public String postOrder(){
            StringBuilder sb = new StringBuilder();
            postOrder(root, sb);
            return sb.toString();
        }
        
        // 후위순회 : 실제 재귀 함수부. StringBuildler를 클라이언트 인터페이스로부터 인자로 넘겨받는 것에 주목.
        // 그렇게 하지 않고 이 메서드 내부에 StringBuilder를 인스턴스화 할 경우,
        // 매 재귀호출마다 새로운 StringBuilder가 초기화/인스턴스화 되므로 원하는 결과가 도출되지 않는다.
        private void postOrder(Node x, StringBuilder sb){
            if(x != null){
                postOrder(x.left, sb);
                postOrder(x.right, sb);
                sb.append(x);
                sb.append("\n");
            }
        }
        // 노드 구현부
        static class Node{
            private int key;
            private Node left;
            private Node right;

            Node(int key){
                this.key = key;
                this.left = null;
                this.right = null;
            }
            Node(int key, Node left, Node right){
                this.key = key;
                this.left = left;
                this.right = right;
            }

            public String toString(){
                return Integer.toString(this.key);
            }
        }
    }
}
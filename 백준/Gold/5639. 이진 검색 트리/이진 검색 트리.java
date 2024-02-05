import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

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

        public String postOrder(){
            StringBuilder sb = new StringBuilder();
            postOrder(root, sb);
            return sb.toString();
        }
        private void postOrder(Node x, StringBuilder sb){
            if(x != null){
                postOrder(x.left, sb);
                postOrder(x.right, sb);
                sb.append(x);
                sb.append("\n");
            }
        }

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
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.BitSet;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        BinaryTree bt = new BinaryTree(N);

        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String parent = st.nextToken();
            String left = st.nextToken();
            String right = st.nextToken();

            bt.insert(parent, left, right);
        }

        System.out.println(bt.preOrder());
        System.out.println(bt.inOrder());
        System.out.println(bt.postOrder());

    }

    static class BinaryTree{
        private Node[] TreeNodes;
        private Node root;

        BinaryTree(int size){
            root = new Node("A");
            TreeNodes = new Node[size];
            for(int i = 0; i < size; i++){TreeNodes[i] = null;}
            TreeNodes[0] = root;
        }

        Node getNode(String key){
            if(!key.equals(".")){
                return TreeNodes[(int)key.charAt(0) - 65];
            }
            return null;
        }

//        Node getNode(Node x){
//
//        }

        void insert(String key, String left, String right){
            if(getNode(key) == null){TreeNodes[(int)key.charAt(0) - 65] = new Node(key);}
            if(getNode(left) == null && !left.equals(".")){TreeNodes[(int)left.charAt(0) - 65] = new Node(left);}
            if(getNode(right) == null && !right.equals(".")){TreeNodes[(int)right.charAt(0) - 65] = new Node(right);}
            Node parent = getNode(key);
            parent.left = getNode(left);
            parent.right = getNode(right);
        }

        // In-Order : LNR.
        public String inOrder(){
            StringBuilder sb = new StringBuilder();
            inOrder(root, sb);
            return sb.toString();
        }
        private void inOrder(Node x, StringBuilder sb){
            if(x != null){
                inOrder(x.left, sb);
                sb.append(x);
                inOrder(x.right, sb);
            }
        }

        // Post-Order : LRN
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
            }
        }


        // Pre-Order : NLR
        public String preOrder(){
            StringBuilder sb = new StringBuilder();
            preOrder(root, sb);
            return sb.toString();
        }

        private void preOrder(Node x, StringBuilder sb){
            if(x != null){
                sb.append(x);
                preOrder(x.left, sb);
                preOrder(x.right, sb);
            }
        }


        static class Node{
            private String key;
            private Node left;
            private Node right;

            Node(String key){
                if(!key.equals(".")){
                    this.key = key;
                    this.left = null;
                    this.right = null;
                }
            }

            public String toString(){
                return this.key;
            }
        }
    }
}
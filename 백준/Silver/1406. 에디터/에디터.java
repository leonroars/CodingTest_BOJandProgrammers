import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

import java.util.LinkedList;
import java.util.ListIterator;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String given = br.readLine();
        Editor editor = new Editor(given);
        
        int numberOfOps = Integer.parseInt(br.readLine());
        
        for(int i = 0; i < numberOfOps; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            String op = st.nextToken();
            
            if(st.hasMoreTokens()){
                String target = st.nextToken();
                editor.P(target);
            }
            else {
                if(op.equals("L")){editor.L();}
                else if(op.equals("D")){editor.D();}
                else{editor.B();}
            }
        }
        
        System.out.print(editor.printResult());
    }
    
    static class Editor {
        String str;
        LinkedList<Character> parsed;
        ListIterator<Character> iter;
        
        public Editor(String given){
            this.str = given;
            this.parsed = new LinkedList<>();
            for(int i = 0; i < given.length(); i++){
                parsed.add(given.charAt(i));
            }
            this.iter = this.parsed.listIterator(parsed.size());
        }
        
        public void L(){
            if(iter.hasPrevious()){iter.previous();}
        }
        
        public void D(){
            if(iter.hasNext()){iter.next();}
        }
        
        public void B(){
            if(iter.hasPrevious()){
                iter.previous();
                iter.remove();
            }
        }
        
        public void P(String target){
            iter.add(target.charAt(0));
        }
        
        public String printResult(){
            StringBuilder answer = new StringBuilder();
            
            // 커서 맨 앞으로 이동. 한 번에 앞으로 가는 법은 없을까?
            while(iter.hasPrevious()){iter.previous();}
            
            while(iter.hasNext()){
                answer.append(iter.next());
            }
            
            return answer.toString();
        }
        
        
    }
}
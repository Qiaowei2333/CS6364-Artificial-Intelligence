package rosettacode;

import java.util.*;

public class Sokoban1{////////////////////////////this is DFS
    String destBoard, currBoard;
    int playerX, playerY, nCols, nRows;
    int nExps=0;// nodes expanded
    LinkedList<String[][]> path = new LinkedList<>();
    
   public String[][] Convert2StringArray (String a) {
    	String[] aa = a.split("");
    	 String[][] matrix = new String[nRows][nCols];
         for (int r = 0; r < nRows; r++) {
             for (int c = 0; c < nCols; c++) {
                 matrix[r][c] = aa[r*nCols+c];
             }
         }
    	return matrix;
    }

   Sokoban1(String[] board) {
        nCols = board[0].length();
        nRows = board.length;
        String[][] matrix = new String[nRows][nCols];
        StringBuilder destBuf = new StringBuilder();
        StringBuilder currBuf = new StringBuilder();
 
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
            	//matrix[r][c] = board[r*nCols+c];
                char ch = board[r].charAt(c);
                destBuf.append(ch != '$' && ch != '@' ? ch : ' ');
                currBuf.append(ch != '.' ? ch : ' ');
 
                if (ch == '@') {
                    this.playerX = c;
                    this.playerY = r;
                } 
            }
        }
        //path.add(matrix);
        destBoard = destBuf.toString();
        //System.out.println(destBoard);
           currBoard = currBuf.toString();
    }
 
    String move(int x, int y, int dx, int dy, String trialBoard) {
 
        int newPlayerPos = (y + dy) * nCols + x + dx;
 
        if (trialBoard.charAt(newPlayerPos) != ' ')
            return null;
 
        char[] trial = trialBoard.toCharArray();
        trial[y * nCols + x] = ' ';
        trial[newPlayerPos] = '@';
 
        return new String(trial);
    }
 
    String push(int x, int y, int dx, int dy, String trialBoard) {
 
        int newBoxPos = (y + 2 * dy) * nCols + x + 2 * dx;
 
        if (trialBoard.charAt(newBoxPos) != ' ')
            return null;
 
        char[] trial = trialBoard.toCharArray();
        trial[y * nCols + x] = ' ';
        trial[(y + dy) * nCols + x + dx] = '@';
        trial[newBoxPos] = '$';
 
        return new String(trial);
    }
 
    boolean isSolved(String trialBoard) {
        for (int i = 0; i < trialBoard.length(); i++)
            if ((destBoard.charAt(i) == '.')
                    != (trialBoard.charAt(i) == '$'))
                return false;
        return true;
    }
 
    String solve() {
        class Board {
            String cur, sol;
            int x, y;
 
            Board(String s1, String s2, int px, int py) {
                cur = s1;
                sol = s2;
                x = px;
                y = py;
            }
        }
        char[][] dirLabels = {{'u', 'U'}, {'r', 'R'}, {'d', 'D'}, {'l', 'L'}};
        int[][] dirs = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
//        char[][] dirLabels = {{'r', 'R'}, {'d', 'D'}, {'l', 'L'},{'u', 'U'}};
//        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0},{0, -1}};    
//        char[][] dirLabels = {{'d', 'D'}, {'l', 'L'},{'u', 'U'},{'r', 'R'}};
//        int[][] dirs = {{0, 1}, {-1, 0},{0, -1},{1, 0}};
//        char[][] dirLabels = {{'l', 'L'},{'u', 'U'},{'r', 'R'},{'d', 'D'}};
//        int[][] dirs = {{-1, 0},{0, -1},{1, 0},{0, 1}};
        Set<String> history = new HashSet<>();
        //LinkedList<Board> open = new LinkedList<>();
        Stack<Board> open = new Stack<>();
        
 
        //history.add(currBoard);
        //path.add(Convert2StringArray(currBoard));
        open.push(new Board(currBoard, "", playerX, playerY));
 
        while (!open.isEmpty()) {
            Board item = open.pop();
            String cur = item.cur;
            String sol = item.sol;
            int x = item.x;
            int y = item.y;
          if (!history.contains(cur)) {
        	  nExps++;
        	  history.add(cur);
           // path.add(Convert2StringArray(cur));
            //View2 view2 = new View2(Convert2StringArray(cur));
           // view2.setVisible(true);
            for (int i = 0; i < dirs.length; i++) {
                String trial = cur;
                int dx = dirs[i][0];
                int dy = dirs[i][1];
 
                // are we standing next to a box ?
                if (trial.charAt((y + dy) * nCols + x + dx) == '$') {
 
                    // can we push it ?
                    if ((trial = push(x, y, dx, dy, trial)) != null) {
 
                        // or did we already try this one ?
                    	
                            String newSol = sol + dirLabels[i][1];
 
                            if (isSolved(trial))
                            {	//path.add(Convert2StringArray(trial));
                            //View2 view12 = new View2(Convert2StringArray(cur));
                            //view12.setVisible(true);
                                return newSol;
                            }
                            open.push(new Board(trial, newSol, x + dx, y + dy));
                            //path.add(Convert2StringArray(trial));
                            //View2 view3 = new View2(Convert2StringArray(trial));
                            //view3.setVisible(true);
                    }
 
                // otherwise try changing position
                } else if ((trial = move(x, y, dx, dy, trial)) != null) {
 
                    if (!history.contains(trial)) {
                        String newSol = sol + dirLabels[i][0];
                        open.push(new Board(trial, newSol, x + dx, y + dy));
                        //path.add(Convert2StringArray(trial));
                        //View2 view4 = new View2(Convert2StringArray(trial));
                        //view4.setVisible(true);
                    }
                }
            }// end for
           }// end if
        }
        return "No solution";
    }
    public void Action2StringArray(String action, String cur){
    	int x = playerX;
    	int y = playerY;
    	String cur1=cur;
    	for (int i=0; i<action.length(); i++){
    		switch(action.charAt(i)){
    		case'u': cur1=move(x,y,0,-1,cur1);y=y-1;path.add(Convert2StringArray(cur1));break;
    		case'r': cur1=move(x,y,1,0,cur1);x=x+1;path.add(Convert2StringArray(cur1));break;
    		case'd': cur1=move(x,y,0,1,cur1);y=y+1;path.add(Convert2StringArray(cur1));break;
    		case'l': cur1=move(x,y,-1,0,cur1);x=x-1;path.add(Convert2StringArray(cur1));break;
    		case'U': cur1=push(x,y,0,-1,cur1);y=y-1;path.add(Convert2StringArray(cur1));break;
    		case'R': cur1=push(x,y,1,0,cur1);x=x+1;path.add(Convert2StringArray(cur1));break;
    		case'D': cur1=push(x,y,0,1,cur1);y=y+1;path.add(Convert2StringArray(cur1));break;
    		case'L': cur1=push(x,y,-1,0,cur1);x=x-1;path.add(Convert2StringArray(cur1));break;    		
    		}
    		//System.out.println(action.charAt(i));
    	}
    }
 
    public static void main(String[] a) {
        String level = "#######,#     #,#     #,#. #  #,#. $$ #,"
                + "#.$$  #,#.#  @#,#######";
        String [][] maze1 = new String [][]
                { {"#","#","#","#","#","#","#"},
            	  {"#"," "," "," "," "," ","#"},
            	  {"#"," "," "," "," "," ","#"},
            	  {"#","."," ","#"," "," ","#"},
            	  {"#","."," ","$","$"," ","#"},
            	  {"#",".","$","$"," "," ","#"},
            	  {"#",".","#"," "," ","@","#"},
            	  {"#","#","#","#","#","#","#"},
            	};
       // Printmap(level.split(","));
       // System.out.println(level);
       Sokoban1 soko = new Sokoban1(level.split(","));
       soko.path.add(maze1);
       String action = soko.solve();
       System.out.println(soko.nExps);
        System.out.println(action);
       // action="ulULLulDDurrrddlULrruLLrrUruLLLulD";
        soko.Action2StringArray(action,soko.currBoard);
        int length = soko.path.size();
        //String [] path1 = new String[length];
        //System.out.println(Arrays.deepToString(soko.path.getLast()));
        View1 view = new View1(soko.path);
        view.setVisible(true);
    }
}

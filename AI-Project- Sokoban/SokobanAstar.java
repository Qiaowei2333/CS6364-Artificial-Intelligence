package rosettacode;

import java.util.*;
/////////////////////////////////// this is A*BFS
public class SokobanAstar {
	
    String destBoard, currBoard;
    int playerX, playerY, nCols, nRows; 
    LinkedList<Coordinate> BC = new LinkedList<>(); // coordinates for box position
    LinkedList<Coordinate> GC = new LinkedList<>(); // coordinates for goal position
    LinkedList<Coordinate> BC1 = new LinkedList<>(); // this is the box position after 匈牙利算法 和 之前的box一一对应
    int nExps=0;// nodes expanded
    LinkedList<String[][]> path = new LinkedList<>();// 存放每一步的图，都是sring[][]形式，因为出图的函数要求这样的输入格式
    
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

    SokobanAstar(String[] board) {
        nCols = board[0].length();
        nRows = board.length;
        String[][] matrix = new String[nRows][nCols];
        StringBuilder destBuf = new StringBuilder();
        StringBuilder currBuf = new StringBuilder();
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
            	//matrix[r][c] = board[r*nCols+c];
                char ch = board[r].charAt(c);
                if (ch == '$') {
                	this.BC.add(new Coordinate(c,r)) ; // get the coordinates of boxes
                }
                if (ch == '.') {
                	this.GC.add(new Coordinate(c,r)) ; // get the coordinates of goals
                }
                if (ch == '@') {
                    this.playerX = c;
                    this.playerY = r;
                } 
                destBuf.append(ch != '$' && ch != '@' ? ch : ' ');
                currBuf.append(ch != '.' ? ch : ' ');
            }
        }
 /// 这里假设经过了匈牙利算法的调整，得到了 与  goal 一一对应的 四个box坐标     
        BC1.add(new Coordinate(4,4));
        BC1.add(new Coordinate(3,5));
        BC1.add(new Coordinate(2,5));
        BC1.add(new Coordinate(3,4));
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
    
	private int manhattan(Coordinate c1, Coordinate c2) {
		return Math.abs(c1.x-c2.x) + Math.abs(c1.y-c2.y);
	}
    
    private int getDistance(String sol, LinkedList<Coordinate> B, LinkedList<Coordinate> G, int x, int y){
	     int a = 0;
	     // 不要乱用poll(), refernce var 指向同一个object
	     for(int i=0;i<B.size();i++) {
	    	 a = a + Math.abs(x-B.get(i).x) + Math.abs(y-B.get(i).y);
	    	 a = a + manhattan(B.get(i),G.get(i));
	     }
    	return a+sol.length();
   }
    
    public class Board {
        String cur, sol;
        int x, y;
        LinkedList<Coordinate> B;
        LinkedList<Coordinate> G;
        int f;// f = g + h   smallest f to decide which is the next board

        Board(String s1, String s2, int px, int py, LinkedList<Coordinate> box, LinkedList<Coordinate> goal) {
            cur = s1;
            sol = s2;
            x = px;
            y = py;
            B = box;
            G = goal;
            f = getDistance(s2, box, goal, x, y);
          //  System.out.println(f);
        }
    }
    
    class DistanceCompare implements Comparator<Board> {
    	public int compare(Board one, Board two) {
    	return one.f-two.f;
    	}
    }
    
    String solve() {
        char[][] dirLabels = {{'u', 'U'}, {'r', 'R'}, {'d', 'D'}, {'l', 'L'}};
        int[][] dirs = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};
    // use priorityqueue to find the smallest f value    
		DistanceCompare cmp1 = new DistanceCompare();
		PriorityQueue<Board> q2 = new PriorityQueue<Board>(cmp1);
        q2.add(new Board(currBoard, "", playerX, playerY , BC1, GC));
        path.add(Convert2StringArray(currBoard));// add the first maze without goals only boxes and player
        Set<String> history = new HashSet<>();
        
        while (!q2.isEmpty()) {
            nExps++;// nodes expanded
            Board item = q2.poll();
            String cur = item.cur;
            String sol = item.sol;
            int x = item.x;
            int y = item.y;
           // View2 view2 = new View2(Convert2StringArray(cur));
          //  view2.setVisible(true);
            for (int i = 0; i < dirs.length; i++) {
                String trial = cur;
                int dx = dirs[i][0];
                int dy = dirs[i][1];
 
                // are we standing next to a box ?
                if (trial.charAt((y + dy) * nCols + x + dx) == '$') {
 
                    // can we push it ?
                    if ((trial = push(x, y, dx, dy, trial)) != null) {
                    	    
                    	// or we already push it
                    	if (!history.contains(trial)) {
                            String newSol = sol + dirLabels[i][1];// add the step to Sol
                            
                            if (isSolved(trial))
                            {	
                                return newSol;  // if it is solved, return the Sol
                            }
                            // find the box be pushed then renew its position
                        
                            ListIterator<Coordinate> iter = BC1.listIterator();
                            while(iter.hasNext()){
                            	  Coordinate temp = (Coordinate) iter.next();
                            	 if ( temp.x==(x+dx) & temp.y==(y+dy) ) { // find the Coordinate which has been pushed
                            	   iter.set(new Coordinate(x+2*dx,y+2*dy));// replace the Coordinate with a new one
                            	   break;
                            	 }//if
                            } // while
                            q2.add(new Board(trial, newSol, x + dx, y + dy, BC1, GC));
                            history.add(trial);
                    	}// if his  
                    }// if trial
                }// if standing
                // otherwise try changing position
                 else if ((trial = move(x, y, dx, dy, trial)) != null) {
                	 if (!history.contains(trial)) {
                        String newSol = sol + dirLabels[i][0];
                        q2.add(new Board(trial, newSol, x + dx, y + dy, BC1, GC));// when you move, add a new board in queue
                        history.add(trial);
                	 }
                }
            }// for
        }// while
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
       SokobanAstar soko = new SokobanAstar(level.split(","));
       soko.path.add(maze1);// initialize the first original maze
       String action = soko.solve();
       System.out.println(soko.nExps);
        System.out.println(action);
        //action="ulULLulDDurrrddlULrruLLrrUruLLLulD";
        soko.Action2StringArray(action,soko.currBoard);
        int length = soko.path.size();
        //String [] path1 = new String[length];
        //System.out.println(Arrays.deepToString(soko.path.getLast()));
        View1 view = new View1(soko.path);
        view.setVisible(true);
    }
}


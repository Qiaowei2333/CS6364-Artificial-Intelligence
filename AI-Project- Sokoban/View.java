package rosettacode;

import java.awt.*;
import javax.swing.*;

public class View extends JFrame{
	//use two dimensional array to represent the maze
	//        String level = "#######,#     #,#     #,#. #  #,#. $$ #,"+"#.$$  #,#.#  @#,#######";
	
	//maze
    private int [][] maze = 
        { {1,1,1,1,1,1,1,1,1,1,1,1,1},
          {1,0,1,0,1,0,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,1,0,1,1,1,0,1},
          {1,0,0,0,1,1,1,0,0,0,0,0,1},
          {1,0,1,0,0,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,0,0,1},
          {1,0,1,0,1,0,0,0,1,1,1,0,1},
          {1,0,1,0,1,1,1,0,1,0,1,0,1},
          {1,0,0,0,0,0,0,0,0,0,1,9,1},// target at 9, pos(11,8)
          {1,1,1,1,1,1,1,1,1,1,1,1,1}
        };
    private String [][] maze1 = new String [][]
        { {"#","#","#","#","#","#","#"},
    	  {"#"," "," "," "," "," ","#"},
    	  {"#"," "," "," "," "," ","#"},
    	  {"#","."," ","#"," "," ","#"},
    	  {"#","."," ","$","$"," ","#"},
    	  {"#",".","$","$"," "," ","#"},
    	  {"#",".","#"," "," ","@","#"},
    	  {"#","#","#","#","#","#","#"},
    	};
    
    
    
	public View(){
        setTitle("Simple Maze Solver");
        //setSize(640, 480);
        setSize(700, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.translate(100, 100);// change original coordinates
        
        // draw the maze
        for (int row = 0; row < maze1.length; row++) {
            for (int col = 0; col < maze1[0].length; col++) {
                Color color;
                switch (maze1[row][col]) {
                    case "#" : color = Color.BLACK; break;
                    case "." : color = Color.RED; break;
                    case "@" : color = Color.BLUE; break;
                    case "$"  : color = Color.ORANGE; break;
                    default : color = Color.WHITE;
                }
                //System.out.println(row+""+col);
                g.setColor(color);
                g.fillRect(50 * col, 50 * row, 50, 50);// fill the graph with different colors
                g.setColor(Color.BLACK);
                g.drawRect(50 * col, 50 * row, 50, 50);// draw the grid
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View();
                view.setVisible(true);
            }
        });
    }
}
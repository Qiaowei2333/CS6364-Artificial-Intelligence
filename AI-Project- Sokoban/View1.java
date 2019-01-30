package rosettacode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class View1 extends JFrame {
	LinkedList<String[][]> path1;
	String[][] maze;
	int length;//36  index 35
	int index;
    public View1(LinkedList<String[][]> path) {
        setTitle("Sokoban Solver");
        setSize(700, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        path1=path;
        length=path.size();
        index=0;//0-35
    }
    
	@Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.translate(100, 100);// change original coordinates
        maze = path1.get(index);
        // draw the maze
        for (int row = 0; row < maze.length; row++) {
            for (int col = 0; col < maze[0].length; col++) {
                Color color;
                switch (maze[row][col]) {
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
	
    @Override
    protected void processKeyEvent(KeyEvent ke) {
        if (ke.getID() != KeyEvent.KEY_PRESSED) {
            return;
        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            index++;
            if (index > length-1) {
                index = length-1;
            }
        }
        else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            index--;
            if (index < 0) {
                index = 0;
            }
        }
        repaint(); 
    }

}

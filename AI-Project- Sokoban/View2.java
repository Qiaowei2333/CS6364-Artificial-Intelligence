package rosettacode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class View2 extends JFrame {
	String[][] maze;
	
    public View2(String[][] path) {
        setTitle("Sokoban Solver");
        setSize(700, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        maze = path;
    }
    
	public View2() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public void paint(Graphics g) {
        super.paint(g);
        
        g.translate(100, 100);// change original coordinates
        
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

}


package assign2;


import javax.swing.*;
//import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Draw extends JPanel{

    private WorldPanel panel1;
    private JFrame frame;
  
  /**Makes and names the frame, sets default close operation, gets the contentpane from the class TrafficLightPanel, sets size to automatically fit objects, sets visibility of the frame*/
  public static void main(String[] args){
   // Draw t = new Draw(200, 300);
  }
  
  public Draw(int x, int y, int sE, Monster[] mon, Creature[] creatures, int[][] berryGrid, int[][] mushroomGrid){
    frame = new JFrame("Evolve"); //names frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets close method
    panel1 = new WorldPanel(x, y, sE, mon, creatures, berryGrid, mushroomGrid);
    setPreferredSize (new Dimension(10*x,10*y)); //sets size and colour of the window
    setBackground(Color.blue);
    frame.add(panel1);
    frame.pack(); //auto size
    frame.setVisible(true);
  }

    public void close(){
	frame.dispose();
    }

    public void update(Monster[] mon, Creature[] creatures, int[][] berryGrid, int[][] mushroomGrid){
	panel1.update(mon, creatures, berryGrid, mushroomGrid);
    }
  
  private class WorldPanel extends JPanel{
    /**Constructor for LightPanel. Sets background colour for the panel, and sets its size*/
    
    private Monster[] mon;
    private Creature[] creatures;
    private int[][] berryGrid;
    private int[][] mushroomGrid;
    private int sE;
    
    public WorldPanel(int x, int y, int sE, Monster[] mon, Creature[] creatures, int[][] berryGrid, int[][] mushroomGrid){
      setPreferredSize(new Dimension(3*x,3*y));
      setBackground(Color.white);
      this.mon = mon;
      this.creatures = creatures;
      this.berryGrid = berryGrid;
      this.mushroomGrid = mushroomGrid;
      this.sE = sE;
    }
    
    public void update(Monster[] mon, Creature[] creatures, int[][] berryGrid, int[][] mushroomGrid){
      this.mon = mon;
      this.creatures = creatures;
      this.berryGrid = berryGrid;
      this.mushroomGrid = mushroomGrid;
      repaint();
      try{
	  Thread.sleep(10);
      }catch(Exception e){
      }
    }
    /** makes a JPanel with 3 circles which change colour based off the button presses*/
    public void paintComponent(Graphics g){
      int[] pos;
      super.paintComponent(g);
      for(int i = 0; i < creatures.length; i++){
        if(creatures[i].isAlive()){
          pos = creatures[i].getPosition();
          if(creatures[i].getEnergy() > sE){
            g.setColor(Color.green);
          }
          else if(creatures[i].getEnergy() > sE/2 + 1){
            g.setColor(Color.magenta);
          }
          else{
            g.setColor(Color.blue);
          }
          g.fillRect(3*pos[0], 3*pos[1], 3, 3);
        }
      }
      for(int i = 0; i < mon.length; i++){
        pos = mon[i].getPosition();
        g.setColor(Color.black);
        g.fillRect(3*pos[0], 3*pos[1], 3, 3);
      }
       for(int i = 0; i < berryGrid.length; i++){
        for(int j = 0; j < berryGrid[i].length; j++){
          g.setColor(Color.red);
	  if(berryGrid[i][j] > 0){
	      g.fillOval(3*i, 3*j, 3, 3);
	  }
        }
      }
      for(int i = 0; i < mushroomGrid.length; i++){
	  for(int j = 0; j < mushroomGrid[i].length; j++){
          g.setColor(Color.cyan);
	  if(mushroomGrid[i][j] > 0){
	      g.fillOval(3*i, 3*j, 3, 3);
	  }
        }
      }
    }
  }
  
  
  
}


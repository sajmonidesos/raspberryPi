
/**
 * Write a description of class FirefoxGame here.
 * 
 * @author Szymon Grocholski
 * @version (a version number or a date)
 */

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPortException;


import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.TexturePaint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Transparency;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


import java.util.ArrayList;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class FirefoxGame extends Canvas implements Stage,KeyListener{
   
  
   public long usedTime;
   public BufferStrategy strategy;
   private SpriteCache spriteCache;
   private ArrayList actorList;
   private Player player;
   
   private BufferedImage spaceBackground, backgroundTile;
   private int backgroundY;
   
   SerialReader serialReader;
   
   public FirefoxGame()
    {
     spriteCache = new SpriteCache();
     JFrame frame = new JFrame("Firefox Game");
     JPanel panel = (JPanel)frame.getContentPane(); 
     setBounds(0,0,Stage.width,Stage.height);
     panel.setPreferredSize(new Dimension(Stage.width,Stage.height));
     panel.setLayout(null);
     panel.add(this);
     
     frame.setBounds(0,0,Stage.width,Stage.height);
     frame.setVisible(true);
     frame.addWindowListener(new WindowAdapter() {
         public void windowClosing (WindowEvent e){
             serialReader.stop();
             System.exit(0);
         }   
        });
     
    createBufferStrategy(2);
    strategy = getBufferStrategy();
    requestFocus();
    addKeyListener(this);
    
   
    }
    
   public void initWorld(){
       actorList = new ArrayList();
       for (int i = 0; i<10 ; i++ ){
            Monster monster = new Monster(this);
            monster.setPosX((int)(Math.random()*Stage.width));
            monster.setPosY(i*20);
            monster.setVx((int)(Math.random()*3) + 1);
            actorList.add(monster);
        }
       player = new Player(this);
       player.setPosX(Stage.width/2);
       player.setPosY(Stage.height - 200);
       // paint space
       backgroundTile = spriteCache.getSprite("space");
       spaceBackground = spriteCache.createCompatible(
                            Stage.width,
                            Stage.height + backgroundTile.getHeight(),
                            Transparency.OPAQUE);
       Graphics2D g = (Graphics2D)spaceBackground.getGraphics();
       g.setPaint(new TexturePaint(backgroundTile, new Rectangle(0,0,spaceBackground.getWidth(),spaceBackground.getHeight())));
       g.fillRect(0,0,spaceBackground.getWidth(),spaceBackground.getHeight());
       backgroundY = backgroundTile.getHeight();
      
       try{
       
           serialReader = new SerialReader();
        }catch(InterruptedException e){
        
            System.out.println("Serial Exception"+e.getMessage());
        }
        
         serialReader.serial.addListener(new SerialDataListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {
                // print out the data received to the console
                //System.out.print(event.getData());
                String line;
                line = serialReader.buildLine(event.getData());
                if (!line.equals("")){
                    
                    player.serialDirectionUpdate(line);
                
                }
                
                
                
            }            
        });
    
       
    }
   public void paintWorld(){
      Graphics2D g = (Graphics2D)strategy.getDrawGraphics();
      g.drawImage( spaceBackground,
                    0,0,Stage.width,Stage.height,
                    0,backgroundY,Stage.width,backgroundY + Stage.height,this);
      
       
      // g.setColor(Color.gray);
      // g.fillRect(0,0,getWidth(),getHeight());
       
       for(int i = 0; i<actorList.size(); i++){
           Actor actor = (Actor)actorList.get(i);
           actor.paint(g);
       }
       player.paint(g);
       g.setColor(Color.white);
       //sensor.getString();
       // g.drawString("Czujnik: "+player.sensorDirectionUpdate(),50,height-100);
       /*try{
           g.drawString("Czujnik: "+sensor.getData(),50,height-100);
        }
       catch (InterruptedException e){
           System.out.println("błąd czunika");
        }*/
           if(usedTime> 0 ){
           g.drawString(String.valueOf(1000/usedTime)+"fps",5,height-50);
        }
        else g.drawString("---fps",5,height-50);
       strategy.show();
    }
   public void updateWorld(){
       for(int i = 0; i<actorList.size(); i++){
           Actor actor = (Actor)actorList.get(i);
           actor.act();
       }
       player.act();
   }
   public SpriteCache getSpriteCache(){ 
       return spriteCache;
    
    } 
   public void game(){
       usedTime = 1000;
      
       initWorld();
       while(isVisible()){
           
           long startTime = System.currentTimeMillis();
           backgroundY--;
           if (backgroundY <0) backgroundY = backgroundTile.getHeight();
           updateWorld();
           paintWorld();
           usedTime = System.currentTimeMillis() - startTime;
           try{
           Thread.sleep(Stage.WAIT);
        }catch(InterruptedException e){System.out.println("Sleep exception");}
        
        }
   }
   public static void main(String [] args){
       
       FirefoxGame game = new FirefoxGame();
       game.game();
   }
    
   public void keyPressed(KeyEvent e){
        player.keyPressed(e);
   }
   public void keyReleased(KeyEvent e){
      player.keyReleased(e);
   }
   public void keyTyped(KeyEvent e){}
    
}

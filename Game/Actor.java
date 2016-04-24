import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Actor
{
 
   protected int posX,posY;
   protected int imageWidth,imageHeight;
   protected String spriteName;
   protected Stage stage;
   protected SpriteCache spriteCache; 
   
    public Actor(Stage stage)
    {
        this.stage = stage;
        spriteCache = stage.getSpriteCache();
        
    }
    public void paint(Graphics2D g){
    
        g.drawImage(spriteCache.getSprite(spriteName),posX,posY,stage);
    }
    public int getPosX(){
        return posX;
    }
    public int getPosY(){
        return posY;
    }
    public void setPosX(int newPosX){
        posX = newPosX;
    }
    public void setPosY(int newPosY){
        posY = newPosY;
    }
    public String getSpriteName(){
        return spriteName;
    }
    public void setSpriteName(String spriteName){
        this.spriteName = spriteName;
        BufferedImage image = spriteCache.getSprite(spriteName);
        this.imageHeight = image.getHeight();
        this.imageWidth = image.getWidth();
        
    }
    public void act(){
    }
}

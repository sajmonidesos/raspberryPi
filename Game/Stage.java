import java.awt.image.ImageObserver;
/**
 * Write a description of class Stage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Stage extends ImageObserver
{
   public static final int width = 800;
   public static final int height = 600;
   public static int WAIT = 60;
   public SpriteCache getSpriteCache();

    
  
}
